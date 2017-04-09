package com.gumtree.android.events;

import com.gumtree.android.conversations.ReplyConversation;

public class OnConversationReplyEvent {
    private final Exception mException;
    private final ReplyConversation mReplyConversation;
    private final boolean mSuccess;

    public OnConversationReplyEvent(boolean z, Exception exception, ReplyConversation replyConversation) {
        this.mSuccess = z;
        this.mException = exception;
        this.mReplyConversation = replyConversation;
    }

    public boolean isSuccess() {
        return this.mSuccess;
    }

    public Exception getException() {
        return this.mException;
    }

    public ReplyConversation getReplyConversation() {
        return this.mReplyConversation;
    }
}
