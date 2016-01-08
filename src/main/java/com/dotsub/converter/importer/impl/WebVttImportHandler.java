package com.dotsub.converter.importer.impl;

import com.dotsub.converter.exception.FileFormatException;
import com.dotsub.converter.importer.SubtitleImportHandler;
import com.dotsub.converter.model.SubtitleItem;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-08.
 */
@Component
public class WebVttImportHandler implements SubtitleImportHandler {

    private static final Log log = LogFactory.getLog(WebVttImportHandler.class);
    private static final Pattern pattern = Pattern.compile(".*(\\d+):(\\d+):(\\d+).(\\d+).*-->.*(\\d+):(\\d+):(\\d+).(\\d+).*");
    private static final Pattern hourlessPattern = Pattern.compile(".*(\\d+):(\\d+).(\\d+).*-->.*(\\d+):(\\d+).(\\d+).*");

    @Override
    public List<SubtitleItem> importFile(InputStream inputStream) {
        try {
            Iterator lines = IOUtils.lineIterator(new InputStreamReader(inputStream, "UTF-8"));

            List<SubtitleItem> items = new ArrayList<>();
            int lineNumber = 0;

            while (lines.hasNext()) {
                String line = (String) lines.next();
                log.debug(line);
                lineNumber++;

                if (processHeader(lineNumber, line)) continue;
                //header is done including its terminator. first line is an cue ID or a time.
                Matcher matcher = pattern.matcher(line);
                Matcher hourLessMatcher = hourlessPattern.matcher(line);
                log.debug("Checking: "+line);
                if(!matcher.matches() && !hourLessMatcher.matches()) {
                    //grab next line to see if it is the time this was just an ID
                    line = (String) lines.next();
                    lineNumber++;
                    matcher = pattern.matcher(line);
                    hourLessMatcher = hourlessPattern.matcher(line);
                    if (!matcher.matches() && !hourLessMatcher.matches()) {
                        throw new FileFormatException("file does not match expected vtt format");
                    }
                }
                log.debug(line);

                int start = 0;
                int end = 0;
                if(matcher.matches()) {
                    start += (Integer.parseInt(matcher.group(1)) * 3600000);
                    start += Integer.parseInt(matcher.group(2)) * 60000;
                    start += Integer.parseInt(matcher.group(3)) * 1000;
                    start += Integer.parseInt(matcher.group(4));
                    end += (Integer.parseInt(matcher.group(5)) * 3600000);
                    end += Integer.parseInt(matcher.group(6)) * 60000;
                    end += Integer.parseInt(matcher.group(7)) * 1000;
                    end += Integer.parseInt(matcher.group(8));
                }
                else {
                    start += Integer.parseInt(hourLessMatcher.group(1)) * 60000;
                    start += Integer.parseInt(hourLessMatcher.group(2)) * 1000;
                    start += Integer.parseInt(hourLessMatcher.group(3));
                    end += Integer.parseInt(hourLessMatcher.group(4)) * 60000;
                    end += Integer.parseInt(hourLessMatcher.group(5)) * 1000;
                    end += Integer.parseInt(hourLessMatcher.group(6));
                }
                int duration = end - start;
                String content;
                StringBuilder caption = new StringBuilder("");
                while (lines.hasNext() && (content = (String) lines.next()) != null && !content.trim().equals("")) {
                    lineNumber++;
                    content = content.trim();
                    caption.append("\n");
                    caption.append(content);
                }
                lineNumber++;
                //replace all non supported info
                String subtitleContent = caption.toString().replaceAll("<(.|\n)*?>", "");
                if (log.isDebugEnabled()) {
                    log.debug(format("creating a new caption from: \n\t times:%d ms to %d ms  \n\t content: %s \n\t", start, duration, subtitleContent));
                }
                SubtitleItem item = new SubtitleItem(start, duration, subtitleContent);
                items.add(item);
            }
            return items;
        }
        catch (IOException e) {
            throw new FileFormatException("Unable to read IO stream");
        }
    }

    private boolean processHeader(int lineNumber, String line) throws FileFormatException {
        //line 1 has to contain WEBVTT
        if (line.contains("WEBVTT") && lineNumber == 1) {
            return true;
        }
        else if (!line.contains("WEBVTT") && lineNumber == 1) {
            throw new FileFormatException("file does not match expected vtt format");
        }
        //Metadata is in the form of x: y . All of those can be ignored in our case.
        else if(line.matches("^[\\w]+:[^:]+")) {
            return true;
        }
        else if (line.equals("")) {
            return true;
        }
        return false;
    }
}

