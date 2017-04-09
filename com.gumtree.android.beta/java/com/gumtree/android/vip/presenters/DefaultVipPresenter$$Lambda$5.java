package com.gumtree.android.vip.presenters;

import com.ebay.classifieds.capi.phone.Phone;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultVipPresenter$$Lambda$5 implements Action1 {
    private final DefaultVipPresenter arg$1;

    private DefaultVipPresenter$$Lambda$5(DefaultVipPresenter defaultVipPresenter) {
        this.arg$1 = defaultVipPresenter;
    }

    public static Action1 lambdaFactory$(DefaultVipPresenter defaultVipPresenter) {
        return new DefaultVipPresenter$$Lambda$5(defaultVipPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$onCallSelected$4((Phone) obj);
    }
}
