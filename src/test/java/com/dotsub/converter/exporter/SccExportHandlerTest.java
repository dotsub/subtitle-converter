package com.dotsub.converter.exporter;

import com.dotsub.converter.SubtitleConverterTests;
import com.dotsub.converter.exporter.impl.SccExportHandler;
import com.dotsub.converter.model.SubtitleItem;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 2016-01-21.
 */
public class SccExportHandlerTest extends SubtitleConverterTests {

    private SubtitleExportHandler sccExportHandler = new SccExportHandler();

    @Test
    public void testSccExport() throws Exception {
        List<SubtitleItem> subtitleItemList = new ArrayList<>();
        subtitleItemList.add(new SubtitleItem(0, 1000, "test line 1"));
        subtitleItemList.add(new SubtitleItem(1000, 1000, "test line 2"));
        subtitleItemList.add(new SubtitleItem(2000, 1000, "test\nmulti line"));

        assertEquals("Scenarist_SCC V1.0", sccExportHandler.getFormatName());

        String sccFile = sccExportHandler.exportSubtitles(subtitleItemList,
                sccExportHandler.getDefaultConfiguration());
        assertNotNull(sccFile);

        String[] lines = sccFile.split("\n");
        //output should be
        assertEquals(9, lines.length);

    }
}
