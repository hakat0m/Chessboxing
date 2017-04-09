package com.gumtree.android.postad.views.attribute;

import com.gumtree.android.postad.views.attribute.AttributeView.OnFieldValueChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class VRMMileageWrapperView$$Lambda$2 implements OnFieldValueChangeListener {
    private final VRMMileageWrapperView arg$1;
    private final OnFieldValueChangeListener arg$2;

    private VRMMileageWrapperView$$Lambda$2(VRMMileageWrapperView vRMMileageWrapperView, OnFieldValueChangeListener onFieldValueChangeListener) {
        this.arg$1 = vRMMileageWrapperView;
        this.arg$2 = onFieldValueChangeListener;
    }

    public static OnFieldValueChangeListener lambdaFactory$(VRMMileageWrapperView vRMMileageWrapperView, OnFieldValueChangeListener onFieldValueChangeListener) {
        return new VRMMileageWrapperView$$Lambda$2(vRMMileageWrapperView, onFieldValueChangeListener);
    }

    @Hidden
    public void onFieldValueChange(String str, String str2, String str3) {
        this.arg$1.lambda$setOnFieldValueChangeListener$1(this.arg$2, str, str2, str3);
    }
}
