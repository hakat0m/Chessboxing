package com.gumtree.android.auth;

import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SocialLoginFragmentFacebook$$Lambda$2 implements GraphUserCallback {
    private final SocialLoginFragmentFacebook arg$1;
    private final Session arg$2;

    private SocialLoginFragmentFacebook$$Lambda$2(SocialLoginFragmentFacebook socialLoginFragmentFacebook, Session session) {
        this.arg$1 = socialLoginFragmentFacebook;
        this.arg$2 = session;
    }

    public static GraphUserCallback lambdaFactory$(SocialLoginFragmentFacebook socialLoginFragmentFacebook, Session session) {
        return new SocialLoginFragmentFacebook$$Lambda$2(socialLoginFragmentFacebook, session);
    }

    @Hidden
    public void onCompleted(GraphUser graphUser, Response response) {
        this.arg$1.lambda$fetchUserInfo$1(this.arg$2, graphUser, response);
    }
}
