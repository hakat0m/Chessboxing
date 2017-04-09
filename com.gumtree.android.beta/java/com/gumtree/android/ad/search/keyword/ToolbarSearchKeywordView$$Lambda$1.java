package com.gumtree.android.ad.search.keyword;

import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ToolbarSearchKeywordView$$Lambda$1 implements OnEditorActionListener {
    private final ToolbarSearchKeywordView arg$1;

    private ToolbarSearchKeywordView$$Lambda$1(ToolbarSearchKeywordView toolbarSearchKeywordView) {
        this.arg$1 = toolbarSearchKeywordView;
    }

    public static OnEditorActionListener lambdaFactory$(ToolbarSearchKeywordView toolbarSearchKeywordView) {
        return new ToolbarSearchKeywordView$$Lambda$1(toolbarSearchKeywordView);
    }

    @Hidden
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return this.arg$1.lambda$init$0(textView, i, keyEvent);
    }
}
