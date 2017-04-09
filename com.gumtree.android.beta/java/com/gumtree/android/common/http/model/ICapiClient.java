package com.gumtree.android.common.http.model;

import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.executor.Result;
import com.gumtree.android.common.gson.JsonParser;
import com.gumtree.android.common.gson.Parser;
import com.gumtree.android.handler.ContentProviderDataStorage;
import java.io.InputStream;

public interface ICapiClient {
    ICapiClient authorize(String str);

    ICapiClient delete(String str);

    <T> Result<T> execute(Parser<T> parser);

    InputStream execute();

    @NonNull
    <T> Result<T> executeDatabase(JsonParser<T> jsonParser, ContentProviderDataStorage contentProviderDataStorage) throws Exception;

    ICapiClient get(String str);

    ICapiClient post(String str);

    ICapiClient put(String str);

    ICapiClient withContent(String str);

    ICapiClient withContentType(String str);

    ICapiClient withParam(String str, String str2);
}
