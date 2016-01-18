package com.dotsub.converter.importer;

import com.dotsub.converter.SubtitleConverterTests;
import com.dotsub.converter.importer.impl.DfxpImportHandler;
import com.dotsub.converter.model.Configuration;
import com.dotsub.converter.model.SubtitleItem;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-08.
 */
public class DfxpImportHandlerTest extends SubtitleConverterTests {

    private SubtitleImportHandler dfxpImportHandler = new DfxpImportHandler();

    @Test
    public void testImportTestFile() throws Exception {

        List<SubtitleItem> subtitleItemList = dfxpImportHandler.importFile(getFile("test.dfxp"), new Configuration());
        assertEquals(14, subtitleItemList.size());
    }
}
