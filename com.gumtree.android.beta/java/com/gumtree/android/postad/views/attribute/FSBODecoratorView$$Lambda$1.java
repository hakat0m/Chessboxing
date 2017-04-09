package com.gumtree.android.postad.views.attribute;

import com.gumtree.android.postad.views.attribute.AttributeView.OnFieldValueChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class FSBODecoratorView$$Lambda$1 implements OnFieldValueChangeListener {
    private final FSBODecoratorView arg$1;
    private final OnFieldValueChangeListener arg$2;

    private FSBODecoratorView$$Lambda$1(FSBODecoratorView fSBODecoratorView, OnFieldValueChangeListener onFieldValueChangeListener) {
        this.arg$1 = fSBODecoratorView;
        this.arg$2 = onFieldValueChangeListener;
    }

    public static OnFieldValueChangeListener lambdaFactory$(FSBODecoratorView fSBODecoratorView, OnFieldValueChangeListener onFieldValueChangeListener) {
        return new FSBODecoratorView$$Lambda$1(fSBODecoratorView, onFieldValueChangeListener);
    }

    @Hidden
    public void onFieldValueChange(String str, String str2, String str3) {
        this.arg$1.lambda$setOnFieldValueChangeListener$0(this.arg$2, str, str2, str3);
    }
}
