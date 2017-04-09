package com.gumtree.android.message_box.conversation;

import com.gumtree.android.conversations.Conversation;

public final class ConversationUtils {
    private static final String EMPTY_CONVERSATION_IDENTIFIER = "temp";

    public static String getTempConversationId(String str) {
        return str + EMPTY_CONVERSATION_IDENTIFIER;
    }

    public static boolean isTempConversationId(Conversation conversation) {
        return conversation.getUid().contains(EMPTY_CONVERSATION_IDENTIFIER);
    }
}
