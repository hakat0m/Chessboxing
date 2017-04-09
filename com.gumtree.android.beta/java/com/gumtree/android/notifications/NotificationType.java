package com.gumtree.android.notifications;

import com.gumtree.android.notifications.providers.GCMSettingsProvider;

public enum NotificationType {
    SAVED_SEARCHES(TYPE_TWO, GCMSettingsProvider.MDNS_KEY_NOTIFICATION_SAVED_SEARCHES, GCMSettingsProvider.PREFERENCE_KEY_NOTIFICATION_SAVED_SEARCHES, 2131165668),
    CHAT_MESSAGE(TYPE_THREE, GCMSettingsProvider.MDNS_KEY_NOTIFICATION_CHAT_MESSAGE, GCMSettingsProvider.PREFERENCE_KEY_NOTIFICATION_CHAT_MESSAGE, 2131165666);
    
    private static final int TYPE_THREE = 3;
    private static final int TYPE_TWO = 2;
    private int mDescriptionResId;
    private String mMDNSKey;
    private String mSettingsKey;
    private int mType;

    private NotificationType(int i, String str, String str2, int i2) {
        this.mType = i;
        this.mMDNSKey = str;
        this.mSettingsKey = str2;
        this.mDescriptionResId = i2;
    }

    public static NotificationType getNotificationType(int i) {
        switch (i) {
            case TYPE_TWO:
                return SAVED_SEARCHES;
            case TYPE_THREE:
                return CHAT_MESSAGE;
            default:
                return null;
        }
    }

    public static NotificationType getNotificationTypeFromMDNSKey(String str) {
        if (GCMSettingsProvider.MDNS_KEY_NOTIFICATION_SAVED_SEARCHES.equals(str)) {
            return SAVED_SEARCHES;
        }
        if (GCMSettingsProvider.MDNS_KEY_NOTIFICATION_CHAT_MESSAGE.equals(str)) {
            return CHAT_MESSAGE;
        }
        return null;
    }

    public int getType() {
        return this.mType;
    }

    public String getMDNSKey() {
        return this.mMDNSKey;
    }

    public String getSettingsKey() {
        return this.mSettingsKey;
    }

    public int getDescriptionResId() {
        return this.mDescriptionResId;
    }
}
