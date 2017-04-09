package com.gumtree.android.srp.textlinks;

import com.google.android.gms.ads.AdSize;
import com.gumtree.android.srp.dfp.SRPDFPProcessor;

public class TextLinkProcessor extends SRPDFPProcessor {
    private static final int HEIGHT = 75;
    private static final int WIDTH = 330;

    public AdSize[] getAdSizes() {
        return new AdSize[]{new AdSize(WIDTH, HEIGHT)};
    }
}
