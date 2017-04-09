package com.ebay.classifieds.capi;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
import retrofit.converter.SimpleXMLConverter;

public final class ContentConverters {
    private ContentConverters() {
    }

    public static Converter getXMLConverter() {
        return new SimpleXMLConverter();
    }

    public static Converter getGsonConverter() {
        return new GsonConverter(new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create());
    }
}
