/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Bundle
 *  android.os.Environment
 *  android.support.v4.app.l
 *  android.view.View
 *  android.view.View$OnClickListener
 *  java.io.File
 *  java.io.IOException
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package com.cygery.repetitouch;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.l;
import android.view.View;
import com.cygery.repetitouch.h;
import java.io.File;
import java.io.IOException;

public class f
extends l {
    private static final String r = f.class.getName();
    protected Class m;
    protected Class n;
    protected Class o;
    protected String p;
    protected boolean q;

    protected void a(final String string) {
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
        builder.setTitle(h.e.title_confirm_overwrite);
        builder.setMessage(h.e.text_confirm_overwrite);
        builder.setPositiveButton(17039370, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
                f.this.startService(new Intent((Context)f.this, f.this.n).setAction("eventmanagerservice").putExtra("message", "saveevents").putExtra("path", string));
            }
        });
        builder.setNegativeButton(17039360, null);
        builder.create().show();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive exception aggregation
     */
    protected void onCreate(Bundle var1) {
        super.onCreate(var1);
        this.setContentView(h.c.activity_menu);
        this.q = false;
        this.p = this.getSharedPreferences("repetitouch_prefs", 0).getString("startDir", Environment.getExternalStorageDirectory().getPath());
        try {
            this.p = new File(this.p).getCanonicalPath();
        }
        catch (IOException var2_2) {
            ** continue;
        }
lbl9: // 2 sources:
        do {
            this.findViewById(h.b.button_settings).setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    f.this.startActivity(new Intent(f.this.getApplicationContext(), f.this.o));
                }
            });
            this.findViewById(h.b.button_exit).setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    f.this.q = true;
                    f.this.stopService(new Intent((Context)f.this, f.this.m));
                    f.this.finish();
                }
            });
            return;
            break;
        } while (true);
    }

    protected void onPause() {
        super.onPause();
        if (!this.q) {
            this.startService(new Intent((Context)this, this.m).setAction("panelservice").putExtra("message", "show"));
        }
    }

    protected void onResume() {
        super.onResume();
        this.startService(new Intent((Context)this, this.m).setAction("panelservice").putExtra("message", "hide"));
    }

    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = this.getSharedPreferences("repetitouch_prefs", 0).edit();
        editor.putString("startDir", this.p);
        editor.apply();
    }

}

