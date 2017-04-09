package com.gumtree.android.postad.views.attribute;

import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CustomBooleanAttributeView$$Lambda$3 implements OnCheckedChangeListener {
    private final CustomBooleanAttributeView arg$1;

    private CustomBooleanAttributeView$$Lambda$3(CustomBooleanAttributeView customBooleanAttributeView) {
        this.arg$1 = customBooleanAttributeView;
    }

    public static OnCheckedChangeListener lambdaFactory$(CustomBooleanAttributeView customBooleanAttributeView) {
        return new CustomBooleanAttributeView$$Lambda$3(customBooleanAttributeView);
    }

    @Hidden
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        this.arg$1.lambda$init$2(radioGroup, i);
    }
}
