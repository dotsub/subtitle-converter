package com.dotsub.converter.importer;

import com.dotsub.converter.SubtitleConverterTests;
import com.dotsub.converter.exception.FileFormatException;
import com.dotsub.converter.importer.impl.QtTextImportHandler;
import com.dotsub.converter.model.Configuration;
import com.dotsub.converter.model.SubtitleItem;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-14.
 */
public class QtTextImportHandlerTest extends SubtitleConverterTests {

    private QtTextImportHandler qtTextImportHandler = new QtTextImportHandler();

    @Test
    public void testImportTestFile() throws Exception {
        List<SubtitleItem> subtitleItemList = qtTextImportHandler.importFile(getFile("test.qt"), new Configuration());
        assertEquals(21, subtitleItemList.size());

        //mock item
        SubtitleItem mockItem = new SubtitleItem(1250, 5000, "[Dotsub Any Video Any Language]");
        //check for line properly handled.
        SubtitleItem subtitleItem = subtitleItemList.get(0);
        assertEquals(mockItem.getStartTime(), subtitleItem.getStartTime());
        assertEquals(mockItem.getDuration(), subtitleItem.getDuration());
        assertEquals(mockItem.getContent(), subtitleItem.getContent());

        mockItem = new SubtitleItem(10780, 4980, "[How to lift your video search results higher\n" +
                "while increasing Sponsor and Ad Value]");
        //check for line break properly handled.
        subtitleItem = subtitleItemList.get(2);
        assertEquals(mockItem.getStartTime(), subtitleItem.getStartTime());
        assertEquals(mockItem.getDuration(), subtitleItem.getDuration());
        assertEquals(mockItem.getContent(), subtitleItem.getContent());
    }

    @Test
    public void testImportFileWrongFormat() throws Exception {
        try {
            qtTextImportHandler.importFile(getFile("test.vtt"), new Configuration());
        }
        catch (FileFormatException e) {
            //file format exception should be thrown since this is a vtt file
        }
    }
}
