package com.dotsub.converter.exporter.impl;

import com.dotsub.converter.exporter.SubtitleExportHandler;
import com.dotsub.converter.exporter.impl.scc.*;
import com.dotsub.converter.model.Configuration;
import com.dotsub.converter.model.HorizontalPosition;
import com.dotsub.converter.model.SubtitleItem;

import java.util.ArrayList;
import java.util.List;
/**
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
        SccViewOptions viewOptions = new SccViewOptions(configuration);

        StringBuilder sccCaptions = new StringBuilder();

        //add the file header
        sccCaptions.append("Scenarist_SCC V1.0\n\n");
        int count = 0;
        int lastEndTime = -1;
        for (SubtitleItem subtitle : subtitles) {
            //write close of last subtitle if this one starts after it ended
            if (lastEndTime != -1 && lastEndTime != subtitle.getStartTime()) {
                clearScreen(sccCaptions, lastEndTime, viewOptions);
            }
            sccCaptions.append(getTimeCode(subtitle.getStartTime()));
            sccCaptions.append("\t");
            //start pop on caption
            writeCaptions(sccCaptions, subtitle, viewOptions);
            sccCaptions.append("\n\n");
            lastEndTime = (subtitle.getStartTime() + subtitle.getDuration());
            count++;
            if (count == subtitles.size()) {
                clearScreen(sccCaptions, subtitle.getDuration() + subtitle.getStartTime(), viewOptions);
            }
        }

        return sccCaptions.toString();
    }

    private void clearScreen(StringBuilder sccCaptions, Integer time, SccViewOptions viewOptions) {
        sccCaptions.append(getTimeCode(time));
        sccCaptions.append("\t");
        List<Byte> bytes = new ArrayList<>();
        addControlCode(bytes, viewOptions.getSccChannelControlCodes().clearScreen(), viewOptions);
        writeCaptionToResponse(sccCaptions, bytes);
        sccCaptions.append("\n\n");
    }

    private void writeCaptions(StringBuilder sccCaptions, SubtitleItem subtitle, SccViewOptions viewOptions) {
        String content = subtitle.getContent();
        List<Byte> captionBytes;
        SccCaptionMode mode = viewOptions.getMode();
        if (mode == SccCaptionMode.POP_ON) {
            captionBytes = createPopOnCaption(content, viewOptions);
        }
        else if (mode == SccCaptionMode.ROLL_UP2
                || mode == SccCaptionMode.ROLL_UP3 || mode == SccCaptionMode.ROLL_UP4) {
            captionBytes = createRollUpCaption(content, viewOptions);
        }
        else {
            throw new IllegalArgumentException("Scc Mode not implemented");
        }
        writeCaptionToResponse(sccCaptions, captionBytes);
    }

    private List<Byte> createRollUpCaption(String content, SccViewOptions viewOptions) {
        List<Byte> captionBytes = new ArrayList<>();
        SccCaptionMode mode = viewOptions.getMode();
        SccChannelControlCodes sccChannelControlCodes = viewOptions.getSccChannelControlCodes();
        switch (mode) {
            case ROLL_UP2:
                addControlCode(captionBytes, sccChannelControlCodes.startRollUpCaption2(), viewOptions);
                break;
            case ROLL_UP3:
                addControlCode(captionBytes, sccChannelControlCodes.startRollUpCaption3(), viewOptions);
                break;
            case ROLL_UP4:
                addControlCode(captionBytes, sccChannelControlCodes.startRollUpCaption4(), viewOptions);
                break;
            default:
                //can't happen
        }
        addControlCode(captionBytes, sccChannelControlCodes.carriageReturn(), viewOptions);
        //split into lines
        String[] lines = parseLines(content);
        int currentLine = 15 - lines.length;
        for (String line : lines) {
            content = content.trim();
            int lineLength = line.length();
            byte[] posBytes = getLinePosition(currentLine, lineLength, viewOptions);
            addControlCode(captionBytes, posBytes, viewOptions);

            encodeSubtitleLine(captionBytes, line, viewOptions);
            if (currentLine < 14) {
                addControlCode(captionBytes, sccChannelControlCodes.carriageReturn(), viewOptions);
            }
            currentLine++;
        }
        return captionBytes;
    }

    private String[] parseLines(String content) {
        return content.split("\n");
    }

    private byte[] getLinePosition(int currentLine, int lineLength, SccViewOptions viewOptions) {
        int pos = 0;
        switch (viewOptions.getConfiguration().getHorizontalPosition()) {
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
        return viewOptions.getSccChannelControlCodes().cursorToPosition(currentLine, pos);
    }

    private void encodeSubtitleLine(List<Byte> captionBytes, String line, SccViewOptions viewOptions) {
        for (char c : line.toCharArray()) {
            byte[] bytes = viewOptions.getEncodingProvider().encodeChar(c);
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

    private List<Byte> createPopOnCaption(String content, SccViewOptions viewOptions) {
        List<Byte> captionBytes = new ArrayList<>();
        addControlCode(captionBytes, viewOptions.getSccChannelControlCodes().startPopOnCaption(), viewOptions);
        //split into lines
        String[] lines = parseLines(content);
        int currentLine = 15 - lines.length;
        for (String line : lines) {
            content = content.trim();
            int lineLength = line.length();
            byte[] posBytes = getLinePosition(currentLine, lineLength, viewOptions);
            addControlCode(captionBytes, posBytes, viewOptions);
            encodeSubtitleLine(captionBytes, line, viewOptions);
            currentLine++;
        }
        addControlCode(captionBytes, viewOptions.getSccChannelControlCodes().clearScreen(), viewOptions);
        addControlCode(captionBytes, viewOptions.getSccChannelControlCodes().endPopOnCaption(), viewOptions);
        return captionBytes;
    }

    private void writeCaptionToResponse(StringBuilder sccCaptions, List<Byte> captionBytes) {
        for (int i = 0; i < captionBytes.size(); i = i + 2) {
            sccCaptions.append(String.format("%02x%02x", captionBytes.get(i), captionBytes.get(i + 1)));
            if (captionBytes.size() > i + 2) {
                sccCaptions.append(" ");
            }
        }
    }

    private void addControlCode(List<Byte> bytes, byte[] controlCode, SccViewOptions viewOptions) {
        int code = (viewOptions.getIsDoubleControlCode() ? 2 : 1);
        for (int i = 0; i < controlCode.length; i = i + 2) {
            for (int j = 0; j < code; j++) {
                bytes.add(controlCode[i]);
                bytes.add(controlCode[i + 1]);
            }
        }
    }

    private String getTimeCode(Integer time) {

        double fps = 29.97002997;
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
