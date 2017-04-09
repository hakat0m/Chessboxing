package com.gumtree.android.vip.reply.ui;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class OptInLayout$$Lambda$1 implements OnCheckedChangeListener {
    private static final OptInLayout$$Lambda$1 instance = new OptInLayout$$Lambda$1();

    private OptInLayout$$Lambda$1() {
    }

    public static OnCheckedChangeListener lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        OptInLayout.lambda$build$0(compoundButton, z);
    }
}
