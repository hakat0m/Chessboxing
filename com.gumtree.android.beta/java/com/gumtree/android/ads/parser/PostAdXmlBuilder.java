package com.gumtree.android.ads.parser;

import android.util.Xml;
import com.adjust.sdk.Constants;
import com.apptentive.android.sdk.BuildConfig;
import com.apptentive.android.sdk.model.CodePointStore;
import com.ebay.classifieds.capi.categories.CategoriesApi;
import com.ebay.classifieds.capi.locations.LocationsApi;
import com.ebay.classifieds.capi.replies.RepliesApi;
import com.ebay.classifieds.capi.users.ads.UserAdsApi;
import com.ebay.classifieds.capi.users.profile.UserProfileApi;
import com.gumtree.android.postad.services.converter.PaymentConverter;
import com.gumtree.android.vip.VIPContactFragment;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.xmlpull.v1.XmlSerializer;

public class PostAdXmlBuilder {
    private static final String AD = "ad:";
    private static final String AD_AD = "ad:ad";
    private static final String AD_AD_ADDRESS = "ad:ad-address";
    private static final String AD_LINK = "ad:link";
    private static final String AD_PRICE = "ad:price";
    private static final String AD_PRICE_FREQUENCY = "ad:price-frequency";
    private static final String ATTR_ATTRIBUTE = "attr:attribute";
    private static final String ATTR_ATTRIBUTES = "attr:attributes";
    private static final String ATTR_VALUE = "attr:value";
    private static final String AVAILABLE_DATE = "available_date";
    private static final String CAT_CATEGORY = "cat:category";
    private static final String DD_MM_YYYY = "dd/MM/yyyy";
    private static final String EN_GB = "en_GB";
    private static final String HREF = "href";
    private static final String ID = "id";
    private static final String LINK = "link";
    private static final String LOC_LOCATION = "loc:location";
    private static final String LOC_LOCATIONS = "loc:locations";
    private static final String NAME = "name";
    private static final String PIC_LINK = "pic:link";
    private static final String PIC_PICTURE = "pic:picture";
    private static final String PIC_PICTURES = "pic:pictures";
    private static final List<String> SPECIAL_ATTRIBUTES = Arrays.asList(new String[]{VIPContactFragment.CATEGORY_ID, "location", ZIP_CODE, "price", LINK, TICKET_DATE, AVAILABLE_DATE, "price-frequency", "phone", "poster-contact-email"});
    private static final String TICKET_DATE = "ticket_date";
    private static final String TYPES_AMOUNT = "types:amount";
    private static final String TYPES_CURRENCY_ISO_CODE = "types:currency-iso-code";
    private static final String TYPES_VALUE = "types:value";
    private static final String TYPES_ZIP_CODE = "types:zip-code";
    private static final String ZIP_CODE = "zip-code";
    private final PostAdData data;
    private final XmlSerializer serializer;

    public interface PostAdData {
        Map<String, String> retrieveAttributes();

        Map<String, String> retrieveDynamicAttributes();

        List<String> retrievePictures();
    }

    public PostAdXmlBuilder(PostAdData postAdData) {
        this(postAdData, Xml.newSerializer());
    }

    public PostAdXmlBuilder(PostAdData postAdData, XmlSerializer xmlSerializer) {
        this.data = postAdData;
        this.serializer = xmlSerializer;
    }

    public String createPostAdRequestXml() throws IOException {
        Writer stringWriter = new StringWriter();
        this.serializer.setOutput(stringWriter);
        this.serializer.startDocument(Constants.ENCODING, Boolean.valueOf(true));
        this.serializer.startTag(BuildConfig.FLAVOR, AD_AD);
        setHeaderAttributes();
        fillInAttributeData();
        fillInPictureLinks();
        fillInDynamicAttributeData();
        this.serializer.endTag(BuildConfig.FLAVOR, AD_AD);
        this.serializer.endDocument();
        return stringWriter.toString();
    }

