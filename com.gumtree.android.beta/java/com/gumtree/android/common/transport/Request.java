package com.gumtree.android.common.transport;

import java.io.IOException;

public interface Request {
    void addHeader(String str, String str2);

    void addPayload(String str, Payload payload);

    Response execute() throws IOException;

    void setCacheControl(String str);

    void setHeader(String str, String str2);

    void setPayload(Payload payload);
}
