package com.gumtree.android.metadata.parser;

import com.google.gson.JsonDeserializer;
import com.gumtree.android.common.gson.BaseGsonParser;
import com.gumtree.android.common.gson.JsonParser;
import com.gumtree.android.common.gson.Parser;
import com.gumtree.android.handler.DataStorage.Batch;
import com.gumtree.android.handler.JsonHandler;
import com.gumtree.android.metadata.SearchMetadata;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class SearchParser extends BaseGsonParser<SearchMetadata[]> implements JsonParser<List<SearchMetadata>>, Parser<List<SearchMetadata>> {
    protected String categoryId;
    protected JsonHandler handler;
    private String url;

    public SearchParser(String str, String str2) {
        this.url = str;
        this.categoryId = str2;
    }

    public void inflateStream(InputStream inputStream, JsonHandler jsonHandler) throws IOException {
        this.handler = jsonHandler;
        for (SearchMetadata onSearchMetadata : parse(inputStream)) {
            jsonHandler.onSearchMetadata(onSearchMetadata, this.categoryId);
        }
    }

    public List<SearchMetadata> parseAndSave(InputStream inputStream, JsonHandler jsonHandler) throws IOException {
        return null;
    }

    public Batch getBatch() {
        if (this.handler == null) {
            return null;
        }
        return this.handler.getBatch();
    }

    protected String getItemName() {
        return null;
    }

    protected Class<SearchMetadata[]> getType() {
        return SearchMetadata[].class;
    }

    public List<SearchMetadata> parse(InputStream inputStream) {
        return Arrays.asList((Object[]) parseValue(inputStream));
    }

    protected String getDomainName() {
        return "{http://www.ebayclassifiedsgroup.com/schema/ad/v1}ads-search-options";
    }

    protected JsonDeserializer<SearchMetadata[]> getDeserializer() {
        return new 1(this);
    }
}
