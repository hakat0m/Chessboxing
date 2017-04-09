package com.gumtree.android.common.transport;

import java.io.IOException;

class CapiAwareRequest extends WrappedRequest {
    public CapiAwareRequest(Request request) {
        super(request);
    }

    public Response execute() throws IOException {
        return new CapiAwareResponse(super.execute());
    }
}
