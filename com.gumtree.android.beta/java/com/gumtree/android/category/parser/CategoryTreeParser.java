package com.gumtree.android.category.parser;

import com.google.gson.JsonDeserializer;
import com.gumtree.android.category.CategoryItem;
import com.gumtree.android.category.deserializer.CategoryDeserializer;
import com.gumtree.android.common.gson.BaseGsonParser;
import com.gumtree.android.common.gson.JsonParser;
import com.gumtree.android.common.gson.Parser;
import com.gumtree.android.handler.DataStorage.Batch;
import com.gumtree.android.handler.JsonHandler;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class CategoryTreeParser extends BaseGsonParser<CategoryItem[]> implements JsonParser, Parser<List<CategoryItem>> {
    private JsonHandler handler;
    private String timestamp;
    private String url;

    public CategoryTreeParser(String str, String str2) {
        this.timestamp = str2;
        this.url = str;
    }

    protected String getItemName() {
        return null;
    }

    protected Class<CategoryItem[]> getType() {
        return CategoryItem[].class;
    }

    protected String getDomainName() {
        return "{http://www.ebayclassifiedsgroup.com/schema/category/v1}category";
    }

    public void inflateStream(InputStream inputStream, JsonHandler jsonHandler) throws IOException {
        this.handler = jsonHandler;
        bundleDataStorage(jsonHandler, (CategoryItem) Arrays.asList((Object[]) parseValue(inputStream)).get(0));
        this.handler.onFinish(this.url, this.timestamp);
    }

    public Object parseAndSave(InputStream inputStream, JsonHandler jsonHandler) throws IOException {
        return null;
    }

    private void bundleDataStorage(JsonHandler jsonHandler, CategoryItem categoryItem) {
        for (int i = 0; i < categoryItem.getCategories().size(); i++) {
            bundleDataStorage(jsonHandler, (CategoryItem) categoryItem.getCategories().get(i));
        }
        jsonHandler.onCategory(categoryItem, this.timestamp);
    }

    public Batch getBatch() {
        return this.handler.getBatch();
    }

    protected JsonDeserializer<CategoryItem[]> getDeserializer() {
        return new CategoryDeserializer();
    }

    public List<CategoryItem> parse(InputStream inputStream) {
        return Arrays.asList((Object[]) parseValue(inputStream));
    }
}
