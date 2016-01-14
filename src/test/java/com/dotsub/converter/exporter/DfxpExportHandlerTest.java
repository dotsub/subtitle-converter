package com.dotsub.converter.exporter;

import com.dotsub.converter.SubtitleConverterTests;
import com.dotsub.converter.exporter.impl.DfxpExportHandler;
import com.dotsub.converter.model.SubtitleItem;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-12.
 */
public class DfxpExportHandlerTest extends SubtitleConverterTests {

    private SubtitleExportHandler dfxpExportHandler = new DfxpExportHandler();

    @Test
    public void testDfxpExport() throws Exception {
        //create test items
        List<SubtitleItem> subtitleItemList = new ArrayList<>();
        subtitleItemList.add(new SubtitleItem(0, 1000, "test line 1"));
        subtitleItemList.add(new SubtitleItem(1000, 1000, "test line 2"));
        subtitleItemList.add(new SubtitleItem(2000, 1000, "test multi\nline"));
        subtitleItemList.add(new SubtitleItem(3000, 1000, "test line 4"));

        assertEquals("Timed-Text (DFXP)", dfxpExportHandler.getFormatName());

        String srtFile = dfxpExportHandler.exportSubtitles(subtitleItemList);
        assertNotNull(srtFile);

        String[] lines = srtFile.split("\n");
        //output should be
        assertEquals(19, lines.length);
        assertEquals("<p begin='00:00:00.000' end='00:00:01.000' style=\"4\">test line 1</p>", lines[12].trim());
        assertEquals("<p begin='00:00:01.000' end='00:00:02.000' style=\"4\">test line 2</p>", lines[13].trim());
        assertTrue(lines[14].contains("<br/>"));
    }
}
