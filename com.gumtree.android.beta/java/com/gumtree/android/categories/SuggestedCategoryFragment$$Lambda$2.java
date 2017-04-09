package com.gumtree.android.categories;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SuggestedCategoryFragment$$Lambda$2 implements OnClickListener {
    private final SuggestedCategoryFragment arg$1;

    private SuggestedCategoryFragment$$Lambda$2(SuggestedCategoryFragment suggestedCategoryFragment) {
        this.arg$1 = suggestedCategoryFragment;
    }

    public static OnClickListener lambdaFactory$(SuggestedCategoryFragment suggestedCategoryFragment) {
        return new SuggestedCategoryFragment$$Lambda$2(suggestedCategoryFragment);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$new$1(view);
    }
}
