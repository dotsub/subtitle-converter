package com.dotsub.converter.exporter.impl.scc;

/**
 * Control codes for channel 2 & 4 SCC captions.
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 16-01-21.
 */
public class SccChannel2Provider extends SccChannel1Provider implements SccEncodingProvider {

    /**
     * Creates a new instance of a SccChannel2 encoding provider.
     * Most values are the same as if been written on channel one.
     * The only exception is the control bits uses to denote extended character sets.
     */
    public SccChannel2Provider() {
        specialCharsHighBit = (byte) 0x19;
        frenchSpanishHighBit = (byte) 0x1a;
        germanDanishHighBit = (byte) 0x9b;
    }
}
