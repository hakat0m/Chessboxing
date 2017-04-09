package com.adjust.sdk;

import android.net.Uri;

public interface OnDeeplinkResponseListener {
    boolean launchReceivedDeeplink(Uri uri);
}
