/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.os.Build
 *  android.os.Build$VERSION
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 */
package com.cygery.utilities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import com.cygery.utilities.h;
import t4X4QwIV.HfM5AFD8kd7A;

public class d {
    private static final String a = d.class.getName();
    private String b;
    private a c;
    private Dialog d;
    private Context e;

    public d(Context context, a a2, String string) {
        this.e = context;
        this.c = a2;
        this.b = string;
    }

    private String b() {
        String string = "";
        try {
            if (this.e.getPackageManager() != null) {
                string = HfM5AFD8kd7A.ra6kJ1Wnv83AG9X((PackageManager)this.e.getPackageManager(), (String)this.e.getPackageName(), (int)0).versionName;
            }
            return string;
        }
        catch (PackageManager.NameNotFoundException var2_2) {
            com.cygery.utilities.a.c(a, (Throwable)var2_2);
            return string;
        }
    }

    public void a() {
        if (this.d != null && this.d.isShowing()) {
            this.d.dismiss();
        }
    }

    public void a(String string) {
        this.a(string, true);
    }

    public void a(final String string, boolean bl) {
        final SharedPreferences sharedPreferences = this.e.getSharedPreferences(this.b, 0);
        if (sharedPreferences.getBoolean("acceptedEULA_" + string, false)) {
            this.c.b();
            return;
        }
        if (bl) {
            String string2 = this.e.getString(h.d.app_name) + " (v" + super.b() + ")";
            String string3 = this.e.getString(h.d.text_intro_eula) + "\n\n" + this.e.getString(h.d.eula);
            AlertDialog.Builder builder = new AlertDialog.Builder(this.e);
            builder.setTitle((CharSequence)string2);
            builder.setMessage((CharSequence)string3);
            builder.setPositiveButton(h.d.label_license_accept, new DialogInterface.OnClickListener(){

                /*
                 * Enabled aggressive block sorting
                 */
                public void onClick(DialogInterface dialogInterface, int n2) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("acceptedEULA_" + string, true);
                    if (Build.VERSION.SDK_INT >= 9) {
                        editor.apply();
                    } else {
                        editor.commit();
                    }
                    dialogInterface.dismiss();
                    d.this.c.b();
                }
            });
            builder.setNegativeButton(h.d.label_license_decline, new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n2) {
                    d.this.c.a();
                }
            });
            builder.setOnCancelListener(new DialogInterface.OnCancelListener(){

                public void onCancel(DialogInterface dialogInterface) {
                    d.this.c.a();
                }
            });
            this.d = builder.create();
            this.d.show();
            return;
        }
        this.c.a();
    }

    public static interface a {
        public void a();

        public void b();
    }

}

