package com.gumtree.android.postad.services.converter;

import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.ads.CurrencyIsoCode;
import com.ebay.classifieds.capi.features.ApplyFeaturePayload;
import com.ebay.classifieds.capi.features.Feature.Type;
import com.ebay.classifieds.capi.features.FeatureBooked;
import com.ebay.classifieds.capi.features.FeatureDuration;
import com.ebay.classifieds.capi.features.FeaturesBooked;
import com.ebay.classifieds.capi.features.Option;
import com.ebay.classifieds.capi.features.PaymentAuthorization;
import com.ebay.classifieds.capi.features.Period;
import com.ebay.classifieds.capi.features.Price;
import com.ebay.classifieds.capi.order.OrderItem;
import com.ebay.classifieds.capi.order.OrderItems;
import com.ebay.classifieds.capi.order.OrderPayload;
import com.ebay.classifieds.capi.order.OrderPayment;
import com.ebay.classifieds.capi.order.OrderResponse;
import com.ebay.classifieds.capi.order.PaymentMethod;
import com.ebay.classifieds.capi.order.PaypalExpressCheckout;
import com.gumtree.android.postad.DraftOption;
import com.gumtree.android.postad.PaymentData;
import com.gumtree.android.postad.PaymentData.InvoiceData;
import com.gumtree.android.postad.payment.models.OrderData;
import com.gumtree.android.postad.promote.PromotionFeature;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.lang3.Validate;

public class PaymentConverter {
    public static final String AD = "ad";
    public static final String DEFAULT_CURRENCY_ISO_CODE = "GBP";
    public static final String DEFAULT_PAYMENT_CANCEL_URL = "https://www.cancel.com/";
    public static final String DEFAULT_PAYMENT_CONFIRM_URL = "https://www.success.com/";
    public static final String DEFAULT_PAYMENT_METHOD_ID = "1";
    public static final String DEFAULT_PAYMENT_METHOD_NAME = "paypal pro express checkout";
    public static final String DEFAULT_PAYMENT_METHOD_TYPE = "paypal-pro-express-checkout";
    public static final String STANDARD = "standard";
    private final DraftFeatureTypeConverter draftFeatureTypeConverter;

    @Inject
    public PaymentConverter(@NonNull DraftFeatureTypeConverter draftFeatureTypeConverter) {
        this.draftFeatureTypeConverter = (DraftFeatureTypeConverter) Validate.notNull(draftFeatureTypeConverter);
    }

    public OrderPayload orderPaymentModelToOrderApi(@NonNull OrderData orderData) {
        Validate.notNull(orderData);
        String adId = orderData.getAdId();
        List<PromotionFeature> promotionFeatures = orderData.getPromotionFeatures();
        List arrayList = new ArrayList();
        for (PromotionFeature promotionFeature : promotionFeatures) {
            DraftOption highlighted = promotionFeature.getHighlighted();
            Type modelToApi = this.draftFeatureTypeConverter.modelToApi(promotionFeature.getFeature().getType());
            String value = modelToApi == null ? null : modelToApi.getValue();
            if (highlighted != null) {
                arrayList.add(OrderItem.builder().targetId(adId).target(AD).featureBooked(FeatureBooked.builder().group(STANDARD).name(value).option(Option.builder().featureDuration(new FeatureDuration(highlighted.getDuration(), new Period(highlighted.getPeriod().name()))).build()).featurePrice(new Price(highlighted.getAmount(), new CurrencyIsoCode(highlighted.getCurrencyCode()), null)).build()).build());
            }
        }
        return OrderPayload.builder().orderItems(new OrderItems(arrayList)).paymentMethod(PaymentMethod.builder().id(DEFAULT_PAYMENT_METHOD_ID).type(DEFAULT_PAYMENT_METHOD_TYPE).name(DEFAULT_PAYMENT_METHOD_NAME).paypalExpressCheckout(PaypalExpressCheckout.builder().confirmURL(DEFAULT_PAYMENT_CONFIRM_URL).cancelURL(DEFAULT_PAYMENT_CANCEL_URL).build()).build()).build();
    }

    public PaymentData orderApiResponseToOrderModel(@NonNull OrderResponse orderResponse) {
        Validate.notNull(orderResponse);
        OrderPayment payment = orderResponse.getPayment();
        return PaymentData.builder().orderId(orderResponse.getOrderId()).paymentProvider(payment.getPaymentProvider()).redirectUrl(payment.getRedirectUrl()).invoiceData(InvoiceData.builder().totalPrice(orderResponse.getInvoice().getTotalPrice().getAmount()).totalPriceCurrency(orderResponse.getInvoice().getTotalPrice().getCurrencyIsoCode().getValue()).taxAmount(orderResponse.getInvoice().getTax().getAmount()).taxAmountCurrency(orderResponse.getInvoice().getTax().getCurrencyIsoCode().getValue()).build()).paymentId(payment.getPaymentId()).build();
    }

    public ApplyFeaturePayload applyFeePaymentModelToFeaturesApi(OrderData orderData, PaymentData paymentData) {
        List<PromotionFeature> promotionFeatures = orderData.getPromotionFeatures();
        List arrayList = new ArrayList();
        for (PromotionFeature promotionFeature : promotionFeatures) {
            Option build;
            DraftOption highlighted = promotionFeature.getHighlighted();
            Type modelToApi = this.draftFeatureTypeConverter.modelToApi(promotionFeature.getFeature().getType());
            String value = modelToApi == null ? null : modelToApi.getValue();
            if (highlighted != null) {
                build = Option.builder().featureDuration(new FeatureDuration(highlighted.getDuration(), new Period(highlighted.getPeriod().name()))).price(new Price(highlighted.getAmount(), new CurrencyIsoCode(highlighted.getCurrencyCode()), null)).build();
            } else {
                build = null;
            }
            arrayList.add(FeatureBooked.builder().group(STANDARD).name(value).option(build).build());
        }
        return ApplyFeaturePayload.builder().featuresBooked(new FeaturesBooked(arrayList)).paymentAuthorization(new PaymentAuthorization(paymentData.getPaymentId(), paymentData.getPaymentProvider())).build();
    }
}
