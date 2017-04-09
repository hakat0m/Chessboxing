/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.Window
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 */
package com.cygery.repetitouch.pro;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import com.cygery.repetitouch.d;
import com.cygery.repetitouch.pro.EventManagerServicePro;
import com.cygery.repetitouch.pro.PanelServicePro;
import java.util.ArrayList;

public class MainActivityPro
extends d {
    private static final String q = MainActivityPro.class.getName();

    public MainActivityPro() {
        super(EventManagerServicePro.class, PanelServicePro.class);
        this.i = true;
    }

    @Override
    protected void d() {
        this.o();
        this.l();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void o() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("repetitouch_prefs", 0);
        ArrayList arrayList = new ArrayList();
        int n2 = sharedPreferences.getInt("numberOfAdditionalEventIndizes", 0);
        for (int i2 = 0; i2 < n2; ++i2) {
            int n3;
            if (!sharedPreferences.getBoolean("additionalEventEnabled_" + i2, false) || (n3 = Integer.parseInt((String)sharedPreferences.getString("additionalEventIndexString_" + i2, "-1"))) == -1) continue;
            arrayList.add((Object)n3);
        }
        this.startService(new Intent(this.c, this.n).putExtra("eventserverPath", this.d).putExtra("eventIndex", this.f).putExtra("eventMultitouchType", this.g).putIntegerArrayListExtra("additionalEventIndizes", arrayList));
        int n4 = this.getResources().getConfiguration().orientation == 2 ? (int)(((float)this.getWindow().getDecorView().getWidth() - 140.0f * ((float)Resources.getSystem().getDisplayMetrics().densityDpi / 160.0f)) / 2.0f) : (int)(((float)this.getWindow().getDecorView().getHeight() - 140.0f * ((float)Resources.getSystem().getDisplayMetrics().densityDpi / 160.0f)) / 2.0f);
        int n5 = sharedPreferences.getInt("startGlobalX", n4);
        int n6 = sharedPreferences.getInt("startGlobalY", 0);
        this.startService(new Intent(this.c, this.o).putExtra("x", n5).putExtra("y", n6));
        if (!sharedPreferences.getBoolean("hidePanelOnStart", false)) {
            this.startService(new Intent(this.c, this.o).setAction("panelservice").putExtra("message", "show"));
        }
    }
}

