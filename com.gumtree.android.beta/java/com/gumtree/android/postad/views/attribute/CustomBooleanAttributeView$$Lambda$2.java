package com.gumtree.android.postad.views.attribute;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CustomBooleanAttributeView$$Lambda$2 implements OnFocusChangeListener {
    private final CustomBooleanAttributeView arg$1;

    private CustomBooleanAttributeView$$Lambda$2(CustomBooleanAttributeView customBooleanAttributeView) {
        this.arg$1 = customBooleanAttributeView;
    }

    public static OnFocusChangeListener lambdaFactory$(CustomBooleanAttributeView customBooleanAttributeView) {
        return new CustomBooleanAttributeView$$Lambda$2(customBooleanAttributeView);
    }

    @Hidden
    public void onFocusChange(View view, boolean z) {
        this.arg$1.lambda$init$1(view, z);
    }
}
