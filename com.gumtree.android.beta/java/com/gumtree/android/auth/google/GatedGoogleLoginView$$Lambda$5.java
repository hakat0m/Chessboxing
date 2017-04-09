package com.gumtree.android.auth.google;

import com.gumtree.android.auth.google.presenter.GoogleLoginPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.lang3.tuple.Pair;

final /* synthetic */ class GatedGoogleLoginView$$Lambda$5 implements Action {
    private final View arg$1;

    private GatedGoogleLoginView$$Lambda$5(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedGoogleLoginView$$Lambda$5(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showPage((String) ((Pair) obj).getLeft(), (String) ((Pair) obj).getRight());
    }
}
