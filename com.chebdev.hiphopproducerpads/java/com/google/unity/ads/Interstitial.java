package com.google.unity.ads;

import android.app.Activity;
import android.util.Log;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class Interstitial {
    private Activity activity;
    private InterstitialAd interstitial;
    private boolean isLoaded = false;
    private UnityAdListener listener;

    public Interstitial(Activity activity, UnityAdListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    public void create(final String adUnitId) {
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                Interstitial.this.interstitial = new InterstitialAd(Interstitial.this.activity);
                Interstitial.this.interstitial.setAdUnitId(adUnitId);
                Interstitial.this.interstitial.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        Interstitial.this.isLoaded = true;
                        Interstitial.this.listener.onAdLoaded();
                    }

                    public void onAdFailedToLoad(int errorCode) {
                        Interstitial.this.listener.onAdFailedToLoad(PluginUtils.getErrorReason(errorCode));
                    }

                    public void onAdOpened() {
                        Interstitial.this.listener.onAdOpened();
                    }

                    public void onAdClosed() {
                        Interstitial.this.listener.onAdClosed();
                    }

                    public void onAdLeftApplication() {
                        Interstitial.this.listener.onAdLeftApplication();
                    }
                });
            }
        });
    }

    public void loadAd(final AdRequest request) {
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                Interstitial.this.interstitial.loadAd(request);
            }
        });
    }

    public boolean isLoaded() {
        return this.isLoaded;
    }

    public void show() {
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                if (Interstitial.this.interstitial.isLoaded()) {
                    Interstitial.this.isLoaded = false;
                    Interstitial.this.interstitial.show();
                    return;
                }
                Log.d(PluginUtils.LOGTAG, "Interstitial was not ready to be shown.");
            }
        });
    }

    public void destroy() {
    }
}
