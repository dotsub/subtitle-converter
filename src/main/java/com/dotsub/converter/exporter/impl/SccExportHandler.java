package com.dotsub.converter.exporter.impl;

import com.dotsub.converter.exporter.SubtitleExportHandler;
import com.dotsub.converter.exporter.impl.scc.*;
import com.dotsub.converter.model.Configuration;
import com.dotsub.converter.model.HorizontalPosition;
import com.dotsub.converter.model.SubtitleItem;

import java.util.ArrayList;
import java.util.List;

/**
 * This code was converted from an older app where it was writing directly to a binary output stream.
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-20.
 */

public class SccExportHandler implements SubtitleExportHandler {

    private static final String fileHeader = "Scenarist_SCC V1.0";
    private static final byte SPACER = (byte) 0x80;

    @Override
    public String getFormatName() {
        return fileHeader;
    }

    @Override
    public Configuration getDefaultConfiguration() {
        Configuration configuration = new Configuration();
        //position
        configuration.setHorizontalPosition(HorizontalPosition.CENTER);
        //default fps settings
        configuration.setExportFps(30d / 1.001d);

        return configuration;
    }

    @Override
    public String exportSubtitles(List<SubtitleItem> subtitles, Configuration configuration) {

        //parse the config
        SccConfiguration sccConfiguration = new SccConfiguration(configuration);

        StringBuilder sccCaptions = new StringBuilder();

        //add the file header
        sccCaptions.append("Scenarist_SCC V1.0\n\n");
        int count = 0;
        int lastEndTime = -1;
        for (SubtitleItem subtitle : subtitles) {
            //write close of last subtitle if this one starts after it ended
            if (lastEndTime != -1 && lastEndTime != subtitle.getStartTime()) {
                clearScreen(sccCaptions, lastEndTime, sccConfiguration);
            }
            //add the time code for this caption
            sccCaptions.append(getTimeCode(subtitle.getStartTime(), sccConfiguration));
            sccCaptions.append("\t");
            //start a new caption
            writeCaptions(sccCaptions, subtitle, sccConfiguration);
            sccCaptions.append("\n\n");
            lastEndTime = (subtitle.getStartTime() + subtitle.getDuration());
            count++;
            if (count == subtitles.size()) {
                //if this is the last subtitle made sure to add a clear screen when it's duration is complete
                clearScreen(sccCaptions, subtitle.getDuration() + subtitle.getStartTime(), sccConfiguration);
            }
        }

        return sccCaptions.toString();
    }

    //clears the screen at a given time.
    private void clearScreen(StringBuilder sccCaptions, Integer time, SccConfiguration sccConfiguration) {
        sccCaptions.append(getTimeCode(time, sccConfiguration));
        sccCaptions.append("\t");
        List<Byte> bytes = new ArrayList<>();
        addControlCode(bytes, sccConfiguration.getSccChannelControlCodes().clearScreen(), sccConfiguration);
        writeCaption(sccCaptions, bytes);
        sccCaptions.append("\n\n");
    }

    //takes the given config and adds the control codes for the correct subtitle type being shown
    private void writeCaptions(StringBuilder sccCaptions, SubtitleItem subtitle, SccConfiguration sccConfiguration) {
        String content = subtitle.getContent();
        List<Byte> captionBytes;
        SccCaptionMode mode = sccConfiguration.getMode();
        if (mode == SccCaptionMode.POP_ON) {
            captionBytes = createPopOnCaption(content, sccConfiguration);
        }
        else if (mode == SccCaptionMode.ROLL_UP2
                || mode == SccCaptionMode.ROLL_UP3 || mode == SccCaptionMode.ROLL_UP4) {
            captionBytes = createRollUpCaption(content, sccConfiguration);
        }
        else {
            throw new IllegalArgumentException("Scc Mode not implemented");
        }
        writeCaption(sccCaptions, captionBytes);
    }

    //Adds the control codes for a new 'roll up' subtitle
    private List<Byte> createRollUpCaption(String content, SccConfiguration sccConfiguration) {
        List<Byte> captionBytes = new ArrayList<>();
        SccCaptionMode mode = sccConfiguration.getMode();
        SccChannelControlCodes sccChannelControlCodes = sccConfiguration.getSccChannelControlCodes();
        switch (mode) {
            case ROLL_UP2:
                addControlCode(captionBytes, sccChannelControlCodes.startRollUpCaption2(), sccConfiguration);
                break;
            case ROLL_UP3:
                addControlCode(captionBytes, sccChannelControlCodes.startRollUpCaption3(), sccConfiguration);
                break;
            case ROLL_UP4:
                addControlCode(captionBytes, sccChannelControlCodes.startRollUpCaption4(), sccConfiguration);
                break;
            default:
                //should not happen
        }
        addControlCode(captionBytes, sccChannelControlCodes.carriageReturn(), sccConfiguration);
        //split into lines
        String[] lines = content.split("\n");
        int currentLine = 15 - lines.length;
        for (String line : lines) {
            content = content.trim();
            int lineLength = line.length();
            byte[] posBytes = getLinePosition(currentLine, lineLength, sccConfiguration);
            addControlCode(captionBytes, posBytes, sccConfiguration);

            encodeSubtitleLine(captionBytes, line, sccConfiguration);
            if (currentLine < 14) {
                addControlCode(captionBytes, sccChannelControlCodes.carriageReturn(), sccConfiguration);
            }
            currentLine++;
        }
        return captionBytes;
    }

