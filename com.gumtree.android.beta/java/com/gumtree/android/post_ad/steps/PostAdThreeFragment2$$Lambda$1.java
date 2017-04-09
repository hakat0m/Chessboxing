package com.gumtree.android.post_ad.steps;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PostAdThreeFragment2$$Lambda$1 implements OnClickListener {
    private final PostAdThreeFragment2 arg$1;

    private PostAdThreeFragment2$$Lambda$1(PostAdThreeFragment2 postAdThreeFragment2) {
        this.arg$1 = postAdThreeFragment2;
    }

    public static OnClickListener lambdaFactory$(PostAdThreeFragment2 postAdThreeFragment2) {
        return new PostAdThreeFragment2$$Lambda$1(postAdThreeFragment2);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onViewCreated$0(view);
    }
}
