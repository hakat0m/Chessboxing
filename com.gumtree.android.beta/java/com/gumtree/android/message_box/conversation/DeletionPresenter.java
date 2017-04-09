package com.gumtree.android.message_box.conversation;

import com.gumtree.android.conversations.Conversation;

public interface DeletionPresenter {

    public interface View {
        public static final View NOOP = new 1();

        void onDeletionFailed(Throwable th);

        void onDeletionSucceeded();
    }

    void attachView(View view);

    void delete(Conversation conversation);

    void detachView(View view);
}
