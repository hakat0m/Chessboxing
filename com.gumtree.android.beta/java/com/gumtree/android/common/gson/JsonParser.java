package com.gumtree.android.common.gson;

import com.gumtree.android.handler.DataStorage.Batch;
import com.gumtree.android.handler.JsonHandler;
import java.io.IOException;
import java.io.InputStream;

public interface JsonParser<T> {
    Batch getBatch();

    void inflateStream(InputStream inputStream, JsonHandler jsonHandler) throws IOException;

    T parseAndSave(InputStream inputStream, JsonHandler jsonHandler) throws IOException;
}
