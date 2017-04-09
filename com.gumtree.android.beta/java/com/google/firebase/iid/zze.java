package com.google.firebase.iid;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.apptentive.android.sdk.BuildConfig;

public class zze {
    private static final Object zzamp = new Object();
    private final zzg baS;

    zze(zzg com_google_firebase_iid_zzg) {
        this.baS = com_google_firebase_iid_zzg;
    }

    @Nullable
    String zzcxc() {
        String str = null;
        synchronized (zzamp) {
            String string = this.baS.zzcxd().getString("topic_operaion_queue", null);
            if (string != null) {
                String[] split = string.split(",");
                if (split.length > 1 && !TextUtils.isEmpty(split[1])) {
                    str = split[1];
                }
            }
        }
        return str;
    }

    void zzsf(String str) {
        synchronized (zzamp) {
            String string = this.baS.zzcxd().getString("topic_operaion_queue", BuildConfig.FLAVOR);
            String valueOf = String.valueOf(",");
            this.baS.zzcxd().edit().putString("topic_operaion_queue", new StringBuilder(((String.valueOf(string).length() + 0) + String.valueOf(valueOf).length()) + String.valueOf(str).length()).append(string).append(valueOf).append(str).toString()).apply();
        }
    }

    boolean zzsj(String str) {
        boolean z;
        synchronized (zzamp) {
            String string = this.baS.zzcxd().getString("topic_operaion_queue", BuildConfig.FLAVOR);
            String valueOf = String.valueOf(",");
            String valueOf2 = String.valueOf(str);
            if (string.startsWith(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf))) {
                valueOf = String.valueOf(",");
                valueOf2 = String.valueOf(str);
                this.baS.zzcxd().edit().putString("topic_operaion_queue", string.substring((valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf)).length())).apply();
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }
}
