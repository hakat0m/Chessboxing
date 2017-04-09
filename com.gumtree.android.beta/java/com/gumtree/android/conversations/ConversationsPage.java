package com.gumtree.android.conversations;

import java.util.ArrayList;
import java.util.List;

public class ConversationsPage {
    private List<Conversation> conversations;
    private int numFound;
    private int totalConversationCount;
    private int totalUnreadConversationCount;

    public ConversationsPage() {
        this.conversations = new ArrayList();
    }

    public ConversationsPage(List<Conversation> list, int i, int i2, int i3) {
        this.conversations = list;
        this.totalConversationCount = i;
        this.totalUnreadConversationCount = i2;
        this.numFound = i3;
    }

    public List<Conversation> getConversations() {
        return this.conversations;
    }

    public void setConversations(List<Conversation> list) {
        this.conversations = list;
    }

    public int getTotalConversationCount() {
        return this.totalConversationCount;
    }

    public void setTotalConversationCount(int i) {
        this.totalConversationCount = i;
    }

    public int getTotalUnreadConversationCount() {
        return this.totalUnreadConversationCount;
    }

    public void setTotalUnreadConversationCount(int i) {
        this.totalUnreadConversationCount = i;
    }

    public int getNumFound() {
        return this.numFound;
    }

    public void setNumFound(int i) {
        this.numFound = i;
    }

    public void addConversation(Conversation conversation) {
        getConversations().add(conversation);
    }
}
