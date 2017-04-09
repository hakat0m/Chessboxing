package com.unity3d.player;

import android.os.Build.VERSION;

public final class e {
    static final boolean a = (VERSION.SDK_INT >= 21);
    static final boolean b;
    static final b c;

    static {
        boolean z = true;
        if (VERSION.SDK_INT < 23) {
            z = false;
        }
        b = z;
        c = z ? new d() : null;
    }
}
