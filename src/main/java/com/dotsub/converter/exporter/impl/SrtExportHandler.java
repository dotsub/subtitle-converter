package com.dotsub.converter.exporter.impl;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-11.
 */
public class SrtExportHandler extends AbstractVelocityExportHandler {

    @Override
    public String getFormatName() {
        return "SubRip";
    }

    @Override
    protected String getTemplateName() {
        return "template.srt.vm";
    }
}
