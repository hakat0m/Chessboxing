package com.gumtree.android.auth;

import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SocialLoginFragmentFacebook$$Lambda$1 implements StatusCallback {
    private final SocialLoginFragmentFacebook arg$1;

    private SocialLoginFragmentFacebook$$Lambda$1(SocialLoginFragmentFacebook socialLoginFragmentFacebook) {
        this.arg$1 = socialLoginFragmentFacebook;
    }

    public static StatusCallback lambdaFactory$(SocialLoginFragmentFacebook socialLoginFragmentFacebook) {
        return new SocialLoginFragmentFacebook$$Lambda$1(socialLoginFragmentFacebook);
    }

    @Hidden
    public void call(Session session, SessionState sessionState, Exception exception) {
        this.arg$1.lambda$new$0(session, sessionState, exception);
    }
}
