package com.gumtree.android.notifications.providers;

import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.http.CapiClientManager;
import com.gumtree.android.notifications.CapiNotificationsSubscriptionTask;
import com.gumtree.android.notifications.NotificationType;
import com.gumtree.android.notifications.providers.PushNotificationsProvider.UpdateSubscriptionsListener;
import java.util.Map;
import javax.inject.Inject;

public class CAPIPushNotificationsProvider implements PushNotificationsProvider {
    @Inject
    CapiClientManager capiClientManager;
    @Inject
    BaseAccountManager customerAccountManager;

    public CAPIPushNotificationsProvider() {
        GumtreeApplication.component().inject(this);
    }

    public void setup() {
    }

    public void updateSubscriptions(Map<NotificationType, Boolean> map, UpdateSubscriptionsListener updateSubscriptionsListener) {
        throw new UnsupportedOperationException("CAPIPushNotificationsProvider does not support update subscriptions without providing gcmRegistrationId");
    }

    public void updateSubscriptions(String str, Map<NotificationType, Boolean> map, UpdateSubscriptionsListener updateSubscriptionsListener) {
        new CapiNotificationsSubscriptionTask(map, str, this.customerAccountManager.getUserId(), this.capiClientManager, new 1(this, updateSubscriptionsListener)).execute(new Void[0]);
    }
}
