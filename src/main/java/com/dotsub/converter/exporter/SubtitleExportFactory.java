package com.dotsub.converter.exporter;

import com.dotsub.converter.exporter.impl.*;
import com.dotsub.converter.model.SubtitleFormat;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by: Francesco Benetti
 * For: Dotsub LLC.
 * Date: 07-04-16.
 */
public class SubtitleExportFactory {

    private final Map<SubtitleFormat, SubtitleExportHandler> exportHandlerMap;

    /**
     * This map will be used to create a PushHandler for each OVP on runtime.
     */
    public SubtitleExportFactory() {

        Map<SubtitleFormat, SubtitleExportHandler> exportHandlers = new HashMap<>();
        exportHandlers.put(SubtitleFormat.DFXP, new DfxpExportHandler());
        exportHandlers.put(SubtitleFormat.WEB_VTT, new WebVttExportHandler());
        exportHandlers.put(SubtitleFormat.QT_TEXT, new QtTextExportHandler());
        exportHandlers.put(SubtitleFormat.SBV, new SbvExportHandler());
        exportHandlers.put(SubtitleFormat.SCC, new SccExportHandler());
        exportHandlers.put(SubtitleFormat.SRT, new SrtExportHandler());
        exportHandlers.put(SubtitleFormat.SSA, new SsaExportHandler());
        exportHandlers.put(SubtitleFormat.STL, new StlExportHandler());
        exportHandlerMap = Collections.unmodifiableMap(exportHandlers);
    }

    public SubtitleExportHandler createExportHandler(SubtitleFormat subtitleFormat) {
        return exportHandlerMap.get(subtitleFormat);
    }

}
