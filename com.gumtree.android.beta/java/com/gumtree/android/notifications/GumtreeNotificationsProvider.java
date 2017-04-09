package com.gumtree.android.notifications;

import android.content.Context;
import android.text.TextUtils;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.notifications.providers.CAPIPushNotificationsProvider;
import com.gumtree.android.notifications.providers.GCMSettingsProvider;
import com.gumtree.android.notifications.providers.MDNSPushNotificationsProvider;
import com.gumtree.android.notifications.providers.PushNotificationsProvider;
import com.gumtree.android.notifications.providers.PushNotificationsProvider.UpdateSubscriptionsListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class GumtreeNotificationsProvider implements ManageGCMRegistrationID$GCMRegistrationListener, PushNotificationsProvider {
    private static final String TAG = GumtreeNotificationsProvider.class.getSimpleName();
    private CAPIPushNotificationsProvider capiPushProvider = new CAPIPushNotificationsProvider();
    private final Context context;
    private BaseAccountManager customerAccountManager;
    private EventBus mEventBus;
    private MDNSPushNotificationsProvider mdnsProvider = new MDNSPushNotificationsProvider(this.context);

    public GumtreeNotificationsProvider(BaseAccountManager baseAccountManager, EventBus eventBus, Context context) {
        this.customerAccountManager = baseAccountManager;
        this.mEventBus = eventBus;
        this.context = context.getApplicationContext();
    }

    private static void onUpdateSubscriptionsError(Map<NotificationType, Boolean> map, String str, UpdateSubscriptionsListener updateSubscriptionsListener) {
        if (updateSubscriptionsListener != null) {
            updateSubscriptionsListener.onUpdateSubscriptionsError(map, str);
        }
        Boolean bool = (Boolean) map.get(NotificationType.SAVED_SEARCHES);
        if (bool != null) {
            Map hashMap = new HashMap();
            hashMap.put(NotificationType.SAVED_SEARCHES, Boolean.valueOf(!bool.booleanValue()));
            new GCMSettingsProvider().persistNotificationTypesStates(hashMap);
        }
    }

    private static void trackEventNotificationSubscription(Map<NotificationType, Boolean> map) {
        for (Entry value : map.entrySet()) {
            if (((Boolean) value.getValue()).booleanValue()) {
                Track.eventNotificationSubscription();
            } else {
                Track.eventNotificationUnsubscription();
            }
        }
    }

    private static void trackEventNotificationSubscriptionOK(Map<NotificationType, Boolean> map) {
        for (Entry value : map.entrySet()) {
            if (((Boolean) value.getValue()).booleanValue()) {
                Track.eventNotificationSubscriptionOK();
            } else {
                Track.eventNotificationUnsubscriptionOK();
            }
        }
    }

    public void setup() {
        this.mdnsProvider.setup();
        this.capiPushProvider.setup();
        initPushNotifications();
    }

    public void updateSubscriptions(Map<NotificationType, Boolean> map, UpdateSubscriptionsListener updateSubscriptionsListener) {
        ManageGCMRegistrationID instance = ManageGCMRegistrationID.getInstance(this.context);
        Object gCMRegistrationID = instance.getGCMRegistrationID();
        if (TextUtils.isEmpty(gCMRegistrationID)) {
            instance.generateGCMNotificationID(this.context, this.mdnsProvider.getGoogleProjectNumber(), this);
        } else {
            updateSubscriptions(gCMRegistrationID, map, updateSubscriptionsListener);
        }
    }

    public void updateSubscriptions(String str, Map<NotificationType, Boolean> map, UpdateSubscriptionsListener updateSubscriptionsListener) {
        this.mdnsProvider.updateSubscriptions(str, map, new 1(this, str, updateSubscriptionsListener));
    }

    private void initPushNotifications() {
        ManageGCMRegistrationID instance = ManageGCMRegistrationID.getInstance(this.context);
        if (instance.isRegistered()) {
            checkNeedToUpdateSubscriptions(instance);
        } else {
            instance.generateGCMNotificationID(this.context, this.mdnsProvider.getGoogleProjectNumber(), this);
        }
    }

    private void checkNeedToUpdateSubscriptions(ManageGCMRegistrationID manageGCMRegistrationID) {
        if (this.customerAccountManager.isUserLoggedIn()) {
            Log.v("Check Notifications Subscriptions: user Logged In");
            String gCMRegistrationID = manageGCMRegistrationID.getGCMRegistrationID();
            String cAPIGCMRegistrationId = ManageGCMRegistrationID.getCAPIGCMRegistrationId(this.context);
            if (!new GCMSettingsProvider().hasChatMessage()) {
                Log.v("Old app version did not have chat messages");
                updateAllSubscriptions();
                new GCMSettingsProvider().persistHasChatMessage(true);
            } else if (!gCMRegistrationID.equals(cAPIGCMRegistrationId)) {
                Log.v("New GCM Registration ID");
                updateAllSubscriptions();
            }
        }
    }

    private void updateAllSubscriptions() {
        Map hashMap = new HashMap();
        hashMap.put(NotificationType.SAVED_SEARCHES, Boolean.valueOf(new GCMSettingsProvider().isNotificationTypeEnabled(NotificationType.SAVED_SEARCHES)));
        hashMap.put(NotificationType.CHAT_MESSAGE, Boolean.valueOf(true));
        updateSubscriptions(hashMap, null);
    }

    public final void gcmRegistrationComplete() {
        ManageGCMRegistrationID instance = ManageGCMRegistrationID.getInstance(this.context);
        if (instance.isRegistered()) {
            this.mEventBus.post(new OnGCMRegistered());
            checkNeedToUpdateSubscriptions(instance);
        }
    }
}
