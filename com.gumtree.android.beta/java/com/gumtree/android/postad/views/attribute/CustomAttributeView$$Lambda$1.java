package com.gumtree.android.postad.views.attribute;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CustomAttributeView$$Lambda$1 implements OnFocusChangeListener {
    private final CustomAttributeView arg$1;

    private CustomAttributeView$$Lambda$1(CustomAttributeView customAttributeView) {
        this.arg$1 = customAttributeView;
    }

    public static OnFocusChangeListener lambdaFactory$(CustomAttributeView customAttributeView) {
        return new CustomAttributeView$$Lambda$1(customAttributeView);
    }

    @Hidden
    public void onFocusChange(View view, boolean z) {
        this.arg$1.lambda$init$0(view, z);
    }
}
