package com.gumtree.android.postad.views;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PriceFrequencySummaryValidationView$$Lambda$1 implements OnClickListener {
    private final PriceFrequencySummaryValidationView arg$1;

    private PriceFrequencySummaryValidationView$$Lambda$1(PriceFrequencySummaryValidationView priceFrequencySummaryValidationView) {
        this.arg$1 = priceFrequencySummaryValidationView;
    }

    public static OnClickListener lambdaFactory$(PriceFrequencySummaryValidationView priceFrequencySummaryValidationView) {
        return new PriceFrequencySummaryValidationView$$Lambda$1(priceFrequencySummaryValidationView);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$init$0(view);
    }
}
