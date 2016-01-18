package com.dotsub.converter.exporter;

import com.dotsub.converter.model.HorizontalPosition;
import org.apache.commons.lang.StringEscapeUtils;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.text.DecimalFormat;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-11.
 */
public final class CaptionUtil {

    private static final PeriodFormatter dfxpFormatter = new PeriodFormatterBuilder().printZeroAlways()
            .minimumPrintedDigits(2).appendHours().appendSeparator(":").appendMinutes().appendSeparator(":")
            .appendSeconds().appendSeparator(".").appendMillis3Digit().toFormatter();

    private static final PeriodFormatter srtFormatter = new PeriodFormatterBuilder().printZeroAlways()
            .minimumPrintedDigits(2).appendHours().appendSeparator(":").appendMinutes().appendSeparator(":")
            .appendSeconds().appendSeparator(",").appendMillis3Digit().toFormatter();

    private static final PeriodFormatter vttFormatter = new PeriodFormatterBuilder().printZeroAlways()
            .minimumPrintedDigits(2).appendHours().appendSeparator(":").appendMinutes().appendSeparator(":")
            .appendSeconds().appendSeparator(".").appendMillis3Digit().toFormatter();

    private static final PeriodFormatter noMillsFormatter = new PeriodFormatterBuilder().printZeroAlways()
            .minimumPrintedDigits(2).appendHours().appendSeparator(":").appendMinutes().appendSeparator(":")
            .appendSeconds().toFormatter();

    private static final PeriodFormatter ssaFormatter = new PeriodFormatterBuilder().printZeroAlways()
            .minimumPrintedDigits(1).appendHours().appendSeparator(":").minimumPrintedDigits(2).appendMinutes()
            .appendSeparator(":").appendSeconds().toFormatter();

    /**
     * Formats a time in milliseconds to the desired caption format time-code.
     * @param format the format to be created.
     * @param periodInMills the time in milliseconds.
     * @return A string of the time in the selected format.
     */
    public static String formatPeriod(String format, Integer periodInMills) {

        Period period = new Period(periodInMills.intValue());
        DecimalFormat df = new DecimalFormat("00");

        switch (format) {
            case "srt":
                return srtFormatter.print(period);
            case "vtt":
                return vttFormatter.print(period);
            case "dfxp":
                return dfxpFormatter.print(period);
            case "sbv":
                double mills = getTwoDigitMills(period);
                return noMillsFormatter.print(period) + "." + df.format(mills);
            case "ssa":
                mills = getTwoDigitMills(period);
                return ssaFormatter.print(period) + "." + df.format(mills);
            case "stl":
                //last field is frames based on 30FPS
                double frames = Math.round(30d * period.getMillis() / 1000d);
                return noMillsFormatter.print(period) + ":" + df.format(frames);
            default:
                throw new RuntimeException(String.format("Unknown time format '%s'", format));
        }
    }

    private static double getTwoDigitMills(Period period) {
        //round to two digits
        //appendMillis and appendMillis3Digit mills don't respect max digits. a manual work around
        return (double) Math.round(period.getMillis() / 10d);
    }

    public static String escapeXml(String string) {
        return StringEscapeUtils.escapeXml(string);
    }

    /**
     * Converts to format specific line endings.
     * @param format the format being written
     * @param str the string to correct line endings in.
     * @return the line with format correct line breaks
     */
    public static String formatLineSeperator(String format, String str) {
        switch (format) {
            case "html":
                return str.replaceAll("\n", "<br/>");
            case "sbv":
                return str.replaceAll("\n", "[br]");
            case "stl":
                return str.replaceAll("\n", "|");
            case "ssa":
                return str.replaceAll("\n", "\\\\N");
            default:
                throw new RuntimeException(String.format("Unknown time format '%s'", format));
        }
    }

    public static String formatColor(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }

    public static String formatColor(int color, int alpha) {
        return String.format("#%06X", (0xFFFFFF & color)) + String.format("%02X", (0xFF & alpha));
    }

    /**
     * Gets a format specific string for HorizontalPosition.
     * @param position the HorizontalPosition of the subtitles
     * @return a string representation of that position.
     */
    public static String formatPosition(HorizontalPosition position) {
        //text editor style formatting used in most cases
        switch (position) {
            case LEFT:
                return "left";
            case RIGHT:
                return "right";
            default:
                return "center";
        }
    }
}
