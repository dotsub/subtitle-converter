package com.dotsub.converter.importer.impl;

import com.dotsub.converter.exception.FileFormatException;
import com.dotsub.converter.exception.FileImportException;
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
 * The srt format is as follows

 1 (the id)
 startTime --> EndTime
 contenr
 2 (the next id)
 startTime --> EndTime
 content

 * The next id means the content has concluded.
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-08.
 */
@Component
public class SrtImportHandler implements SubtitleImportHandler {

    private static final Log log = LogFactory.getLog(SrtImportHandler.class);
    private static final Pattern pattern =
            Pattern.compile(".*(\\d+):(\\d+):(\\d+),(\\d+).*-->.*(\\d+):(\\d+):(\\d+),(\\d+).*");

    @Override
    public String getFormatName() {
        return "SubRip";
    }

    @Override
    public List<SubtitleItem> importFile(InputStream inputStream) throws IOException {
        Iterator lines = IOUtils.lineIterator(new InputStreamReader(inputStream, "UTF-8"));

        List<SubtitleItem> items = new ArrayList<>();
        int lineNumber = 0;
        Boolean isPal = null;
        int palOffset = 0;

        while (lines.hasNext()) {
            String line = (String) lines.next();
            log.debug(line);
            lineNumber++;
            if (line.equals("")) {
                continue;
            }
            String times = (String) lines.next();
            lineNumber++;
            Matcher matcher = pattern.matcher(times);
            if (!matcher.matches()) {
                if (lineNumber < 3) {
                    log.debug(times);
                    throw new FileFormatException("File does not match expected srt format");
                } else {
                    throw new FileImportException("File does not match expected srt format");
                }
            }
            if (isPal == null) {
                palOffset = Integer.parseInt(matcher.group(1)) * 3600000;
                isPal = palOffset > 0;
            }

            int start = 0;
            start += (Integer.parseInt(matcher.group(1)) * 3600000) - palOffset;
            start += Integer.parseInt(matcher.group(2)) * 60000;
            start += Integer.parseInt(matcher.group(3)) * 1000;
            start += Integer.parseInt(matcher.group(4));

            int end = 0;
            end += (Integer.parseInt(matcher.group(5)) * 3600000) - palOffset;
            end += Integer.parseInt(matcher.group(6)) * 60000;
            end += Integer.parseInt(matcher.group(7)) * 1000;
            end += Integer.parseInt(matcher.group(8));

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
            if (log.isDebugEnabled()) {
                log.debug(
                        format("Creating a new subtitle from:\t times:%d ms to %d ms  \t content: %s\t",
                                start, duration, caption.toString()));
            }
            SubtitleItem item = new SubtitleItem(start, duration, caption.toString());
            items.add(item);
        }
        return items;
    }
}
