package com.gumtree.android.features.parser;

import com.google.gson.JsonDeserializer;
import com.gumtree.android.common.gson.BaseGsonParser;
import com.gumtree.android.common.gson.JsonParser;
import com.gumtree.android.common.gson.Parser;
import com.gumtree.android.features.Feature;
import com.gumtree.android.features.deserializer.FeatureDeserializer;
import com.gumtree.android.handler.DataStorage.Batch;
import com.gumtree.android.handler.JsonHandler;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class FeaturesParser extends BaseGsonParser<Feature[]> implements JsonParser<List<Feature>>, Parser<List<Feature>> {
    protected JsonHandler handler;
    private String id;

    public FeaturesParser(String str, String str2) {
        this.id = str + ";" + str2;
    }

    protected String getItemName() {
        return "feature-group";
    }

    protected Class<Feature[]> getType() {
        return Feature[].class;
    }

    protected String getDomainName() {
        return "{http://www.ebayclassifiedsgroup.com/schema/feature/v1}feature-groups";
    }

    public List<Feature> parse(InputStream inputStream) {
        return Arrays.asList((Object[]) parseValue(inputStream));
    }

    protected JsonDeserializer<Feature[]> getDeserializer() {
        return new FeatureDeserializer(this.id);
    }

    public void inflateStream(InputStream inputStream, JsonHandler jsonHandler) throws IOException {
    }

    public List<Feature> parseAndSave(InputStream inputStream, JsonHandler jsonHandler) throws IOException {
        this.handler = jsonHandler;
        List<Feature> parse = parse(inputStream);
        for (Feature onFeature : parse) {
            jsonHandler.onFeature(onFeature);
        }
        return parse;
    }

    public Batch getBatch() {
        if (this.handler == null) {
            return null;
        }
        return this.handler.getBatch();
    }
}
