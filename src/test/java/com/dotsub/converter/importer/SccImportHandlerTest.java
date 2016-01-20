package com.dotsub.converter.importer;

import com.dotsub.converter.SubtitleConverterTests;
import com.dotsub.converter.exception.FileFormatException;
import com.dotsub.converter.importer.impl.SccImportHandler;
import com.dotsub.converter.model.Configuration;
import com.dotsub.converter.model.SubtitleItem;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-19.
 */
public class SccImportHandlerTest extends SubtitleConverterTests {

    private SubtitleImportHandler sccImportHandler = new SccImportHandler();

    @Test
    public void testImportTestFile() throws Exception {

        final Configuration configuration = sccImportHandler.getDefaultConfiguration();

        List<SubtitleItem> subtitleItemList = sccImportHandler.importFile(getFile("test.scc"), configuration);
        assertEquals(21, subtitleItemList.size());

        //check line breaks are handled correctly
        assertEquals("Online videos are becoming more\nand more important", subtitleItemList.get(3).getContent());
    }

    @Test
    public void testImportFileWrongFormat() throws Exception {
        try {
            sccImportHandler.importFile(getFile("test.vtt"), new Configuration());
        }
        catch (FileFormatException e) {
            //file format exception should be thrown since this is a vtt file
        }
    }
}
