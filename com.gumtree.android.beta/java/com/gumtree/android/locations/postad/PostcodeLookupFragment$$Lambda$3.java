package com.gumtree.android.locations.postad;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PostcodeLookupFragment$$Lambda$3 implements OnCheckedChangeListener {
    private final PostcodeLookupFragment arg$1;

    private PostcodeLookupFragment$$Lambda$3(PostcodeLookupFragment postcodeLookupFragment) {
        this.arg$1 = postcodeLookupFragment;
    }

    public static OnCheckedChangeListener lambdaFactory$(PostcodeLookupFragment postcodeLookupFragment) {
        return new PostcodeLookupFragment$$Lambda$3(postcodeLookupFragment);
    }

    @Hidden
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.arg$1.lambda$initVisibleOnMap$2(compoundButton, z);
    }
}
