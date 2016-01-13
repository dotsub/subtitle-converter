package com.dotsub.converter.importer.impl;

import com.dotsub.converter.exception.FileFormatException;
import com.dotsub.converter.exception.FileImportException;
import com.dotsub.converter.importer.SubtitleImportHandler;
import com.dotsub.converter.model.SubtitleItem;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-12.
 */
@Component
public class StlImportHandler implements SubtitleImportHandler {

    Pattern pattern = Pattern.compile(
            "\\uFEFF*(\\d+):(\\d+):(\\d+)[:\\.]{1}(\\d+)\\s,\\s(\\d+):(\\d+):(\\d+)[:\\.]{1}(\\d+)\\s,\\s(.*)");

    @Override
    public String getFormatName() {
        return "Spruce Subtitle";
    }

    @Override
    public List<SubtitleItem> importFile(InputStream inputStream) throws IOException {
        //The stl format is as follows
        //00:00:00:01 , 00:00:08:00 , Did you know?
        List<SubtitleItem> items = new ArrayList<SubtitleItem>();
        Iterator lines = IOUtils.lineIterator(new InputStreamReader(inputStream, "UTF-8"));
        Boolean isPal = null;
        int palOffset = 0;
        int lineNumber = 0;
        while (lines.hasNext()) {
            String line = (String) lines.next();
            lineNumber++;
            line = line.trim();
            if (line.equals("") || line.startsWith("$") || line.startsWith("//")) {
                continue;
            }
            Matcher matcher = pattern.matcher(line);
            if (!matcher.matches()) {
                if (lineNumber < 2) {
                    throw new FileFormatException("File does not match expected stl format");
                } else {
                    throw new FileImportException("File does not match expected stl format");
                }
            }

            int start = 0;
            if (isPal == null) {
                //PAL's not set so see if group 1 start at '1' which would be x1:xx:xx:xx meaning this is PAL
                //and we should drop the first hour
                isPal = Integer.parseInt(matcher.group(1)) > 0;
                palOffset = Integer.parseInt(matcher.group(1));
            }
            start += Integer.parseInt(matcher.group(1)) * 3600000;
            start += Integer.parseInt(matcher.group(2)) * 60000;
            start += Integer.parseInt(matcher.group(3)) * 1000;
            //last digit is frame number based on 30fps
            int fps = 30;
            int frame = Integer.parseInt(matcher.group(4));
            start += Math.floor(1000 / fps * frame);

            int end = 0;
            end += Integer.parseInt(matcher.group(5)) * 3600000;
            end += Integer.parseInt(matcher.group(6)) * 60000;
            end += Integer.parseInt(matcher.group(7)) * 1000;
            //last digit is frame number based on 30ps
            frame = Integer.parseInt(matcher.group(4));
            end += Math.floor(1000 / fps * frame);

            int duration = end - start;

            String content = matcher.group(9);
            if (isPal) {
                start = start - (3600000 * palOffset);
            }
            //replace any '|' since they are line breaks
            content = content.replaceAll("\\|", "\n");
            SubtitleItem item = new SubtitleItem(start, duration, content);
            items.add(item);
        }
        return items;
    }
}
