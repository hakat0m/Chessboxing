package com.gumtree.android.srp;

import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.gumtree.android.srp.BaseDynAttributeFragment.BaseMetadataAdapter;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class BaseDynAttributeFragment$BaseMetadataAdapter$$Lambda$1 implements OnEditorActionListener {
    private final BaseMetadataAdapter arg$1;

    private BaseDynAttributeFragment$BaseMetadataAdapter$$Lambda$1(BaseMetadataAdapter baseMetadataAdapter) {
        this.arg$1 = baseMetadataAdapter;
    }

    public static OnEditorActionListener lambdaFactory$(BaseMetadataAdapter baseMetadataAdapter) {
        return new BaseDynAttributeFragment$BaseMetadataAdapter$$Lambda$1(baseMetadataAdapter);
    }

    @Hidden
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return this.arg$1.lambda$onViewTypeString$0(textView, i, keyEvent);
    }
}
