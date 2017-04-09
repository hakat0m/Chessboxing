package com.gumtree.android.postad.views.attribute;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CustomEnumBooleanAttributeView$$Lambda$1 implements OnClickListener {
    private final CustomEnumBooleanAttributeView arg$1;

    private CustomEnumBooleanAttributeView$$Lambda$1(CustomEnumBooleanAttributeView customEnumBooleanAttributeView) {
        this.arg$1 = customEnumBooleanAttributeView;
    }

    public static OnClickListener lambdaFactory$(CustomEnumBooleanAttributeView customEnumBooleanAttributeView) {
        return new CustomEnumBooleanAttributeView$$Lambda$1(customEnumBooleanAttributeView);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$init$0(view);
    }
}
