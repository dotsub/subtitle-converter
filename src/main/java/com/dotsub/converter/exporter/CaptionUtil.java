package com.dotsub.converter.exporter;

import org.apache.commons.lang.StringEscapeUtils;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

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

    /**
     * Formats a time in milliseconds to the desired caption format time-code.
     * @param format the format to be created.
     * @param period the time in milliseconds.
     * @return A string of the time in the selected format.
     */
    public static String formatPeriod(String format, Integer period) {
        switch (format) {
            case "srt":
                return srtFormatter.print(new Period(period.intValue()));
            case "vtt":
                return vttFormatter.print(new Period(period.intValue()));
            case "dfxp":
                return dfxpFormatter.print(new Period(period.intValue()));
            default:
                throw new RuntimeException(String.format("Unknown time format '%s'", format));
        }
    }

    public static String escapeXml(String string) {
        return StringEscapeUtils.escapeXml(string);
    }

    public static String nl2br(String str) {
        return str.replaceAll("\n", "<br/>");
    }

}
