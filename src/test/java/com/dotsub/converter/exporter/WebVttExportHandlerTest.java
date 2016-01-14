package com.dotsub.converter.exporter;

import com.dotsub.converter.SubtitleConverterTests;
import com.dotsub.converter.exporter.impl.WebVttExportHandler;
import com.dotsub.converter.model.SubtitleItem;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-11.
 */
public class WebVttExportHandlerTest extends SubtitleConverterTests {

    private SubtitleExportHandler webVttExportHandler = new WebVttExportHandler();

    @Test
    public void testWebVttExport() throws Exception {
        //create test items
        List<SubtitleItem> subtitleItemList = new ArrayList<>();
        subtitleItemList.add(new SubtitleItem(0, 1000, "test line 1"));
        subtitleItemList.add(new SubtitleItem(1000, 1000, "test line 2"));
        subtitleItemList.add(new SubtitleItem(2000, 1000, "test multi\nline"));
        subtitleItemList.add(new SubtitleItem(3000, 1000, "test line 4"));

        assertEquals("WebVTT", webVttExportHandler.getFormatName());

        String srtFile = webVttExportHandler.exportSubtitles(subtitleItemList);
        assertNotNull(srtFile);

        String[] lines = srtFile.split("\n");
        //output should be
        assertEquals(14, lines.length);
        assertEquals("WEBVTT", lines[0]);

        assertEquals("00:00:00.000 --> 00:00:01.000", lines[2]);
        assertEquals("test line 1", lines[3]);
        assertEquals("", lines[4]);

        assertEquals("00:00:01.000 --> 00:00:02.000", lines[5]);
        assertEquals("test line 2", lines[6]);
        //multi line subtitle
        assertEquals("00:00:02.000 --> 00:00:03.000", lines[8]);
        assertEquals("test multi", lines[9]);
        assertEquals("line", lines[10]);
        assertEquals("", lines[11]);
    }
}
