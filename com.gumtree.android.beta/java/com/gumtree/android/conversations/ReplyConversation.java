package com.gumtree.android.conversations;

import java.io.Serializable;

public class ReplyConversation implements Serializable {
    private Conversation conversation;
    private boolean isMine;
    private boolean isNewConversation;
    private String replyMessage;

    public ReplyConversation(Conversation conversation, String str, boolean z, boolean z2) {
        this.conversation = conversation;
        this.replyMessage = str;
        this.isNewConversation = z;
        this.isMine = z2;
    }

    public String getReplyMessage() {
        return this.replyMessage;
    }

    public void setReplyMessage(String str) {
        this.replyMessage = str;
    }

    public Conversation getConversation() {
        return this.conversation;
    }

    public boolean isNewConversation() {
        return this.isNewConversation;
    }

    public void setIsNewConversation(boolean z) {
        this.isNewConversation = z;
    }

    public boolean isMine() {
        return this.isMine;
    }
}
