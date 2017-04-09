package com.gumtree.android.post_ad.preview;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PreviewImagePagerFragment$$Lambda$1 implements OnTouchListener {
    private final PreviewImagePagerFragment arg$1;

    private PreviewImagePagerFragment$$Lambda$1(PreviewImagePagerFragment previewImagePagerFragment) {
        this.arg$1 = previewImagePagerFragment;
    }

    public static OnTouchListener lambdaFactory$(PreviewImagePagerFragment previewImagePagerFragment) {
        return new PreviewImagePagerFragment$$Lambda$1(previewImagePagerFragment);
    }

    @Hidden
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return this.arg$1.lambda$new$0(view, motionEvent);
    }
}
