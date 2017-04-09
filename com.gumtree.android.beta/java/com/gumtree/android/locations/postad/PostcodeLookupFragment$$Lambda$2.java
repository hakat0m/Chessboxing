package com.gumtree.android.locations.postad;

import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PostcodeLookupFragment$$Lambda$2 implements OnEditorActionListener {
    private final PostcodeLookupFragment arg$1;

    private PostcodeLookupFragment$$Lambda$2(PostcodeLookupFragment postcodeLookupFragment) {
        this.arg$1 = postcodeLookupFragment;
    }

    public static OnEditorActionListener lambdaFactory$(PostcodeLookupFragment postcodeLookupFragment) {
        return new PostcodeLookupFragment$$Lambda$2(postcodeLookupFragment);
    }

    @Hidden
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return this.arg$1.lambda$initPostcodeLookup$1(textView, i, keyEvent);
    }
}
