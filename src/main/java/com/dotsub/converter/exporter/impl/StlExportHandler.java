package com.dotsub.converter.exporter.impl;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-13.
 */
public class StlExportHandler extends AbstractVelocityExportHandler {

    @Override
    protected String getTemplateName() {
        return "template.stl.vm";
    }

    @Override
    public String getFormatName() {
        return "Spruce Subtitle";
    }
}
