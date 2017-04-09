package com.gumtree.android.common.http;

import android.content.Intent;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.transport.Response;
import java.io.IOException;

public abstract class JsonRestExecutor {
    private final HttpIntentRequest request;

    public JsonRestExecutor(Intent intent) {
        this.request = prepareRequest(intent);
    }

    public HttpIntentRequest prepareRequest(Intent intent) {
        return new HttpIntentRequest(new HttpIntentBuilder().createHttpIntentFromIntent(intent).build());
    }

    public Response execute() throws IOException {
        return this.request.makeRequest(GumtreeApplication.getHttpTransport());
    }
}
