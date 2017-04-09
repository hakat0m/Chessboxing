package com.gumtree.android.auth;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Session;

public class FBHelper {
    public static void logoutViaFacebook() {
        clearTokenInformation();
    }

    public static void revokeFBPermissions() {
        new Request(Session.getActiveSession(), "/me/permissions", null, HttpMethod.DELETE, FBHelper$$Lambda$1.lambdaFactory$()).executeAsync();
    }

    private static void clearTokenInformation() {
        if (Session.getActiveSession() != null) {
            Session.getActiveSession().closeAndClearTokenInformation();
        }
    }
}
