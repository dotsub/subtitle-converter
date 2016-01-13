package com.dotsub.converter.exporter.impl;

import org.springframework.stereotype.Component;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-13.
 */
@Component
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
