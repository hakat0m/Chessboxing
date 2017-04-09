package com.gumtree.android.vip_treebay;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class VIPImagePagerFragmentTreebay$$Lambda$1 implements OnTouchListener {
    private final VIPImagePagerFragmentTreebay arg$1;

    private VIPImagePagerFragmentTreebay$$Lambda$1(VIPImagePagerFragmentTreebay vIPImagePagerFragmentTreebay) {
        this.arg$1 = vIPImagePagerFragmentTreebay;
    }

    public static OnTouchListener lambdaFactory$(VIPImagePagerFragmentTreebay vIPImagePagerFragmentTreebay) {
        return new VIPImagePagerFragmentTreebay$$Lambda$1(vIPImagePagerFragmentTreebay);
    }

    @Hidden
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return this.arg$1.lambda$new$0(view, motionEvent);
    }
}
