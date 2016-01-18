package com.dotsub.converter.exporter;

import com.dotsub.converter.model.Configuration;
import com.dotsub.converter.model.HorizontalPosition;
import com.dotsub.converter.model.SubtitleItem;
import com.dotsub.converter.model.VerticalPosition;

import java.util.List;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-11.
 */
public interface SubtitleExportHandler {

    String getFormatName();

    /**
     * Gets the apps default subtitle export configuration.
     * @return A configuration object.
     */
    default Configuration getDefaultConfiguration() {
        Configuration configuration = new Configuration();
        //font
        configuration.setFont("Arial");
        configuration.setFontSize("16");
        //position
        configuration.setHorizontalPosition(HorizontalPosition.CENTER);
        configuration.setVerticalPosition(VerticalPosition.BOTTOM);
        //color: White text on transparent background
        configuration.setBackgroundColor(0);
        configuration.setBackgroundAlpha(0);
        configuration.setColor(0xFFFFFF);

        return configuration;
    }

    String exportSubtitles(List<SubtitleItem> subtitles, Configuration configuration);

}
