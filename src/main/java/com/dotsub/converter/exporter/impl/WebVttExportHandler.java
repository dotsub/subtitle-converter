package com.dotsub.converter.exporter.impl;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-11.
 */
public class WebVttExportHandler extends AbstractVelocityExportHandler {

    @Override
    public String getFormatName() {
        return "WebVTT";
    }

    @Override
    protected String getTemplateName() {
        return "template.vtt.vm";
    }

}
