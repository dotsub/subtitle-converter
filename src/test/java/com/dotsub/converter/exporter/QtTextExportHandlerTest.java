package com.dotsub.converter.exporter;

import com.dotsub.converter.SubtitleConverterTests;
import com.dotsub.converter.exporter.impl.QtTextExportHandler;
import com.dotsub.converter.model.SubtitleItem;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-14.
 */
public class QtTextExportHandlerTest extends SubtitleConverterTests {

    private QtTextExportHandler qtTextImportHandler = new QtTextExportHandler();

    @Test
    public void testSsaExport() throws Exception {
        List<SubtitleItem> subtitleItemList = new ArrayList<>();
        subtitleItemList.add(new SubtitleItem(0, 1000, "test line 1"));
        subtitleItemList.add(new SubtitleItem(1000, 1000, "test line 2"));
        subtitleItemList.add(new SubtitleItem(2000, 1000, "test\nmulti line"));

        assertEquals("QuickTime Text", qtTextImportHandler.getFormatName());

        String srtFile = qtTextImportHandler.exportSubtitles(subtitleItemList);
        assertNotNull(srtFile);

        String[] lines = srtFile.split("\n");
        //output should be
        assertEquals(9, lines.length);

        assertTrue(lines[0].startsWith("{QTtext}"));
        assertEquals("[00:00:00.00]", lines[1]);
        assertEquals("test line 1", lines[2]);
        assertEquals("[00:00:01.00]", lines[3]);
        assertEquals("test line 2", lines[4]);
        assertEquals("[00:00:02.00]", lines[5]);
        assertEquals("test", lines[6]);
        assertEquals("multi line", lines[7]);
        assertEquals("[00:00:03.00]", lines[8]);
    }


}
