package com.gumtree.android.login.facebook;

import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class FacebookLoginFragment$$Lambda$1 implements StatusCallback {
    private final FacebookLoginFragment arg$1;

    private FacebookLoginFragment$$Lambda$1(FacebookLoginFragment facebookLoginFragment) {
        this.arg$1 = facebookLoginFragment;
    }

    public static StatusCallback lambdaFactory$(FacebookLoginFragment facebookLoginFragment) {
        return new FacebookLoginFragment$$Lambda$1(facebookLoginFragment);
    }

    @Hidden
    public void call(Session session, SessionState sessionState, Exception exception) {
        this.arg$1.lambda$new$0(session, sessionState, exception);
    }
}
