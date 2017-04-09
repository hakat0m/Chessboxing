package com.gumtree.android.postad.customdetails;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CustomDetailsActivity$$Lambda$1 implements OnFocusChangeListener {
    private final CustomDetailsActivity arg$1;

    private CustomDetailsActivity$$Lambda$1(CustomDetailsActivity customDetailsActivity) {
        this.arg$1 = customDetailsActivity;
    }

    public static OnFocusChangeListener lambdaFactory$(CustomDetailsActivity customDetailsActivity) {
        return new CustomDetailsActivity$$Lambda$1(customDetailsActivity);
    }

    @Hidden
    public void onFocusChange(View view, boolean z) {
        this.arg$1.lambda$onCreate$0(view, z);
    }
}
