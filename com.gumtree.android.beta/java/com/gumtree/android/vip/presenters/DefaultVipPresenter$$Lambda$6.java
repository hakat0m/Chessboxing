package com.gumtree.android.vip.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultVipPresenter$$Lambda$6 implements Action1 {
    private final DefaultVipPresenter arg$1;
    private final String arg$2;

    private DefaultVipPresenter$$Lambda$6(DefaultVipPresenter defaultVipPresenter, String str) {
        this.arg$1 = defaultVipPresenter;
        this.arg$2 = str;
    }

    public static Action1 lambdaFactory$(DefaultVipPresenter defaultVipPresenter, String str) {
        return new DefaultVipPresenter$$Lambda$6(defaultVipPresenter, str);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$onCallSelected$5(this.arg$2, (Throwable) obj);
    }
}
