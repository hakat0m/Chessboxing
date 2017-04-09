package com.gumtree.android.vip.reply;

import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ReplyToVIPFragment$$Lambda$1 implements OnEditorActionListener {
    private final ReplyToVIPFragment arg$1;

    private ReplyToVIPFragment$$Lambda$1(ReplyToVIPFragment replyToVIPFragment) {
        this.arg$1 = replyToVIPFragment;
    }

    public static OnEditorActionListener lambdaFactory$(ReplyToVIPFragment replyToVIPFragment) {
        return new ReplyToVIPFragment$$Lambda$1(replyToVIPFragment);
    }

    @Hidden
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        return this.arg$1.lambda$new$0(textView, i, keyEvent);
    }
}
