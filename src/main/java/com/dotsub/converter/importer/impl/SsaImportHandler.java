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
 * Date: 16-01-13.
 */
@Component
public class SsaImportHandler implements SubtitleImportHandler {

    private static final Pattern timePattern = Pattern.compile("(\\d+):(\\d+):(\\d+)\\.(\\d+)");

    @Override
    public String getFormatName() {
        return "SubStation Alpha";
    }

    @Override
    public List<SubtitleItem> importFile(InputStream inputStream) throws IOException {

        Iterator lines = IOUtils.lineIterator(new InputStreamReader(inputStream, "UTF-8"));
        List<SubtitleItem> items = new ArrayList<>();
        int lineNumber = 0;

        while (lines.hasNext()) {
            String line = (String) lines.next();
            if (lineNumber == 0) {
                //parse the file header
                if (!line.equals("[Script Info]")) {
                    throw new FileFormatException("File does not start with [Script Info] not a SubStation Alpha file");
                }
                //consume the header, this can all be ignored.
                while (!line.startsWith("Dialogue")) {
                    line = (String) lines.next();
                    lineNumber++;
                }
            }
            //now we have the subtitles that all start with 'Dialogue' which contain the captions
            //there can also a few other items here, we can safely ignore those.
            if (!line.startsWith("Dialogue")) {
                lineNumber++;
                continue;
            }
            //Dialogue: Marked, Start, End, Style, Name, MarginL, MarginR, MarginV, Effect, Text
            //we only care about start, end and text.
            String[] lineParts = line.split(",", 10);

            if (lineParts.length < 10) {
                throw new FileImportException(
                        String.format("Encountered wrong number of parts in Dialogue line %d", lineNumber));
            }

            int startTime = parseTime(lineParts[1]);
            int endTime = parseTime(lineParts[2]);
            String content = lineParts[9];

            //replace all hard and soft linebreaks
            content = content.replace("\\N", "\n").replace("\\n", "");
            //remove override codes ex {\pos(400,570)}
            content = content.replaceAll("\\{\\\\(.|\n)*?\\}", "");

            items.add(new SubtitleItem(startTime, endTime - startTime, content));
            lineNumber++;
        }
        return items;
    }

    private int parseTime(String rawTime) {
        Matcher matcher = timePattern.matcher(rawTime);
        if (!matcher.matches()) {
            throw new FileImportException(String.format("Time does not match expected format: %s", rawTime));
        }

        int time = 0;
        time += (Integer.parseInt(matcher.group(1)) * 3600000);
        time += Integer.parseInt(matcher.group(2)) * 60000;
        time += Integer.parseInt(matcher.group(3)) * 1000;
        time += Integer.parseInt(matcher.group(4)) * 10;

        return time;
    }
}
