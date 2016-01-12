package com.dotsub.converter.exporter;

import com.dotsub.converter.SubtitleConverterApplicationTests;
import com.dotsub.converter.exporter.impl.SrtExportHandler;
import com.dotsub.converter.model.SubtitleItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-11.
 */
public class SrtExportHandlerTest extends SubtitleConverterApplicationTests {

    @Autowired
    private SrtExportHandler srtExportHandler;

    @Test
    public void testSrtExport() throws Exception {
        //create test items
        List<SubtitleItem> subtitleItemList = new ArrayList<>();
        subtitleItemList.add(new SubtitleItem(0, 1000, "test line 1"));
        subtitleItemList.add(new SubtitleItem(1000, 1000, "test line 2"));
        subtitleItemList.add(new SubtitleItem(2000, 1000, "test multi\nline"));
        subtitleItemList.add(new SubtitleItem(3000, 1000, "test line 4"));

        assertEquals("SubRip", srtExportHandler.getFormatName());

        String srtFile = srtExportHandler.exportSubtitles(subtitleItemList);
        assertNotNull(srtFile);

        String[] lines = srtFile.split("\n");
        //output should be
        assertEquals(16, lines.length);
        assertEquals("00:00:00,000 --> 00:00:01,000", lines[1]);
        assertEquals("test line 1", lines[2]);
        assertEquals("", lines[3]);
        assertEquals("2", lines[4]);
        assertEquals("00:00:01,000 --> 00:00:02,000", lines[5]);
        assertEquals("test line 2", lines[6]);
        //multi line subtitle
        assertEquals("00:00:02,000 --> 00:00:03,000", lines[9]);
        assertEquals("test multi", lines[10]);
        assertEquals("line", lines[11]);
        assertEquals("", lines[12]);
    }
}
