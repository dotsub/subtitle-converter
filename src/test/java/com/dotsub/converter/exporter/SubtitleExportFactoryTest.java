package com.dotsub.converter.exporter;

import com.dotsub.converter.SubtitleConverterTests;
import com.dotsub.converter.exporter.impl.WebVttExportHandler;
import com.dotsub.converter.model.SubtitleFormat;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by: Francesco Benetti
 * For: Dotsub LLC.
 * Date: 13-04-16.
 */
public class SubtitleExportFactoryTest extends SubtitleConverterTests {

    private SubtitleExportFactory subtitleExportFactory = new SubtitleExportFactory();

    @Test
    public void testFactoryWebVttHandler() {
        SubtitleExportHandler subtitleExportHandler = subtitleExportFactory.createExportHandler(SubtitleFormat.VTT);
        assertTrue(subtitleExportHandler instanceof WebVttExportHandler);
    }

}
