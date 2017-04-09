package com.gumtree.android.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Patterns;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.classifieds.capi.TokenFormatter;
import com.ebay.classifieds.capi.users.login.UserLogin;
import com.ebay.classifieds.capi.users.profile.UserProfile;
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.BaseAccountManager.AccountRemovedCallback;
import com.gumtree.android.common.providers.AppProvider;
import com.gumtree.android.favourites.FavouritesSyncIntentService;
import com.gumtree.android.notifications.GumtreeBadgeCounterManager;
import java.io.IOException;
import java.util.HashSet;
import org.apache.commons.lang3.StringUtils;

public class CustomerAccountManager implements BaseAccountManager {
    private static final Handler HANDLER = new Handler();
    private final AccountManager accountManager;
    private final Context context;

    public CustomerAccountManager(Context context) {
        this.context = context.getApplicationContext();
        this.accountManager = AccountManager.get(context.getApplicationContext());
    }

    public static String[] getListOfUserNames(Context context) {
        HashSet hashSet = new HashSet();
        for (Account account : AccountManager.get(context.getApplicationContext()).getAccounts()) {
            CharSequence charSequence = account.name;
            if (Patterns.EMAIL_ADDRESS.matcher(charSequence).matches()) {
                hashSet.add(charSequence);
            }
        }
        return (String[]) hashSet.toArray(new String[hashSet.size()]);
    }

    public void removeAccount(AccountRemovedCallback accountRemovedCallback) {
        try {
            this.accountManager.removeAccount(getLoggedInUser(), CustomerAccountManager$$Lambda$1.lambdaFactory$(accountRemovedCallback, getUsername()), HANDLER);
        } catch (UserNotLoggedInException e) {
            if (accountRemovedCallback == null) {
                Log.w(getClass().getSimpleName(), "Absorbed this error: ", e);
            } else {
                accountRemovedCallback.onFailure("Unknown user", e);
            }
        }
    }

    static /* synthetic */ void lambda$removeAccount$0(AccountRemovedCallback accountRemovedCallback, String str, AccountManagerFuture accountManagerFuture) {
        if (accountRemovedCallback != null) {
            try {
                if (((Boolean) accountManagerFuture.getResult()).booleanValue()) {
                    accountRemovedCallback.onSuccess(str);
                } else {
                    accountRemovedCallback.onFailure(str, new Exception("Unable to remove account"));
                }
            } catch (RuntimeException e) {
                Throwable e2 = e;
                accountRemovedCallback.onFailure(str, e2);
            } catch (AuthenticatorException e3) {
                e2 = e3;
                accountRemovedCallback.onFailure(str, e2);
            } catch (IOException e4) {
                e2 = e4;
                accountRemovedCallback.onFailure(str, e2);
            } catch (OperationCanceledException e5) {
                e2 = e5;
                accountRemovedCallback.onFailure(str, e2);
            }
        }
    }

    private Account getLoggedInUser() throws UserNotLoggedInException {
        Account[] accountsByType = this.accountManager.getAccountsByType(AppProvider.AUTHORITY);
        if (accountsByType.length > 0) {
            return accountsByType[0];
        }
        throw new UserNotLoggedInException();
    }

    public String getUserId() {
        try {
            return this.accountManager.getUserData(getLoggedInUser(), BaseAccountManager.KEY_USER_ID);
        } catch (RuntimeException e) {
            return null;
        } catch (UserNotLoggedInException e2) {
            return null;
        }
    }

    public String getContactEmail() {
        Exception exception;
        String username = getUsername();
        try {
            String userData = this.accountManager.getUserData(getLoggedInUser(), BaseAccountManager.KEY_CONTACT_ID);
            try {
                if (TextUtils.isEmpty(userData)) {
                    return getUsername();
                }
                return userData;
            } catch (Exception e) {
                Exception exception2 = e;
                username = userData;
                exception = exception2;
                exception.printStackTrace();
                return username;
            } catch (UserNotLoggedInException e2) {
                UserNotLoggedInException userNotLoggedInException = e2;
                username = userData;
                exception = userNotLoggedInException;
                exception.printStackTrace();
                return username;
            }
        } catch (RuntimeException e3) {
            exception = e3;
            exception.printStackTrace();
            return username;
        } catch (UserNotLoggedInException e4) {
            exception = e4;
            exception.printStackTrace();
            return username;
        }
    }

