package com.gumtree.android.message_box.conversation;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ConversationFragment$$Lambda$3 implements OnClickListener {
    private final ConversationFragment arg$1;

    private ConversationFragment$$Lambda$3(ConversationFragment conversationFragment) {
        this.arg$1 = conversationFragment;
    }

    public static OnClickListener lambdaFactory$(ConversationFragment conversationFragment) {
        return new ConversationFragment$$Lambda$3(conversationFragment);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$displayWelcomeMessage$2(view);
    }
}
