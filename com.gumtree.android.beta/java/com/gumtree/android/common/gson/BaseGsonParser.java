package com.gumtree.android.common.gson;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.gumtree.android.common.http.model.CapiApiException;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class BaseGsonParser<T> {
    private static final String GUMTREE = "Gumtree";
    private static final String UTF_8 = "UTF-8";
    private static final String VALUE = "value";
    protected JsonReader reader;
    private T value;

    protected abstract JsonDeserializer<T> getDeserializer();

    protected abstract String getDomainName();

    protected abstract String getItemName();

    protected abstract Class<T> getType();

    protected void prepareReader(InputStream inputStream) throws IOException {
        this.reader = new JsonReader(new BufferedReader(new InputStreamReader(logStream(inputStream), UTF_8)));
        this.reader.setLenient(true);
    }

    public T parseValue(InputStream inputStream) {
        Exception e;
        if (inputStream == null) {
            throw new CapiApiException("Stream received is null");
        }
        try {
            prepareReader(inputStream);
            JsonElement jsonElement = new JsonParser().parse(this.reader).getAsJsonObject().get(getDomainName());
            if (getItemName() != null) {
                jsonElement = jsonElement.getAsJsonObject().get(VALUE).getAsJsonObject().get(getItemName());
            } else {
                jsonElement = jsonElement.getAsJsonObject().get(VALUE);
            }
            if (getDeserializer() != null) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(getType(), getDeserializer());
                this.value = gsonBuilder.create().fromJson(jsonElement, getType());
            } else {
                this.value = new Gson().fromJson(jsonElement, getType());
            }
            return this.value;
        } catch (IOException e2) {
            e = e2;
            throw new CapiApiException(e.getMessage());
        } catch (NullPointerException e3) {
            e = e3;
            throw new CapiApiException(e.getMessage());
        } catch (IndexOutOfBoundsException e4) {
            e = e4;
            throw new CapiApiException(e.getMessage());
        } catch (IllegalStateException e5) {
            e = e5;
            throw new CapiApiException(e.getMessage());
        }
    }

    private InputStream logStream(InputStream inputStream) throws IOException {
        if (isLogDisabled()) {
            return inputStream;
        }
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF_8));
        for (String readLine = bufferedReader.readLine(); readLine != null; readLine = bufferedReader.readLine()) {
            stringBuilder.append(readLine);
        }
        bufferedReader.close();
        inputStream.close();
        log("Parser: " + stringBuilder.toString());
        return new ByteArrayInputStream(stringBuilder.toString().getBytes(UTF_8));
    }

    protected void log(String str) {
        try {
            Log.v(GUMTREE, str);
        } catch (Exception e) {
            System.out.println(str);
        }
    }

    protected boolean isLogDisabled() {
        try {
            return !Log.isLoggable(GUMTREE, 2);
        } catch (Throwable th) {
            return false;
        }
    }
}
