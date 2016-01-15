package com.dotsub.converter.exporter.impl;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-14.
 */
public class QtTextExportHandler extends AbstractVelocityExportHandler {

    @Override
    protected String getTemplateName() {
        return "template.qt.vm";
    }

    @Override
    public String getFormatName() {
        return "QuickTime Text";
    }
}
