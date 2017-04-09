package com.gumtree.android.vip;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class VIPImagePagerFragment$$Lambda$1 implements OnTouchListener {
    private final VIPImagePagerFragment arg$1;

    private VIPImagePagerFragment$$Lambda$1(VIPImagePagerFragment vIPImagePagerFragment) {
        this.arg$1 = vIPImagePagerFragment;
    }

    public static OnTouchListener lambdaFactory$(VIPImagePagerFragment vIPImagePagerFragment) {
        return new VIPImagePagerFragment$$Lambda$1(vIPImagePagerFragment);
    }

    @Hidden
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return this.arg$1.lambda$new$0(view, motionEvent);
    }
}
