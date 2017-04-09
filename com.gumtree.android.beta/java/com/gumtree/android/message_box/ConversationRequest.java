package com.gumtree.android.message_box;

import com.ebay.classifieds.capi.executor.Request;
import com.ebay.classifieds.capi.executor.Result;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.http.CapiClientManager;
import com.gumtree.android.common.http.model.ICapiClient;
import com.gumtree.android.conversations.Conversation;
import com.gumtree.android.conversations.parser.ConversationParser;
import javax.inject.Inject;

public class ConversationRequest implements Request<Conversation> {
    @Inject
    CapiClientManager capiClientManager;
    private final String conversationId;
    @Inject
    BaseAccountManager customerAccountManager;
    private int tail;

    public ConversationRequest(String str, int i) {
        GumtreeApplication.component().inject(this);
        this.conversationId = str;
        this.tail = i;
    }

    public Result<Conversation> execute() {
        ICapiClient capiClient = this.capiClientManager.getCapiClient();
        String format = String.format("users/%s/conversations/%s.json", new Object[]{this.customerAccountManager.getUsername(), this.conversationId});
        capiClient.authorize(this.customerAccountManager.getAuthentication());
        capiClient.withParam("tail", String.valueOf(this.tail));
        return capiClient.get(format).execute(new ConversationParser(this.conversationId));
    }
}
