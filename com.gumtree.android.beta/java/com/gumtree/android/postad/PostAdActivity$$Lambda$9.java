package com.gumtree.android.postad;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PostAdActivity$$Lambda$9 implements OnFocusChangeListener {
    private final PostAdActivity arg$1;

    private PostAdActivity$$Lambda$9(PostAdActivity postAdActivity) {
        this.arg$1 = postAdActivity;
    }

    public static OnFocusChangeListener lambdaFactory$(PostAdActivity postAdActivity) {
        return new PostAdActivity$$Lambda$9(postAdActivity);
    }

    @Hidden
    public void onFocusChange(View view, boolean z) {
        this.arg$1.lambda$initFocusListeners$8(view, z);
    }
}
