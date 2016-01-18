package com.dotsub.converter.importer;

import com.dotsub.converter.exception.FileFormatException;
import com.dotsub.converter.exception.FileNotSupportedException;
import com.dotsub.converter.importer.impl.*;
import com.dotsub.converter.model.Configuration;
import com.dotsub.converter.model.SubtitleItem;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-11.
 */
public class SubtitleImporter {

    private static final Log log = LogFactory.getLog(SubtitleImporter.class);

    private List<SubtitleImportHandler> importHandlers = new ArrayList<>();

    /**
     * Creates a new importer instance and adds the default parsers.
     */
    public SubtitleImporter() {
        //TODO: Handle this via reflection?
        this.addImportHandler(new QtTextImportHandler());
        this.addImportHandler(new SrtImportHandler());
        this.addImportHandler(new StlImportHandler());
        this.addImportHandler(new DfxpImportHandler());
        this.addImportHandler(new SsaImportHandler());
        this.addImportHandler(new WebVttImportHandler());
        this.addImportHandler(new SbvImportHandler());
    }

    public void addImportHandler(SubtitleImportHandler importHandler) {
        importHandlers.add(importHandler);
    }

    /**
     * Iterates all importer implementations to find the one that can handle this file.
     * If not found it throws FileNotSupportedException
     * @param inputStream the file being imported
     * @return the SubtitleItems from the file
     * @throws IOException when there are issues reading the file.
     */
    public List<SubtitleItem> importFile(InputStream inputStream, Configuration configuration) throws IOException {
        //make a copy of the inputSteam since it can only be read once.
        byte[] fileContents = IOUtils.toByteArray(inputStream);
        //iterate all the know importers to determine which can import the file
        for (SubtitleImportHandler importHandler : importHandlers) {
            try {
                log.info(String.format("Importing with %s", importHandler.getFormatName()));
                List<SubtitleItem> subtitleItems = importHandler.importFile(
                        new ByteArrayInputStream(fileContents), configuration);
                log.info(String.format("File successfully imported. Format %s.", importHandler.getFormatName()));
                return subtitleItems;
            }
            catch (FileFormatException e) {
                log.debug(String.format("Unable to import as %s. This file is not in this format",
                        importHandler.getFormatName()));
            }
        }
        throw new FileNotSupportedException("Unable to import file. No parser was able to handle this file format.");
    }
}
