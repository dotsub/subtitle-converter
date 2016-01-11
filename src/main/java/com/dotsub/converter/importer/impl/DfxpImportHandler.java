package com.dotsub.converter.importer.impl;

import com.dotsub.converter.exception.FileFormatException;
import com.dotsub.converter.importer.SubtitleImportHandler;
import com.dotsub.converter.model.SubtitleItem;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-08.
 */
@Component
public class DfxpImportHandler implements SubtitleImportHandler {

    private static final Pattern pattern = Pattern.compile(".*(\\d+):(\\d+):(\\d+)[:\\.]{1}(\\d+)");
    private static final Pattern otherPattern = Pattern.compile("(\\d+)([a-zA-Z]+)");

    @Override
    public String getFormatName() {
        return "Timed-Text (DFXP)";
    }

    @Override
    public List<SubtitleItem> importFile(InputStream inputStream) {
        try {
            List<SubtitleItem> items = new ArrayList<>();
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            //find all p nodes with time
            List list = document.selectNodes("//*[@begin]");
            for (Iterator iter = list.iterator(); iter.hasNext(); ) {
                Element element = (Element) iter.next();
                StringBuilder content = new StringBuilder("");
                for (int i = 0; i < element.nodeCount(); i++) {
                    Node node = element.node(i);
                    if (node.getNodeType() == Node.TEXT_NODE) {
                        content.append(node.getText());
                    }
                    else if (node.getNodeType() == Node.ANY_NODE && node.getName().equals("br")) {
                        content.append("\n");
                    }
                }
                String begin = (String) element.attribute("begin").getData();
                boolean hasEnd = element.attribute("end") != null;
                int endTime;
                if (hasEnd) {
                    String end = (String) element.attribute("end").getData();
                    endTime = parseTime(end);
                }
                else {
                    String dur = (String) element.attribute("dur").getData();
                    endTime = parseTime(dur) + parseTime(begin);
                }
                int startTime = parseTime(begin);
                int duration = endTime - startTime;
                items.add(new SubtitleItem(startTime, duration, content.toString()));
            }

            return items;
        }
        catch (DocumentException e) {
            throw new FileFormatException("Unable to read xml");
        }
    }

    private int parseTime(String time) throws FileFormatException {
        Matcher matcher = pattern.matcher(time);
        if (matcher.matches()) {
            int timeInMills = 0;

            timeInMills += Integer.parseInt(matcher.group(1)) * 3600000;
            timeInMills += Integer.parseInt(matcher.group(2)) * 60000;
            timeInMills += Integer.parseInt(matcher.group(3)) * 1000;
            String millsString = matcher.group(4);
            int mtpl = 1;
            if (millsString.length() == 2) {
                mtpl = 10;
            }
            else if (millsString.length() == 1) {
                mtpl = 100;
            }
            timeInMills += Integer.parseInt(millsString) * mtpl;

            return timeInMills;
        }
        Matcher otherMatcher = otherPattern.matcher(time);
        if (otherMatcher.matches()) {

            int rawTime = Integer.parseInt(otherMatcher.group(1));
            String units = otherMatcher.group(2);

            if (units.equals("s")) {
                rawTime = rawTime * 1000;
            }
            return rawTime;
        }
        throw new FileFormatException("File does not match expected dfpx format");
    }

}
