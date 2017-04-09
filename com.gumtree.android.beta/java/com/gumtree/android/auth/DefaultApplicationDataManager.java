package com.gumtree.android.auth;

import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.preference.PreferenceManager;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.UserAccountClearedEvent;
import com.gumtree.android.favourites.SavedFavouritesSync;
import com.gumtree.android.message_box.ConversationsDao;
import com.gumtree.android.message_box.MessagesDao;
import com.gumtree.android.message_box.conversation.ConversationActivity;
import com.gumtree.android.notifications.IBadgeCounterManager;
import com.gumtree.android.notifications.ManageGCMRegistrationID;
import com.gumtree.android.notifications.NotificationType;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.repositories.Repository;
import com.gumtree.android.savedsearches.NewSavedSearchesDao;
import com.gumtree.android.savedsearches.SavedSearchSync;

public class DefaultApplicationDataManager implements ApplicationDataManager {
    private final Context appContext;
    private final IBadgeCounterManager badgeCounterManager;
    private final BaseAccountManager baseAccountManager;
    private final Repository<DraftAd> draftAdRepository;
    private final EventBus eventBus;

    public DefaultApplicationDataManager(Context context, Repository<DraftAd> repository, IBadgeCounterManager iBadgeCounterManager, BaseAccountManager baseAccountManager, EventBus eventBus) {
        this.appContext = context;
        this.draftAdRepository = repository;
        this.badgeCounterManager = iBadgeCounterManager;
        this.baseAccountManager = baseAccountManager;
        this.eventBus = eventBus;
    }

    public void clearAllUserData() {
        clearAppData();
        this.baseAccountManager.removeAccount(null);
        this.eventBus.post(new UserAccountClearedEvent());
    }

    public void clearAppData() {
        clearSavedSearches();
        clearFavourites();
        clearConversations();
        clearDraftAd();
        clearPushNotificationsData();
        clearFacebookData();
        clearGoogleAndSmartLock();
    }

    private void clearSavedSearches() {
        new SavedSearchSync(this.appContext).cleanup();
        new NewSavedSearchesDao(this.appContext.getContentResolver()).clearNewSavedSearches();
    }

    private void clearFavourites() {
        new SavedFavouritesSync(this.appContext).cleanup();
    }

    private void clearConversations() {
        ContentResolver contentResolver = this.appContext.getContentResolver();
        new ConversationsDao().cleanTable(contentResolver);
        new MessagesDao().cleanTable(contentResolver);
        this.badgeCounterManager.resetNumUnreadConversations();
    }

    private void clearDraftAd() {
        this.draftAdRepository.clear(DraftAd.NEW_AD_ID).subscribe(DefaultApplicationDataManager$$Lambda$1.lambdaFactory$(), DefaultApplicationDataManager$$Lambda$2.lambdaFactory$());
    }

    static /* synthetic */ void lambda$clearDraftAd$0(Void voidR) {
    }

    static /* synthetic */ void lambda$clearDraftAd$1(Throwable th) {
    }

    private void clearPushNotificationsData() {
        ManageGCMRegistrationID.setCAPIGCMRegistrationId(this.appContext, BuildConfig.FLAVOR);
        PreferenceManager.getDefaultSharedPreferences(this.appContext).edit().putString("pref_hashed_email", BuildConfig.FLAVOR).apply();
        NotificationManager notificationManager = (NotificationManager) this.appContext.getSystemService(ConversationActivity.NOTIFICATION);
        notificationManager.cancel(NotificationType.SAVED_SEARCHES.getType());
        notificationManager.cancel(NotificationType.CHAT_MESSAGE.getType());
    }

    private void clearFacebookData() {
        FBHelper.logoutViaFacebook();
    }

    private void clearGoogleAndSmartLock() {
    }
}
