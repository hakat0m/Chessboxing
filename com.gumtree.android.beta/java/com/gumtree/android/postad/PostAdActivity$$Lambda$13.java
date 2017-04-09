package com.gumtree.android.postad;

import android.graphics.Bitmap;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class PostAdActivity$$Lambda$13 implements Action1 {
    private final PostAdActivity arg$1;

    private PostAdActivity$$Lambda$13(PostAdActivity postAdActivity) {
        this.arg$1 = postAdActivity;
    }

    public static Action1 lambdaFactory$(PostAdActivity postAdActivity) {
        return new PostAdActivity$$Lambda$13(postAdActivity);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$getBlurredBitmap$12((Bitmap) obj);
    }
}
