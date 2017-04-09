package com.gumtree.android.ads.parser;

import com.apptentive.android.sdk.BuildConfig;
import com.google.gson.JsonDeserializer;
import com.gumtree.android.ads.ManageAd;
import com.gumtree.android.ads.ManageAds;
import com.gumtree.android.ads.Picture;
import com.gumtree.android.common.gson.BaseGsonParser;
import com.gumtree.android.common.gson.JsonParser;
import com.gumtree.android.common.gson.Parser;
import com.gumtree.android.handler.DataStorage.Batch;
import com.gumtree.android.handler.JsonHandler;
import java.io.IOException;
import java.io.InputStream;

public class ManageAdsListParser extends BaseGsonParser<ManageAds> implements JsonParser, Parser<ManageAds> {
    private static final String AD = "ad";
    private JsonHandler handler;
    private String timestamp;
    private String url;

    public ManageAdsListParser(String str, String str2) {
        this.url = str;
        this.timestamp = str2;
    }

    public void inflateStream(InputStream inputStream, JsonHandler jsonHandler) throws IOException {
        this.handler = jsonHandler;
        ManageAds parse = parse(inputStream);
        for (ManageAd manageAd : parse.getAds()) {
            this.handler.onManagedAdFromList(manageAd, this.timestamp);
            if (manageAd.getAdvert().getPictures().size() > 0) {
                for (Picture onPicture : manageAd.getAdvert().getPictures()) {
                    this.handler.onPicture(manageAd.getAdvert().getId(), onPicture);
                }
            }
        }
        this.handler.onFinish(this.url, Long.parseLong(this.timestamp), BuildConfig.FLAVOR + parse.getAbundance());
    }

    public Object parseAndSave(InputStream inputStream, JsonHandler jsonHandler) throws IOException {
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

    protected Class<ManageAds> getType() {
        return ManageAds.class;
    }

    protected String getDomainName() {
        return "{http://www.ebayclassifiedsgroup.com/schema/ad/v1}ads";
    }

    public ManageAds parse(InputStream inputStream) {
        return (ManageAds) parseValue(inputStream);
    }

    protected JsonDeserializer<ManageAds> getDeserializer() {
        return new 1(this);
    }
}
