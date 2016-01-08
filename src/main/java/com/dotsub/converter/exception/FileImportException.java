package com.dotsub.converter.exception;

/**
 * This exception is thrown when there is a format issue within a file. This is to denote an import issue
 * but not format issue. For format issues see: FileFormatException
 *
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-08.
 */
public class FileImportException extends RuntimeException {

    public FileImportException(String message) {
        super(message);
    }
}
