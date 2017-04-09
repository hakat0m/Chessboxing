package com.gumtree.android.postad.views;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PriceFrequencySummaryValidationView$$Lambda$2 implements OnFocusChangeListener {
    private final PriceFrequencySummaryValidationView arg$1;

    private PriceFrequencySummaryValidationView$$Lambda$2(PriceFrequencySummaryValidationView priceFrequencySummaryValidationView) {
        this.arg$1 = priceFrequencySummaryValidationView;
    }

    public static OnFocusChangeListener lambdaFactory$(PriceFrequencySummaryValidationView priceFrequencySummaryValidationView) {
        return new PriceFrequencySummaryValidationView$$Lambda$2(priceFrequencySummaryValidationView);
    }

    @Hidden
    public void onFocusChange(View view, boolean z) {
        this.arg$1.lambda$init$1(view, z);
    }
}
