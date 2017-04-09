package com.gumtree.android.common.transport;

import java.io.IOException;

public class AutoInflatingTransport implements Transport {
    private final Transport transport;

    public AutoInflatingTransport(Transport transport) {
        this.transport = transport;
    }

    public Request prepare(RequestMethod requestMethod, String str) throws IOException {
        return new AutoInflatingRequest(this.transport.prepare(requestMethod, str));
    }
}
