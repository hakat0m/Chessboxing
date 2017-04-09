package com.gumtree.android.events;

import android.support.annotation.Nullable;
import com.gumtree.android.conversations.Conversation;

public class OnConversationResultEvent {
    private Conversation mConversation;
    private Exception mException;
    private boolean mSuccess;

    public OnConversationResultEvent(boolean z, Exception exception, Conversation conversation) {
        this.mSuccess = z;
        this.mException = exception;
        this.mConversation = conversation;
    }

    public boolean isSuccess() {
        return this.mSuccess;
    }

    @Nullable
    public Exception getException() {
        return this.mException;
    }

    @Nullable
    public Conversation getConversation() {
        return this.mConversation;
    }
}
