package com.gumtree.android.auth.login;

import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.login.presenter.LoginPresenter.View;
import com.gumtree.android.auth.model.AuthResult;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.lang3.tuple.Pair;

final /* synthetic */ class GatedLoginView$$Lambda$4 implements Action {
    private final View arg$1;

    private GatedLoginView$$Lambda$4(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedLoginView$$Lambda$4(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.onAccountAdded((AuthIdentifier) ((Pair) obj).getLeft(), (AuthResult) ((Pair) obj).getRight());
    }
}
