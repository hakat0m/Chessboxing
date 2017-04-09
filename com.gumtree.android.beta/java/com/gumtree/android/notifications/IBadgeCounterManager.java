package com.gumtree.android.notifications;

public interface IBadgeCounterManager {
    int getNumUnreadConversations();

    void resetNumUnreadConversations();
}
