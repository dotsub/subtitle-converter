package com.dotsub.converter.importer;

import com.dotsub.converter.SubtitleConverterTests;
import com.dotsub.converter.exception.FileFormatException;
import com.dotsub.converter.importer.impl.StlImportHandler;
import com.dotsub.converter.model.Configuration;
import com.dotsub.converter.model.SubtitleItem;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-12.
 */
public class StlImportHandlerTest extends SubtitleConverterTests {

    private SubtitleImportHandler stlSubtitleImporter = new StlImportHandler();

    @Test
    public void testImportTestFile() throws Exception {

        List<SubtitleItem> subtitleItemList = stlSubtitleImporter.importFile(getFile("test.stl"), new Configuration());
        assertEquals(21, subtitleItemList.size());

        subtitleItemList = stlSubtitleImporter.importFile(getFile("test_pal.stl"), new Configuration());
        assertEquals(21, subtitleItemList.size());
        //make sure the hours were properly dropped
        subtitleItemList.forEach(
                item -> assertTrue(item.getStartTime() < 3600000)
        );
    }

    @Test
    public void testImportTestFileAlternateFrameRate() throws Exception {
        final Configuration configuration = new Configuration();
        configuration.setImportFps(60d);
        List<SubtitleItem> subtitleItemList = stlSubtitleImporter.importFile(getFile("test.stl"), configuration);
        assertEquals(21, subtitleItemList.size());
        //times should be changes to do the FPS being 60
        SubtitleItem mockItem = new SubtitleItem(1100, 5000, "[Dotsub Any Video Any Language]");
        //check an item in the file.
        SubtitleItem subtitleItem = subtitleItemList.get(0);
        assertEquals(mockItem.getStartTime(), subtitleItem.getStartTime());
        assertEquals(mockItem.getDuration(), subtitleItem.getDuration());
        assertEquals(mockItem.getContent(), subtitleItem.getContent());
    }

    @Test
    public void testImportFileWrongFormat() throws Exception {
        try {
            stlSubtitleImporter.importFile(getFile("test.srt"), new Configuration());
        }
        catch (FileFormatException e) {
            //file format exception should be thrown since this is a vtt file
        }
    }
}