    public void setContactEmail(String str) {
        try {
            this.accountManager.setUserData(getLoggedInUser(), BaseAccountManager.KEY_CONTACT_ID, StringUtils.trimToNull(str));
        } catch (UserNotLoggedInException e) {
            e.printStackTrace();
        }
    }

    public String getContactPhone() {
        Exception e;
        try {
            return this.accountManager.getUserData(getLoggedInUser(), BaseAccountManager.KEY_CONTACT_PHONE);
        } catch (RuntimeException e2) {
            e = e2;
            e.printStackTrace();
            return null;
        } catch (UserNotLoggedInException e3) {
            e = e3;
            e.printStackTrace();
            return null;
        }
    }

    public String getDisplayName() {
        Exception e;
        try {
            return this.accountManager.getUserData(getLoggedInUser(), BaseAccountManager.KEY_DISPLAY_NAME);
        } catch (RuntimeException e2) {
            e = e2;
            e.printStackTrace();
            return null;
        } catch (UserNotLoggedInException e3) {
            e = e3;
            e.printStackTrace();
            return null;
        }
    }

    public void setContactPhone(String str) {
        try {
            this.accountManager.setUserData(getLoggedInUser(), BaseAccountManager.KEY_CONTACT_PHONE, StringUtils.trimToNull(str));
        } catch (UserNotLoggedInException e) {
            e.printStackTrace();
        }
    }

    public void setDisplayName(String str) {
        try {
            this.accountManager.setUserData(getLoggedInUser(), BaseAccountManager.KEY_DISPLAY_NAME, StringUtils.trimToNull(str));
        } catch (UserNotLoggedInException e) {
            e.printStackTrace();
        }
    }

    public void setHashedEmail(String str) {
        try {
            this.accountManager.setUserData(getLoggedInUser(), BaseAccountManager.KEY_HASHED_EMAIL, StringUtils.trimToNull(str));
            PreferenceManager.getDefaultSharedPreferences(this.context).edit().putString("pref_hashed_email", str).apply();
        } catch (UserNotLoggedInException e) {
            e.printStackTrace();
        }
    }

    public String getHashedEmail() {
        Exception e;
        try {
            return this.accountManager.getUserData(getLoggedInUser(), BaseAccountManager.KEY_HASHED_EMAIL);
        } catch (RuntimeException e2) {
            e = e2;
            e.printStackTrace();
            return null;
        } catch (UserNotLoggedInException e3) {
            e = e3;
            e.printStackTrace();
            return null;
        }
    }

    public String getPassword() {
        try {
            return this.accountManager.getPassword(getLoggedInUser());
        } catch (RuntimeException e) {
            return BuildConfig.FLAVOR;
        } catch (UserNotLoggedInException e2) {
            return BuildConfig.FLAVOR;
        }
    }

    public String getAuthentication() {
        try {
            return TokenFormatter.format(getUsername(), this.accountManager.peekAuthToken(getLoggedInUser(), AppProvider.AUTHORITY));
        } catch (RuntimeException e) {
            return null;
        } catch (UserNotLoggedInException e2) {
            return null;
        }
    }

    public String getAuthenticationToken() {
        try {
            return this.accountManager.peekAuthToken(getLoggedInUser(), AppProvider.AUTHORITY);
        } catch (RuntimeException e) {
            return null;
        } catch (UserNotLoggedInException e2) {
            return null;
        }
    }

    public String getUsername() {
        try {
            return getLoggedInUser().name;
        } catch (UserNotLoggedInException e) {
            return null;
        }
    }

    public UserLocation getUserLocation() {
        try {
            return new UserLocation(this.accountManager.getUserData(getLoggedInUser(), BaseAccountManager.KEY_LOC_ID), this.accountManager.getUserData(getLoggedInUser(), BaseAccountManager.KEY_LOC_NAME), this.accountManager.getUserData(getLoggedInUser(), BaseAccountManager.KEY_LOC_NEIGHBOURHOOD), this.accountManager.getUserData(getLoggedInUser(), BaseAccountManager.KEY_LOC_MAP), this.accountManager.getUserData(getLoggedInUser(), BaseAccountManager.KEY_LOC_POSTCODE));
        } catch (RuntimeException e) {
        } catch (UserNotLoggedInException e2) {
        }
        return null;
    }

