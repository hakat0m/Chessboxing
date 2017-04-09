package com.gumtree.android.location;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class LocationActivity$$Lambda$1 implements OnItemClickListener {
    private final LocationActivity arg$1;

    private LocationActivity$$Lambda$1(LocationActivity locationActivity) {
        this.arg$1 = locationActivity;
    }

    public static OnItemClickListener lambdaFactory$(LocationActivity locationActivity) {
        return new LocationActivity$$Lambda$1(locationActivity);
    }

    @Hidden
    public void onItemClick(AdapterView adapterView, View view, int i, long j) {
        this.arg$1.lambda$initializeSuggestionsList$0(adapterView, view, i, j);
    }
}
