package com.gumtree.android.common.fragments;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GridFragment$$Lambda$1 implements OnItemClickListener {
    private final GridFragment arg$1;

    private GridFragment$$Lambda$1(GridFragment gridFragment) {
        this.arg$1 = gridFragment;
    }

    public static OnItemClickListener lambdaFactory$(GridFragment gridFragment) {
        return new GridFragment$$Lambda$1(gridFragment);
    }

    @Hidden
    public void onItemClick(AdapterView adapterView, View view, int i, long j) {
        this.arg$1.lambda$new$0(adapterView, view, i, j);
    }
}
