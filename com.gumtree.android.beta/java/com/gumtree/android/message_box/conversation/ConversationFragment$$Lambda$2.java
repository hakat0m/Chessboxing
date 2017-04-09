package com.gumtree.android.message_box.conversation;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ConversationFragment$$Lambda$2 implements OnClickListener {
    private final ConversationFragment arg$1;
    private final ViewGroup arg$2;
    private final ViewGroup arg$3;

    private ConversationFragment$$Lambda$2(ConversationFragment conversationFragment, ViewGroup viewGroup, ViewGroup viewGroup2) {
        this.arg$1 = conversationFragment;
        this.arg$2 = viewGroup;
        this.arg$3 = viewGroup2;
    }

    public static OnClickListener lambdaFactory$(ConversationFragment conversationFragment, ViewGroup viewGroup, ViewGroup viewGroup2) {
        return new ConversationFragment$$Lambda$2(conversationFragment, viewGroup, viewGroup2);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$displayWelcomeMessage$1(this.arg$2, this.arg$3, view);
    }
}
