package com.dotsub.converter.exporter;

import com.dotsub.converter.SubtitleConverterTests;
import com.dotsub.converter.exporter.impl.SbvExportHandler;
import com.dotsub.converter.model.Configuration;
import com.dotsub.converter.model.SubtitleItem;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-12.
 */
public class SbvExportHandlerTest extends SubtitleConverterTests {

    private SubtitleExportHandler sbvExportHandler = new SbvExportHandler();

    @Test
    public void testSbvExport() throws Exception {
        //create test items
        List<SubtitleItem> subtitleItemList = new ArrayList<>();
        subtitleItemList.add(new SubtitleItem(0, 1000, "test line 1"));
        subtitleItemList.add(new SubtitleItem(1000, 1000, "test line 2"));
        subtitleItemList.add(new SubtitleItem(2010, 1000, "test\nmulti line"));

        assertEquals("SubViewer", sbvExportHandler.getFormatName());

        String srtFile = sbvExportHandler.exportSubtitles(subtitleItemList, new Configuration());
        assertNotNull(srtFile);

        String[] lines = srtFile.split("\n");
        //output should be
        assertEquals(8, lines.length);

        assertEquals("00:00:00.00,00:00:01.00", lines[0]);
        assertEquals("test line 1", lines[1]);
        assertEquals("", lines[2]);

        assertEquals("00:00:01.00,00:00:02.00", lines[3]);
        assertEquals("test line 2", lines[4]);

        assertEquals("00:00:02.01,00:00:03.01", lines[6]);
        assertEquals("test[br]multi line", lines[7]);
    }

}
