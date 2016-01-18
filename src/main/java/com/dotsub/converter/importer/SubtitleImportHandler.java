package com.dotsub.converter.importer;

import com.dotsub.converter.model.Configuration;
import com.dotsub.converter.model.SubtitleItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-08.
 */
public interface SubtitleImportHandler {

    String getFormatName();

    /**
     * Gets the apps default subtitle export configuration.
     * @return A configuration object.
     */
    default Configuration getDefaultConfiguration() {
        return new Configuration();
    }

    List<SubtitleItem> importFile(InputStream inputStream, Configuration configuration) throws IOException;

}
