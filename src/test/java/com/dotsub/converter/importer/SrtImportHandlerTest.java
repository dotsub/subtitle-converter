package com.dotsub.converter.importer;

import com.dotsub.converter.SubtitleConverterApplicationTests;
import com.dotsub.converter.exception.FileFormatException;
import com.dotsub.converter.model.SubtitleItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-08.
 */
public class SrtImportHandlerTest extends SubtitleConverterApplicationTests {

    @Autowired
    private SubtitleImportHandler srtImportHandler;

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    public void testImportTestFile() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:test.srt");

        List<SubtitleItem> subtitleItemList = srtImportHandler.importFile(resource.getInputStream());
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

        resource = resourceLoader.getResource("classpath:test_pal.srt");
        subtitleItemList = srtImportHandler.importFile(resource.getInputStream());
        assertEquals(94, subtitleItemList.size());
        //make sure the hours were properly dropped
        subtitleItemList.forEach(
                item -> assertTrue(item.getStartTime() < 3600000)
        );
    }

    @Test
    public void testImportFileWrongFormat() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:test.vtt");

        try {
            srtImportHandler.importFile(resource.getInputStream());
        }
        catch (FileFormatException e) {
            //file format exception should be thrown since this is a vtt file
        }
    }
}
