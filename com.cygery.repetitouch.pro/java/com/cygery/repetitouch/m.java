/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.SharedPreferences
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.preference.ListPreference
 *  android.preference.Preference
 *  android.preference.Preference$OnPreferenceClickListener
 *  android.preference.PreferenceActivity
 *  android.preference.PreferenceManager
 *  android.preference.PreferenceScreen
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.ArrayList
 *  java.util.Arrays
 */
package com.cygery.repetitouch;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import com.cygery.repetitouch.d;
import com.cygery.repetitouch.h;
import com.cygery.repetitouch.n;
import com.cygery.utilities.a;
import com.cygery.utilities.b;
import java.util.ArrayList;
import java.util.Arrays;

@SuppressLint(value={"NewApi"})
public class m
extends PreferenceActivity
implements a.d {
    private static final String e = m.class.getName();
    protected Class a;
    protected Class b;
    protected SharedPreferences c;
    protected n d;

    @Override
    public String a(Context context) {
        return d.d(context);
    }

    @Override
    public void a_(boolean bl) {
    }

    @Override
    public String c() {
        return e;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onCreate(Bundle bundle) {
        Preference preference;
        Preference preference2;
        Preference preference3;
        Preference preference4;
        super.onCreate(bundle);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        this.c = this.getSharedPreferences("repetitouch_prefs", 0);
        String string = this.c.getString("eventIndexString", "-1");
        int n2 = this.c.getInt("numberOfEventIndizes", 0);
        for (int i2 = 0; i2 < n2; ++i2) {
            int n3 = Integer.parseInt((String)this.c.getString("eventIndexString_" + i2, "-1"));
            if (n3 == -1) continue;
            arrayList2.add((Object)("" + n3));
            arrayList.add((Object)("" + n3 + ": " + this.c.getString(new StringBuilder().append("eventName_").append(i2).toString(), "")));
        }
        if (Build.VERSION.SDK_INT >= 11) {
            Bundle bundle2 = new Bundle();
            bundle2.putStringArrayList("entriesTouchDevice", arrayList);
            bundle2.putStringArrayList("entryValuesTouchDevice", arrayList2);
            bundle2.putString("eventIndexString", string);
            this.d.setArguments(bundle2);
            this.getFragmentManager().beginTransaction().replace(16908290, (Fragment)this.d).commit();
            return;
        }
        PreferenceManager preferenceManager = this.getPreferenceManager();
        preferenceManager.setSharedPreferencesName("repetitouch_prefs");
        preferenceManager.setSharedPreferencesMode(0);
        this.addPreferencesFromResource(h.f.preferences);
        Preference preference5 = this.findPreference((CharSequence)"about");
        if (preference5 != null) {
            preference5.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){

                public boolean onPreferenceClick(Preference preference) {
                    new b((Context)m.this).show();
                    return true;
                }
            });
        }
        if ((preference4 = this.findPreference((CharSequence)"send_report")) != null) {
            preference4.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){

                public boolean onPreferenceClick(Preference preference) {
                    a.a((Context)m.this, m.this);
                    return true;
                }
            });
        }
        if ((preference2 = this.findPreference((CharSequence)"save_report")) != null) {
            preference2.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){

                public boolean onPreferenceClick(Preference preference) {
                    m.this.startActivity(new Intent((Context)m.this, m.this.b).addFlags(335544320).putExtra("message", "showSaveReportDialog"));
                    return true;
                }
            });
        }
        if ((preference3 = this.findPreference((CharSequence)"eventIndexString")) != null) {
            if (arrayList.size() == arrayList2.size() && arrayList.size() > 1) {
                ((ListPreference)preference3).setEntries((CharSequence[])Arrays.copyOf((Object[])arrayList.toArray(), (int)arrayList.toArray().length, (Class)String[].class));
                ((ListPreference)preference3).setEntryValues((CharSequence[])Arrays.copyOf((Object[])arrayList2.toArray(), (int)arrayList2.toArray().length, (Class)String[].class));
            } else if (this.getPreferenceScreen() != null) {
                this.getPreferenceScreen().removePreference(preference3);
            }
        }
        if ((preference = this.findPreference((CharSequence)"eventMultitouchType")) != null && "-1".equals((Object)string) && this.getPreferenceScreen() != null) {
            preference.setEnabled(false);
        }
    }

    public void onPause() {
        super.onPause();
        this.startService(new Intent((Context)this, this.a).setAction("panelservice").putExtra("message", "show"));
    }

    public void onResume() {
        super.onResume();
        this.startService(new Intent((Context)this, this.a).setAction("panelservice").putExtra("message", "hide"));
    }

}

