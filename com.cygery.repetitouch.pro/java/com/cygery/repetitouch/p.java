/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.SharedPreferences
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.preference.ListPreference
 *  android.preference.Preference
 *  android.preference.PreferenceActivity
 *  android.preference.PreferenceFragment
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 */
package com.cygery.repetitouch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import com.cygery.repetitouch.h;

public class p {
    private static final String d = p.class.getName();
    protected Context a;
    protected PreferenceFragment b;
    protected PreferenceActivity c;

    protected Preference a(CharSequence charSequence) {
        if (Build.VERSION.SDK_INT < 11) {
            return this.c.findPreference(charSequence);
        }
        return this.b.findPreference(charSequence);
    }

    public void a() {
    }

    public void a(PreferenceActivity preferenceActivity) {
        this.c = preferenceActivity;
    }

    public void a(PreferenceFragment preferenceFragment) {
        this.b = preferenceFragment;
    }

    protected void a(String string) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.a);
        builder.setMessage((CharSequence)string);
        builder.setPositiveButton(h.e.label_ok, new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n2) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean a(SharedPreferences sharedPreferences, String string) {
        if (!"eventMultitouchType".equals((Object)string)) return false;
        String string2 = ((ListPreference)this.a((CharSequence)"eventMultitouchType")).getValue();
        if ("O".equals((Object)string2)) {
            this.a(this.a.getString(h.e.info_compatibility_mode));
            return true;
        }
        if (!"H".equals((Object)string2)) return true;
        this.a(this.a.getString(h.e.info_experimental_driver_support));
        return true;
    }

}

