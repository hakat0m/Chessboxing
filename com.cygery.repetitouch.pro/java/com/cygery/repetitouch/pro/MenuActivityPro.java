/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.support.v4.app.p
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Toast
 *  java.io.File
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package com.cygery.repetitouch.pro;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.p;
import android.view.View;
import android.widget.Toast;
import com.cygery.repetitouch.f;
import com.cygery.repetitouch.pro.EventManagerServicePro;
import com.cygery.repetitouch.pro.PanelServicePro;
import com.cygery.repetitouch.pro.SettingsActivityPro;
import com.cygery.utilities.e;
import java.io.File;

public class MenuActivityPro
extends f
implements e.b {
    private static final String r = MenuActivityPro.class.getName();

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean a(File file, int n2) {
        String string = file != null ? file.getAbsolutePath() : null;
        if (string == null) {
            Toast.makeText((Context)this, (int)2131165243, (int)0).show();
            return true;
        }
        switch (n2) {
            case 1: {
                this.startService(new Intent((Context)this, this.n).setAction("eventmanagerservice").putExtra("message", "loadevents").putExtra("path", string));
            }
            default: {
                break;
            }
            case 2: {
                if (com.cygery.utilities.f.a(string)) {
                    this.a(string);
                    break;
                }
                this.startService(new Intent((Context)this, this.n).setAction("eventmanagerservice").putExtra("message", "saveevents").putExtra("path", string));
            }
        }
        this.p = string;
        return true;
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.m = PanelServicePro.class;
        this.n = EventManagerServicePro.class;
        this.o = SettingsActivityPro.class;
        this.findViewById(2131558545).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("savedialog", true);
                bundle.putString("path", MenuActivityPro.this.p);
                bundle.putInt("requestcode", 2);
                e e2 = new e();
                e2.g(bundle);
                e2.a(MenuActivityPro.this.e(), null);
            }
        });
        this.findViewById(2131558546).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("savedialog", false);
                bundle.putString("path", MenuActivityPro.this.p);
                bundle.putInt("requestcode", 1);
                e e2 = new e();
                e2.g(bundle);
                e2.a(MenuActivityPro.this.e(), null);
            }
        });
        this.findViewById(2131558547).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MenuActivityPro.this.startService(new Intent(MenuActivityPro.this.getApplicationContext(), MenuActivityPro.this.m).setAction("panelservice").putExtra("message", "starteditor"));
                MenuActivityPro.this.finish();
            }
        });
        this.findViewById(2131558548).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MenuActivityPro.this.startService(new Intent(MenuActivityPro.this.getApplicationContext(), MenuActivityPro.this.n).setAction("eventmanagerservice").putExtra("message", "launchmerger").putExtra("clearMarkers", true));
            }
        });
        this.findViewById(2131558550).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MenuActivityPro.this.startService(new Intent(MenuActivityPro.this.getApplicationContext(), MenuActivityPro.this.m).setAction("panelservice").putExtra("message", "togglevisibilitynext"));
            }
        });
        this.findViewById(2131558551).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MenuActivityPro.this.startService(new Intent(MenuActivityPro.this.getApplicationContext(), MenuActivityPro.this.m).setAction("panelservice").putExtra("message", "toggledrag"));
            }
        });
        this.findViewById(2131558552).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                MenuActivityPro.this.startService(new Intent(MenuActivityPro.this.getApplicationContext(), MenuActivityPro.this.m).setAction("panelservice").putExtra("message", "togglerecordbutton"));
            }
        });
    }

}

