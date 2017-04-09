package com.gumtree.android.message_box;

import com.ebay.classifieds.capi.executor.Request;
import com.ebay.classifieds.capi.executor.Result;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.http.CapiClientManager;
import com.gumtree.android.common.http.model.ICapiClient;
import com.gumtree.android.conversations.ConversationsPage;
import com.gumtree.android.conversations.parser.ConversationsPageParser;
import javax.inject.Inject;

public class ConversationsRequest implements Request<ConversationsPage> {
    private final String adId;
    @Inject
    CapiClientManager capiClientManager;
    @Inject
    BaseAccountManager customerAccountManager;
    private final int page;
    private final int size;

    public ConversationsRequest(int i, int i2, String str) {
        GumtreeApplication.component().inject(this);
        this.page = i;
        this.size = i2;
        this.adId = str;
    }

    public Result<ConversationsPage> execute() {
        ICapiClient capiClient = this.capiClientManager.getCapiClient();
        String format = String.format("users/%s/conversations.json", new Object[]{this.customerAccountManager.getUsername()});
        capiClient.authorize(this.customerAccountManager.getAuthentication());
        capiClient.withParam("page", String.valueOf(this.page));
        capiClient.withParam("size", String.valueOf(this.size));
        if (this.adId != null) {
            capiClient.withParam("ad-id", this.adId);
        }
        return capiClient.get(format).execute(new ConversationsPageParser());
    }
}
