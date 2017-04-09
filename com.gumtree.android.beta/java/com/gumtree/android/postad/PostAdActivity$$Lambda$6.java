package com.gumtree.android.postad;

import com.gumtree.android.postad.views.PriceFrequencySummaryValidationView.OnSpinnerItemSelectedListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PostAdActivity$$Lambda$6 implements OnSpinnerItemSelectedListener {
    private final PostAdActivity arg$1;

    private PostAdActivity$$Lambda$6(PostAdActivity postAdActivity) {
        this.arg$1 = postAdActivity;
    }

    public static OnSpinnerItemSelectedListener lambdaFactory$(PostAdActivity postAdActivity) {
        return new PostAdActivity$$Lambda$6(postAdActivity);
    }

    @Hidden
    public void onSpinnerValueSelected(String str) {
        this.arg$1.lambda$initTextListeners$5(str);
    }
}
