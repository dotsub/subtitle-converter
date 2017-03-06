package com.dotsub.converter.importer;

import com.dotsub.converter.SubtitleConverterTests;
import com.dotsub.converter.importer.impl.JsonImportHandler;
import com.dotsub.converter.model.Configuration;
import com.dotsub.converter.model.SubtitleItem;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by: Francesco Benetti
 * For: Dotsub LLC.
 * Date: 16-03-30.
 */
public class JsonImportHandlerTest extends SubtitleConverterTests {

    private SubtitleImportHandler jsonImportHandler = new JsonImportHandler();

    @Test
    public void testImportTestFile() throws Exception {
        List<SubtitleItem> subtitleItemList = jsonImportHandler.importFile(getFile("test.json"), new Configuration());
        assertEquals(2, subtitleItemList.size());
        SubtitleItem firstSubtitleItem = subtitleItemList.get(0);
        assertEquals("First Line", firstSubtitleItem.getContent());
    }
}
