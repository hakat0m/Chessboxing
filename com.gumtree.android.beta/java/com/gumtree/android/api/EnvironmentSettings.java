package com.gumtree.android.api;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.support.annotation.NonNull;
import com.gumtree.android.api.environment.Environment;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Validate;

public class EnvironmentSettings {
    private final Environment defaultEnvironment = Environment.LIVE;
    private final String environmentPreferenceKey;
    private OnSharedPreferenceChangeListener environmentSummaryRefresher;
    private final String environmentSummaryTemplate;
    private final SharedPreferences sharedPreferences;

    public EnvironmentSettings(@NonNull Resources resources, @NonNull SharedPreferences sharedPreferences) {
        this.sharedPreferences = (SharedPreferences) Validate.notNull(sharedPreferences);
        this.environmentPreferenceKey = resources.getString(2131165426);
        this.environmentSummaryTemplate = resources.getString(2131165427);
    }

    /* synthetic */ void lambda$display$0(PreferenceGroup preferenceGroup, SharedPreferences sharedPreferences, String str) {
        if (ObjectUtils.equals(str, getPreferenceKey())) {
            refreshSummary(preferenceGroup);
        }
    }

    public void display(PreferenceGroup preferenceGroup) {
        Preference findPreference = preferenceGroup.findPreference(getPreferenceKey());
        if (findPreference != null) {
            preferenceGroup.removePreference(findPreference);
        }
    }

    public Environment getEnvironment() {
        return this.defaultEnvironment;
    }

    @NonNull
    private String getPreferenceKey() {
        return this.environmentPreferenceKey;
    }

    private void refreshSummary(PreferenceGroup preferenceGroup) {
        ListPreference listPreference = (ListPreference) preferenceGroup.findPreference(getPreferenceKey());
        Environment[] values = Environment.values();
        CharSequence[] charSequenceArr = new String[values.length];
        CharSequence[] charSequenceArr2 = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            charSequenceArr[i] = values[i].name();
            charSequenceArr2[i] = values[i].name();
        }
        listPreference.setEntries(charSequenceArr);
        listPreference.setEntryValues(charSequenceArr2);
        listPreference.setSummary(String.format(this.environmentSummaryTemplate, new Object[]{getEnvironment().name()}));
    }
}
