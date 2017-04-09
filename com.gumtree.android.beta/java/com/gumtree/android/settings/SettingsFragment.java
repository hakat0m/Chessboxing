package com.gumtree.android.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.widget.Toast;
import com.apptentive.android.sdk.BuildConfig;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.gumtree.DevelopmentFlags;
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.api.EnvironmentSettings;
import com.gumtree.android.auth.ApplicationDataManager;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.AuthenticatorActivity;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.cleanup.RecentSearchesCleanup;
import com.gumtree.android.common.utils.crashlytics.CrashlyticsHelper;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnAccountRemovedEvent;
import com.gumtree.android.events.OnSharedPreferenceValueChangedEvent;
import com.gumtree.android.locations.LocationActivity;
import com.gumtree.android.login.AuthActivity;
import com.gumtree.android.notifications.GumtreeNotificationsProvider.OnGCMRegistered;
import com.gumtree.android.notifications.IBadgeCounterManager;
import com.gumtree.android.notifications.ManageGCMRegistrationID;
import com.gumtree.android.notifications.NotificationType;
import com.gumtree.android.notifications.providers.GCMSettingsProvider;
import com.gumtree.android.notifications.providers.PushNotificationsProvider;
import com.gumtree.android.notifications.providers.PushNotificationsProvider.UpdateSubscriptionsListener;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.repositories.Repository;
import com.gumtree.android.userprofile.UserProfileStatusService;
import com.gumtree.android.userprofile.services.UserService;
import com.squareup.otto.Subscribe;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

