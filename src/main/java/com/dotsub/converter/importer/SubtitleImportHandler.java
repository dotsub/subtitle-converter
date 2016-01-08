package com.dotsub.converter.importer;

import com.dotsub.converter.model.SubtitleItem;

import java.io.InputStream;
import java.util.List;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-08.
 */
public interface SubtitleImportHandler {

    public List<SubtitleItem> importFile(InputStream inputStream);

}
