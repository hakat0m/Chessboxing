package com.gumtree.android.postad.views.attribute;

import com.gumtree.android.postad.views.attribute.AttributeView.OnFieldValueChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class VRMMileageWrapperView$$Lambda$1 implements OnFieldValueChangeListener {
    private final VRMMileageWrapperView arg$1;

    private VRMMileageWrapperView$$Lambda$1(VRMMileageWrapperView vRMMileageWrapperView) {
        this.arg$1 = vRMMileageWrapperView;
    }

    public static OnFieldValueChangeListener lambdaFactory$(VRMMileageWrapperView vRMMileageWrapperView) {
        return new VRMMileageWrapperView$$Lambda$1(vRMMileageWrapperView);
    }

    @Hidden
    public void onFieldValueChange(String str, String str2, String str3) {
        this.arg$1.lambda$init$0(str, str2, str3);
    }
}