public class SettingsFragment extends PreferenceFragment implements OnPreferenceChangeListener, OnPreferenceClickListener, UpdateSubscriptionsListener {
    private static final String ACCOUNT_DETAILS = "account_details";
    private static final String CLEAR_SEARCH = "clear_search";
    private static final String IS_LOGGED = "is_logged";
    private static final String IS_LOGGING_OUT = "Is Logging out";
    private static final String SPACE = " ";
    private static final String VERSION = "version";
    private Context appContext;
    @Inject
    PushNotificationsProvider appNotificationsProvider;
    @Inject
    ApplicationDataManager applicationDataManager;
    @Inject
    IBadgeCounterManager badgeCounterManager;
    @Inject
    Repository<DraftAd> draftAdRepository;
    @Inject
    EnvironmentSettings environmentSettings;
    private boolean isLoggingOut;
    private Preference loginPreference;
    @Inject
    protected EventBus mEventBus;
    private GoogleApiClient mGoogleApiClient;
    private Map<NotificationType, Boolean> mModifiedNotificationSettings;
    @Inject
    BaseAccountManager user;
    @Inject
    UserProfileStatusService userProfileStatusService;
    @Inject
    UserService userService;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.appContext = activity.getApplicationContext();
    }

    public void onCreate(Bundle bundle) {
        boolean z = false;
        super.onCreate(bundle);
        GumtreeApplication.component().inject(this);
        addPreferencesFromResource(2131034115);
        findPreference(CLEAR_SEARCH).setOnPreferenceClickListener(this);
        this.loginPreference = findPreference(IS_LOGGED);
        this.loginPreference.setOnPreferenceClickListener(this);
        findPreference(ACCOUNT_DETAILS).setOnPreferenceClickListener(this);
        this.mModifiedNotificationSettings = new HashMap();
        findPreference(NotificationType.SAVED_SEARCHES.getSettingsKey()).setOnPreferenceChangeListener(this);
        findPreference(NotificationType.CHAT_MESSAGE.getSettingsKey()).setOnPreferenceChangeListener(this);
        this.environmentSettings.display(getPreferenceScreen());
        if (bundle != null && bundle.getBoolean(IS_LOGGING_OUT, false)) {
            z = true;
        }
        this.isLoggingOut = z;
        setUserState();
        setApplicationInfoPreferences();
        updateNotificationSettingsEnabledState();
    }

    public void onResume() {
        super.onResume();
        this.mEventBus.register(this);
        if (this.userProfileStatusService.isProfileDirty() && this.user.getUsername() != null) {
            this.userService.update();
        }
    }

    public void onPause() {
        super.onPause();
        this.mEventBus.unregister(this);
        disconnectGoogleApi();
    }

    public void onStop() {
        super.onStop();
        if (this.user.isUserLoggedIn() && this.mModifiedNotificationSettings.size() > 0) {
            this.appNotificationsProvider.updateSubscriptions(this.mModifiedNotificationSettings, null);
        }
        disconnectGoogleApi();
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(IS_LOGGING_OUT, this.isLoggingOut);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == LocationActivity.ACTIVITY_REQUEST_CODE && i2 == -1 && getView() != null && DevelopmentFlags.getInstance().isNewLoginEnabled() && intent.hasExtra("displayname")) {
            String stringExtra = intent.getStringExtra("displayname");
            Snackbar.make(getView(), getString(2131165552, new Object[]{stringExtra}), 0).show();
        }
        setUserState();
        updateNotificationSettingsEnabledState();
        updateNotificationSettingsValuesStates();
    }

    protected void setUserState() {
        if (this.user.getUsername() != null) {
            this.loginPreference.setTitle(getString(2131165873));
            this.loginPreference.setSummary(getString(2131165630) + SPACE + this.user.getUsername());
        } else {
            this.loginPreference.setTitle(getString(2131165871));
            this.loginPreference.setSummary(getString(2131165872));
        }
        if (this.isLoggingOut) {
            this.loginPreference.setTitle(getString(2131165870));
            this.loginPreference.setEnabled(false);
            return;
        }
        this.loginPreference.setEnabled(true);
    }

    private void setApplicationInfoPreferences() {
        try {
            findPreference(VERSION).setSummary(BuildConfig.FLAVOR + this.appContext.getPackageManager().getPackageInfo(this.appContext.getApplicationInfo().packageName, 128).versionName);
        } catch (Throwable e) {
            CrashlyticsHelper.getInstance().catchGumtreeException(e);
        }
    }

    public boolean onPreferenceClick(Preference preference) {
        if (preference.getKey().equals(CLEAR_SEARCH)) {
            new RecentSearchesCleanup(this.appContext).startCleanup();
            Toast.makeText(this.appContext, getString(2131165638), 0).show();
        } else if (preference.getKey().equals(IS_LOGGED)) {
            if (this.user.isUserLoggedIn()) {
                Track.eventLogoutBegin();
                if (!ManageGCMRegistrationID.getInstance(GumtreeApplication.getContext()).isRegistered() || TextUtils.isEmpty(ManageGCMRegistrationID.getCAPIGCMRegistrationId(GumtreeApplication.getContext()))) {
                    onUpdateSubscriptionsOK(null);
                } else {
                    Map hashMap = new HashMap();
                    for (Object put : NotificationType.values()) {
                        hashMap.put(put, Boolean.valueOf(false));
                    }
                    this.isLoggingOut = true;
                    this.appNotificationsProvider.updateSubscriptions(hashMap, this);
                    updateNotificationSettingsEnabledState();
                    setUserState();
                }
            } else if (DevelopmentFlags.getInstance().isNewLoginEnabled()) {
                startActivityForResult(AuthActivity.createIntent(AuthIdentifier.SETTINGS, getActivity()), LocationActivity.ACTIVITY_REQUEST_CODE);
            } else {
                AuthenticatorActivity.startActivity((PreferenceFragment) this, 3);
            }
        } else if (preference.getKey().equals(ACCOUNT_DETAILS)) {
            updateContactEmail();
        }
        return false;
    }

    private void updateNotificationSettingsEnabledState() {
        if (!ManageGCMRegistrationID.getInstance(GumtreeApplication.getContext()).isRegistered()) {
            findPreference(NotificationType.SAVED_SEARCHES.getSettingsKey()).setEnabled(false);
            findPreference(NotificationType.CHAT_MESSAGE.getSettingsKey()).setEnabled(false);
        } else if (this.isLoggingOut) {
            findPreference(NotificationType.SAVED_SEARCHES.getSettingsKey()).setEnabled(false);
            findPreference(NotificationType.CHAT_MESSAGE.getSettingsKey()).setEnabled(false);
        } else {
            findPreference(NotificationType.SAVED_SEARCHES.getSettingsKey()).setEnabled(this.user.isUserLoggedIn());
            findPreference(NotificationType.CHAT_MESSAGE.getSettingsKey()).setEnabled(this.user.isUserLoggedIn());
        }
    }

    private void updateNotificationSettingsValuesStates() {
        onSharedPreferenceValueChangedEvent(new OnSharedPreferenceValueChangedEvent(NotificationType.SAVED_SEARCHES.getSettingsKey(), new GCMSettingsProvider().isNotificationTypeEnabled(NotificationType.SAVED_SEARCHES)));
        onSharedPreferenceValueChangedEvent(new OnSharedPreferenceValueChangedEvent(NotificationType.CHAT_MESSAGE.getSettingsKey(), new GCMSettingsProvider().isNotificationTypeEnabled(NotificationType.CHAT_MESSAGE)));
    }

    public boolean onPreferenceChange(Preference preference, Object obj) {
        boolean z = false;
        String key = preference.getKey();
        if (key.equals(NotificationType.SAVED_SEARCHES.getSettingsKey())) {
            if (((SwitchPreference) preference).isChecked() == ((Boolean) obj).booleanValue()) {
                return true;
            }
            boolean z2 = !((SwitchPreference) preference).isChecked();
            Editor editor = preference.getEditor();
            editor.putBoolean(NotificationType.SAVED_SEARCHES.getSettingsKey(), z2);
            editor.apply();
            if (this.mModifiedNotificationSettings.remove(NotificationType.SAVED_SEARCHES) == null) {
                this.mModifiedNotificationSettings.put(NotificationType.SAVED_SEARCHES, Boolean.valueOf(z2));
            }
            if (z2) {
                Track.eventAlertNotificationToggleOn();
                return true;
            }
            Track.eventAlertNotificationToggleOff();
            return true;
        } else if (!key.equals(NotificationType.CHAT_MESSAGE.getSettingsKey())) {
            return false;
        } else {
            if (((SwitchPreference) preference).isChecked() == ((Boolean) obj).booleanValue()) {
                return true;
            }
            if (!((SwitchPreference) preference).isChecked()) {
                z = true;
            }
            Editor editor2 = preference.getEditor();
            editor2.putBoolean(NotificationType.CHAT_MESSAGE.getSettingsKey(), z);
            editor2.apply();
            if (z) {
                Track.eventMessageNotificationToggleOn();
                return true;
            }
            Track.eventMessageNotificationToggleOff();
            return true;
        }
    }

    @Subscribe
    public void onAccountRemovedEvent(OnAccountRemovedEvent onAccountRemovedEvent) {
        setUserState();
        updateNotificationSettingsEnabledState();
    }

    @Subscribe
    public void onGCMRegistered(OnGCMRegistered onGCMRegistered) {
        updateNotificationSettingsEnabledState();
    }

    @Subscribe
    public void onSharedPreferenceValueChangedEvent(OnSharedPreferenceValueChangedEvent onSharedPreferenceValueChangedEvent) {
        String key = onSharedPreferenceValueChangedEvent.getKey();
        boolean value;
        Preference findPreference;
        if (key.equals(NotificationType.SAVED_SEARCHES.getSettingsKey())) {
            value = onSharedPreferenceValueChangedEvent.getValue();
            findPreference = findPreference(NotificationType.SAVED_SEARCHES.getSettingsKey());
            findPreference.setOnPreferenceChangeListener(null);
            ((SwitchPreference) findPreference).setChecked(value);
            findPreference.setOnPreferenceChangeListener(this);
        } else if (key.equals(NotificationType.CHAT_MESSAGE.getSettingsKey())) {
            value = onSharedPreferenceValueChangedEvent.getValue();
            findPreference = findPreference(NotificationType.CHAT_MESSAGE.getSettingsKey());
            findPreference.setOnPreferenceChangeListener(null);
            ((SwitchPreference) findPreference).setChecked(value);
            findPreference.setOnPreferenceChangeListener(this);
        }
    }

    public void onUpdateSubscriptionsOK(Map<NotificationType, Boolean> map) {
        this.isLoggingOut = false;
        performLogout();
    }

    public void onUpdateSubscriptionsError(Map<NotificationType, Boolean> map, String str) {
        this.isLoggingOut = false;
        Toast.makeText(GumtreeApplication.getContext(), 2131165632, 0).show();
        this.mEventBus.post(new OnAccountRemovedEvent(false));
    }

    private void performLogout() {
        this.applicationDataManager.clearAppData();
        signOutFromGoogleAndSmartLock();
        this.user.removeAccount(new 1(this));
    }

    private void signOutFromGoogleAndSmartLock() {
        2 2 = new 2(this);
        this.mGoogleApiClient = new Builder(GumtreeApplication.getContext()).addConnectionCallbacks(2).addOnConnectionFailedListener(SettingsFragment$$Lambda$1.lambdaFactory$(this)).addApi(Plus.API).addApi(Auth.CREDENTIALS_API).addScope(new Scope("profile")).addScope(new Scope("email")).build();
        this.mGoogleApiClient.connect();
    }

    /* synthetic */ void lambda$signOutFromGoogleAndSmartLock$0(ConnectionResult connectionResult) {
        Log.d(getClass().getSimpleName(), "Cannot connect to disconnect");
        smartLockDisableFailSafe();
    }

    private void smartLockDisableFailSafe() {
        3 3 = new 3(this);
        this.mGoogleApiClient = new Builder(GumtreeApplication.getContext()).addConnectionCallbacks(3).addOnConnectionFailedListener(SettingsFragment$$Lambda$2.lambdaFactory$(this)).addApi(Auth.CREDENTIALS_API).build();
        this.mGoogleApiClient.connect();
    }

    /* synthetic */ void lambda$smartLockDisableFailSafe$1(ConnectionResult connectionResult) {
        Log.d(getClass().getSimpleName(), "SmartLock disable fail-safe failed");
    }

    private void disconnectGoogleApi() {
        if (this.mGoogleApiClient != null && this.mGoogleApiClient.isConnected()) {
            this.mGoogleApiClient.disconnect();
        }
    }

    private void updateContactEmail() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(GumtreeApplication.getAPI().getUpdateEmailUrl()));
        startActivity(intent);
        Track.eventSettingsUserDetailsEditBegin();
        this.userProfileStatusService.setProfileDirty(true);
    }
}
