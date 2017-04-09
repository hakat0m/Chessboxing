package com.gumtree.android.home.dfp;

import com.google.android.gms.ads.AdSize;

public class HomeTextLinkProcessor extends HomeDFPProcessor {
    private static final int HEIGHT = 75;
    private static final int WIDTH = 330;

    public AdSize[] getAdSizes() {
        return new AdSize[]{new AdSize(WIDTH, HEIGHT)};
    }
}
