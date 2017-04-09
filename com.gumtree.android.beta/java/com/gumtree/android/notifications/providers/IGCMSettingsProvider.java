package com.gumtree.android.notifications.providers;

import com.gumtree.android.notifications.NotificationType;
import java.util.Map;

public interface IGCMSettingsProvider {
    Map<NotificationType, Boolean> getNotificationTypesStates();

    boolean hasChatMessage();

    boolean isNotificationTypeEnabled(NotificationType notificationType);

    void persistHasChatMessage(boolean z);

    void persistNotificationTypesStates(Map<NotificationType, Boolean> map);
}
