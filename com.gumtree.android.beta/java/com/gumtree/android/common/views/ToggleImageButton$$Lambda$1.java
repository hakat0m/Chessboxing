package com.gumtree.android.common.views;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ToggleImageButton$$Lambda$1 implements OnClickListener {
    private final ToggleImageButton arg$1;

    private ToggleImageButton$$Lambda$1(ToggleImageButton toggleImageButton) {
        this.arg$1 = toggleImageButton;
    }

    public static OnClickListener lambdaFactory$(ToggleImageButton toggleImageButton) {
        return new ToggleImageButton$$Lambda$1(toggleImageButton);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$new$0(view);
    }
}
