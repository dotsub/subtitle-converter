package com.dotsub.converter.exporter.impl.scc;

import com.dotsub.converter.model.Configuration;

/**
 * Created by: Brooks Lyrette
 * For: Dotsub LLC.
 * Date: 2016-01-21.
 */
public class SccViewOptions {

    protected EncodingProvider encodingProvider;
    protected SccChannelControlCodes sccChannelControlCodes;
    protected SccCaptionMode mode = SccCaptionMode.POP_ON;
    protected Configuration configuration;

    /**
     * Creates a new SccView based on the selected configuration.
     * @param configuration the configuration set for this export.
     */
    public SccViewOptions(Configuration configuration) {
        this.configuration = configuration;
        //process the config to get the right encoder and control codes
        if (configuration.getChannel() == 1 || configuration.getChannel() == 3) {
            encodingProvider = new SccChannel1Provider();
            if (configuration.getChannel() == 1) {
                sccChannelControlCodes = new SccChannelControlCodes(SccChannelControlCodes.channel1);
            }
            else {
                sccChannelControlCodes = new SccChannelControlCodes(SccChannelControlCodes.channel3);
            }
        }
        else if (configuration.getChannel() == 2 || configuration.getChannel() == 4) {
            encodingProvider = new SccChannel2Provider();
            if (configuration.getChannel() == 2) {
                sccChannelControlCodes = new SccChannelControlCodes(SccChannelControlCodes.channel2);
            }
            else {
                sccChannelControlCodes = new SccChannelControlCodes(SccChannelControlCodes.channel4);
            }
        }
        else {
            throw new IllegalArgumentException("Only channels 1, 2, 3 and 4 are supported.");
        }
    }

    public EncodingProvider getEncodingProvider() {
        return encodingProvider;
    }

    public SccChannelControlCodes getSccChannelControlCodes() {
        return sccChannelControlCodes;
    }

    public SccCaptionMode getMode() {
        return mode;
    }

    public Boolean getIsDoubleControlCode() {
        return configuration.getDoubleControlCodes();
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
