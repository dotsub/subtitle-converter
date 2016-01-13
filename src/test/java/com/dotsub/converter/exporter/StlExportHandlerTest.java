package com.dotsub.converter.exporter;

import com.dotsub.converter.SubtitleConverterApplicationTests;
import com.dotsub.converter.model.SubtitleItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-13.
 */
public class StlExportHandlerTest extends SubtitleConverterApplicationTests {

    @Autowired
    private SubtitleExportHandler stlExportHandler;

    @Test
    public void testSrtExport() throws Exception {
        //create test items
        List<SubtitleItem> subtitleItemList = new ArrayList<>();
        subtitleItemList.add(new SubtitleItem(0, 1000, "test line 1"));
        subtitleItemList.add(new SubtitleItem(1000, 1000, "test line 2"));
        subtitleItemList.add(new SubtitleItem(2000, 1000, "test multi\nline"));
        subtitleItemList.add(new SubtitleItem(3000, 1000, "test line 4"));

        assertEquals("Spruce Subtitle", stlExportHandler.getFormatName());

        String srtFile = stlExportHandler.exportSubtitles(subtitleItemList);
        assertNotNull(srtFile);

        String[] lines = srtFile.split("\n");
        //output should be
        assertEquals(4, lines.length);
        assertEquals("00:00:00:00 , 00:00:01:00 , test line 1", lines[0]);
        assertEquals("00:00:01:00 , 00:00:02:00 , test line 2", lines[1]);
        assertEquals("00:00:02:00 , 00:00:03:00 , test multi|line", lines[2]);
    }
}
