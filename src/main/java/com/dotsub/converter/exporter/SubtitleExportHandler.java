package com.dotsub.converter.exporter;

import com.dotsub.converter.model.SubtitleItem;

import java.util.List;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-11.
 */
public interface SubtitleExportHandler {

    String getFormatName();

    String exportSubtitles(List<SubtitleItem> subtitles);

}
