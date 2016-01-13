package com.dotsub.converter.importer;

import com.dotsub.converter.SubtitleConverterApplicationTests;
import com.dotsub.converter.exception.FileFormatException;
import com.dotsub.converter.model.SubtitleItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-12.
 */
public class StlImportHandlerTest extends SubtitleConverterApplicationTests {

    @Autowired
    private SubtitleImporter stlSubtitleImporter;

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    public void testImportTestFile() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:test.stl");

        List<SubtitleItem> subtitleItemList = stlSubtitleImporter.importFile(resource.getInputStream());
        assertEquals(21, subtitleItemList.size());

        resource = resourceLoader.getResource("classpath:test_pal.stl");
        subtitleItemList = stlSubtitleImporter.importFile(resource.getInputStream());
        assertEquals(21, subtitleItemList.size());
        //make sure the hours were properly dropped
        subtitleItemList.forEach(
                item -> assertTrue(item.getStartTime() < 3600000)
        );
    }

    @Test
    public void testImportFileWrongFormat() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:test.vtt");

        try {
            stlSubtitleImporter.importFile(resource.getInputStream());
        }
        catch (FileFormatException e) {
            //file format exception should be thrown since this is a vtt file
        }
    }
}
