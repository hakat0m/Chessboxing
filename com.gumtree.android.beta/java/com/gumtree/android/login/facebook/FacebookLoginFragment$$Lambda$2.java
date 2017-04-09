package com.gumtree.android.login.facebook;

import com.facebook.FacebookException;
import com.facebook.widget.LoginButton.OnErrorListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class FacebookLoginFragment$$Lambda$2 implements OnErrorListener {
    private final FacebookLoginFragment arg$1;

    private FacebookLoginFragment$$Lambda$2(FacebookLoginFragment facebookLoginFragment) {
        this.arg$1 = facebookLoginFragment;
    }

    public static OnErrorListener lambdaFactory$(FacebookLoginFragment facebookLoginFragment) {
        return new FacebookLoginFragment$$Lambda$2(facebookLoginFragment);
    }

    @Hidden
    public void onError(FacebookException facebookException) {
        this.arg$1.lambda$initAuthButton$1(facebookException);
    }
}
