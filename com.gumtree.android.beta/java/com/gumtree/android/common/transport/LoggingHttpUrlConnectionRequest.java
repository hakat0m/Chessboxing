package com.gumtree.android.common.transport;

import com.gumtree.android.common.utils.Log;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

class LoggingHttpUrlConnectionRequest extends HttpUrlConnectionRequest {
    private static final String SPACE = " ";

    public LoggingHttpUrlConnectionRequest(HttpURLConnection httpURLConnection) {
        super(httpURLConnection);
    }

    public Response execute() throws IOException {
        if (Log.verboseLoggingEnabled()) {
            logRequestInfo();
        }
        return super.execute();
    }

    private void logRequestInfo() throws IOException {
        Map requestProperties = this.connection.getRequestProperties();
        for (String str : requestProperties.keySet()) {
            Log.v("Request: " + str + SPACE + requestProperties.get(str));
        }
        if (this.singlePayload != null) {
            Log.v("Request: Single Payload:" + this.singlePayload.getContentType() + SPACE + this.singlePayload.getContentLength());
        }
        if (this.mimeParts != null) {
            for (Payload payload : this.mimeParts.getPayloads()) {
                Log.v("Request: Multi Part Payload:" + payload.getContentType() + SPACE + payload.getContentLength());
            }
        }
    }
}
