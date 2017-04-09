package com.gumtree.android.postad.views;

import com.gumtree.android.postad.views.PriceFrequencySummaryValidationView.OnSpinnerItemSelectedListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PriceFrequencySummaryValidationView$$Lambda$3 implements OnSpinnerItemSelectedListener {
    private static final PriceFrequencySummaryValidationView$$Lambda$3 instance = new PriceFrequencySummaryValidationView$$Lambda$3();

    private PriceFrequencySummaryValidationView$$Lambda$3() {
    }

    public static OnSpinnerItemSelectedListener lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void onSpinnerValueSelected(String str) {
        PriceFrequencySummaryValidationView.lambda$setOnSpinnerItemSelectedListener$2(str);
    }
}