    //Determine where on the line the cursor should start writing the subtitle.
    private byte[] getLinePosition(int currentLine, int lineLength, SccConfiguration sccConfiguration) {
        int pos;
        switch (sccConfiguration.getConfiguration().getHorizontalPosition()) {
            case CENTER:
                pos = (32 - lineLength) / 2;
                break;
            case LEFT:
                pos = 0;
                break;
            default:
                pos = (32 - lineLength);
                break;
        }
        if (pos < 0) {
            pos = 0;
        }
        return sccConfiguration.getSccChannelControlCodes().cursorToPosition(currentLine, pos);
    }

    //SCC captions are encoded in a text file as two byte pairs.
    private void encodeSubtitleLine(List<Byte> captionBytes, String line, SccConfiguration sccConfiguration) {
        for (char c : line.toCharArray()) {
            byte[] bytes = sccConfiguration.getSccEncodingProvider().encodeChar(c);
            if (bytes.length < 2) {
                captionBytes.add(bytes[0]);
            } else {
                //if two bytes we have to pad odd numbered arrays with 80 before adding us to the list
                if (captionBytes.size() % 2 == 1) {
                    captionBytes.add(SPACER);
                }
                //special chars and extended codes are always doubled even if control codes are not.
                for (int i = 0; i < 2; i++) {
                    captionBytes.add(bytes[0]);
                    captionBytes.add(bytes[1]);
                }
            }
        }
        if (captionBytes.size() % 2 == 1) {
            captionBytes.add(SPACER);
        }
    }

    //Starts a new pop on caption
    private List<Byte> createPopOnCaption(String content, SccConfiguration sccConfiguration) {
        List<Byte> captionBytes = new ArrayList<>();
        addControlCode(captionBytes,
                sccConfiguration.getSccChannelControlCodes().startPopOnCaption(), sccConfiguration);
        //split into lines
        String[] lines = content.split("\n");
        int currentLine = 15 - lines.length;
        for (String line : lines) {
            content = content.trim();
            int lineLength = line.length();
            byte[] posBytes = getLinePosition(currentLine, lineLength, sccConfiguration);
            addControlCode(captionBytes, posBytes, sccConfiguration);
            encodeSubtitleLine(captionBytes, line, sccConfiguration);
            currentLine++;
        }
        addControlCode(captionBytes, sccConfiguration.getSccChannelControlCodes().clearScreen(), sccConfiguration);
        addControlCode(captionBytes, sccConfiguration.getSccChannelControlCodes().endPopOnCaption(), sccConfiguration);
        return captionBytes;
    }

    //Writes the captions content
    private void writeCaption(StringBuilder sccCaptions, List<Byte> captionBytes) {
        for (int i = 0; i < captionBytes.size(); i = i + 2) {
            sccCaptions.append(String.format("%02x%02x", captionBytes.get(i), captionBytes.get(i + 1)));
            if (captionBytes.size() > i + 2) {
                sccCaptions.append(" ");
            }
        }
    }

    //Adds a control code, appending it twice if the config dictates.
    private void addControlCode(List<Byte> bytes, byte[] controlCode, SccConfiguration sccConfiguration) {
        int code = (sccConfiguration.getIsDoubleControlCode() ? 2 : 1);
        for (int i = 0; i < controlCode.length; i = i + 2) {
            for (int j = 0; j < code; j++) {
                bytes.add(controlCode[i]);
                bytes.add(controlCode[i + 1]);
            }
        }
    }

    //The time code SCC uses is not wall-clock time. It's # of frames that have passed.
    private String getTimeCode(Integer time, SccConfiguration sccConfiguration) {
        //TODO: Add drop frame support.
        double fps = sccConfiguration.getConfiguration().getExportFps();
        double frames = (time / 1000d) * fps;

        double hours;
        hours = (frames / 30 / 60 / 60) % 60;
        hours = Math.floor(hours);
        double minutes;
        minutes = (frames / 30 / 60) % 60;
        minutes = Math.floor(minutes);
        double seconds;
        seconds = (frames / 30)  % 60;
        seconds = Math.floor(seconds);
        double frame = Math.floor(frames % 30);

        return String.format("%02.0f:%02.0f:%02.0f:%02.0f", hours, minutes, seconds, frame);
    }
}
