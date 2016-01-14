package com.dotsub.converter.exporter.impl;

import com.dotsub.converter.exporter.CaptionUtil;
import com.dotsub.converter.exporter.SubtitleExportHandler;
import com.dotsub.converter.model.SubtitleItem;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-11.
 */
public abstract class AbstractVelocityExportHandler implements SubtitleExportHandler {

    protected VelocityEngine velocityEngine;

    /**
     * Creates a new instance with it's own velocityEngine.
     */
    public AbstractVelocityExportHandler() {
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        velocityEngine.init();
    }

    //can be overloaded so implementations can provide more context to their template.
    protected Map<String, Object> getAdditionalContext() {
        return new HashMap<>();
    }

    protected abstract String getTemplateName();

    @Override
    public String exportSubtitles(List<SubtitleItem> subtitles) {

        VelocityContext context = new VelocityContext();
        Map<String, Object> model = getAdditionalContext();
        //add the impl's extras
        for (Map.Entry<String, Object> entry : model.entrySet()) {
            context.put(entry.getKey(), entry.getValue());
        }
        context.put("subtitles", subtitles);
        //CaptionUtil has some helpful methods for rendering output
        context.put("CaptionUtil", CaptionUtil.class);

        StringWriter writer = new StringWriter();
        velocityEngine.mergeTemplate(getTemplateName(), "UTF-8", context, writer);
        return writer.toString();
    }
}
