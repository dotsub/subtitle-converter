package com.dotsub.converter.exporter.impl;

import com.dotsub.converter.model.Configuration;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-11.
 */
public class DfxpExportHandler extends AbstractVelocityExportHandler {

    @Override
    public String getFormatName() {
        return "Timed-Text (DFXP)";
    }

    @Override
    protected String getTemplateName() {
        return "template.dfxp.vm";
    }
}
