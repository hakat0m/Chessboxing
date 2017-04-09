package com.gumtree.android.srp;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SearchRefineFragment$$Lambda$1 implements OnClickListener {
    private final SearchRefineFragment arg$1;

    private SearchRefineFragment$$Lambda$1(SearchRefineFragment searchRefineFragment) {
        this.arg$1 = searchRefineFragment;
    }

    public static OnClickListener lambdaFactory$(SearchRefineFragment searchRefineFragment) {
        return new SearchRefineFragment$$Lambda$1(searchRefineFragment);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$initializeButton$0(view);
    }
}
