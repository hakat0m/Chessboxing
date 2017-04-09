package com.gumtree.android.ads.parser;

import com.apptentive.android.sdk.BuildConfig;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.gumtree.android.ads.CoreAttribute;
import com.gumtree.android.ads.DynamicAttribute;
import com.gumtree.android.ads.Picture;
import com.gumtree.android.ads.PostAd;
import com.gumtree.android.category.NewPostAdCategoryActivity;
import com.gumtree.android.common.gson.BaseGsonParser;
import com.gumtree.android.common.gson.JsonParser;
import com.gumtree.android.common.gson.Parser;
import com.gumtree.android.dfp.DFPProcessor;
import com.gumtree.android.handler.DataStorage.Batch;
import com.gumtree.android.handler.JsonHandler;
import com.gumtree.android.model.PostAdsImages;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

public class ManageAdParser extends BaseGsonParser<PostAd> implements JsonParser, Parser<PostAd> {
    private static final String ATTRIBUTE = "attribute";
    private static final List<String> ATTRIBUTE_WITH_VALUE_NODE = Arrays.asList(new String[]{NewPostAdCategoryActivity.EXTRA_TITLE, "description", "email", PHONE, "price-frequency", "neighborhood", "zip-code", "visible-on-map", "poster-contact-name"});
    private static final String FEATURE_ACTIVE = "feature-active";
    private static final String ID = "id";
    private static final String LOCALIZED_LABEL = "localized-label";
    private static final String LOCALIZED_NAME = "localized-name";
    private static final String NAME = "name";
    private static final String ONE = "1";
    private static final String PHONE = "phone";
    private static final String PICTURE = "picture";
    private static final String REL = "rel";
    private static final String VALUE = "value";
    private String adId;
    private JsonHandler handler;
    private String timestamp;
    private String url;

    public ManageAdParser(String str, String str2) {
        this.adId = getAdIdFromUrl(str);
        this.url = str;
        this.timestamp = str2;
    }

    public Batch getBatch() {
        if (this.handler == null) {
            return null;
        }
        return this.handler.getBatch();
    }

    private void setBatch(JsonHandler jsonHandler) {
        this.handler = jsonHandler;
    }

    public void inflateStream(InputStream inputStream, JsonHandler jsonHandler) throws IOException {
        setBatch(jsonHandler);
        this.handler.onManageAd((PostAd) parseValue(inputStream));
        this.handler.onFinish(this.url, this.timestamp);
    }

    public PostAd parseAndSave(InputStream inputStream, JsonHandler jsonHandler) throws IOException {
        setBatch(jsonHandler);
        PostAd postAd = (PostAd) parseValue(inputStream);
        postAd.setId(this.adId);
        this.handler.onManageAd(postAd);
        this.handler.onFinish(this.url, this.timestamp);
        return postAd;
    }

    protected String getItemName() {
        return null;
    }

    protected Class<PostAd> getType() {
        return PostAd.class;
    }

    protected String getDomainName() {
        return "{http://www.ebayclassifiedsgroup.com/schema/ad/v1}ad";
    }

    protected String getAdIdFromUrl(String str) {
        int lastIndexOf = str.lastIndexOf(DFPProcessor.SEPARATOR);
        int lastIndexOf2 = str.lastIndexOf(".json");
        if (lastIndexOf2 != -1 || lastIndexOf != -1) {
            return str.substring(lastIndexOf + 1, lastIndexOf2);
        }
        throw new RuntimeException("IllegalUrlException url : " + str);
    }

    protected JsonDeserializer<PostAd> getDeserializer() {
        return new 1(this);
    }

    private DynamicAttribute setDynamicAttribute(JsonObject jsonObject) {
        DynamicAttribute dynamicAttribute = new DynamicAttribute();
        dynamicAttribute.setAdId(this.adId);
        dynamicAttribute.setKey(jsonObject.get(NAME).getAsString());
        JsonObject asJsonObject = jsonObject.get(VALUE).getAsJsonArray().get(0).getAsJsonObject();
        dynamicAttribute.setValue(asJsonObject.get(VALUE).getAsString());
        if (asJsonObject.has(LOCALIZED_LABEL)) {
            dynamicAttribute.setLabel(asJsonObject.get(LOCALIZED_LABEL).getAsString());
        }
        dynamicAttribute.setDescription(jsonObject.get(LOCALIZED_LABEL).getAsString());
        return dynamicAttribute;
    }

    private CoreAttribute setCoreAttribute(JsonElement jsonElement, String... strArr) {
        CoreAttribute coreAttribute = new CoreAttribute();
        coreAttribute.setAdId(this.adId);
        coreAttribute.setKey(strArr[0]);
        if (!jsonElement.isJsonObject()) {
            coreAttribute.setValue(jsonElement.getAsString());
        } else if (jsonElement.getAsJsonObject().has(VALUE)) {
            coreAttribute.setValue(((JsonObject) jsonElement).get(VALUE).getAsString());
        }
        if (strArr.length > 1) {
            coreAttribute.setLabel(strArr[1]);
        }
        return coreAttribute;
    }

    public String setFeatureActive(JsonObject jsonObject) {
        return jsonObject.get(NAME).getAsString();
    }

    private void onPictures(PostAd postAd, Entry<String, JsonElement> entry) {
        JsonObject asJsonObject = ((JsonElement) entry.getValue()).getAsJsonObject();
        if (asJsonObject.has(PICTURE)) {
            JsonArray asJsonArray = asJsonObject.get(PICTURE).getAsJsonArray();
            for (int i = 0; i < asJsonArray.size(); i++) {
                JsonArray asJsonArray2 = asJsonArray.get(i).getAsJsonObject().get("link").getAsJsonArray();
                for (int i2 = 0; i2 < asJsonArray2.size(); i2++) {
                    JsonObject asJsonObject2 = asJsonArray2.get(i2).getAsJsonObject();
                    if (asJsonObject2.has(REL) && "extrabig".equals(asJsonObject2.get(REL).getAsString())) {
                        Picture picture = new Picture();
                        picture.setAdId(this.adId);
                        picture.setStatus(PostAdsImages.SUCCESS);
                        picture.setHref(asJsonObject2.get("href").getAsString());
                        picture.setRel(asJsonObject2.get(REL).getAsString());
                        picture.setIndex(BuildConfig.FLAVOR + i);
                        postAd.addPicture(picture);
                    }
                }
            }
        }
    }

    public PostAd parse(InputStream inputStream) {
        return (PostAd) parseValue(inputStream);
    }
}
