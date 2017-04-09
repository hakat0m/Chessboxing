package com.gumtree.android.message_box;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.gumtree.android.common.activities.NavigationActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.drawer.NavigationItem;
import com.gumtree.android.notifications.NotificationType;

public class InboxActivity extends NavigationActivity {
    public static final String ACTION_BAR = "actionBar";
    private static final String EXTRA_ORIGIN = "com.gumtree.android.message_box.origin";
    public static final String NAV_DRAWER = "navigationDrawer";
    public static final String NOTIFICATION = "notification";

    public @interface MessagesOrigin {
    }

    public static Intent createIntent(Context context, String str) {
        Intent intent = new Intent(context, InboxActivity.class);
        intent.putExtra(EXTRA_ORIGIN, str);
        return intent;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2130903086, bundle);
        if (bundle == null) {
            ((NotificationManager) getSystemService(NOTIFICATION)).cancel(NotificationType.CHAT_MESSAGE.getType());
            String stringExtra = getIntent().getStringExtra(EXTRA_ORIGIN);
            Track.viewConversations(stringExtra);
            if (NOTIFICATION.equals(stringExtra)) {
                Track.eventMessageNotificationOpenToConversations();
            }
        }
    }

    protected NavigationItem getNavigationItem() {
        return NavigationItem.MESSAGES;
    }

    protected String getTrackingView() {
        return "MessageCenter";
    }
}
