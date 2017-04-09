package com.gumtree.android.postad.contactdetails;

import com.gumtree.android.postad.contactdetails.ChangeContactEmailDialog.Listener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PostAdContactDetailsActivity$$Lambda$1 implements Listener {
    private final PostAdContactDetailsActivity arg$1;

    private PostAdContactDetailsActivity$$Lambda$1(PostAdContactDetailsActivity postAdContactDetailsActivity) {
        this.arg$1 = postAdContactDetailsActivity;
    }

    public static Listener lambdaFactory$(PostAdContactDetailsActivity postAdContactDetailsActivity) {
        return new PostAdContactDetailsActivity$$Lambda$1(postAdContactDetailsActivity);
    }

    @Hidden
    public void onGoClick() {
        this.arg$1.lambda$openContactEmailDialog$9e4a27f$1();
    }
}
