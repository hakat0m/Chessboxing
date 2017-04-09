package com.gumtree.android.srp.treebay;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class AbstractTreebayAdItemView$$Lambda$1 implements OnClickListener {
    private final AbstractTreebayAdItemView arg$1;

    private AbstractTreebayAdItemView$$Lambda$1(AbstractTreebayAdItemView abstractTreebayAdItemView) {
        this.arg$1 = abstractTreebayAdItemView;
    }

    public static OnClickListener lambdaFactory$(AbstractTreebayAdItemView abstractTreebayAdItemView) {
        return new AbstractTreebayAdItemView$$Lambda$1(abstractTreebayAdItemView);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$init$0(view);
    }
}
