package com.gumtree.android.vip;

import com.ebay.classifieds.capi.users.profile.UserProfile;
import com.gumtree.android.vip.VIPSellerOtherItemsFragment.UserProfileAsyncTaskLoader;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class VIPSellerOtherItemsFragment$UserProfileAsyncTaskLoader$$Lambda$2 implements Func1 {
    private static final VIPSellerOtherItemsFragment$UserProfileAsyncTaskLoader$$Lambda$2 instance = new VIPSellerOtherItemsFragment$UserProfileAsyncTaskLoader$$Lambda$2();

    private VIPSellerOtherItemsFragment$UserProfileAsyncTaskLoader$$Lambda$2() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return UserProfileAsyncTaskLoader.lambda$loadInBackground$1((UserProfile) obj);
    }
}
