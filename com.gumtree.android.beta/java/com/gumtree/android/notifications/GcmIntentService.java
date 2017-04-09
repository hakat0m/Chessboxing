package com.gumtree.android.notifications;

import android.app.IntentService;
import android.os.Bundle;
import com.ebay.android.frlib.FrontierLib.NotificationSourceApp;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.notifications.handlers.ConversationMessageNotificationHandler;
import com.gumtree.android.notifications.handlers.SavedSearchesNotificationHandler;
import javax.inject.Inject;

public class GcmIntentService extends IntentService {
    private static final String GCM_DATA_TYPE = "type";
    private static final String TAG = GcmIntentService.class.getSimpleName();
    @Inject
    BaseAccountManager customerAccountManager;
    @Inject
    EventBus eventBus;

    /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$ebay$android$frlib$FrontierLib$NotificationSourceApp = new int[NotificationSourceApp.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$gumtree$android$notifications$NotificationType = new int[NotificationType.values().length];

        static {
            try {
                $SwitchMap$com$gumtree$android$notifications$NotificationType[NotificationType.SAVED_SEARCHES.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$gumtree$android$notifications$NotificationType[NotificationType.CHAT_MESSAGE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$ebay$android$frlib$FrontierLib$NotificationSourceApp[NotificationSourceApp.ThisApp.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$ebay$android$frlib$FrontierLib$NotificationSourceApp[NotificationSourceApp.AnotherAppInTheDomain.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public GcmIntentService() {
        super("GcmIntentService");
        GumtreeApplication.component().inject(this);
    }

    public static int getNotificationTypeId(Bundle bundle) {
        try {
            return Integer.valueOf(bundle.getString(GCM_DATA_TYPE)).intValue();
        } catch (NumberFormatException e) {
            Log.e(e.getMessage());
            return -1;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void onHandleIntent(android.content.Intent r5) {
        /*
        r4 = this;
        r1 = r5.getExtras();
        r0 = com.google.android.gms.gcm.GoogleCloudMessaging.getInstance(r4);
        r2 = r0.getMessageType(r5);
        if (r1 == 0) goto L_0x0037;
    L_0x000e:
        r0 = r1.isEmpty();
        if (r0 != 0) goto L_0x0037;
    L_0x0014:
        r0 = -1;
        r3 = r2.hashCode();
        switch(r3) {
            case -2062414158: goto L_0x0045;
            case 102161: goto L_0x004f;
            case 814694033: goto L_0x003b;
            default: goto L_0x001c;
        };
    L_0x001c:
        switch(r0) {
            case 0: goto L_0x0059;
            case 1: goto L_0x0061;
            case 2: goto L_0x0069;
            default: goto L_0x001f;
        };
    L_0x001f:
        r0 = TAG;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r3 = "Unhandled GoogleCloudMessage ";
        r1 = r1.append(r3);
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.gumtree.android.common.utils.Log.v(r0, r1);
    L_0x0037:
        com.gumtree.android.notifications.GcmBroadcastReceiver.completeWakefulIntent(r5);
        return;
    L_0x003b:
        r3 = "send_error";
        r3 = r2.equals(r3);
        if (r3 == 0) goto L_0x001c;
    L_0x0043:
        r0 = 0;
        goto L_0x001c;
    L_0x0045:
        r3 = "deleted_messages";
        r3 = r2.equals(r3);
        if (r3 == 0) goto L_0x001c;
    L_0x004d:
        r0 = 1;
        goto L_0x001c;
    L_0x004f:
        r3 = "gcm";
        r3 = r2.equals(r3);
        if (r3 == 0) goto L_0x001c;
    L_0x0057:
        r0 = 2;
        goto L_0x001c;
    L_0x0059:
        r0 = TAG;
        r1 = "Notification of type ERROR, ignored";
        com.gumtree.android.common.utils.Log.e(r0, r1);
        goto L_0x0037;
    L_0x0061:
        r0 = TAG;
        r1 = "Notification of type DELETED, ignored";
        com.gumtree.android.common.utils.Log.e(r0, r1);
        goto L_0x0037;
    L_0x0069:
        r0 = com.ebay.android.frlib.FrontierLib.getInstance(r4);	 Catch:{ NameNotFoundException -> 0x0084 }
        r0 = r0.classifyNotificationSource(r5);	 Catch:{ NameNotFoundException -> 0x0084 }
        r2 = com.gumtree.android.notifications.GcmIntentService.AnonymousClass1.$SwitchMap$com$ebay$android$frlib$FrontierLib$NotificationSourceApp;	 Catch:{ NameNotFoundException -> 0x0084 }
        r0 = r0.ordinal();	 Catch:{ NameNotFoundException -> 0x0084 }
        r0 = r2[r0];	 Catch:{ NameNotFoundException -> 0x0084 }
        switch(r0) {
            case 1: goto L_0x00a2;
            case 2: goto L_0x00a2;
            default: goto L_0x007c;
        };	 Catch:{ NameNotFoundException -> 0x0084 }
    L_0x007c:
        r0 = TAG;	 Catch:{ NameNotFoundException -> 0x0084 }
        r1 = "Notification is not from my MDNS domain, ignored";
        com.gumtree.android.common.utils.Log.e(r0, r1);	 Catch:{ NameNotFoundException -> 0x0084 }
        goto L_0x0037;
    L_0x0084:
        r0 = move-exception;
        r1 = TAG;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "NameNotFound exception, ";
        r2 = r2.append(r3);
        r0 = r0.getMessage();
        r0 = r2.append(r0);
        r0 = r0.toString();
        com.gumtree.android.common.utils.Log.e(r1, r0);
        goto L_0x0037;
    L_0x00a2:
        r4.handleAppPushNotification(r1);	 Catch:{ NameNotFoundException -> 0x0084 }
        goto L_0x0037;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gumtree.android.notifications.GcmIntentService.onHandleIntent(android.content.Intent):void");
    }

    private void handleAppPushNotification(Bundle bundle) {
        Log.v(TAG, "Push Notification Received: " + bundle.toString());
        int notificationTypeId = getNotificationTypeId(bundle);
        NotificationType notificationType = NotificationType.getNotificationType(notificationTypeId);
        if (notificationType == null) {
            Log.e(TAG, "Bad notification type number: " + notificationTypeId);
            return;
        }
        switch (AnonymousClass1.$SwitchMap$com$gumtree$android$notifications$NotificationType[notificationType.ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                new SavedSearchesNotificationHandler().handlePushNotification(this, bundle);
                return;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                new ConversationMessageNotificationHandler().handlePushNotification(this, bundle, this.eventBus, this.customerAccountManager.getUsername());
                return;
            default:
                Log.e(TAG, "Bad notification type: " + notificationType);
                return;
        }
    }
}
