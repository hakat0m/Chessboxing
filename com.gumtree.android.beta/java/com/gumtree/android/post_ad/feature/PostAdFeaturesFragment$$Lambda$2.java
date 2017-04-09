package com.gumtree.android.post_ad.feature;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PostAdFeaturesFragment$$Lambda$2 implements OnClickListener {
    private final PostAdFeaturesFragment arg$1;

    private PostAdFeaturesFragment$$Lambda$2(PostAdFeaturesFragment postAdFeaturesFragment) {
        this.arg$1 = postAdFeaturesFragment;
    }

    public static OnClickListener lambdaFactory$(PostAdFeaturesFragment postAdFeaturesFragment) {
        return new PostAdFeaturesFragment$$Lambda$2(postAdFeaturesFragment);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$initButtons$1(view);
    }
}
