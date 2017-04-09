package com.gumtree.android.auth;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.apptentive.android.sdk.BuildConfig;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.common.utils.Log;
import java.io.IOException;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class SocialLoginFragmentGoogle extends BaseFragment implements OnClickListener, ConnectionCallbacks, OnConnectionFailedListener {
    private static final int AUTH_CODE_REQUEST_CODE = 1;
    private static final String EMAIL = "email";
    private static final String PROFILE = "profile";
    public static final int RC_SIGN_IN = 0;
    private AuthenticatorLoginAction authenticatorLoginAction;
    private ConnectionResult mConnectionResult;
    private GoogleApiClient mGoogleApiClient;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private Button signInButton;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof AuthenticatorLoginAction) {
            this.authenticatorLoginAction = (AuthenticatorLoginAction) activity;
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(2130903367, viewGroup, false);
        this.signInButton = (Button) inflate.findViewById(2131624863);
        this.signInButton.setOnClickListener(this);
        return inflate;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mGoogleApiClient = new Builder(GumtreeApplication.getContext()).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Plus.API).addScope(new Scope(PROFILE)).addScope(new Scope(EMAIL)).build();
    }

    public void onStop() {
        super.onStop();
        if (this.mGoogleApiClient.isConnected()) {
            this.mGoogleApiClient.disconnect();
        }
    }

    public void onClick(View view) {
        if (view.getId() == 2131624863 && !this.mGoogleApiClient.isConnecting()) {
            SocialLoginFragmentGooglePermissionsDispatcher.connectWithCheck(this);
        }
    }

    @NeedsPermission({"android.permission.GET_ACCOUNTS"})
    public void connect() {
        this.mSignInClicked = true;
        this.mGoogleApiClient.connect();
        resolveSignInError();
        Track.eventLoginAttempt("Google");
    }

    private void resolveSignInError() {
        if (this.mConnectionResult != null && this.mConnectionResult.hasResolution()) {
            try {
                this.mIntentInProgress = true;
                if (getActivity() != null) {
                    getActivity().startIntentSenderForResult(this.mConnectionResult.getResolution().getIntentSender(), 0, null, 0, 0, 0);
                }
            } catch (SendIntentException e) {
                Log.v("Resolving Sign in Errors For Google+ SignIn");
                this.mIntentInProgress = false;
                this.mGoogleApiClient.connect();
            }
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 0) {
            if (i2 != -1) {
                this.mSignInClicked = false;
            }
            this.mIntentInProgress = false;
            if (!this.mGoogleApiClient.isConnecting()) {
                this.mGoogleApiClient.connect();
            }
        }
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.signInButton.setEnabled(true);
        this.authenticatorLoginAction.onSocialLoginFailed();
        if (!this.mIntentInProgress) {
            this.mConnectionResult = connectionResult;
            if (this.mSignInClicked) {
                Log.v("Connection Failed For Google+ SignIn");
                resolveSignInError();
            }
        }
    }

    public void onConnected(Bundle bundle) {
        this.signInButton.setEnabled(false);
        this.authenticatorLoginAction.onSocialLoginStarted();
        this.mSignInClicked = false;
        Log.v("Connected For Google+ SignIn. Executing Token Task");
        new GetTokenTask(this, null).execute(new Void[0]);
    }

    public void onConnectionSuspended(int i) {
        Log.v("Google+ SignIn Connection Suspended. Attempting Connect.");
        this.mGoogleApiClient.connect();
    }

    public void resetGoogleApiState() {
        if (this.mGoogleApiClient != null && this.mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(this.mGoogleApiClient);
            this.mGoogleApiClient.disconnect();
        }
        this.signInButton.setEnabled(true);
        this.mSignInClicked = false;
    }

    private UserInfo getAuthToken() {
        Throwable e;
        UserInfo userInfo = null;
        String[] strArr = new String[]{PROFILE, EMAIL};
        UserInfo userInfo2 = new UserInfo(this, userInfo);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("oauth2:");
        int length = strArr.length;
        for (int i = 0; i < length; i += AUTH_CODE_REQUEST_CODE) {
            stringBuilder.append(strArr[i]);
            stringBuilder.append(" ");
        }
        new Bundle().putString("request_visible_actions", BuildConfig.FLAVOR);
        String stringBuilder2 = stringBuilder.toString();
        try {
            String accountName = Plus.AccountApi.getAccountName(this.mGoogleApiClient);
            Object[] objArr = new Object[AUTH_CODE_REQUEST_CODE];
            objArr[0] = accountName;
            Log.v(String.format("email Successfully retrieved: %s", objArr));
            userInfo2.setEmail(accountName);
            String token = GoogleAuthUtil.getToken(getActivity(), accountName, stringBuilder2);
            Object[] objArr2 = new Object[AUTH_CODE_REQUEST_CODE];
            objArr2[0] = token;
            Log.v(String.format("Token successfully retrieved: %s", objArr2));
            userInfo2.setAccessToken(token);
            return userInfo2;
        } catch (IOException e2) {
            e = e2;
            Log.e("Error requesting token Exception: ", e);
            return userInfo;
        } catch (GoogleAuthException e3) {
            e = e3;
            Log.e("Error requesting token Exception: ", e);
            return userInfo;
        } catch (NullPointerException e4) {
            e = e4;
            Log.e("Error requesting token Exception: ", e);
            return userInfo;
        }
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        SocialLoginFragmentGooglePermissionsDispatcher.onRequestPermissionsResult(this, i, iArr);
    }
}
