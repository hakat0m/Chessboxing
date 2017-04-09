package com.gumtree.android.features.parser;

import android.util.Xml;
import com.adjust.sdk.Constants;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.classifieds.capi.features.FeaturesApi;
import com.ebay.classifieds.capi.features.Period;
import com.ebay.classifieds.capi.order.OrderApi;
import com.gumtree.android.features.Feature;
import com.gumtree.android.postad.services.converter.PaymentConverter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import org.xmlpull.v1.XmlSerializer;

public class OrderRequestSerializer {
    public static final String CANCEL_URL = "https://www.cancel.com/";
    private static final String FEAT_AMOUNT = "feat:amount";
    private static final String FEAT_CURRENCY_ISO_CODE = "feat:currency-iso-code";
    private static final String FEAT_FEATURE_DURATION = "feat:feature-duration";
    private static final String FEAT_FEATURE_PRICE = "feat:feature-price";
    private static final String FEAT_OPTION = "feat:option";
    private static final String NAME = "name";
    private static final String ORDER_FEATURE_BOOKED = "order:feature-booked";
    private static final String ORDER_ORDER_ITEM = "order:order-item";
    private static final String ORDER_ORDER_ITEMS = "order:order-items";
    private static final String ORDER_ORDER_REQUEST = "order:order-request";
    private static final String ORDER_PAYMENT_METHOD = "order:payment-method";
    private static final String PAYMENT_CANCEL_URL = "payment:cancel-url";
    private static final String PAYMENT_CONFIRM_URL = "payment:confirm-url";
    private static final String PAYMENT_SET_PAYPAL_EXPRESS_CHECKOUT = "payment:set-paypal-express-checkout";
    public static final String SUCCESS_URL = "https://www.success.com/";
    private static final String TYPE_PERIOD = "type:period";
    private static final String TYPE_VALUE = "type:value";
    private String adId;
    private final List<Feature> features;
    private final XmlSerializer serializer;

    public OrderRequestSerializer(List<Feature> list) {
        this(list, Xml.newSerializer());
    }

    public OrderRequestSerializer(List<Feature> list, XmlSerializer xmlSerializer) {
        this.features = list;
        this.serializer = xmlSerializer;
    }

    public String createOrderRequest(String str) throws IOException {
        this.adId = str;
        Writer stringWriter = new StringWriter();
        this.serializer.setOutput(stringWriter);
        this.serializer.startDocument(Constants.ENCODING, Boolean.valueOf(true));
        this.serializer.startTag(BuildConfig.FLAVOR, ORDER_ORDER_REQUEST);
        setHeaderAttributes();
        this.serializer.startTag(BuildConfig.FLAVOR, ORDER_ORDER_ITEMS);
        addFeatures();
        this.serializer.endTag(BuildConfig.FLAVOR, ORDER_ORDER_ITEMS);
        addPaymentMethod();
        this.serializer.endTag(BuildConfig.FLAVOR, ORDER_ORDER_REQUEST);
        this.serializer.endDocument();
        return stringWriter.toString();
    }

    private void addPaymentMethod() throws IOException {
        this.serializer.startTag(BuildConfig.FLAVOR, ORDER_PAYMENT_METHOD);
        this.serializer.attribute(BuildConfig.FLAVOR, "id", PaymentConverter.DEFAULT_PAYMENT_METHOD_ID);
        this.serializer.attribute(BuildConfig.FLAVOR, "type", PaymentConverter.DEFAULT_PAYMENT_METHOD_TYPE);
        this.serializer.attribute(BuildConfig.FLAVOR, NAME, PaymentConverter.DEFAULT_PAYMENT_METHOD_NAME);
        this.serializer.startTag(BuildConfig.FLAVOR, PAYMENT_SET_PAYPAL_EXPRESS_CHECKOUT);
        this.serializer.startTag(BuildConfig.FLAVOR, PAYMENT_CONFIRM_URL);
        this.serializer.text(SUCCESS_URL);
        this.serializer.endTag(BuildConfig.FLAVOR, PAYMENT_CONFIRM_URL);
        this.serializer.startTag(BuildConfig.FLAVOR, PAYMENT_CANCEL_URL);
        this.serializer.text(CANCEL_URL);
        this.serializer.endTag(BuildConfig.FLAVOR, PAYMENT_CANCEL_URL);
        this.serializer.endTag(BuildConfig.FLAVOR, PAYMENT_SET_PAYPAL_EXPRESS_CHECKOUT);
        this.serializer.endTag(BuildConfig.FLAVOR, ORDER_PAYMENT_METHOD);
    }

