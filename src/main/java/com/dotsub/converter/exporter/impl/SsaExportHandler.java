package com.dotsub.converter.exporter.impl;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-13.
 */
public class SsaExportHandler extends AbstractVelocityExportHandler {

    @Override
    protected String getTemplateName() {
        return "template.ssa.vm";
    }

    @Override
    public String getFormatName() {
        return "SubStation Alpha";
    }
}
