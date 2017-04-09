package com.gumtree.android.postad.views.attribute;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CustomEnumAttributeView$$Lambda$1 implements OnClickListener {
    private final CustomEnumAttributeView arg$1;

    private CustomEnumAttributeView$$Lambda$1(CustomEnumAttributeView customEnumAttributeView) {
        this.arg$1 = customEnumAttributeView;
    }

    public static OnClickListener lambdaFactory$(CustomEnumAttributeView customEnumAttributeView) {
        return new CustomEnumAttributeView$$Lambda$1(customEnumAttributeView);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$init$0(view);
    }
}
