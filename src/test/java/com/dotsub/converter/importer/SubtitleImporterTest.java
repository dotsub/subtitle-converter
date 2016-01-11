package com.dotsub.converter.importer;

import com.dotsub.converter.SubtitleConverterApplicationTests;
import com.dotsub.converter.exception.FileNotSupportedException;
import com.dotsub.converter.model.SubtitleItem;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-11.
 */
public class SubtitleImporterTest extends SubtitleConverterApplicationTests {

    @Autowired
    private SubtitleImporter subtitleImporter;

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    public void testImportFormatIsDetected() throws Exception {
        String[] files = {"test.sbv", "test.vtt", "test.srt", "test.dfxp"};

        for (String file : files) {
            Resource resource = resourceLoader.getResource("classpath:" + file);
            try {
                List<SubtitleItem> subtitleItemList = subtitleImporter.importFile(resource.getInputStream());
                assertNotNull(subtitleItemList);
                assertTrue(subtitleItemList.size() > 1);
            }
            catch (FileNotSupportedException e) {
                //should not happen
                fail(String.format("No importer was able to import %s", file));
            }
        }
    }

    @Test
    public void testNotSubtitleFile() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:test.txt");
        try {
            subtitleImporter.importFile(resource.getInputStream());
            fail("This file should not be importable.");
        }
        catch (FileNotSupportedException e) {
            //should happen this file is not importable
        }
    }
}