    private void addFeatures() throws IOException {
        if (this.features != null) {
            for (Feature feature : this.features) {
                this.serializer.startTag(BuildConfig.FLAVOR, ORDER_ORDER_ITEM);
                this.serializer.attribute(BuildConfig.FLAVOR, "target-id", this.adId);
                this.serializer.attribute(BuildConfig.FLAVOR, "target", PaymentConverter.AD);
                addFeature(feature);
                this.serializer.endTag(BuildConfig.FLAVOR, ORDER_ORDER_ITEM);
            }
        }
    }

    private void addFeature(Feature feature) throws IOException {
        this.serializer.startTag(BuildConfig.FLAVOR, ORDER_FEATURE_BOOKED);
        this.serializer.attribute(BuildConfig.FLAVOR, "group", PaymentConverter.STANDARD);
        this.serializer.attribute(BuildConfig.FLAVOR, NAME, BuildConfig.FLAVOR + feature.getName());
        this.serializer.startTag(BuildConfig.FLAVOR, FEAT_OPTION);
        this.serializer.startTag(BuildConfig.FLAVOR, FEAT_FEATURE_DURATION);
        this.serializer.startTag(BuildConfig.FLAVOR, TYPE_VALUE);
        this.serializer.text(BuildConfig.FLAVOR + feature.hasFeatureDurationOptionChecked().getDurationDays());
        this.serializer.endTag(BuildConfig.FLAVOR, TYPE_VALUE);
        this.serializer.startTag(BuildConfig.FLAVOR, TYPE_PERIOD);
        this.serializer.startTag(BuildConfig.FLAVOR, TYPE_VALUE);
        this.serializer.text(Period.DAY);
        this.serializer.endTag(BuildConfig.FLAVOR, TYPE_VALUE);
        this.serializer.endTag(BuildConfig.FLAVOR, TYPE_PERIOD);
        this.serializer.endTag(BuildConfig.FLAVOR, FEAT_FEATURE_DURATION);
        this.serializer.endTag(BuildConfig.FLAVOR, FEAT_OPTION);
        this.serializer.startTag(BuildConfig.FLAVOR, FEAT_FEATURE_PRICE);
        this.serializer.startTag(BuildConfig.FLAVOR, FEAT_CURRENCY_ISO_CODE);
        this.serializer.startTag(BuildConfig.FLAVOR, TYPE_VALUE);
        this.serializer.attribute(BuildConfig.FLAVOR, "localized-label", "\u00a3");
        this.serializer.text(PaymentConverter.DEFAULT_CURRENCY_ISO_CODE);
        this.serializer.endTag(BuildConfig.FLAVOR, TYPE_VALUE);
        this.serializer.endTag(BuildConfig.FLAVOR, FEAT_CURRENCY_ISO_CODE);
        this.serializer.startTag(BuildConfig.FLAVOR, FEAT_AMOUNT);
        this.serializer.text(BuildConfig.FLAVOR + feature.hasFeatureDurationOptionChecked().getPrice());
        this.serializer.endTag(BuildConfig.FLAVOR, FEAT_AMOUNT);
        this.serializer.endTag(BuildConfig.FLAVOR, FEAT_FEATURE_PRICE);
        this.serializer.endTag(BuildConfig.FLAVOR, ORDER_FEATURE_BOOKED);
    }

    private void setHeaderAttributes() throws IOException {
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:type", "http://www.ebayclassifiedsgroup.com/schema/types/v1");
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:feat", FeaturesApi.FEATURE_NAMESPACE);
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:order", OrderApi.ORDER_NAMESPACE);
        this.serializer.attribute(BuildConfig.FLAVOR, "xmlns:payment", OrderApi.PAYMENT_NAMESPACE);
    }
}
