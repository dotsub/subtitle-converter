package com.dotsub.converter.exporter;

import com.dotsub.converter.SubtitleConverterTests;
import com.dotsub.converter.exporter.impl.DfxpExportHandler;
import com.dotsub.converter.model.Configuration;
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

        String dfxpFile = dfxpExportHandler.exportSubtitles(subtitleItemList,
                dfxpExportHandler.getDefaultConfiguration());
        assertNotNull(dfxpFile);

        String[] lines = dfxpFile.split("\n");
        //output should be
        assertEquals(16, lines.length);
        assertEquals("<p begin='00:00:00.000' end='00:00:01.000' style=\"1\">test line 1</p>", lines[9].trim());
        assertEquals("<p begin='00:00:01.000' end='00:00:02.000' style=\"1\">test line 2</p>", lines[10].trim());
        assertTrue(lines[11].contains("<br/>"));

        //check formatting
        assertTrue(lines[4].contains("16"));
        assertTrue(lines[4].contains("16"));
        assertTrue(lines[4].contains("tts:color=\"#FFFFFF\""));
        assertTrue(lines[4].contains("tts:backgroundColor=\"#00000000\""));

        //test alternate formatting
        final Configuration defaultConfiguration = dfxpExportHandler.getDefaultConfiguration();
        final String font = "Comic Sans";
        defaultConfiguration.setFont(font);
        final String fontSize = "10";
        defaultConfiguration.setFontSize(fontSize);
        dfxpFile = dfxpExportHandler.exportSubtitles(subtitleItemList, defaultConfiguration);

        lines = dfxpFile.split("\n");
        assertTrue(lines[4].contains(font));
        assertTrue(lines[4].contains(fontSize));
    }
}
