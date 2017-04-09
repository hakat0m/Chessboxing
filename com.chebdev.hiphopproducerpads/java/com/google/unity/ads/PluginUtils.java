package com.google.unity.ads;

import android.util.Log;
import com.google.unity.BuildConfig;
import com.google.unity.R;

public class PluginUtils {
    public static final String LOGTAG = "GoogleMobileAdsUnityPlugin";

    public static String getErrorReason(int errorCode) {
        switch (errorCode) {
            case R.styleable.WalletFragmentStyle_buyButtonHeight /*0*/:
                return "Internal error";
            case R.styleable.WalletFragmentStyle_buyButtonWidth /*1*/:
                return "Invalid request";
            case R.styleable.WalletFragmentStyle_buyButtonText /*2*/:
                return "Network Error";
            case R.styleable.WalletFragmentStyle_buyButtonAppearance /*3*/:
                return "No fill";
            default:
                Log.w(LOGTAG, String.format("Unexpected error code: %s", new Object[]{Integer.valueOf(errorCode)}));
                return BuildConfig.FLAVOR;
        }
    }
}
