package com.gumtree.android.auth;

import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import com.gumtree.android.auth.BaseAccountManager.AccountRemovedCallback;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CustomerAccountManager$$Lambda$1 implements AccountManagerCallback {
    private final AccountRemovedCallback arg$1;
    private final String arg$2;

    private CustomerAccountManager$$Lambda$1(AccountRemovedCallback accountRemovedCallback, String str) {
        this.arg$1 = accountRemovedCallback;
        this.arg$2 = str;
    }

    public static AccountManagerCallback lambdaFactory$(AccountRemovedCallback accountRemovedCallback, String str) {
        return new CustomerAccountManager$$Lambda$1(accountRemovedCallback, str);
    }

    @Hidden
    public void run(AccountManagerFuture accountManagerFuture) {
        CustomerAccountManager.lambda$removeAccount$0(this.arg$1, this.arg$2, accountManagerFuture);
    }
}
