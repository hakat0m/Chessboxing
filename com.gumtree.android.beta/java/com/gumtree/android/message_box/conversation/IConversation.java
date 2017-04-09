package com.gumtree.android.message_box.conversation;

import com.gumtree.android.conversations.Conversation;

public interface IConversation {
    Conversation getConversation();

    boolean isNewConversation();
}
