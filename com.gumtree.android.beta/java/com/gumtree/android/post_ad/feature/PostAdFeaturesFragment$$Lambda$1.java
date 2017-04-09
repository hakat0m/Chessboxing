package com.gumtree.android.post_ad.feature;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PostAdFeaturesFragment$$Lambda$1 implements OnCheckedChangeListener {
    private final PostAdFeaturesFragment arg$1;

    private PostAdFeaturesFragment$$Lambda$1(PostAdFeaturesFragment postAdFeaturesFragment) {
        this.arg$1 = postAdFeaturesFragment;
    }

    public static OnCheckedChangeListener lambdaFactory$(PostAdFeaturesFragment postAdFeaturesFragment) {
        return new PostAdFeaturesFragment$$Lambda$1(postAdFeaturesFragment);
    }

    @Hidden
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.arg$1.lambda$new$0(compoundButton, z);
    }
}