    public void setUserLocation(UserLocation userLocation) {
        String locId;
        String locText;
        String locMap;
        String str = null;
        String locName = userLocation != null ? userLocation.getLocName() : null;
        if (userLocation != null) {
            locId = userLocation.getLocId();
        } else {
            locId = null;
        }
        if (userLocation != null) {
            locText = userLocation.getLocText();
        } else {
            locText = null;
        }
        if (userLocation != null) {
            locMap = userLocation.getLocMap();
        } else {
            locMap = null;
        }
        if (userLocation != null) {
            str = userLocation.getLocPostcode();
        }
        try {
            this.accountManager.setUserData(getLoggedInUser(), BaseAccountManager.KEY_LOC_ID, locId);
            this.accountManager.setUserData(getLoggedInUser(), BaseAccountManager.KEY_LOC_NAME, locName);
            this.accountManager.setUserData(getLoggedInUser(), BaseAccountManager.KEY_LOC_NEIGHBOURHOOD, locText);
            this.accountManager.setUserData(getLoggedInUser(), BaseAccountManager.KEY_LOC_MAP, locMap);
            this.accountManager.setUserData(getLoggedInUser(), BaseAccountManager.KEY_LOC_POSTCODE, str);
        } catch (RuntimeException e) {
        } catch (UserNotLoggedInException e2) {
        }
    }

    public boolean isUserLoggedIn() {
        try {
            if (getLoggedInUser() != null) {
                return true;
            }
        } catch (UserNotLoggedInException e) {
        }
        return false;
    }

    @WorkerThread
    public void initialiseSync() {
        if (isUserLoggedIn()) {
            boolean z = PreferenceManager.getDefaultSharedPreferences(this.context).getBoolean(BaseAccountManager.PREF_SETUP_COMPLETE, false);
            try {
                String packageNameForIntent = GumtreeApplication.getPackageNameForIntent();
                Account loggedInUser = getLoggedInUser();
                if (loggedInUser != null) {
                    ContentResolver.setIsSyncable(loggedInUser, packageNameForIntent, 1);
                    ContentResolver.setSyncAutomatically(loggedInUser, packageNameForIntent, true);
                    ContentResolver.addPeriodicSync(loggedInUser, packageNameForIntent, new Bundle(), BaseAccountManager.SYNC_FREQUENCY);
                    if (!z) {
                        triggerSearchSync();
                        PreferenceManager.getDefaultSharedPreferences(this.context).edit().putBoolean(BaseAccountManager.PREF_SETUP_COMPLETE, true).apply();
                    }
                }
            } catch (UserNotLoggedInException e) {
            }
        }
    }

    public void triggerSearchSync() {
        if (isUserLoggedIn()) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("force", true);
            bundle.putBoolean("expedited", true);
            try {
                ContentResolver.requestSync(getLoggedInUser(), GumtreeApplication.getPackageNameForIntent(), bundle);
            } catch (Exception e) {
            }
        }
    }

    public void createAccount(boolean z, UserLogin userLogin) {
        Account account = new Account(userLogin.getUserEmail(), AppProvider.AUTHORITY);
        if (z) {
            this.accountManager.addAccountExplicitly(account, userLogin.getPassword(), null);
        } else {
            this.accountManager.setPassword(account, userLogin.getPassword());
        }
        this.accountManager.setUserData(account, BaseAccountManager.KEY_USER_ID, userLogin.getUserId());
        this.accountManager.setAuthToken(account, AppProvider.AUTHORITY, userLogin.getUserToken());
        firstSync();
    }

    private void firstSync() {
        FavouritesSyncIntentService.start();
        GumtreeBadgeCounterManager.requestNumUnreadConversations();
    }

    public void updateProfile(UserProfile userProfile) {
        setContactEmail(userProfile.getPrimaryContactEmail());
        setDisplayName(userProfile.getDisplayName());
        setContactPhone(userProfile.getPhoneNumber());
        setHashedEmail(userProfile.getHashedUserEmailHex());
    }
}
