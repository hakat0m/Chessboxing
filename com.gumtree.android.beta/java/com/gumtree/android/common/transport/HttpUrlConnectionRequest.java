package com.gumtree.android.common.transport;

import com.gumtree.android.common.utils.io.StreamUtils;
import java.io.IOException;
import java.net.HttpURLConnection;

class HttpUrlConnectionRequest implements Request {
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String CONTENT_TYPE = "Content-Type";
    protected final HttpURLConnection connection;
    protected MimeParts mimeParts;
    protected Payload singlePayload;

    public HttpUrlConnectionRequest(HttpURLConnection httpURLConnection) {
        this.connection = httpURLConnection;
    }

    public void setHeader(String str, String str2) {
        this.connection.setRequestProperty(str, str2);
    }

    public void addHeader(String str, String str2) {
        this.connection.addRequestProperty(str, str2);
    }

    public void setPayload(Payload payload) {
        this.singlePayload = payload;
        this.mimeParts = null;
    }

    public void addPayload(String str, Payload payload) {
        if (this.mimeParts == null) {
            this.singlePayload = null;
            this.mimeParts = new MimeParts();
        }
        this.mimeParts.append(str, payload);
    }

    public Response execute() throws IOException {
        if (this.singlePayload != null) {
            uploadPayload();
        }
        if (this.mimeParts != null) {
            uploadPayloads();
        }
        return new LoggingHttpUrlConnectionResponse(this.connection);
    }

    private void uploadPayload() throws IOException {
        if (this.singlePayload != null) {
            this.connection.setDoOutput(true);
            this.connection.setRequestProperty(CONTENT_TYPE, this.singlePayload.getContentType());
            this.connection.setRequestProperty(CONTENT_LENGTH, String.valueOf(this.singlePayload.getContentLength()));
            StreamUtils.copy(this.singlePayload.getContent(), this.connection.getOutputStream());
        }
    }

    private void uploadPayloads() throws IOException {
        int combinedContentLength = this.mimeParts.getCombinedContentLength();
        this.connection.setDoOutput(true);
        this.connection.setUseCaches(false);
        this.connection.setRequestProperty(CONTENT_TYPE, "multipart/form-data; boundary=" + this.mimeParts.getSafeBoundary());
        this.connection.setRequestProperty(CONTENT_LENGTH, String.valueOf(combinedContentLength));
        this.connection.setFixedLengthStreamingMode(combinedContentLength);
        this.mimeParts.streamInto(this.connection.getOutputStream());
    }

    public void setCacheControl(String str) {
        this.connection.addRequestProperty("Cache-Control", str);
    }
}
