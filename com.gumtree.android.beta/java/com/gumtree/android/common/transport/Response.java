package com.gumtree.android.common.transport;

import java.io.IOException;
import java.io.InputStream;

public interface Response {
    void close();

    InputStream getContent() throws IOException;

    InputStream getError() throws IOException;

    String getHeaderField(String str) throws IOException;

    int getStatusCode() throws IOException;
}
