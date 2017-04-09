package com.gumtree.android.common.transport;

import java.io.IOException;

class AutoInflatingRequest extends WrappedRequest {
    public AutoInflatingRequest(Request request) {
        super(request);
        setHeader("Accept-Encoding", "gzip");
    }

    public Response execute() throws IOException {
        return new AutoInflatingResponse(super.execute());
    }
}
