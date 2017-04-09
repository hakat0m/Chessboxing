package com.gumtree.android.notifications.providers;

import com.gumtree.android.notifications.NotificationType;
import java.util.Map;

public interface PushNotificationsProvider {
    void setup();

    void updateSubscriptions(String str, Map<NotificationType, Boolean> map, UpdateSubscriptionsListener updateSubscriptionsListener);

    void updateSubscriptions(Map<NotificationType, Boolean> map, UpdateSubscriptionsListener updateSubscriptionsListener);
}
