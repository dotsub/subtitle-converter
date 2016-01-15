package com.dotsub.converter.importer.impl;

import com.dotsub.converter.exception.FileFormatException;
import com.dotsub.converter.importer.SubtitleImportHandler;
import com.dotsub.converter.model.SubtitleItem;
import org.apache.commons.io.IOUtils;

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
 * Date: 16-01-14.
 */
public class QtTextImportHandler implements SubtitleImportHandler {

    private static final Pattern timePattern = Pattern.compile("\\[(\\d+):(\\d+):(\\d+).(\\d+)\\].*");

    // Format looks like:
    //      {QTtext}{font:Helvetica}{plain}{size:14}{etc...}
    //      [00:00:00.00]
    //      Now let's look at the topic of estimation.
    //      [00:00:03.00]
    //      Here's our first example.

    @Override
    public String getFormatName() {
        return "QuickTime Text";
    }

    @Override
    public List<SubtitleItem> importFile(InputStream inputStream) throws IOException {
        Iterator lines = IOUtils.lineIterator(new InputStreamReader(inputStream, "UTF-8"));
        String firstLine = (String) lines.next();
        if (!firstLine.startsWith("{QTtext}")) {
            throw new FileFormatException("file does not match expected QTText format");
        }
        List<SubtitleItem> items = new ArrayList<>();
        int start = -1;
        boolean foundStart = false;
        StringBuffer content = new StringBuffer("");

        while (lines.hasNext()) {
            String nextLine = (String) lines.next();
            int currentTime = 0;
            //first see if it is a time code.
            Matcher matcher = timePattern.matcher(nextLine);
            if (!matcher.matches()) {
                //this must be text not time codes
                if (foundStart) {
                    content.append(nextLine);
                    if (!content.toString().equals("")) {
                        content.append("\n");
                    }
                }
            }
            else {
                foundStart = true;
                currentTime = parseTime(currentTime, matcher);
                //see if we have a full line behind this
                if ((start != currentTime) && !content.toString().equals("")) {
                    int duration = currentTime - start;
                    items.add(new SubtitleItem(start, duration, content.toString()));
                    content = new StringBuffer("");
                }
                start = currentTime;
            }
            //now see what we have
        }
        //if there is no last time to end the last subtitle make it 3 seconds.
        items.add(new SubtitleItem(start, 3000, content.toString()));
        return items;
    }

    private int parseTime(int currentTime, Matcher matcher) {
        currentTime += (Integer.parseInt(matcher.group(1)) * 3600000);
        currentTime += Integer.parseInt(matcher.group(2)) * 60000;
        currentTime += Integer.parseInt(matcher.group(3)) * 1000;
        final String milliseconds = matcher.group(4);
        if (milliseconds.length() == 3) {
            //for 0.000
            currentTime += Integer.parseInt(milliseconds);
        }
        else {
            //for 0.00
            currentTime += Integer.parseInt(milliseconds) * 10;
        }
        return currentTime;
    }
}
