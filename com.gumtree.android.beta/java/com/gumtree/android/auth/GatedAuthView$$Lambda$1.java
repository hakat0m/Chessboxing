package com.gumtree.android.auth;

import com.gumtree.android.auth.presenter.AuthPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedAuthView$$Lambda$1 implements Action1 {
    private final GatedAuthView arg$1;

    private GatedAuthView$$Lambda$1(GatedAuthView gatedAuthView) {
        this.arg$1 = gatedAuthView;
    }

    public static Action1 lambdaFactory$(GatedAuthView gatedAuthView) {
        return new GatedAuthView$$Lambda$1(gatedAuthView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
