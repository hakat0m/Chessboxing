package com.gumtree.android.postad.views.attribute;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CustomVRMNumberAttributeView$$Lambda$1 implements OnFocusChangeListener {
    private final CustomVRMNumberAttributeView arg$1;

    private CustomVRMNumberAttributeView$$Lambda$1(CustomVRMNumberAttributeView customVRMNumberAttributeView) {
        this.arg$1 = customVRMNumberAttributeView;
    }

    public static OnFocusChangeListener lambdaFactory$(CustomVRMNumberAttributeView customVRMNumberAttributeView) {
        return new CustomVRMNumberAttributeView$$Lambda$1(customVRMNumberAttributeView);
    }

    @Hidden
    public void onFocusChange(View view, boolean z) {
        this.arg$1.lambda$init$0(view, z);
    }
}
