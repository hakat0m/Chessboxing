package com.gumtree.android.clients.model;

import com.apptentive.android.sdk.BuildConfig;
import java.io.Serializable;

public class SoftUpdate implements Serializable {
    private final boolean lastChance;
    private final String message;
    private final boolean show;

    public SoftUpdate() {
        this.show = false;
        this.message = BuildConfig.FLAVOR;
        this.lastChance = false;
    }

    public SoftUpdate(boolean z, boolean z2, String str) {
        this.show = z;
        this.message = str;
        this.lastChance = z2;
    }

    public boolean shouldShow() {
        return this.show;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isLastChance() {
        return this.lastChance;
    }
}
