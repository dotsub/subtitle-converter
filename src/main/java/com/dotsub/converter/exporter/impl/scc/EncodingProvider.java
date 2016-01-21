package com.dotsub.converter.exporter.impl.scc;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-21.
 */
public interface EncodingProvider {

    /**
     * Takes a given character and encoders it for SCC.
     * @param character to be encoded.
     * @return the hex value that represents that character.
     */
    byte[] encodeChar(Character character);

}
