package com.dotsub.converter.exception;

/**
 * Thrown when a file does not match the expected format.
 * Ex: If a SCC caption file was parsed by a SRT importer this error would be thrown.
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-08.
 */
public class FileFormatException extends RuntimeException {

    public FileFormatException(String message) {
        super(message);
    }
}
