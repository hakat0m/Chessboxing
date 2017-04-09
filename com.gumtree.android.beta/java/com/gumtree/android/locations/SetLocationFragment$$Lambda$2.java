package com.gumtree.android.locations;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SetLocationFragment$$Lambda$2 implements OnItemClickListener {
    private final SetLocationFragment arg$1;

    private SetLocationFragment$$Lambda$2(SetLocationFragment setLocationFragment) {
        this.arg$1 = setLocationFragment;
    }

    public static OnItemClickListener lambdaFactory$(SetLocationFragment setLocationFragment) {
        return new SetLocationFragment$$Lambda$2(setLocationFragment);
    }

    @Hidden
    public void onItemClick(AdapterView adapterView, View view, int i, long j) {
        this.arg$1.lambda$onViewCreated$1(adapterView, view, i, j);
    }
}
