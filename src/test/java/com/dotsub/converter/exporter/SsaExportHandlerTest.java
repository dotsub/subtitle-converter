package com.dotsub.converter.exporter;

import com.dotsub.converter.SubtitleConverterTests;
import com.dotsub.converter.exporter.impl.SsaExportHandler;
import com.dotsub.converter.model.SubtitleItem;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-13.
 */
public class SsaExportHandlerTest extends SubtitleConverterTests {

    private SubtitleExportHandler ssaExportHandler = new SsaExportHandler();

    @Test
    public void testSsaExport() throws Exception {
        List<SubtitleItem> subtitleItemList = new ArrayList<>();
        subtitleItemList.add(new SubtitleItem(0, 1000, "test line 1"));
        subtitleItemList.add(new SubtitleItem(1000, 1000, "test line 2"));
        subtitleItemList.add(new SubtitleItem(2010, 1000, "test\nmulti line"));

        assertEquals("SubStation Alpha", ssaExportHandler.getFormatName());

        String srtFile = ssaExportHandler.exportSubtitles(subtitleItemList);
        assertNotNull(srtFile);

        String[] lines = srtFile.split("\n");
        //output should be
        assertEquals(16, lines.length);

        assertEquals("Dialogue: 0,0:00:00.00,0:00:01.00,Default,00001,0000,0000,0000,,test line 1", lines[13]);
        assertEquals("Dialogue: 0,0:00:01.00,0:00:02.00,Default,00001,0000,0000,0000,,test line 2", lines[14]);
        assertEquals("Dialogue: 0,0:00:02.01,0:00:03.01,Default,00001,0000,0000,0000,,test\\Nmulti line", lines[15]);
    }
}
