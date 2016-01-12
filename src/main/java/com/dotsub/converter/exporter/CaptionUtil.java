package com.dotsub.converter.exporter;

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

    private static final PeriodFormatter sbvFormatter = new PeriodFormatterBuilder().printZeroAlways()
            .minimumPrintedDigits(2).appendHours().appendSeparator(":").appendMinutes().appendSeparator(":")
            .appendSeconds().toFormatter();

    /**
     * Formats a time in milliseconds to the desired caption format time-code.
     * @param format the format to be created.
     * @param periodInMills the time in milliseconds.
     * @return A string of the time in the selected format.
     */
    public static String formatPeriod(String format, Integer periodInMills) {

        Period period = new Period(periodInMills.intValue());

        switch (format) {
            case "srt":
                return srtFormatter.print(period);
            case "vtt":
                return vttFormatter.print(period);
            case "dfxp":
                return dfxpFormatter.print(period);
            case "sbv":
                //round to two digits
                //appendMillis and appendMillis3Digit mills don't respect max digits. a manual work around
                DecimalFormat df = new DecimalFormat("00");
                double mills = Math.round(period.getMillis()/10d);
                return sbvFormatter.print(period) + "." + df.format(mills);
            default:
                throw new RuntimeException(String.format("Unknown time format '%s'", format));
        }
    }

    public static String escapeXml(String string) {
        return StringEscapeUtils.escapeXml(string);
    }

    public static String formatLineSeperator(String format, String str) {
        switch (format) {
            case "html":
                return str.replaceAll("\n", "<br/>");
            case "sbv":
                return str.replaceAll("\n", "[br]");
            default:
                throw new RuntimeException(String.format("Unknown time format '%s'", format));
        }
    }

}
