package com.gumtree.android.common.views.recycler;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class EnhancedRecyclerView$$Lambda$1 implements Runnable {
    private final EnhancedRecyclerView arg$1;

    private EnhancedRecyclerView$$Lambda$1(EnhancedRecyclerView enhancedRecyclerView) {
        this.arg$1 = enhancedRecyclerView;
    }

    public static Runnable lambdaFactory$(EnhancedRecyclerView enhancedRecyclerView) {
        return new EnhancedRecyclerView$$Lambda$1(enhancedRecyclerView);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$checkIfEmpty$0();
    }
}
