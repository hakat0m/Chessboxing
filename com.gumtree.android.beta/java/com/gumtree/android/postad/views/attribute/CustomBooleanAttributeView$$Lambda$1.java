package com.gumtree.android.postad.views.attribute;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CustomBooleanAttributeView$$Lambda$1 implements OnClickListener {
    private final CustomBooleanAttributeView arg$1;

    private CustomBooleanAttributeView$$Lambda$1(CustomBooleanAttributeView customBooleanAttributeView) {
        this.arg$1 = customBooleanAttributeView;
    }

    public static OnClickListener lambdaFactory$(CustomBooleanAttributeView customBooleanAttributeView) {
        return new CustomBooleanAttributeView$$Lambda$1(customBooleanAttributeView);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$init$0(view);
    }
}
