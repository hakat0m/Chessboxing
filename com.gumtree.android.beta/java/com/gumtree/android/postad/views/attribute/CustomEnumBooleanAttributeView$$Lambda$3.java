package com.gumtree.android.postad.views.attribute;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CustomEnumBooleanAttributeView$$Lambda$3 implements OnCheckedChangeListener {
    private final CustomEnumBooleanAttributeView arg$1;

    private CustomEnumBooleanAttributeView$$Lambda$3(CustomEnumBooleanAttributeView customEnumBooleanAttributeView) {
        this.arg$1 = customEnumBooleanAttributeView;
    }

    public static OnCheckedChangeListener lambdaFactory$(CustomEnumBooleanAttributeView customEnumBooleanAttributeView) {
        return new CustomEnumBooleanAttributeView$$Lambda$3(customEnumBooleanAttributeView);
    }

    @Hidden
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        this.arg$1.lambda$init$2(radioGroup, i);
    }
}
