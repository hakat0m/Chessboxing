package com.gumtree.android.ads.parser;

import com.google.gson.JsonDeserializer;
import com.gumtree.android.ads.Ad;
import com.gumtree.android.ads.deserializer.AdDeserializer;
import com.gumtree.android.common.gson.BaseGsonParser;
import com.gumtree.android.common.gson.JsonParser;
import com.gumtree.android.common.gson.Parser;
import com.gumtree.android.handler.DataStorage.Batch;
import com.gumtree.android.handler.JsonHandler;
import java.io.IOException;
import java.io.InputStream;

public class AdParser extends BaseGsonParser<Ad> implements JsonParser, Parser<Ad> {
    private static final String NOT_IMPLEMENTED = "Not implemented";

    public Ad parse(InputStream inputStream) {
        return (Ad) parseValue(inputStream);
    }

    public void inflateStream(InputStream inputStream, JsonHandler jsonHandler) throws IOException {
    }

    public Object parseAndSave(InputStream inputStream, JsonHandler jsonHandler) throws IOException {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    public Batch getBatch() {
        throw new UnsupportedOperationException(NOT_IMPLEMENTED);
    }

    protected String getItemName() {
        return null;
    }

    protected Class<Ad> getType() {
        return Ad.class;
    }

    protected String getDomainName() {
        return "{http://www.ebayclassifiedsgroup.com/schema/ad/v1}ad";
    }

    protected JsonDeserializer<Ad> getDeserializer() {
        return new AdDeserializer(true);
    }
}
