package com.dotsub.converter.exporter.impl;

import org.springframework.stereotype.Component;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-11.
 */
@Component
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
