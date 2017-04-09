package com.gumtree.android.metadata.parser;

import com.google.gson.JsonDeserializer;
import com.gumtree.android.handler.JsonHandler;
import com.gumtree.android.metadata.SearchMetadata;
import com.gumtree.android.metadata.SupportedValueOptions;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MetadataParser extends SearchParser {
    public MetadataParser(String str, String str2) {
        super(str, str2);
    }

    protected String getDomainName() {
        return "{http://www.ebayclassifiedsgroup.com/schema/ad/v1}ad";
    }

    public void inflateStream(InputStream inputStream, JsonHandler jsonHandler) throws IOException {
        this.handler = jsonHandler;
        for (SearchMetadata searchMetadata : parse(inputStream)) {
            jsonHandler.onMetadata(searchMetadata, this.categoryId);
            List<SupportedValueOptions> supportedValueOptions = searchMetadata.getSupportedValueOptions();
            if (!(supportedValueOptions == null || supportedValueOptions.isEmpty())) {
                for (SupportedValueOptions onMetadataSupportedValuesOptions : supportedValueOptions) {
                    jsonHandler.onMetadataSupportedValuesOptions(onMetadataSupportedValuesOptions, searchMetadata.getName());
                }
            }
        }
    }

    public List<SearchMetadata> parseAndSave(InputStream inputStream, JsonHandler jsonHandler) throws IOException {
        this.handler = jsonHandler;
        List<SearchMetadata> parse = parse(inputStream);
        for (SearchMetadata searchMetadata : parse) {
            jsonHandler.onMetadata(searchMetadata, this.categoryId);
            List<SupportedValueOptions> supportedValueOptions = searchMetadata.getSupportedValueOptions();
            if (!(supportedValueOptions == null || supportedValueOptions.isEmpty())) {
                for (SupportedValueOptions onMetadataSupportedValuesOptions : supportedValueOptions) {
                    jsonHandler.onMetadataSupportedValuesOptions(onMetadataSupportedValuesOptions, searchMetadata.getName());
                }
            }
        }
        return parse;
    }

    protected JsonDeserializer<SearchMetadata[]> getDeserializer() {
        return new 1(this);
    }
}
