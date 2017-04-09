package com.gumtree.android.login;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.gumtree.DevelopmentFlags;
import com.gumtree.android.auth.AuthenticatorActivity;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.providers.AppProvider;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.home.HomeActivity;
import javax.inject.Inject;

public class AccountAuthenticator extends AbstractAccountAuthenticator {
    @Inject
    BaseAccountManager accountManager;
    private final Context context;

    public AccountAuthenticator(Context context) {
        super(context);
        ComponentsManager.get().getAppComponent().inject(this);
        this.context = context.getApplicationContext();
    }

    public Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse, String str, String str2, String[] strArr, Bundle bundle) throws NetworkErrorException {
        Parcelable homeIntent = this.accountManager.isUserLoggedIn() ? getHomeIntent() : getLoginIntent();
        homeIntent.putExtra("accountAuthenticatorResponse", accountAuthenticatorResponse);
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable("intent", homeIntent);
        return bundle2;
    }

    @NonNull
    private Intent getLoginIntent() {
        Class cls = AuthenticatorActivity.class;
        if (DevelopmentFlags.getInstance().isNewLoginEnabled()) {
            cls = AuthActivity.class;
        }
        return new Intent(this.context, cls);
    }

    @NonNull
    private Intent getHomeIntent() {
        return new Intent(this.context, HomeActivity.class);
    }

    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
        throw new UnsupportedOperationException();
    }

    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String str) {
        throw new UnsupportedOperationException();
    }

    public Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String str, Bundle bundle) throws NetworkErrorException {
        if (str.equals(AppProvider.AUTHORITY)) {
            Parcelable loginIntent = getLoginIntent();
            loginIntent.putExtra("username", account.name);
            loginIntent.putExtra("authtokenType", str);
            loginIntent.putExtra("accountAuthenticatorResponse", accountAuthenticatorResponse);
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable("intent", loginIntent);
            return bundle2;
        }
        bundle2 = new Bundle();
        bundle2.putString("errorMessage", "invalid authTokenType");
        return bundle2;
    }

    public String getAuthTokenLabel(String str) {
        return null;
    }

    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strArr) throws NetworkErrorException {
        Bundle bundle = new Bundle();
        bundle.putBoolean("booleanResult", false);
        return bundle;
    }

    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String str, Bundle bundle) throws NetworkErrorException {
        return null;
    }
}
