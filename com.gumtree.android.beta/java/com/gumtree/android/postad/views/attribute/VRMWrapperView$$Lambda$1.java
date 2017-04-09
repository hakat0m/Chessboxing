package com.gumtree.android.postad.views.attribute;

import com.gumtree.android.postad.views.attribute.AttributeView.OnFieldValueChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class VRMWrapperView$$Lambda$1 implements OnFieldValueChangeListener {
    private final VRMWrapperView arg$1;
    private final OnFieldValueChangeListener arg$2;

    private VRMWrapperView$$Lambda$1(VRMWrapperView vRMWrapperView, OnFieldValueChangeListener onFieldValueChangeListener) {
        this.arg$1 = vRMWrapperView;
        this.arg$2 = onFieldValueChangeListener;
    }

    public static OnFieldValueChangeListener lambdaFactory$(VRMWrapperView vRMWrapperView, OnFieldValueChangeListener onFieldValueChangeListener) {
        return new VRMWrapperView$$Lambda$1(vRMWrapperView, onFieldValueChangeListener);
    }

    @Hidden
    public void onFieldValueChange(String str, String str2, String str3) {
        this.arg$1.lambda$setOnFieldValueChangeListener$0(this.arg$2, str, str2, str3);
    }
}
