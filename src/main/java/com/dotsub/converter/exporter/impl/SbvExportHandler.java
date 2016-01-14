package com.dotsub.converter.exporter.impl;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-12.
 */
public class SbvExportHandler extends AbstractVelocityExportHandler {

    @Override
    protected String getTemplateName() {
        return "template.sbv.vm";
    }

    @Override
    public String getFormatName() {
        return "SubViewer";
    }
}
