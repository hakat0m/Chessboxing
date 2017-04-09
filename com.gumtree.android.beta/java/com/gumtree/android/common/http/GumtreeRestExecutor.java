package com.gumtree.android.common.http;

import android.content.Intent;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.UrlMatcher;
import com.gumtree.android.auth.ApplicationDataManager;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.gson.JsonParser;
import com.gumtree.android.common.gson.JsonParserFactory;
import com.gumtree.android.common.gson.RealJsonParserFactory;
import com.gumtree.android.common.transport.CapiIoException;
import com.gumtree.android.common.transport.Response;
import com.gumtree.android.common.transport.Transport;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.handler.DataStorage;
import com.gumtree.android.handler.DataStorage.Batch;
import com.gumtree.android.handler.DataStorageBatchJsonHandler;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Inject;

public class GumtreeRestExecutor implements RestExecutor {
    @Inject
    ApplicationDataManager applicationDataManager;
    private final DataStorage dataStorage;
    private JsonParserFactory jsonParserFactory;
    private HttpIntentRequest request;
    private final Transport transport;

    public GumtreeRestExecutor() {
        this(new RealJsonParserFactory(GumtreeApplication.getAPI().getBaseUrl()));
    }

    public GumtreeRestExecutor(JsonParserFactory jsonParserFactory) {
        ComponentsManager.get().getAppComponent().inject(this);
        this.transport = GumtreeApplication.getHttpTransport();
        this.dataStorage = GumtreeApplication.getDataStorage();
        this.jsonParserFactory = jsonParserFactory;
    }

    public HttpIntentRequest prepareRequest(Intent intent) {
        this.request = new HttpIntentRequest(new HttpIntentBuilder().createHttpIntentFromIntent(intent).build());
        return this.request;
    }

    public int execute() {
        Exception exception;
        Throwable th;
        Response response = null;
        int i = UrlMatcher.URI_CODE_SAVED_SEARCHES;
        int statusCode;
        try {
            String url = this.request.getUrl();
            long currentTimeMillis = System.currentTimeMillis();
            response = this.request.makeRequest(this.transport);
            statusCode = response != null ? response.getStatusCode() : UrlMatcher.URI_CODE_SAVED_SEARCHES;
            try {
                if (this.request.skipResponseParsing()) {
                    i = response.getStatusCode();
                    if (response != null) {
                        response.close();
                    }
                    if (statusCode == 401) {
                        this.applicationDataManager.clearAllUserData();
                    }
                    return i;
                } else if (response != null) {
                    Batch responseToBatch = responseToBatch(this.dataStorage.createBatchOperation(), response);
                    Track.httpPerformanceEvent(url, System.currentTimeMillis() - currentTimeMillis);
                    currentTimeMillis = System.currentTimeMillis();
                    responseToBatch.execute();
                    Track.dbPerformanceEvent(url, System.currentTimeMillis() - currentTimeMillis);
                    i = response.getStatusCode();
                    if (response != null) {
                        response.close();
                    }
                    if (statusCode == 401) {
                        this.applicationDataManager.clearAllUserData();
                    }
                    return i;
                } else {
                    if (response != null) {
                        response.close();
                    }
                    if (statusCode != 401) {
                        return statusCode;
                    }
                    this.applicationDataManager.clearAllUserData();
                    return statusCode;
                }
            } catch (Exception e) {
                Exception exception2 = e;
                i = statusCode;
                exception = exception2;
                try {
                    if (exception instanceof CapiIoException) {
                        statusCode = i;
                    } else {
                        statusCode = ((CapiIoException) exception).statusCode();
                    }
                    if (response != null) {
                        response.close();
                    }
                    if (statusCode == 401) {
                        return statusCode;
                    }
                    this.applicationDataManager.clearAllUserData();
                    return statusCode;
                } catch (Throwable th2) {
                    th = th2;
                    if (response != null) {
                        response.close();
                    }
                    if (i == 401) {
                        this.applicationDataManager.clearAllUserData();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                Throwable th4 = th3;
                i = statusCode;
                th = th4;
                if (response != null) {
                    response.close();
                }
                if (i == 401) {
                    this.applicationDataManager.clearAllUserData();
                }
                throw th;
            }
        } catch (Exception e2) {
            exception = e2;
            if (exception instanceof CapiIoException) {
                statusCode = i;
            } else {
                statusCode = ((CapiIoException) exception).statusCode();
            }
            if (response != null) {
                response.close();
            }
            if (statusCode == 401) {
                return statusCode;
            }
            this.applicationDataManager.clearAllUserData();
            return statusCode;
        }
    }

    private Batch responseToBatch(Batch batch, Response response) throws IOException {
        InputStream inputStream = null;
        try {
            JsonParser jsonParserFor = this.jsonParserFactory.getJsonParserFor(this.request.getUrl(), this.request.getTimestamp());
            inputStream = response.getContent();
            jsonParserFor.inflateStream(inputStream, new DataStorageBatchJsonHandler(batch));
            Batch batch2 = jsonParserFor.getBatch();
            return batch2;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
