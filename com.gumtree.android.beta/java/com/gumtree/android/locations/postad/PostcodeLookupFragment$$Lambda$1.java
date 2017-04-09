package com.gumtree.android.locations.postad;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PostcodeLookupFragment$$Lambda$1 implements OnClickListener {
    private final PostcodeLookupFragment arg$1;
    private final EditText arg$2;

    private PostcodeLookupFragment$$Lambda$1(PostcodeLookupFragment postcodeLookupFragment, EditText editText) {
        this.arg$1 = postcodeLookupFragment;
        this.arg$2 = editText;
    }

    public static OnClickListener lambdaFactory$(PostcodeLookupFragment postcodeLookupFragment, EditText editText) {
        return new PostcodeLookupFragment$$Lambda$1(postcodeLookupFragment, editText);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$initPostcodeLookup$0(this.arg$2, view);
    }
}
