package com.gumtree.android.common.views.fields;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class EnumField$$Lambda$1 implements OnClickListener {
    private final EnumField arg$1;
    private final String arg$2;
    private final String arg$3;
    private final String arg$4;

    private EnumField$$Lambda$1(EnumField enumField, String str, String str2, String str3) {
        this.arg$1 = enumField;
        this.arg$2 = str;
        this.arg$3 = str2;
        this.arg$4 = str3;
    }

    public static OnClickListener lambdaFactory$(EnumField enumField, String str, String str2, String str3) {
        return new EnumField$$Lambda$1(enumField, str, str2, str3);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$updateTextLink$0(this.arg$2, this.arg$3, this.arg$4, view);
    }
}
