package com.dotsub.converter.exporter.impl;

import com.dotsub.converter.exporter.CaptionUtil;
import com.dotsub.converter.exporter.SubtitleExportHandler;
import com.dotsub.converter.model.SubtitleItem;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.velocity.VelocityEngineUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-11.
 */
public abstract class AbstractVelocityExportHandler implements SubtitleExportHandler {

    @Autowired
    protected VelocityEngine velocityEngine;

    //can be overloaded so implementations can provide more context to their template.
    protected Map<String, Object> getAdditionalContext() {
        return new HashMap<>();
    }

    protected abstract String getTemplateName();

    @Override
    public String exportSubtitles(List<SubtitleItem> subtitles) {

        Map<String, Object> model = getAdditionalContext();
        model.put("subtitles", subtitles);
        //CaptionUtil has some helpful methods for rendering output
        model.put("CaptionUtil", CaptionUtil.class);

        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                getTemplateName(), "UTF-8", model);
    }
}
