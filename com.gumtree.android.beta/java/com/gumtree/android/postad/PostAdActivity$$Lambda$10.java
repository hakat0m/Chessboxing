package com.gumtree.android.postad;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PostAdActivity$$Lambda$10 implements OnFocusChangeListener {
    private final PostAdActivity arg$1;

    private PostAdActivity$$Lambda$10(PostAdActivity postAdActivity) {
        this.arg$1 = postAdActivity;
    }

    public static OnFocusChangeListener lambdaFactory$(PostAdActivity postAdActivity) {
        return new PostAdActivity$$Lambda$10(postAdActivity);
    }

    @Hidden
    public void onFocusChange(View view, boolean z) {
        this.arg$1.lambda$initFocusListeners$9(view, z);
    }
}
