package com.adjust.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class AdjustReferrerReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra(Constants.REFERRER);
        if (stringExtra != null) {
            try {
                stringExtra = URLDecoder.decode(stringExtra, Constants.ENCODING);
            } catch (UnsupportedEncodingException e) {
                stringExtra = Constants.MALFORMED;
            }
            Adjust.getDefaultInstance().sendReferrer(stringExtra);
        }
    }
}
