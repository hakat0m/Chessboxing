package com.gumtree.android.notifications.handlers;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.TaskStackBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.message_box.conversation.ConversationActivity;
import com.gumtree.android.notifications.NotificationAlertText;
import com.gumtree.android.notifications.NotificationType;
import com.gumtree.android.savedsearches.NewSavedSearchesDao;
import com.gumtree.android.savedsearches.SavedSearchesActivity;
import java.util.Random;

public class SavedSearchesNotificationHandler {
    private static final String GCM_DATA_ALERT_ID = "alertId";
    private static final String GCM_DATA_TITLE = "title";
    private static final int RAND = 100;
    private static final String TAG = SavedSearchesNotificationHandler.class.getSimpleName();

    private static PendingIntent getNotificationPendingIntent(Context context) {
        TaskStackBuilder create = TaskStackBuilder.create(context);
        create.addParentStack(SavedSearchesActivity.class);
        Intent createIntent = SavedSearchesActivity.createIntent(context);
        createIntent.putExtra(SavedSearchesActivity.EXTRA_FROM_PUSH_NOTIFICATION, true);
        create.addNextIntent(createIntent);
        return create.getPendingIntent(new Random().nextInt(RAND), 134217728);
    }

    public void handlePushNotification(Context context, Bundle bundle) {
        ((NotificationManager) context.getSystemService(ConversationActivity.NOTIFICATION)).notify(NotificationType.SAVED_SEARCHES.getType(), buildGumtreePushNotification(context, bundle));
        Track.prepare((Application) context.getApplicationContext());
        Track.viewAlertNotificationReceived();
    }

    private Notification buildGumtreePushNotification(Context context, Bundle bundle) {
        NewSavedSearchesDao newSavedSearchesDao = new NewSavedSearchesDao(context.getContentResolver());
        newSavedSearchesDao.addNewSavedSearch(String.valueOf(bundle.getString(GCM_DATA_ALERT_ID)));
        int numNewSavedSearches = newSavedSearchesDao.getNumNewSavedSearches();
        if (numNewSavedSearches == 0) {
            numNewSavedSearches = 1;
        }
        CharSequence quantityString = context.getResources().getQuantityString(2131689479, numNewSavedSearches, new Object[]{Integer.valueOf(numNewSavedSearches)});
        CharSequence notificationMessage = getNotificationMessage(context, bundle, numNewSavedSearches);
        return new Builder(context).setSmallIcon(2130837844).setPriority(0).setAutoCancel(true).setColor(context.getResources().getColor(2131492902)).setStyle(new BigTextStyle().bigText(notificationMessage)).setContentTitle(quantityString).setContentText(notificationMessage).setContentIntent(getNotificationPendingIntent(context)).setDefaults(-1).build();
    }

    private String getNotificationMessage(Context context, Bundle bundle, int i) {
        String string;
        if (i == 1) {
            string = bundle.getString(GCM_DATA_TITLE);
            if (string != null) {
                try {
                    NotificationAlertText notificationAlertText = (NotificationAlertText) new Gson().fromJson(string, NotificationAlertText.class);
                    if (notificationAlertText != null) {
                        string = notificationAlertText.getBody();
                        return string != null ? context.getResources().getString(2131165775) : string;
                    }
                } catch (JsonSyntaxException e) {
                    Log.e(TAG, "Error trying to parse AlertTextJson from a push notification", e);
                }
            }
        }
        string = null;
        if (string != null) {
        }
    }
}
