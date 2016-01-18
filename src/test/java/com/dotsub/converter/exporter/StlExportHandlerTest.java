package com.dotsub.converter.exporter;

import com.dotsub.converter.SubtitleConverterTests;
import com.dotsub.converter.exporter.impl.StlExportHandler;
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
 * Date: 16-01-13.
 */
public class StlExportHandlerTest extends SubtitleConverterTests {

    private SubtitleExportHandler stlExportHandler = new StlExportHandler();

    @Test
    public void testStlExport() throws Exception {
        //create test items
        List<SubtitleItem> subtitleItemList = new ArrayList<>();
        subtitleItemList.add(new SubtitleItem(0, 1000, "test line 1"));
        subtitleItemList.add(new SubtitleItem(1000, 1000, "test line 2"));
        subtitleItemList.add(new SubtitleItem(2000, 1000, "test multi\nline"));
        subtitleItemList.add(new SubtitleItem(3000, 1000, "test line 4"));

        assertEquals("Spruce Subtitle", stlExportHandler.getFormatName());

        String srtFile = stlExportHandler.exportSubtitles(subtitleItemList, new Configuration());
        assertNotNull(srtFile);

        String[] lines = srtFile.split("\n");
        //output should be
        assertEquals(9, lines.length);
        assertEquals("00:00:00:00 , 00:00:01:00 , test line 1", lines[5]);
        assertEquals("00:00:01:00 , 00:00:02:00 , test line 2", lines[6]);
        assertEquals("00:00:02:00 , 00:00:03:00 , test multi|line", lines[7]);
    }
}
