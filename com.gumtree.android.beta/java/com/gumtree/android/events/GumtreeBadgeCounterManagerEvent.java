package com.gumtree.android.events;

public class GumtreeBadgeCounterManagerEvent {
    private int numUnreadConversations;

    public GumtreeBadgeCounterManagerEvent(int i) {
        this.numUnreadConversations = i;
    }

    public int getNumUnreadConversations() {
        return this.numUnreadConversations;
    }
}
