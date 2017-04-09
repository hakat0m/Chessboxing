package com.gumtree.android.managead;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.View;
import com.gumtree.android.managead.ExpandableCursorListAdapter.ExpandCollapseHelper;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ExpandableCursorListAdapter$ExpandCollapseHelper$$Lambda$1 implements AnimatorUpdateListener {
    private final View arg$1;

    private ExpandableCursorListAdapter$ExpandCollapseHelper$$Lambda$1(View view) {
        this.arg$1 = view;
    }

    public static AnimatorUpdateListener lambdaFactory$(View view) {
        return new ExpandableCursorListAdapter$ExpandCollapseHelper$$Lambda$1(view);
    }

    @Hidden
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        ExpandCollapseHelper.lambda$createHeightAnimator$0(this.arg$1, valueAnimator);
    }
}
