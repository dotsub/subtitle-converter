package com.dotsub.converter.importer.impl;

import com.dotsub.converter.importer.SubtitleImportHandler;
import com.dotsub.converter.model.Configuration;
import com.dotsub.converter.model.SubtitleItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * Created by: Francesco Benetti
 * For: Dotsub LLC.
 * Date: 30-03-16.
 */
public class JsonImportHandler implements SubtitleImportHandler {

    private static final Log log = LogFactory.getLog(JsonImportHandler.class);

    @Override
    public String getFormatName() {
        return "JSON";
    }

    @Override
    public List<SubtitleItem> importFile(InputStream inputStream, Configuration configuration) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = IOUtils.toString(inputStream, "UTF-8");
        return Arrays.asList(mapper.readValue(jsonContent, SubtitleItem[].class));
    }
}

