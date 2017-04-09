package com.gumtree.android.auth.api;

import android.text.TextUtils;
import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.exceptions.ApiException;
import com.ebay.classifieds.capi.executor.Request;
import com.ebay.classifieds.capi.executor.Result;
import com.ebay.classifieds.capi.users.login.LoginApi;
import com.ebay.classifieds.capi.users.login.UserLogin;
import com.ebay.classifieds.capi.users.login.UserLogins;
import com.gumtree.android.GumtreeApplication;
import javax.inject.Inject;
import javax.inject.Named;

public class AuthRequest implements Request<UserLogin> {
    @Named("xmlClient")
    @Inject
    ICapiClient capiClient;
    private String password;
    private String rel;
    private String username;

    public AuthRequest(String str, String str2) {
        this(str, str2, null);
    }

    public AuthRequest(String str, String str2, String str3) {
        GumtreeApplication.component().inject(this);
        this.username = str;
        this.password = str2;
        this.rel = str3;
    }

    public Result<UserLogin> execute() {
        LoginApi loginApi = (LoginApi) this.capiClient.api(LoginApi.class);
        try {
            UserLogins userLogins;
            if (TextUtils.isEmpty(this.rel)) {
                userLogins = (UserLogins) loginApi.login(this.username, this.password).toBlocking().first();
            } else {
                userLogins = (UserLogins) loginApi.login(this.username, this.password, this.rel).toBlocking().first();
            }
            Object obj = (UserLogin) userLogins.getList().get(0);
            obj.setPassword(this.password);
            return new Result(obj);
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (e.getCause() instanceof ApiException) {
                return new Result((ApiException) e.getCause());
            }
            throw e;
        }
    }
}
