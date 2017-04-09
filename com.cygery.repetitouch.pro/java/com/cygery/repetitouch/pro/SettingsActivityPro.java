/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$OnSharedPreferenceChangeListener
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.preference.CheckBoxPreference
 *  android.preference.EditTextPreference
 *  android.preference.Preference
 *  android.preference.PreferenceActivity
 *  android.preference.PreferenceScreen
 *  android.util.Pair
 *  android.view.View
 *  android.view.Window
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

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import com.cygery.repetitouch.m;
import com.cygery.repetitouch.n;
import com.cygery.repetitouch.pro.PanelServicePro;
import com.cygery.repetitouch.pro.TranslucentActivityPro;
import com.cygery.repetitouch.pro.d;
import com.cygery.repetitouch.pro.e;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class SettingsActivityPro
extends m
implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String e = SettingsActivityPro.class.getName();
    private e f;

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onCreate(Bundle bundle) {
        this.a = PanelServicePro.class;
        this.b = TranslucentActivityPro.class;
        this.d = Build.VERSION.SDK_INT >= 11 ? new d() : null;
        this.f = new e((Context)this);
        this.f.a((PreferenceActivity)this);
        super.onCreate(bundle);
        if (Build.VERSION.SDK_INT < 11) {
            Preference preference;
            CheckBoxPreference checkBoxPreference;
            this.f.d = (CheckBoxPreference)this.findPreference((CharSequence)"enableLocaleSupport");
            this.f.e = (CheckBoxPreference)this.findPreference((CharSequence)"ignoreCallState");
            this.f.f = (EditTextPreference)this.findPreference((CharSequence)"loopTimes");
            this.f.g = (EditTextPreference)this.findPreference((CharSequence)"replaySpeed");
            int n2 = this.c.getInt("numberOfAdditionalEventIndizes", 0);
            PreferenceScreen preferenceScreen = (PreferenceScreen)this.findPreference((CharSequence)"additional_events_preferencescreen");
            if (preferenceScreen != null) {
                preferenceScreen.removeAll();
                ArrayList arrayList = new ArrayList();
                if (n2 > 0) {
                    preferenceScreen.setEnabled(true);
                    for (int i2 = 0; i2 < n2; ++i2) {
                        String string = this.c.getString("additionalEventName_" + i2, "");
                        String string2 = this.c.getString("additionalEventIndexString_" + i2, "");
                        CheckBoxPreference checkBoxPreference2 = new CheckBoxPreference((Context)this);
                        checkBoxPreference2.setKey("additionalEventEnabled_" + i2);
                        checkBoxPreference2.setDefaultValue((Object)false);
                        checkBoxPreference2.setTitle((CharSequence)(string2 + ":" + string));
                        arrayList.add((Object)new Pair((Object)Integer.parseInt((String)string2), (Object)checkBoxPreference2));
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
            boolean bl = (checkBoxPreference = (CheckBoxPreference)this.findPreference((CharSequence)"dimScreenOnReplay")) != null && checkBoxPreference.isChecked();
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
            this.f.a();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT < 11 && this.getPreferenceScreen() != null && this.getPreferenceScreen().getSharedPreferences() != null) {
            this.getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener)this);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (Build.VERSION.SDK_INT >= 11) return false;
        if (preference == this.f.d) {
            if (!((CheckBoxPreference)preference).isChecked()) return false;
            this.f.h = new AlertDialog.Builder((Context)this).setMessage(2131165308).setTitle(2131165309).setIcon(2130837643).setPositiveButton(17039379, (DialogInterface.OnClickListener)this.f).setNegativeButton(17039369, (DialogInterface.OnClickListener)this.f).show();
            this.f.h.setOnDismissListener((DialogInterface.OnDismissListener)this.f);
            return false;
        }
        if (preference == this.f.e) {
            if (!((CheckBoxPreference)preference).isChecked()) return false;
            this.f.i = new AlertDialog.Builder((Context)this).setMessage(2131165230).setTitle(2131165231).setIcon(2130837643).setPositiveButton(17039379, (DialogInterface.OnClickListener)this.f).setNegativeButton(17039369, (DialogInterface.OnClickListener)this.f).show();
            this.f.i.setOnDismissListener((DialogInterface.OnDismissListener)this.f);
            return false;
        }
        if (preference == this.findPreference((CharSequence)"dimScreenOnReplay")) {
            boolean bl = ((CheckBoxPreference)preference).isChecked();
            Preference preference2 = this.findPreference((CharSequence)"dimScreenPercentageInt");
            Preference preference3 = this.findPreference((CharSequence)"dimDelaySeconds");
            if (preference2 != null) {
                preference2.setEnabled(bl);
            }
            if (preference3 == null) return false;
            preference3.setEnabled(bl);
            return false;
        }
        if (preference == this.f.f) {
            this.f.f.getEditText().setSelection(this.f.f.getText().length());
            return false;
        }
        if (preference == null) return false;
        if (!(preference instanceof PreferenceScreen)) return false;
        PreferenceScreen preferenceScreen2 = (PreferenceScreen)preference;
        if (preferenceScreen2.getDialog() == null) return false;
        if (this.getWindow().getDecorView().getBackground() == null) return false;
        Drawable drawable = this.getWindow().getDecorView().getBackground().getConstantState().newDrawable();
        preferenceScreen2.getDialog().getWindow().getDecorView().setBackgroundDrawable(drawable);
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT < 11 && this.getPreferenceScreen() != null && this.getPreferenceScreen().getSharedPreferences() != null) {
            this.getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener)this);
        }
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String string) {
        this.f.a(sharedPreferences, string);
    }

}

