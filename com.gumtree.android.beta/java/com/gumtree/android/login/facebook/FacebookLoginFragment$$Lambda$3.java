package com.gumtree.android.login.facebook;

import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class FacebookLoginFragment$$Lambda$3 implements GraphUserCallback {
    private final FacebookLoginFragment arg$1;
    private final Session arg$2;

    private FacebookLoginFragment$$Lambda$3(FacebookLoginFragment facebookLoginFragment, Session session) {
        this.arg$1 = facebookLoginFragment;
        this.arg$2 = session;
    }

    public static GraphUserCallback lambdaFactory$(FacebookLoginFragment facebookLoginFragment, Session session) {
        return new FacebookLoginFragment$$Lambda$3(facebookLoginFragment, session);
    }

    @Hidden
    public void onCompleted(GraphUser graphUser, Response response) {
        this.arg$1.lambda$fetchUserInfo$2(this.arg$2, graphUser, response);
    }
}
