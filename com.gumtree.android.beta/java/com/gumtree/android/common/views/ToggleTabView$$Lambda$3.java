package com.gumtree.android.common.views;

import com.gumtree.android.common.views.ToggleImageButton.OnCheckedChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ToggleTabView$$Lambda$3 implements OnCheckedChangeListener {
    private final ToggleTabView arg$1;

    private ToggleTabView$$Lambda$3(ToggleTabView toggleTabView) {
        this.arg$1 = toggleTabView;
    }

    public static OnCheckedChangeListener lambdaFactory$(ToggleTabView toggleTabView) {
        return new ToggleTabView$$Lambda$3(toggleTabView);
    }

    @Hidden
    public void onCheckedChanged(ToggleImageButton toggleImageButton, boolean z) {
        this.arg$1.lambda$new$0(toggleImageButton, z);
    }
}
