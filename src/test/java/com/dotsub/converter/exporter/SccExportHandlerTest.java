package com.dotsub.converter.exporter;

import com.dotsub.converter.SubtitleConverterTests;
import com.dotsub.converter.exporter.impl.SccExportHandler;
import com.dotsub.converter.exporter.impl.scc.SccCaptionMode;
import com.dotsub.converter.model.Configuration;
import com.dotsub.converter.model.SubtitleItem;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 2016-01-21.
 */
public class SccExportHandlerTest extends SubtitleConverterTests {

    private SubtitleExportHandler sccExportHandler = new SccExportHandler();

    @Test
    public void testSccExport() throws Exception {
        List<SubtitleItem> subtitleItemList = getSubtitleItems();

        assertEquals("Scenarist_SCC V1.0", sccExportHandler.getFormatName());

        final Configuration defaultConfiguration = sccExportHandler.getDefaultConfiguration();
        String sccFile = sccExportHandler.exportSubtitles(subtitleItemList,
                defaultConfiguration);
        assertNotNull(sccFile);

        String[] lines = sccFile.split("\n");
        //output should be
        assertEquals(9, lines.length);

    }

    @Test
    public void testExportChannels() throws Exception {
        final Configuration defaultConfiguration = sccExportHandler.getDefaultConfiguration();
        List<SubtitleItem> subtitleItemList = getSubtitleItems();

        String sccFile = sccExportHandler.exportSubtitles(subtitleItemList,
                defaultConfiguration);

        assertNotNull(sccFile);

        //export on channels 2 - 5
        for (int count = 2; count < 6 ; count++) {
            try {
                defaultConfiguration.setChannel(count);
                sccExportHandler.exportSubtitles(subtitleItemList,
                        defaultConfiguration);
                assertNotNull(sccFile);

                String[] lines = sccFile.split("\n");
                //output should be
                assertEquals(9, lines.length);
                if (count == 5) {
                    fail("SCC can not be exported on channel 5");
                }
            }
            catch (IllegalArgumentException exception) {
                //should only happen for channel 5
                if (count != 5) {
                    fail("Channels 2 - 4 should be exportable");
                }
            }
        }
    }

    @Test
    public void testExportOtherFormats() throws Exception {
        final Configuration defaultConfiguration = sccExportHandler.getDefaultConfiguration();
        List<SubtitleItem> subtitleItemList = getSubtitleItems();

        String sccFile = sccExportHandler.exportSubtitles(subtitleItemList, defaultConfiguration);

        //test other format types
        SccCaptionMode[] testModes = { SccCaptionMode.ROLL_UP2, SccCaptionMode.ROLL_UP3, SccCaptionMode.ROLL_UP4 };
        for (SccCaptionMode mode : testModes) {
            defaultConfiguration.setChannel(1);
            defaultConfiguration.setCaptionMode(mode);
            sccExportHandler.exportSubtitles(subtitleItemList, defaultConfiguration);
            assertNotNull(sccFile);

            String[] lines = sccFile.split("\n");
            //output should be
            assertEquals(9, lines.length);
        }

    }

    @Test
    public void testExtendedCharEncoding() throws Exception {
        List<SubtitleItem> subtitleItemList = new ArrayList<>();
        final Configuration defaultConfiguration = sccExportHandler.getDefaultConfiguration();

        //test for chars and encoding
        subtitleItemList.clear();
        subtitleItemList.add(new SubtitleItem(0, 1000, "1234567890+=-_÷!@#$%^&*()"));
        subtitleItemList.add(new SubtitleItem(1000, 1000, "\"'{}[]\\|/<>,.?`~"));
        subtitleItemList.add(new SubtitleItem(2000, 1000, "çå´ˆøüá:;éíóúÑñ"));
        String sccFile = sccExportHandler.exportSubtitles(subtitleItemList, defaultConfiguration);
        assertNotNull(sccFile);

        String[] lines = sccFile.split("\n");
        //output should be
        assertEquals(9, lines.length);

        //extended and special
        subtitleItemList.clear();
        subtitleItemList.add(new SubtitleItem(0, 1000, "®°½¿™¢£♪àèâêîôû"));
        //EXTENDED French/Spanish
        subtitleItemList.add(new SubtitleItem(1000, 1000, "ÁÉÓÚÜü‘¡*’—©℠•“”ÀÂÇÈÊËëÎÏïÔÙùÛ«»"));
        //EXTENDED Portuguese/German/Danish
        subtitleItemList.add(new SubtitleItem(2000, 1000, "ÃãÍÌìÒòÕõ¦ÄäÖöß¥¤|ÅåØø┌┐┕┘"));
        sccExportHandler.exportSubtitles(subtitleItemList, defaultConfiguration);
        assertNotNull(sccFile);

        lines = sccFile.split("\n");
        //output should be
        assertEquals(9, lines.length);

    }

    private List<SubtitleItem> getSubtitleItems() {
        List<SubtitleItem> subtitleItemList = new ArrayList<>();
        subtitleItemList.add(new SubtitleItem(0, 1000, "ABCDEFGHIJKLMNOPQRSTUVWXYZ"));
        subtitleItemList.add(new SubtitleItem(1000, 1000, "abcdefghijklmnopqrstuvwxyz"));
        subtitleItemList.add(new SubtitleItem(2000, 1000, "test\nmulti lines"));
        return subtitleItemList;
    }
}
