package com.gumtree.android.ads.parser;

import com.google.gson.JsonDeserializer;
import com.gumtree.android.ads.Ad;
import com.gumtree.android.ads.Picture;
import com.gumtree.android.ads.SRPAds;
import com.gumtree.android.common.gson.BaseGsonParser;
import com.gumtree.android.common.gson.JsonParser;
import com.gumtree.android.common.gson.Parser;
import com.gumtree.android.handler.DataStorage.Batch;
import com.gumtree.android.handler.JsonHandler;
import java.io.IOException;
import java.io.InputStream;

public class AdsListParser extends BaseGsonParser<SRPAds> implements JsonParser, Parser<SRPAds> {
    private static final String AD = "ad";
    private static final String AD_SLOT = "adSlot";
    private static final String AD_SLOTS = "adSlots";
    private static final String EBAY_SEARCH = "ebay-search";
    private static final String HREF = "href";
    private static final String LINK = "link";
    private static final String NUM_FOUND = "numFound";
    private static final String NUM_NEARBY_FOUND = "numNearbyFound";
    private static final String NUM_TOP_ADS_FOUND = "numTopAdsFound";
    private static final String PAGING = "paging";
    private static final String REL = "rel";
    private static final String SELF_PUBLIC_WEBSITE = "self-public-website";
    private static final String SRP_TREEBAY = "SrpTreebay";
    private static final String VALUE = "value";
    private JsonHandler handler;
    private String timestamp;
    private String url;

    public AdsListParser(String str, String str2) {
        this.url = str;
        this.timestamp = str2;
    }

    public void inflateStream(InputStream inputStream, JsonHandler jsonHandler) throws IOException {
        this.handler = jsonHandler;
        SRPAds parse = parse(inputStream);
        for (Ad ad : parse.getAds()) {
            jsonHandler.onAdFromList(ad, this.timestamp);
            if (ad.getPictures().size() > 0) {
                for (Picture onPicture : ad.getPictures()) {
                    jsonHandler.onPicture(ad.getId(), onPicture);
                }
            }
        }
        this.handler.onFinish(this.url, Long.parseLong(this.timestamp), parse.getContentUrl(), parse.getAbundance(), parse.getAdditionalAbundance(), parse.getTopAdCount(), parse.getTreebaySearchUrl(), parse.getTreebayItemTemplateUrl());
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

    protected Class<SRPAds> getType() {
        return SRPAds.class;
    }

    protected String getDomainName() {
        return "{http://www.ebayclassifiedsgroup.com/schema/ad/v1}ads";
    }

    public SRPAds parse(InputStream inputStream) {
        return (SRPAds) parseValue(inputStream);
    }

    protected JsonDeserializer<SRPAds> getDeserializer() {
        return new 1(this);
    }
}
