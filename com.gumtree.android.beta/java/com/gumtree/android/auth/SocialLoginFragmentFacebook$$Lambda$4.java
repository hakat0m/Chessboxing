package com.gumtree.android.auth;

import com.facebook.FacebookException;
import com.facebook.widget.LoginButton.OnErrorListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SocialLoginFragmentFacebook$$Lambda$4 implements OnErrorListener {
    private final SocialLoginFragmentFacebook arg$1;

    private SocialLoginFragmentFacebook$$Lambda$4(SocialLoginFragmentFacebook socialLoginFragmentFacebook) {
        this.arg$1 = socialLoginFragmentFacebook;
    }

    public static OnErrorListener lambdaFactory$(SocialLoginFragmentFacebook socialLoginFragmentFacebook) {
        return new SocialLoginFragmentFacebook$$Lambda$4(socialLoginFragmentFacebook);
    }

    @Hidden
    public void onError(FacebookException facebookException) {
        this.arg$1.lambda$onCreateView$3(facebookException);
    }
}
