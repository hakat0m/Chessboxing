package com.gumtree.android.notifications.providers;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnSharedPreferenceValueChangedEvent;
import com.gumtree.android.notifications.NotificationType;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.inject.Inject;

public class GCMSettingsProvider implements IGCMSettingsProvider {
    public static final String MDNS_KEY_NOTIFICATION_CHAT_MESSAGE = "CHATMESSAGE";
    public static final String MDNS_KEY_NOTIFICATION_SAVED_SEARCHES = "SEARCHALERTS";
    public static final String PREFERENCE_HAS_CHAT_MESSAGE = "has_chat_message";
    public static final String PREFERENCE_KEY_NOTIFICATION_CHAT_MESSAGE = "preference_key_notification_chat_message";
    public static final String PREFERENCE_KEY_NOTIFICATION_SAVED_SEARCHES = "preference_key_notification_saved_searches";
    @Inject
    EventBus eventBus;

    public GCMSettingsProvider() {
        GumtreeApplication.component().inject(this);
    }

    public boolean isNotificationTypeEnabled(NotificationType notificationType) {
        return PreferenceManager.getDefaultSharedPreferences(GumtreeApplication.getContext()).getBoolean(notificationType.getSettingsKey(), true);
    }

    public Map<NotificationType, Boolean> getNotificationTypesStates() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(GumtreeApplication.getContext());
        Map<NotificationType, Boolean> hashMap = new HashMap();
        for (NotificationType notificationType : NotificationType.values()) {
            hashMap.put(notificationType, Boolean.valueOf(defaultSharedPreferences.getBoolean(notificationType.getSettingsKey(), true)));
        }
        return hashMap;
    }

    public void persistNotificationTypesStates(Map<NotificationType, Boolean> map) {
        Editor edit = PreferenceManager.getDefaultSharedPreferences(GumtreeApplication.getContext()).edit();
        for (Entry entry : map.entrySet()) {
            edit.putBoolean(((NotificationType) entry.getKey()).getSettingsKey(), ((Boolean) entry.getValue()).booleanValue());
        }
        edit.apply();
        for (Entry entry2 : map.entrySet()) {
            this.eventBus.post(new OnSharedPreferenceValueChangedEvent(((NotificationType) entry2.getKey()).getSettingsKey(), ((Boolean) entry2.getValue()).booleanValue()));
        }
    }

    public boolean hasChatMessage() {
        return PreferenceManager.getDefaultSharedPreferences(GumtreeApplication.getContext()).getBoolean(PREFERENCE_HAS_CHAT_MESSAGE, false);
    }

    public void persistHasChatMessage(boolean z) {
        Editor edit = PreferenceManager.getDefaultSharedPreferences(GumtreeApplication.getContext()).edit();
        edit.putBoolean(PREFERENCE_HAS_CHAT_MESSAGE, z);
        edit.apply();
    }
}
