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
public class WebVttImportHandlerTest extends SubtitleConverterApplicationTests {

    @Autowired
    private SubtitleImportHandler webVttImportHandler;

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    public void testImportTestFile() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:test.vtt");

        List<SubtitleItem> subtitleItemList = webVttImportHandler.importFile(resource.getInputStream());
        assertEquals(21, subtitleItemList.size());
    }

    @Test
    public void testImportFileWrongFormat() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:test.srt");

        try {
            webVttImportHandler.importFile(resource.getInputStream());
        }
        catch (FileFormatException e) {
            //file format exception should be thrown since this is a vtt file
        }
    }
}
