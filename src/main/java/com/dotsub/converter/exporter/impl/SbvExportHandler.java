package com.dotsub.converter.exporter.impl;

import org.springframework.stereotype.Component;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-12.
 */
@Component
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
