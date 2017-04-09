package com.gumtree.android.postad.views.attribute;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CustomDateAttributeView$$Lambda$1 implements OnClickListener {
    private final CustomDateAttributeView arg$1;

    private CustomDateAttributeView$$Lambda$1(CustomDateAttributeView customDateAttributeView) {
        this.arg$1 = customDateAttributeView;
    }

    public static OnClickListener lambdaFactory$(CustomDateAttributeView customDateAttributeView) {
        return new CustomDateAttributeView$$Lambda$1(customDateAttributeView);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$init$0(view);
    }
}
