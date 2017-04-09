package com.gumtree.android.events;

import com.gumtree.android.conversations.ConversationsPage;

public class OnConversationsListResultEvent {
    private ConversationsPage mConversationsPage;
    private Exception mException;
    private boolean mSuccess;

    public OnConversationsListResultEvent(boolean z, Exception exception, ConversationsPage conversationsPage) {
        this.mSuccess = z;
        this.mException = exception;
        this.mConversationsPage = conversationsPage;
    }

    public boolean isSuccess() {
        return this.mSuccess;
    }

    public Exception getException() {
        return this.mException;
    }

    public ConversationsPage getConversationsPage() {
        return this.mConversationsPage;
    }
}