    private void setHeaderAttributes() throws IOException {
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:types", "http://www.ebayclassifiedsgroup.com/schema/types/v1");
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:cat", CategoriesApi.CATEGORY_NAMESPACE);
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:ad", UserAdsApi.SCHEMA_AD);
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:loc", LocationsApi.LOCATION_NAMESPACE);
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:attr", "http://www.ebayclassifiedsgroup.com/schema/attribute/v1");
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:pic", "http://www.ebayclassifiedsgroup.com/schema/picture/v1");
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:user", UserProfileApi.SCHEMA_USER_PROFILE);
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:rate", "http://www.ebayclassifiedsgroup.com/schema/rate/v1");
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:reply", RepliesApi.NAMESPACE);
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:feed", "http://www.ebayclassifiedsgroup.com/schema/feed/v1");
        this.serializer.attribute(BuildConfig.FLAVOR, "supported-locales", EN_GB);
        this.serializer.attribute(BuildConfig.FLAVOR, "locale", EN_GB);
        this.serializer.attribute(BuildConfig.FLAVOR, CodePointStore.KEY_VERSION, "1.15");
    }

    private void fillInAttributeData() throws IOException {
        Map retrieveAttributes = this.data.retrieveAttributes();
        if (retrieveAttributes.size() != 0) {
            serializePhone(retrieveAttributes);
            serializeEmail(retrieveAttributes);
            serializeNormalAttributes(retrieveAttributes);
            serializeCategory(retrieveAttributes);
            serializeLocation(retrieveAttributes);
            serializePrice(retrieveAttributes);
            serializePriceFrequency(this.data.retrieveDynamicAttributes());
            serializeLink(retrieveAttributes);
            serializeZipCode(retrieveAttributes);
        }
    }

    private void fillInDynamicAttributeData() throws IOException {
        Map retrieveDynamicAttributes = this.data.retrieveDynamicAttributes();
        if (retrieveDynamicAttributes.size() != 0) {
            this.serializer.startTag(BuildConfig.FLAVOR, ATTR_ATTRIBUTES);
            for (String serializeNormalDynamicAttribute : retrieveDynamicAttributes.keySet()) {
                serializeNormalDynamicAttribute(serializeNormalDynamicAttribute, retrieveDynamicAttributes);
            }
            serializeAvailableDateAttribute(retrieveDynamicAttributes);
            serializeTicketDate(retrieveDynamicAttributes);
            this.serializer.endTag(BuildConfig.FLAVOR, ATTR_ATTRIBUTES);
        }
    }

    private void fillInPictureLinks() throws IOException {
        List<String> retrievePictures = this.data.retrievePictures();
        if (!retrievePictures.isEmpty()) {
            this.serializer.startTag(BuildConfig.FLAVOR, PIC_PICTURES);
            for (String str : retrievePictures) {
                this.serializer.startTag(BuildConfig.FLAVOR, PIC_PICTURE);
                this.serializer.startTag(BuildConfig.FLAVOR, PIC_LINK);
                this.serializer.attribute(BuildConfig.FLAVOR, HREF, str);
                this.serializer.endTag(BuildConfig.FLAVOR, PIC_LINK);
                this.serializer.endTag(BuildConfig.FLAVOR, PIC_PICTURE);
            }
            this.serializer.endTag(BuildConfig.FLAVOR, PIC_PICTURES);
        }
    }

    private void serializePhone(Map<String, String> map) throws IOException {
        if (map.containsKey("phone-enabled") && PaymentConverter.DEFAULT_PAYMENT_METHOD_ID.equals(map.get("phone-enabled"))) {
            addAttribute(map, "phone");
        }
    }

    private void serializeEmail(Map<String, String> map) throws IOException {
        if (map.containsKey("poster-contact-email-enabled") && PaymentConverter.DEFAULT_PAYMENT_METHOD_ID.equals(map.get("poster-contact-email-enabled"))) {
            addAttribute(map, "poster-contact-email");
        }
    }

