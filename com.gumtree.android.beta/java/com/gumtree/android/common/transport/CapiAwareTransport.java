package com.gumtree.android.common.transport;

import java.io.IOException;

public class CapiAwareTransport implements Transport {
    private final Transport transport;

    public CapiAwareTransport(Transport transport) {
        this.transport = transport;
    }

    public Request prepare(RequestMethod requestMethod, String str) throws IOException {
        return new CapiAwareRequest(this.transport.prepare(requestMethod, str));
    }
}
