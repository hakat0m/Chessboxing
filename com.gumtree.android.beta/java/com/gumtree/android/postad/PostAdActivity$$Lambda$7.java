package com.gumtree.android.postad;

import com.gumtree.android.postad.views.EditTextSummaryValidationView.OnFieldValueChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PostAdActivity$$Lambda$7 implements OnFieldValueChangeListener {
    private final PostAdActivity arg$1;

    private PostAdActivity$$Lambda$7(PostAdActivity postAdActivity) {
        this.arg$1 = postAdActivity;
    }

    public static OnFieldValueChangeListener lambdaFactory$(PostAdActivity postAdActivity) {
        return new PostAdActivity$$Lambda$7(postAdActivity);
    }

    @Hidden
    public void onFieldValueChange(String str) {
        this.arg$1.lambda$initTextListeners$6(str);
    }
}
