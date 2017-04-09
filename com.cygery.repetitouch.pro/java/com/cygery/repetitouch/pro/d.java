/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$OnSharedPreferenceChangeListener
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.preference.CheckBoxPreference
 *  android.preference.EditTextPreference
 *  android.preference.Preference
 *  android.preference.PreferenceFragment
 *  android.preference.PreferenceManager
 *  android.preference.PreferenceScreen
 *  android.util.Pair
 *  android.widget.EditText
 *  java.lang.CharSequence
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Collections
 *  java.util.Comparator
 *  java.util.Iterator
 *  java.util.List
 */
package com.cygery.repetitouch.pro;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Pair;
import android.widget.EditText;
import com.cygery.repetitouch.n;
import com.cygery.repetitouch.pro.TranslucentActivityPro;
import com.cygery.repetitouch.pro.e;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@SuppressLint(value={"NewApi"})
public class d
extends n
implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String d = d.class.getName();
    private e e;

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onCreate(Bundle bundle) {
        Preference preference;
        this.a = TranslucentActivityPro.class;
        super.onCreate(bundle);
        this.e = new e((Context)this.getActivity());
        this.e.a((PreferenceFragment)this);
        this.e.d = (CheckBoxPreference)this.findPreference((CharSequence)"enableLocaleSupport");
        this.e.e = (CheckBoxPreference)this.findPreference((CharSequence)"ignoreCallState");
        this.e.f = (EditTextPreference)this.findPreference((CharSequence)"loopTimes");
        this.e.g = (EditTextPreference)this.findPreference((CharSequence)"replaySpeed");
        SharedPreferences sharedPreferences = this.b.getSharedPreferences();
        int n2 = sharedPreferences != null ? sharedPreferences.getInt("numberOfAdditionalEventIndizes", 0) : 0;
        PreferenceScreen preferenceScreen = (PreferenceScreen)this.findPreference((CharSequence)"additional_events_preferencescreen");
        if (preferenceScreen != null) {
            preferenceScreen.removeAll();
            if (n2 > 0 && this.getActivity() != null) {
                preferenceScreen.setEnabled(true);
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 < n2; ++i2) {
                    String string = sharedPreferences.getString("additionalEventName_" + i2, "");
                    String string2 = sharedPreferences.getString("additionalEventIndexString_" + i2, "");
                    CheckBoxPreference checkBoxPreference = new CheckBoxPreference((Context)this.getActivity());
                    checkBoxPreference.setKey("additionalEventEnabled_" + i2);
                    checkBoxPreference.setDefaultValue((Object)false);
                    checkBoxPreference.setTitle((CharSequence)(string2 + " : " + string));
                    arrayList.add((Object)new Pair((Object)Integer.parseInt((String)string2), (Object)checkBoxPreference));
                }
                Collections.sort((List)arrayList, (Comparator)new Comparator<Pair<Integer, Preference>>(){

                    public int a(Pair<Integer, Preference> pair, Pair<Integer, Preference> pair2) {
                        return (Integer)pair.first - (Integer)pair2.first;
                    }

                    public /* synthetic */ int compare(Object object, Object object2) {
                        return this.a((Pair)object, (Pair)object2);
                    }
                });
                Iterator iterator = arrayList.iterator();
                while (iterator.hasNext()) {
                    preferenceScreen.addPreference((Preference)((Pair)iterator.next()).second);
                }
            } else {
                preferenceScreen.setEnabled(false);
            }
        }
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference)this.findPreference((CharSequence)"dimScreenOnReplay");
        boolean bl = false;
        if (checkBoxPreference != null) {
            boolean bl2 = checkBoxPreference.isChecked();
            bl = false;
            if (bl2) {
                bl = true;
            }
        }
        Preference preference2 = this.findPreference((CharSequence)"dimScreenPercentageInt");
        Preference preference3 = this.findPreference((CharSequence)"dimDelaySeconds");
        if (preference2 != null) {
            preference2.setEnabled(bl);
        }
        if (preference3 != null) {
            preference3.setEnabled(bl);
        }
        if (Build.VERSION.SDK_INT < 16 && (preference = this.findPreference((CharSequence)"notificationPriority")) != null && this.getPreferenceScreen() != null) {
            this.getPreferenceScreen().removePreference(preference);
        }
        this.e.a();
    }

    public void onPause() {
        super.onPause();
        if (this.getPreferenceScreen().getSharedPreferences() != null) {
            this.getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener)this);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference == this.e.d) {
            if (!this.e.d.isChecked()) return false;
            {
                if (this.getActivity() == null) {
                    ((CheckBoxPreference)preference).setChecked(false);
                    return false;
                }
                this.e.h = new AlertDialog.Builder((Context)this.getActivity()).setMessage(2131165308).setTitle(2131165309).setIconAttribute(16843605).setPositiveButton(17039379, (DialogInterface.OnClickListener)this.e).setNegativeButton(17039369, (DialogInterface.OnClickListener)this.e).show();
                this.e.h.setOnDismissListener((DialogInterface.OnDismissListener)this.e);
                return false;
            }
        } else if (preference == this.e.e) {
            if (!this.e.e.isChecked()) return false;
            {
                if (this.getActivity() != null) {
                    this.e.i = new AlertDialog.Builder((Context)this.getActivity()).setMessage(2131165230).setTitle(2131165231).setIconAttribute(16843605).setPositiveButton(17039379, (DialogInterface.OnClickListener)this.e).setNegativeButton(17039369, (DialogInterface.OnClickListener)this.e).show();
                    this.e.i.setOnDismissListener((DialogInterface.OnDismissListener)this.e);
                    return false;
                }
                ((CheckBoxPreference)preference).setChecked(false);
                return false;
            }
        } else if (preference == this.findPreference((CharSequence)"dimScreenOnReplay")) {
            boolean bl = ((CheckBoxPreference)preference).isChecked();
            Preference preference2 = this.findPreference((CharSequence)"dimScreenPercentageInt");
            Preference preference3 = this.findPreference((CharSequence)"dimDelaySeconds");
            if (preference2 != null) {
                preference2.setEnabled(bl);
            }
            if (preference3 == null) return false;
            {
                preference3.setEnabled(bl);
                return false;
            }
        } else {
            if (preference != this.e.f) return false;
            {
                this.e.f.getEditText().setSelection(this.e.f.getText().length());
                return false;
            }
        }
    }

    public void onResume() {
        super.onResume();
        if (this.getPreferenceScreen().getSharedPreferences() != null) {
            this.getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener)this);
        }
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String string) {
        this.e.a(sharedPreferences, string);
    }

}