    private void serializeNormalAttributes(Map<String, String> map) throws IOException {
        for (String str : map.keySet()) {
            if (!SPECIAL_ATTRIBUTES.contains(str)) {
                addAttribute(map, str);
            }
        }
    }

    private void addAttribute(Map<String, String> map, String str) throws IOException {
        String str2 = (String) map.get(str);
        if (!"neighborhood".equals(str) || str2 != null) {
            this.serializer.startTag(BuildConfig.FLAVOR, AD + str);
            if (str2 != null) {
                this.serializer.text(str2.replaceAll("[^\\u0000-\\uFFFF]", BuildConfig.FLAVOR));
            }
            this.serializer.endTag(BuildConfig.FLAVOR, AD + str);
        }
    }

    private void serializeZipCode(Map<String, String> map) throws IOException {
        if (map.containsKey(ZIP_CODE) && map.get(ZIP_CODE) != null) {
            this.serializer.startTag(BuildConfig.FLAVOR, AD_AD_ADDRESS);
            this.serializer.startTag(BuildConfig.FLAVOR, TYPES_ZIP_CODE);
            this.serializer.text(((String) map.get(ZIP_CODE)).replaceAll("\\s+", BuildConfig.FLAVOR));
            this.serializer.endTag(BuildConfig.FLAVOR, TYPES_ZIP_CODE);
            this.serializer.endTag(BuildConfig.FLAVOR, AD_AD_ADDRESS);
        }
    }

    private void serializeLink(Map<String, String> map) throws IOException {
        if (map.containsKey(LINK)) {
            this.serializer.startTag(BuildConfig.FLAVOR, AD_LINK);
            this.serializer.attribute(BuildConfig.FLAVOR, "rel", "reply");
            this.serializer.attribute(BuildConfig.FLAVOR, HREF, (String) map.get(LINK));
            this.serializer.endTag(BuildConfig.FLAVOR, AD_LINK);
        }
    }

    private void serializePrice(Map<String, String> map) throws IOException {
        if (map.containsKey("price")) {
            this.serializer.startTag(BuildConfig.FLAVOR, AD_PRICE);
            this.serializer.startTag(BuildConfig.FLAVOR, TYPES_CURRENCY_ISO_CODE);
            this.serializer.startTag(BuildConfig.FLAVOR, TYPES_VALUE);
            this.serializer.text(PaymentConverter.DEFAULT_CURRENCY_ISO_CODE);
            this.serializer.endTag(BuildConfig.FLAVOR, TYPES_VALUE);
            this.serializer.endTag(BuildConfig.FLAVOR, TYPES_CURRENCY_ISO_CODE);
            this.serializer.startTag(BuildConfig.FLAVOR, TYPES_AMOUNT);
            this.serializer.text((String) map.get("price"));
            this.serializer.endTag(BuildConfig.FLAVOR, TYPES_AMOUNT);
            this.serializer.endTag(BuildConfig.FLAVOR, AD_PRICE);
        }
    }

    private void serializePriceFrequency(Map<String, String> map) throws IOException {
        String str = (String) map.get("price-frequency");
        if (str != null) {
            this.serializer.startTag(BuildConfig.FLAVOR, AD_PRICE_FREQUENCY);
            this.serializer.startTag(BuildConfig.FLAVOR, TYPES_VALUE);
            this.serializer.text(str);
            this.serializer.endTag(BuildConfig.FLAVOR, TYPES_VALUE);
            this.serializer.endTag(BuildConfig.FLAVOR, AD_PRICE_FREQUENCY);
        }
    }

