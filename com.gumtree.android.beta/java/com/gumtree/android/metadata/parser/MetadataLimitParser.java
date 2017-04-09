package com.gumtree.android.metadata.parser;

import com.google.gson.JsonDeserializer;
import com.gumtree.android.metadata.SearchMetadata;

public class MetadataLimitParser extends MetadataParser {
    public MetadataLimitParser(String str, String str2) {
        super(str, str2);
    }

    protected JsonDeserializer<SearchMetadata[]> getDeserializer() {
        return new 1(this);
    }
}
