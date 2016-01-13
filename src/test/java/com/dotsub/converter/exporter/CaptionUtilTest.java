package com.dotsub.converter.exporter;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-11.
 */
public class CaptionUtilTest {

    @Test
    public void testFormatTimes() throws Exception {
        String time = CaptionUtil.formatPeriod("srt", 0);
        assertEquals("00:00:00,000", time);

        time = CaptionUtil.formatPeriod("srt", 1000);
        assertEquals("00:00:01,000", time);

        time = CaptionUtil.formatPeriod("srt", 1001);
        assertEquals("00:00:01,001", time);

        time = CaptionUtil.formatPeriod("srt", 60000);
        assertEquals("00:01:00,000", time);

        time = CaptionUtil.formatPeriod("vtt", 0);
        assertEquals("00:00:00.000", time);

        time = CaptionUtil.formatPeriod("vtt", 1000);
        assertEquals("00:00:01.000", time);

        time = CaptionUtil.formatPeriod("vtt", 1001);
        assertEquals("00:00:01.001", time);

        time = CaptionUtil.formatPeriod("vtt", 60000);
        assertEquals("00:01:00.000", time);

        time = CaptionUtil.formatPeriod("dfxp", 0);
        assertEquals("00:00:00.000", time);

        time = CaptionUtil.formatPeriod("dfxp", 1000);
        assertEquals("00:00:01.000", time);

        time = CaptionUtil.formatPeriod("dfxp", 1001);
        assertEquals("00:00:01.001", time);

        time = CaptionUtil.formatPeriod("dfxp", 60000);
        assertEquals("00:01:00.000", time);

        time = CaptionUtil.formatPeriod("sbv", 0);
        assertEquals("00:00:00.00", time);

        time = CaptionUtil.formatPeriod("sbv", 1000);
        assertEquals("00:00:01.00", time);

        time = CaptionUtil.formatPeriod("sbv", 1010);
        assertEquals("00:00:01.01", time);

        time = CaptionUtil.formatPeriod("sbv", 1001);
        assertEquals("00:00:01.00", time);

        time = CaptionUtil.formatPeriod("sbv", 1005);
        assertEquals("00:00:01.01", time);

        time = CaptionUtil.formatPeriod("ssa", 0);
        assertEquals("0:00:00.00", time);

        time = CaptionUtil.formatPeriod("ssa", 1000);
        assertEquals("0:00:01.00", time);

        time = CaptionUtil.formatPeriod("ssa", 1010);
        assertEquals("0:00:01.01", time);

        time = CaptionUtil.formatPeriod("ssa", 1001);
        assertEquals("0:00:01.00", time);

        time = CaptionUtil.formatPeriod("ssa", 1005);
        assertEquals("0:00:01.01", time);

        time = CaptionUtil.formatPeriod("ssa", 1009);
        assertEquals("0:00:01.01", time);

        //throw a non-supported format at it. It should error.
        try {
            CaptionUtil.formatPeriod("asdf", 60000);
            fail("Unknown format did not error");
        }
        catch (RuntimeException e) {
            //should happen
        }
    }

    @Test
    public void testEscapeXml() throws Exception {
        String escaped = CaptionUtil.escapeXml("<br/>");
        assertNotEquals("<br/>", escaped);
    }
}
