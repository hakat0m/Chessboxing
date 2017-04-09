package com.gumtree.android.common.transport;

import java.io.IOException;

public interface Transport {
    Request prepare(RequestMethod requestMethod, String str) throws IOException;
}
