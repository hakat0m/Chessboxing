package com.facebook.stetho.inspector.network;

import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nullable;

public interface NetworkEventReporter {

    public interface InspectorHeaders {
        @Nullable
        String firstHeaderValue(String str);

        int headerCount();

        String headerName(int i);

        String headerValue(int i);
    }

    public interface InspectorRequest extends InspectorHeaders {
        @Nullable
        byte[] body() throws IOException;

        String friendlyName();

        @Nullable
        Integer friendlyNameExtra();

        String id();

        String method();

        String url();
    }

    void dataReceived(String str, int i, int i2);

    void dataSent(String str, int i, int i2);

    void httpExchangeFailed(String str, String str2);

    @Nullable
    InputStream interpretResponseStream(String str, @Nullable String str2, @Nullable String str3, @Nullable InputStream inputStream, ResponseHandler responseHandler);

    boolean isEnabled();

    void requestWillBeSent(InspectorRequest inspectorRequest);

    void responseHeadersReceived(InspectorResponse inspectorResponse);

    void responseReadFailed(String str, String str2);

    void responseReadFinished(String str);
}
