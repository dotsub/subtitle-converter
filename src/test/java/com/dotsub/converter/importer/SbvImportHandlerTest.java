package com.dotsub.converter.importer;

import com.dotsub.converter.SubtitleConverterTests;
import com.dotsub.converter.exception.FileFormatException;
import com.dotsub.converter.importer.impl.SbvImportHandler;
import com.dotsub.converter.model.Configuration;
import com.dotsub.converter.model.SubtitleItem;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-11.
 */
public class SbvImportHandlerTest extends SubtitleConverterTests {

    private SubtitleImportHandler sbvImportHandler = new SbvImportHandler();

    @Test
    public void testImportTestFile() throws Exception {
        List<SubtitleItem> subtitleItemList = sbvImportHandler.importFile(getFile("test.sbv"), new Configuration());
        assertEquals(21, subtitleItemList.size());
    }

    @Test
    public void testImportFileWrongFormat() throws Exception {
        try {
            sbvImportHandler.importFile(getFile("test.vtt"), new Configuration());
        }
        catch (FileFormatException e) {
            //file format exception should be thrown since this is a vtt file
        }
    }

}
