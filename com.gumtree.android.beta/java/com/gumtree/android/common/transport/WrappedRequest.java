package com.gumtree.android.common.transport;

import java.io.IOException;

class WrappedRequest implements Request {
    private final Request request;

    public WrappedRequest(Request request) {
        this.request = request;
    }

    public void setHeader(String str, String str2) {
        this.request.setHeader(str, str2);
    }

    public void addHeader(String str, String str2) {
        this.request.addHeader(str, str2);
    }

    public void setPayload(Payload payload) {
        this.request.setPayload(payload);
    }

    public void addPayload(String str, Payload payload) {
        this.request.addPayload(str, payload);
    }

    public Response execute() throws IOException {
        return this.request.execute();
    }

    public void setCacheControl(String str) {
        this.request.setCacheControl(str);
    }
}
