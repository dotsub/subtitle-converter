package com.dotsub.converter.exporter.impl.scc;

/**
 * Control codes for channel 2 & 4 SCC captions.
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-21.
 */
public class SccChannel2Provider extends SccChannel1Provider implements EncodingProvider {

    /**
     * Creates a new instance of a SccChannel2 encoding provider.
     */
    public SccChannel2Provider() {
        specialCharsHighBit = (byte) 0x19;
        frenchSpanishHighBit = (byte) 0x1a;
        germanDanishHighBit = (byte) 0x9b;
    }
}
