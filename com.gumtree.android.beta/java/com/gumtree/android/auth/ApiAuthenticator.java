package com.gumtree.android.auth;

import android.support.annotation.NonNull;
import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.net.Proxy;

public class ApiAuthenticator implements Authenticator {
    private final ApplicationDataManager applicationDataManager;

    public ApiAuthenticator(@NonNull ApplicationDataManager applicationDataManager) {
        this.applicationDataManager = applicationDataManager;
    }

    public Request authenticate(Proxy proxy, Response response) throws IOException {
        this.applicationDataManager.clearAllUserData();
        return null;
    }

    public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
        this.applicationDataManager.clearAllUserData();
        return null;
    }
}
