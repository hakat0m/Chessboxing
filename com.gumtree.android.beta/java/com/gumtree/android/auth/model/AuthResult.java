package com.gumtree.android.auth.model;

import com.ebay.classifieds.capi.users.login.UserLogin;
import com.ebay.classifieds.capi.users.profile.UserProfile;

public class AuthResult {
    private final String passWord;
    private UserProfile profile;
    private final String userName;

    public AuthResult(UserLogin userLogin) {
        this.userName = userLogin.getUserEmail();
        this.passWord = userLogin.getPassword();
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassWord() {
        return this.passWord;
    }

    public UserProfile getUserProfile() {
        return this.profile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.profile = userProfile;
    }
}
