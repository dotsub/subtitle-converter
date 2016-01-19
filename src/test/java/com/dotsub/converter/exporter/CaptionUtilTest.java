package com.dotsub.converter.exporter;

import com.dotsub.converter.model.HorizontalPosition;
import com.dotsub.converter.model.VerticalPosition;
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

    @Test
    public void testFormatPosition() throws Exception {
        String pos = CaptionUtil.formatPosition("text", HorizontalPosition.CENTER);
        assertEquals("center", pos);

        pos = CaptionUtil.formatPosition("text", HorizontalPosition.LEFT);
        assertEquals("left", pos);

        pos = CaptionUtil.formatPosition("text", HorizontalPosition.RIGHT);
        assertEquals("right", pos);

        pos = CaptionUtil.formatPosition("stl", HorizontalPosition.CENTER);
        assertEquals("Center", pos);

        pos = CaptionUtil.formatPosition("stl", HorizontalPosition.LEFT);
        assertEquals("Left", pos);

        pos = CaptionUtil.formatPosition("stl", HorizontalPosition.RIGHT);
        assertEquals("Right", pos);

        try {
            CaptionUtil.formatPosition("nothing", HorizontalPosition.RIGHT);
            fail("Can not convert a unknown type to a position");
        }
        catch (RuntimeException e) {
            //should happen
        }

        pos = CaptionUtil.formatPosition("text", VerticalPosition.TOP);
        assertEquals("top", pos);

        pos = CaptionUtil.formatPosition("text", VerticalPosition.MIDDLE);
        assertEquals("middle", pos);

        pos = CaptionUtil.formatPosition("text", VerticalPosition.BOTTOM);
        assertEquals("bottom", pos);

        pos = CaptionUtil.formatPosition("stl", VerticalPosition.TOP);
        assertEquals("Top", pos);

        pos = CaptionUtil.formatPosition("stl", VerticalPosition.MIDDLE);
        assertEquals("Center", pos);

        pos = CaptionUtil.formatPosition("stl", VerticalPosition.BOTTOM);
        assertEquals("Bottom", pos);

        try {
            CaptionUtil.formatPosition("nothing", VerticalPosition.BOTTOM);
            fail("Can not convert a unknown type to a position");
        }
        catch (RuntimeException e) {
            //should happen
        }
    }
}
