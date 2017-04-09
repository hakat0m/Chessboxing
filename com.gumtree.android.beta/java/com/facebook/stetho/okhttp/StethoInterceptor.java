package com.facebook.stetho.okhttp;

import com.facebook.stetho.inspector.network.DefaultResponseHandler;
import com.facebook.stetho.inspector.network.NetworkEventReporter;
import com.facebook.stetho.inspector.network.NetworkEventReporter.InspectorRequest;
import com.facebook.stetho.inspector.network.NetworkEventReporter.InspectorResponse;
import com.facebook.stetho.inspector.network.NetworkEventReporterImpl;
import com.facebook.stetho.inspector.network.RequestBodyHelper;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Interceptor.Chain;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import okio.BufferedSink;
import okio.Okio;

@Deprecated
public class StethoInterceptor implements Interceptor {
    private final NetworkEventReporter mEventReporter = NetworkEventReporterImpl.get();
    private final AtomicInteger mNextRequestId = new AtomicInteger(0);

    class OkHttpInspectorRequest implements InspectorRequest {
        private final Request mRequest;
        private RequestBodyHelper mRequestBodyHelper;
        private final String mRequestId;

        public OkHttpInspectorRequest(String str, Request request, RequestBodyHelper requestBodyHelper) {
            this.mRequestId = str;
            this.mRequest = request;
            this.mRequestBodyHelper = requestBodyHelper;
        }

        public String id() {
            return this.mRequestId;
        }

        public String friendlyName() {
            return null;
        }

        @Nullable
        public Integer friendlyNameExtra() {
            return null;
        }

        public String url() {
            return this.mRequest.urlString();
        }

        public String method() {
            return this.mRequest.method();
        }

        @Nullable
        public byte[] body() throws IOException {
            RequestBody body = this.mRequest.body();
            if (body == null) {
                return null;
            }
            BufferedSink buffer = Okio.buffer(Okio.sink(this.mRequestBodyHelper.createBodySink(firstHeaderValue("Content-Encoding"))));
            try {
                body.writeTo(buffer);
                return this.mRequestBodyHelper.getDisplayBody();
            } finally {
                buffer.close();
            }
        }

        public int headerCount() {
            return this.mRequest.headers().size();
        }

        public String headerName(int i) {
            return this.mRequest.headers().name(i);
        }

        public String headerValue(int i) {
            return this.mRequest.headers().value(i);
        }

        @Nullable
        public String firstHeaderValue(String str) {
            return this.mRequest.header(str);
        }
    }

    class OkHttpInspectorResponse implements InspectorResponse {
        private final Connection mConnection;
        private final Request mRequest;
        private final String mRequestId;
        private final Response mResponse;

        public OkHttpInspectorResponse(String str, Request request, Response response, Connection connection) {
            this.mRequestId = str;
            this.mRequest = request;
            this.mResponse = response;
            this.mConnection = connection;
        }

        public String requestId() {
            return this.mRequestId;
        }

        public String url() {
            return this.mRequest.urlString();
        }

        public int statusCode() {
            return this.mResponse.code();
        }

        public String reasonPhrase() {
            return this.mResponse.message();
        }

        public boolean connectionReused() {
            return false;
        }

        public int connectionId() {
            return this.mConnection.hashCode();
        }

        public boolean fromDiskCache() {
            return this.mResponse.cacheResponse() != null;
        }

        public int headerCount() {
            return this.mResponse.headers().size();
        }

        public String headerName(int i) {
            return this.mResponse.headers().name(i);
        }

        public String headerValue(int i) {
            return this.mResponse.headers().value(i);
        }

        @Nullable
        public String firstHeaderValue(String str) {
            return this.mResponse.header(str);
        }
    }

    public Response intercept(Chain chain) throws IOException {
        RequestBodyHelper requestBodyHelper;
        String str = null;
        String valueOf = String.valueOf(this.mNextRequestId.getAndIncrement());
        Request request = chain.request();
        if (this.mEventReporter.isEnabled()) {
            requestBodyHelper = new RequestBodyHelper(this.mEventReporter, valueOf);
            this.mEventReporter.requestWillBeSent(new OkHttpInspectorRequest(valueOf, request, requestBodyHelper));
        } else {
            requestBodyHelper = null;
        }
        try {
            Response proceed = chain.proceed(request);
            if (this.mEventReporter.isEnabled()) {
                InputStream byteStream;
                MediaType mediaType;
                if (requestBodyHelper != null && requestBodyHelper.hasBody()) {
                    requestBodyHelper.reportDataSent();
                }
                this.mEventReporter.responseHeadersReceived(new OkHttpInspectorResponse(valueOf, request, proceed, chain.connection()));
                ResponseBody body = proceed.body();
                if (body != null) {
                    MediaType contentType = body.contentType();
                    byteStream = body.byteStream();
                    mediaType = contentType;
                } else {
                    byteStream = null;
                    mediaType = null;
                }
                NetworkEventReporter networkEventReporter = this.mEventReporter;
                if (mediaType != null) {
                    str = mediaType.toString();
                }
                InputStream interpretResponseStream = networkEventReporter.interpretResponseStream(valueOf, str, proceed.header("Content-Encoding"), byteStream, new DefaultResponseHandler(this.mEventReporter, valueOf));
                if (interpretResponseStream != null) {
                    return proceed.newBuilder().body(new ForwardingResponseBody(body, interpretResponseStream)).build();
                }
            }
            return proceed;
        } catch (IOException e) {
            if (this.mEventReporter.isEnabled()) {
                this.mEventReporter.httpExchangeFailed(valueOf, e.toString());
            }
            throw e;
        }
    }
}
