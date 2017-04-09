package com.gumtree.android.message_box;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.ebay.classifieds.capi.executor.ResponseException;
import com.ebay.classifieds.capi.executor.Result;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.http.CapiClientManager;
import com.gumtree.android.common.http.model.ICapiClient;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.conversations.ReplyConversation;
import com.gumtree.android.conversations.ReplyConversationResponse;
import com.gumtree.android.conversations.UserMessage;
import com.gumtree.android.conversations.parser.ReplyConversationResponseParser;
import com.gumtree.android.conversations.parser.ReplyConversationXmlBuilder;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnConversationReplyEvent;
import java.io.IOException;
import javax.inject.Inject;

public class PostConversationReplyIntentService extends IntentService {
    private static final String EXTRA_IS_MY_AD = "com.gumtree.android.message_box.isMyAd";
    private static final String EXTRA_REPLY_MESSAGE = "com.gumtree.android.message_box.reply_message";
    public static final String IS_NEW = "IS_NEW";
    private static final String TAG = PostConversationReplyIntentService.class.getSimpleName();
    @Inject
    BaseAccountManager accountManager;
    @Inject
    CapiClientManager capiClientManager;
    String direction;
    @Inject
    EventBus eventBus;

    public PostConversationReplyIntentService() {
        super("PostConversationMessageIntentService");
        GumtreeApplication.component().inject(this);
    }

    public static void reply(ReplyConversation replyConversation, boolean z) {
        Context context = GumtreeApplication.getContext();
        Intent intent = new Intent(context, PostConversationReplyIntentService.class);
        intent.putExtra(IS_NEW, false);
        intent.putExtra(EXTRA_REPLY_MESSAGE, replyConversation);
        intent.putExtra(EXTRA_IS_MY_AD, z);
        context.startService(intent);
        Track.messageSendAttempt(replyConversation.getConversation().getAdId(), z);
    }

    public static void newConversation(ReplyConversation replyConversation) {
        Context context = GumtreeApplication.getContext();
        Intent intent = new Intent(context, PostConversationReplyIntentService.class);
        intent.putExtra(IS_NEW, true);
        intent.putExtra(EXTRA_REPLY_MESSAGE, replyConversation);
        intent.putExtra(EXTRA_IS_MY_AD, false);
        context.startService(intent);
        Track.messageSendAttempt(replyConversation.getConversation().getAdId(), false);
    }

    protected void onHandleIntent(Intent intent) {
        replyToConversation((ReplyConversation) intent.getSerializableExtra(EXTRA_REPLY_MESSAGE), this.accountManager, intent.getBooleanExtra(EXTRA_IS_MY_AD, false));
    }

    private void replyToConversation(ReplyConversation replyConversation, BaseAccountManager baseAccountManager, boolean z) {
        UserMessage userMessage = getUserMessage(replyConversation);
        String addToLocalDb = addToLocalDb(0, userMessage);
        String adId = replyConversation.getConversation().getAdId();
        try {
            ICapiClient capiClient = this.capiClientManager.getCapiClient();
            String str = "replies/reply-to-ad-conversation";
            capiClient.authorize(baseAccountManager.getAuthentication());
            capiClient.withContent(getPostReplyConversationRequestXml(replyConversation));
            Result execute = capiClient.post("replies/reply-to-ad-conversation").execute(new ReplyConversationResponseParser());
            if (execute.hasError()) {
                addToLocalDb(4, addToLocalDb, userMessage);
                this.eventBus.post(new OnConversationReplyEvent(false, null, null));
                Track.messageSendFail(adId, z);
                return;
            }
            ReplyConversationResponse replyConversationResponse = (ReplyConversationResponse) execute.getData();
            if (replyConversationResponse.isSuccess()) {
                Track.messageSendSuccess(adId, z);
                this.eventBus.post(new OnConversationReplyEvent(true, null, replyConversation));
                addToLocalDb(1, addToLocalDb, userMessage);
                return;
            }
            this.eventBus.post(new OnConversationReplyEvent(false, new ResponseException(replyConversationResponse.getErrorMessage()), null));
            Track.messageSendFail(adId, z);
        } catch (Throwable e) {
            addToLocalDb(4, addToLocalDb, userMessage);
            Log.e(TAG, "Error when trying to reply to a conversation", e);
            this.eventBus.post(new OnConversationReplyEvent(false, e, null));
            Track.messageSendFail(adId, z);
        }
    }

    private String addToLocalDb(int i, UserMessage userMessage) {
        return new MessagesDao().persist(userMessage, i, getContentResolver()).getLastPathSegment();
    }

    private int addToLocalDb(int i, String str, UserMessage userMessage) {
        return new MessagesDao().persist(str, userMessage, i, getContentResolver());
    }

    private UserMessage getUserMessage(ReplyConversation replyConversation) {
        return UserMessage.reply(replyConversation);
    }

    private String getPostReplyConversationRequestXml(ReplyConversation replyConversation) throws IOException {
        return new ReplyConversationXmlBuilder(replyConversation).createPostReplyConversationRequestXml();
    }
}
