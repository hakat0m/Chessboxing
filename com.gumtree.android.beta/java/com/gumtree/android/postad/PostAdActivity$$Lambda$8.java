package com.gumtree.android.postad;

import android.text.InputFilter;
import android.text.Spanned;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.regex.Pattern;

final /* synthetic */ class PostAdActivity$$Lambda$8 implements InputFilter {
    private final Pattern arg$1;

    private PostAdActivity$$Lambda$8(Pattern pattern) {
        this.arg$1 = pattern;
    }

    public static InputFilter lambdaFactory$(Pattern pattern) {
        return new PostAdActivity$$Lambda$8(pattern);
    }

    @Hidden
    public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
        return PostAdActivity.lambda$initPriceInputFilter$7(this.arg$1, charSequence, i, i2, spanned, i3, i4);
    }
}
