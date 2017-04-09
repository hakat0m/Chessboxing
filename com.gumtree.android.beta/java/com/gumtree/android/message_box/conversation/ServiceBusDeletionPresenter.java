package com.gumtree.android.message_box.conversation;

import android.support.annotation.NonNull;
import com.gumtree.android.conversations.Conversation;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnConversationDeletedEvent;
import com.gumtree.android.message_box.DeleteConversationIntentService;
import com.gumtree.android.message_box.conversation.DeletionPresenter.View;
import com.squareup.otto.Subscribe;
import org.apache.commons.lang3.Validate;

public class ServiceBusDeletionPresenter implements DeletionPresenter {
    private final EventBus eventBus;
    private final Object eventBusReceiver = new Object() {
        @Subscribe
        public void onOnConversationDeletedEvent(OnConversationDeletedEvent onConversationDeletedEvent) {
            ServiceBusDeletionPresenter.this.eventBus.unregister(ServiceBusDeletionPresenter.this.eventBusReceiver);
            if (onConversationDeletedEvent.isSuccess()) {
                ServiceBusDeletionPresenter.this.view.onDeletionSucceeded();
            } else {
                ServiceBusDeletionPresenter.this.view.onDeletionFailed(onConversationDeletedEvent.getResponseException());
            }
        }
    };
    private View view = View.NOOP;

    public ServiceBusDeletionPresenter(@NonNull EventBus eventBus) {
        this.eventBus = (EventBus) Validate.notNull(eventBus);
    }

    public void attachView(View view) {
        this.view = view;
    }

    public void detachView(View view) {
        if (view != this.view) {
            throw new IllegalArgumentException("Detaching a view that wasn't attached");
        }
        this.view = View.NOOP;
    }

    public void delete(Conversation conversation) {
        this.eventBus.register(this.eventBusReceiver);
        DeleteConversationIntentService.deleteConversation(conversation);
    }
}
