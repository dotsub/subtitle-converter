package com.dotsub.converter.importer;

import com.dotsub.converter.SubtitleConverterTests;
import com.dotsub.converter.exception.FileFormatException;
import com.dotsub.converter.importer.impl.SrtImportHandler;
import com.dotsub.converter.model.SubtitleItem;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-08.
 */
public class SrtImportHandlerTest extends SubtitleConverterTests {

    private SubtitleImportHandler srtImportHandler = new SrtImportHandler();

    @Test
    public void testImportTestFile() throws Exception {
        List<SubtitleItem> subtitleItemList = srtImportHandler.importFile(getFile("test.srt"));
        assertEquals(94, subtitleItemList.size());

        //mock item
        SubtitleItem mockItem = new SubtitleItem(0, 2832, "[Brooks]\nDotsub's number one mission is to ensure");
        //check an item in the file.
        SubtitleItem subtitleItem = subtitleItemList.get(0);
        assertEquals(mockItem.getStartTime(), subtitleItem.getStartTime());
        assertEquals(mockItem.getDuration(), subtitleItem.getDuration());
        assertEquals(mockItem.getContent(), subtitleItem.getContent());
        assertNotNull(subtitleItem.toString());
        assertNotNull(subtitleItem.hashCode());
        assertEquals(mockItem, subtitleItem);

        subtitleItemList = srtImportHandler.importFile(getFile("test_pal.srt"));
        assertEquals(94, subtitleItemList.size());
        //make sure the hours were properly dropped
        subtitleItemList.forEach(
                item -> assertTrue(item.getStartTime() < 3600000)
        );
    }

    @Test
    public void testImportFileWrongFormat() throws Exception {
        try {
            srtImportHandler.importFile(getFile("test.vtt"));
        }
        catch (FileFormatException e) {
            //file format exception should be thrown since this is a vtt file
        }
    }
}
