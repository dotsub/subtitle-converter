package com.dotsub.converter.exporter.impl;

import org.springframework.stereotype.Component;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-11.
 */
@Component
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
