package com.gumtree.android.postad.views.attribute;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CustomEnumAttributeView$$Lambda$2 implements OnFocusChangeListener {
    private final CustomEnumAttributeView arg$1;

    private CustomEnumAttributeView$$Lambda$2(CustomEnumAttributeView customEnumAttributeView) {
        this.arg$1 = customEnumAttributeView;
    }

    public static OnFocusChangeListener lambdaFactory$(CustomEnumAttributeView customEnumAttributeView) {
        return new CustomEnumAttributeView$$Lambda$2(customEnumAttributeView);
    }

    @Hidden
    public void onFocusChange(View view, boolean z) {
        this.arg$1.lambda$init$1(view, z);
    }
}
