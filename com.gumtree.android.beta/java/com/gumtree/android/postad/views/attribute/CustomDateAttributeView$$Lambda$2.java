package com.gumtree.android.postad.views.attribute;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CustomDateAttributeView$$Lambda$2 implements OnFocusChangeListener {
    private final CustomDateAttributeView arg$1;

    private CustomDateAttributeView$$Lambda$2(CustomDateAttributeView customDateAttributeView) {
        this.arg$1 = customDateAttributeView;
    }

    public static OnFocusChangeListener lambdaFactory$(CustomDateAttributeView customDateAttributeView) {
        return new CustomDateAttributeView$$Lambda$2(customDateAttributeView);
    }

    @Hidden
    public void onFocusChange(View view, boolean z) {
        this.arg$1.lambda$init$1(view, z);
    }
}
