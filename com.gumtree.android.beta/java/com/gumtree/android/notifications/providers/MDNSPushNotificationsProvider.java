package com.gumtree.android.notifications.providers;

import android.content.Context;
import com.ebay.android.frlib.CredentialManager;
import com.ebay.android.frlib.FrontierLib;
import com.ebay.android.frlib.FrontierLib.LibraryComponents;
import com.ebay.android.frlib.mdns.MDNS;
import com.ebay.android.frlib.mdns.Subscription;
import com.ebay.android.frlib.mdns.Subscriptions;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.notifications.MDNSCredentialsFactory;
import com.gumtree.android.notifications.NotificationType;
import com.gumtree.android.notifications.providers.PushNotificationsProvider.UpdateSubscriptionsListener;
import java.util.Map;
import java.util.Map.Entry;
import javax.inject.Inject;

public class MDNSPushNotificationsProvider implements PushNotificationsProvider {
    private Context context;
    @Inject
    BaseAccountManager customerAccountManager;
    private FrontierLib mFrontierLib;

    public MDNSPushNotificationsProvider(Context context) {
        GumtreeApplication.component().inject(this);
        this.context = context.getApplicationContext();
    }

    public void setup() {
        String string = this.context.getString(2131165352);
        CredentialManager.set(string, true, MDNSCredentialsFactory.getMDNSCredentials(0));
        CredentialManager.set(string, false, MDNSCredentialsFactory.getMDNSCredentials(1));
        try {
            this.mFrontierLib = FrontierLib.getInstance(this.context);
        } catch (Throwable e) {
            Log.e("Error when trying to initialize FrontierLib", e);
        }
    }

    public void updateSubscriptions(Map<NotificationType, Boolean> map, UpdateSubscriptionsListener updateSubscriptionsListener) {
        throw new UnsupportedOperationException("MDNSPushNotificationsProvider does not support update subscriptions without providing gcmRegistrationId");
    }

    public void updateSubscriptions(String str, Map<NotificationType, Boolean> map, UpdateSubscriptionsListener updateSubscriptionsListener) {
        MDNS.getInstance(this.mFrontierLib).setSubscriptions(getSubscriptions(str, map), this.customerAccountManager.getAuthenticationToken(), new 1(this, updateSubscriptionsListener, map));
    }

    private Subscriptions getSubscriptions(String str, Map<NotificationType, Boolean> map) {
        Subscriptions subscriptions = new Subscriptions(this.customerAccountManager.getUsername(), this.customerAccountManager.getUserId(), str);
        Subscription subscription = new Subscription();
        for (Entry entry : map.entrySet()) {
            NotificationType notificationType = (NotificationType) entry.getKey();
            subscription.mEvents.put(notificationType.getMDNSKey(), entry.getValue());
            subscription.mDescription = GumtreeApplication.getContext().getString(notificationType.getDescriptionResId());
        }
        subscriptions.mSubscriptions.add(subscription);
        return subscriptions;
    }

    public String getGoogleProjectNumber() {
        return this.mFrontierLib.getSettings().getCredentials(LibraryComponents.MDNS).getGoogleProjectNumber();
    }
}
