/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Handler
 *  android.preference.ListPreference
 *  android.preference.Preference
 *  android.preference.Preference$OnPreferenceClickListener
 *  android.preference.PreferenceFragment
 *  android.preference.PreferenceManager
 *  android.preference.PreferenceScreen
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Arrays
 */
package com.cygery.repetitouch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import com.cygery.repetitouch.d;
import com.cygery.repetitouch.h;
import com.cygery.utilities.a;
import com.cygery.utilities.b;
import java.util.ArrayList;
import java.util.Arrays;

@SuppressLint(value={"NewApi"})
public class n
extends PreferenceFragment
implements a.d {
    private static final String d = n.class.getName();
    protected Class a;
    protected PreferenceManager b;
    protected Handler c;

    @Override
    public String a(Context context) {
        return d.d(context);
    }

    @Override
    public void a_(boolean bl) {
    }

    @Override
    public String c() {
        return d;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onCreate(Bundle bundle) {
        Preference preference;
        Preference preference2;
        Preference preference3;
        Bundle bundle2;
        String string;
        ArrayList arrayList;
        ArrayList arrayList2;
        Preference preference4;
        super.onCreate(bundle);
        this.c = new Handler();
        this.b = this.getPreferenceManager();
        if (this.b != null) {
            this.b.setSharedPreferencesName("repetitouch_prefs");
            this.b.setSharedPreferencesMode(0);
        }
        this.addPreferencesFromResource(h.f.preferences);
        Preference preference5 = this.findPreference((CharSequence)"about");
        if (preference5 != null) {
            preference5.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){

                public boolean onPreferenceClick(Preference preference) {
                    new b((Context)n.this.getActivity()).show();
                    return true;
                }
            });
        }
        if ((preference4 = this.findPreference((CharSequence)"send_report")) != null && this.getActivity() != null) {
            preference4.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){

                public boolean onPreferenceClick(Preference preference) {
                    a.a((Context)n.this.getActivity(), n.this);
                    return true;
                }
            });
        }
        if ((preference3 = this.findPreference((CharSequence)"save_report")) != null) {
            preference3.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener(){

                public boolean onPreferenceClick(Preference preference) {
                    n.this.startActivity(new Intent((Context)n.this.getActivity(), n.this.a).addFlags(335544320).putExtra("message", "showSaveReportDialog"));
                    return true;
                }
            });
        }
        if ((bundle2 = this.getArguments()) != null) {
            arrayList = bundle2.getStringArrayList("entriesTouchDevice");
            ArrayList arrayList3 = bundle2.getStringArrayList("entryValuesTouchDevice");
            string = bundle2.getString("eventIndexString");
            arrayList2 = arrayList3;
        } else {
            string = "-1";
            arrayList = null;
            arrayList2 = null;
        }
        if ((preference = this.findPreference((CharSequence)"eventIndexString")) != null) {
            if (arrayList != null && arrayList2 != null && arrayList.size() == arrayList2.size() && arrayList.size() > 1) {
                ((ListPreference)preference).setEntries((CharSequence[])Arrays.copyOf((Object[])arrayList.toArray(), (int)arrayList.toArray().length, (Class)String[].class));
                ((ListPreference)preference).setEntryValues((CharSequence[])Arrays.copyOf((Object[])arrayList2.toArray(), (int)arrayList2.toArray().length, (Class)String[].class));
            } else {
                this.getPreferenceScreen().removePreference(preference);
            }
        }
        if ((preference2 = this.findPreference((CharSequence)"eventMultitouchType")) != null && "-1".equals((Object)string)) {
            preference2.setEnabled(false);
        }
    }

}

