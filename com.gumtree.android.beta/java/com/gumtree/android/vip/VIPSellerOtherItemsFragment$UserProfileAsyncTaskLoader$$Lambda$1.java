package com.gumtree.android.vip;

import com.gumtree.android.vip.VIPSellerOtherItemsFragment.UserProfileAsyncTaskLoader;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class VIPSellerOtherItemsFragment$UserProfileAsyncTaskLoader$$Lambda$1 implements Func1 {
    private static final VIPSellerOtherItemsFragment$UserProfileAsyncTaskLoader$$Lambda$1 instance = new VIPSellerOtherItemsFragment$UserProfileAsyncTaskLoader$$Lambda$1();

    private VIPSellerOtherItemsFragment$UserProfileAsyncTaskLoader$$Lambda$1() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return UserProfileAsyncTaskLoader.lambda$loadInBackground$0((Throwable) obj);
    }
}