    private void serializeLocation(Map<String, String> map) throws IOException {
        if (map.containsKey("location")) {
            this.serializer.startTag(BuildConfig.FLAVOR, LOC_LOCATIONS);
            this.serializer.startTag(BuildConfig.FLAVOR, LOC_LOCATION);
            this.serializer.attribute(BuildConfig.FLAVOR, ID, (String) map.get("location"));
            this.serializer.endTag(BuildConfig.FLAVOR, LOC_LOCATION);
            this.serializer.endTag(BuildConfig.FLAVOR, LOC_LOCATIONS);
        }
    }

    private void serializeCategory(Map<String, String> map) throws IOException {
        if (map.containsKey(VIPContactFragment.CATEGORY_ID)) {
            this.serializer.startTag(BuildConfig.FLAVOR, CAT_CATEGORY);
            this.serializer.attribute(BuildConfig.FLAVOR, ID, (String) map.get(VIPContactFragment.CATEGORY_ID));
            this.serializer.endTag(BuildConfig.FLAVOR, CAT_CATEGORY);
        }
    }

    private void serializeNormalDynamicAttribute(String str, Map<String, String> map) throws IOException {
        if (!SPECIAL_ATTRIBUTES.contains(str)) {
            String str2 = (String) map.get(str);
            if (str2 != null && str2.length() != 0) {
                this.serializer.startTag(BuildConfig.FLAVOR, ATTR_ATTRIBUTE);
                this.serializer.attribute(BuildConfig.FLAVOR, NAME, str);
                this.serializer.startTag(BuildConfig.FLAVOR, ATTR_VALUE);
                this.serializer.text(str2);
                this.serializer.endTag(BuildConfig.FLAVOR, ATTR_VALUE);
                this.serializer.endTag(BuildConfig.FLAVOR, ATTR_ATTRIBUTE);
            }
        }
    }

    private void serializeAvailableDateAttribute(Map<String, String> map) throws IOException {
        if (map.containsKey(AVAILABLE_DATE)) {
            String str = (String) map.get(AVAILABLE_DATE);
            if (str != null && str.length() != 0) {
                this.serializer.startTag(BuildConfig.FLAVOR, ATTR_ATTRIBUTE);
                this.serializer.attribute(BuildConfig.FLAVOR, NAME, AVAILABLE_DATE);
                this.serializer.startTag(BuildConfig.FLAVOR, ATTR_VALUE);
                this.serializer.text(formatDate(str));
                this.serializer.endTag(BuildConfig.FLAVOR, ATTR_VALUE);
                this.serializer.endTag(BuildConfig.FLAVOR, ATTR_ATTRIBUTE);
            }
        }
    }

    private void serializeTicketDate(Map<String, String> map) throws IOException {
        if (map.containsKey(TICKET_DATE)) {
            String str = (String) map.get(TICKET_DATE);
            this.serializer.startTag(BuildConfig.FLAVOR, ATTR_ATTRIBUTE);
            this.serializer.attribute(BuildConfig.FLAVOR, NAME, TICKET_DATE);
            if (str != null) {
                this.serializer.startTag(BuildConfig.FLAVOR, ATTR_VALUE);
                this.serializer.text(formatDate(str));
                this.serializer.endTag(BuildConfig.FLAVOR, ATTR_VALUE);
            }
            this.serializer.endTag(BuildConfig.FLAVOR, ATTR_ATTRIBUTE);
        }
    }

    private String formatDate(String str) {
        DateFormat simpleDateFormat;
        if (str.contains("Z")) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSS'Z'");
            try {
                str = new SimpleDateFormat(DD_MM_YYYY).format(simpleDateFormat.parse(str));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (str.contains("+")) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSS+01:00");
            try {
                str = new SimpleDateFormat(DD_MM_YYYY).format(simpleDateFormat.parse(str));
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        } else {
            simpleDateFormat = new SimpleDateFormat(DD_MM_YYYY);
            try {
                str = new SimpleDateFormat(DD_MM_YYYY).format(simpleDateFormat.parse(str));
            } catch (ParseException e22) {
                e22.printStackTrace();
            }
        }
        return str;
    }
}
