package com.dotsub.converter.importer;

import com.dotsub.converter.SubtitleConverterTests;
import com.dotsub.converter.exception.FileNotSupportedException;
import com.dotsub.converter.model.SubtitleItem;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-11.
 */
public class SubtitleImporterTest extends SubtitleConverterTests {

    private SubtitleImporter subtitleImporter = new SubtitleImporter();

    @Test
    public void testImportFormatIsDetected() throws Exception {
        String[] files = {"test.sbv", "test.vtt", "test.srt", "test.dfxp", "test.qt", "test.stl"};

        for (String file : files) {
            try {
                List<SubtitleItem> subtitleItemList = subtitleImporter.importFile(getFile(file));
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
        try {
            subtitleImporter.importFile(getFile("test.txt"));
            fail("This file should not be importable.");
        }
        catch (FileNotSupportedException e) {
            //should happen this file is not importable
        }
    }
}
