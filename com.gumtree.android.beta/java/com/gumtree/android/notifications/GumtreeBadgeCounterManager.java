package com.gumtree.android.notifications;

import com.gumtree.android.conversations.ConversationsPage;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.GumtreeBadgeCounterManagerEvent;
import com.gumtree.android.events.OnConversationsListResultEvent;
import com.gumtree.android.message_box.ConversationsIntentService;
import com.squareup.otto.Subscribe;

public class GumtreeBadgeCounterManager implements IBadgeCounterManager {
    private EventBus eventBus;
    private int numUnreadConversations;

    public GumtreeBadgeCounterManager(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    public static void requestNumUnreadConversations() {
        ConversationsIntentService.start(0, 0, null);
    }

    public int getNumUnreadConversations() {
        return this.numUnreadConversations;
    }

    public void resetNumUnreadConversations() {
        this.numUnreadConversations = 0;
    }

    @Subscribe
    public void onConversationsListResultEvent(OnConversationsListResultEvent onConversationsListResultEvent) {
        ConversationsPage conversationsPage = onConversationsListResultEvent.getConversationsPage();
        if (conversationsPage != null) {
            int totalUnreadConversationCount = conversationsPage.getTotalUnreadConversationCount();
            this.numUnreadConversations = totalUnreadConversationCount;
            this.eventBus.post(new GumtreeBadgeCounterManagerEvent(totalUnreadConversationCount));
        }
    }
}
