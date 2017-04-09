package com.gumtree.android.login;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AccountAuthenticatorService extends Service {
    private AccountAuthenticator authenticator;

    public void onCreate() {
        this.authenticator = new AccountAuthenticator(this);
    }

    public IBinder onBind(Intent intent) {
        return this.authenticator.getIBinder();
    }
}
