package com.gumtree.android.vip.reply.ui;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CVLayout$$Lambda$1 implements OnCheckedChangeListener {
    private static final CVLayout$$Lambda$1 instance = new CVLayout$$Lambda$1();

    private CVLayout$$Lambda$1() {
    }

    public static OnCheckedChangeListener lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        CVLayout.lambda$build$0(compoundButton, z);
    }
}
