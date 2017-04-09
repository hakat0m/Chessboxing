package com.gumtree.android.auth;

public interface AuthenticatorLoginAction {
    void onSocialLoginFailed();

    void onSocialLoginStarted();

    void onSocialLoginSuccessful(String str, String str2, String str3);
}
