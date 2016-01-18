package com.dotsub.converter.importer;

import com.dotsub.converter.SubtitleConverterTests;
import com.dotsub.converter.exception.FileFormatException;
import com.dotsub.converter.importer.impl.WebVttImportHandler;
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
public class WebVttImportHandlerTest extends SubtitleConverterTests {

    private SubtitleImportHandler webVttImportHandler = new WebVttImportHandler();

    @Test
    public void testImportTestFile() throws Exception {
        List<SubtitleItem> subtitleItemList = webVttImportHandler.importFile(getFile("test.vtt"), new Configuration());
        assertEquals(21, subtitleItemList.size());

        //test file with no indexes
        subtitleItemList = webVttImportHandler.importFile(getFile("test_no_indexes.vtt"), new Configuration());
        assertEquals(21, subtitleItemList.size());
    }

    @Test
    public void testImportFileWrongFormat() throws Exception {
        try {
            webVttImportHandler.importFile(getFile("test.srt"), new Configuration());
        }
        catch (FileFormatException e) {
            //file format exception should be thrown since this is a vtt file
        }
    }
}
