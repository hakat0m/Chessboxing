package com.google.unity.ads;

public interface UnityAdListener {
    void onAdClosed();

    void onAdFailedToLoad(String str);

    void onAdLeftApplication();

    void onAdLoaded();

    void onAdOpened();
}
