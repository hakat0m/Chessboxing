package com.gumtree.android.postad.views.attribute;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CustomEnumBooleanAttributeView$$Lambda$2 implements OnFocusChangeListener {
    private final CustomEnumBooleanAttributeView arg$1;

    private CustomEnumBooleanAttributeView$$Lambda$2(CustomEnumBooleanAttributeView customEnumBooleanAttributeView) {
        this.arg$1 = customEnumBooleanAttributeView;
    }

    public static OnFocusChangeListener lambdaFactory$(CustomEnumBooleanAttributeView customEnumBooleanAttributeView) {
        return new CustomEnumBooleanAttributeView$$Lambda$2(customEnumBooleanAttributeView);
    }

    @Hidden
    public void onFocusChange(View view, boolean z) {
        this.arg$1.lambda$init$1(view, z);
    }
}
