package com.gumtree.android.features.parser;

import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.common.gson.Parser;
import com.gumtree.android.common.utils.XmlStringUtils;
import com.gumtree.android.features.OrderConfirmation;
import java.io.InputStream;

public class OrderConfirmationParser implements Parser<OrderConfirmation> {
    private static final String END_TAG_ADID = " target=\"ad\">";
    private static final String END_TAG_AMOUNT = "</order:amount>";
    private static final String END_TAG_ORDERID = ">";
    private static final String END_TAG_PAYMENT = "</payment:redirect-url>";
    private static final String END_TAG_PAYMENT_PROVIDER = "</payment:provider>";
    private static final String END_TAG_TAX = "</order:tax>";
    private static final String END_TAG_TOTAL = "</order:total-price>";
    private static final String MESSAGE = "<message>";
    private static final String STARTING_TAG_ADID = "<order:order-item target-id=";
    private static final String STARTING_TAG_AMOUNT = "<order:amount>";
    private static final String STARTING_TAG_ORDERID = "<order:payment id=";
    private static final String STARTING_TAG_PAYMENT = "<payment:redirect-url>";
    private static final String STARTING_TAG_PAYMENT_PROVIDER = "<payment:provider>";
    private static final String STARTING_TAG_TAX = "<order:tax>";
    private static final String STARTING_TAG_TOTAL = "<order:total-price>";
    private static final String TARGET = "\"";
    private static final String UNKNOWN_ERROR = "Unknown error";

    public OrderConfirmation parse(InputStream inputStream) {
        OrderConfirmation orderConfirmation = new OrderConfirmation();
        String xmlStringUtils = XmlStringUtils.toString(inputStream);
        if (inputStream == null || xmlStringUtils == null) {
            orderConfirmation.setError(UNKNOWN_ERROR);
        } else if (xmlStringUtils.contains("api-base-error")) {
            orderConfirmation.setError(getError(xmlStringUtils));
        } else {
            String payPalUrl = getPayPalUrl(xmlStringUtils);
            String paymentProvider = getPaymentProvider(xmlStringUtils);
            Long orderId = getOrderId(xmlStringUtils);
            Double total = getTotal(xmlStringUtils);
            Double taxAmount = getTaxAmount(xmlStringUtils);
            xmlStringUtils = getAdId(xmlStringUtils);
            if (payPalUrl == null && "paypal".equalsIgnoreCase(paymentProvider)) {
                orderConfirmation.setError(UNKNOWN_ERROR);
            } else {
                orderConfirmation.setProvider(paymentProvider);
                orderConfirmation.setAdId(xmlStringUtils);
                orderConfirmation.setPayPalUrl(payPalUrl);
                orderConfirmation.setOrderId(orderId);
                orderConfirmation.setTotal(total);
                orderConfirmation.setTax(taxAmount);
            }
        }
        return orderConfirmation;
    }

    private String getPaymentProvider(String str) {
        return getValueFromResponse(str, STARTING_TAG_PAYMENT_PROVIDER, END_TAG_PAYMENT_PROVIDER);
    }

    private String getAdId(String str) {
        String valueFromResponse = getValueFromResponse(str, STARTING_TAG_ADID, END_TAG_ADID);
        if (valueFromResponse == null || valueFromResponse.length() <= 0) {
            return valueFromResponse;
        }
        return valueFromResponse.replace(TARGET, BuildConfig.FLAVOR);
    }

    private Double getTaxAmount(String str) {
        String valueFromResponse = getValueFromResponse(getValueFromResponse(str, STARTING_TAG_TAX, END_TAG_TAX), STARTING_TAG_AMOUNT, END_TAG_AMOUNT);
        if (valueFromResponse == null || valueFromResponse.length() <= 0) {
            return null;
        }
        return Double.valueOf(Double.parseDouble(valueFromResponse));
    }

    private Double getTotal(String str) {
        String valueFromResponse = getValueFromResponse(getValueFromResponse(str, STARTING_TAG_TOTAL, END_TAG_TOTAL), STARTING_TAG_AMOUNT, END_TAG_AMOUNT);
        if (valueFromResponse == null || valueFromResponse.length() <= 0) {
            return null;
        }
        return Double.valueOf(Double.parseDouble(valueFromResponse));
    }

    private Long getOrderId(String str) {
        String valueFromResponse = getValueFromResponse(str, STARTING_TAG_ORDERID, END_TAG_ORDERID);
        if (valueFromResponse == null || valueFromResponse.length() <= 0) {
            return null;
        }
        return Long.valueOf(Long.parseLong(valueFromResponse.replace(TARGET, BuildConfig.FLAVOR)));
    }

    private String getPayPalUrl(String str) {
        String valueFromResponse = getValueFromResponse(str, STARTING_TAG_PAYMENT, END_TAG_PAYMENT);
        if (valueFromResponse != null) {
            return valueFromResponse.replace("&amp;", "&");
        }
        return valueFromResponse;
    }

    private String getValueFromResponse(String str, String str2, String str3) {
        String str4 = null;
        try {
            int indexOf = str.indexOf(str2);
            int indexOf2 = str.indexOf(str3, str2.length() + indexOf);
            if (!(indexOf == -1 || indexOf2 == -1)) {
                str4 = str.substring(indexOf + str2.length(), indexOf2);
            }
        } catch (NullPointerException e) {
        } catch (IndexOutOfBoundsException e2) {
        }
        return str4;
    }

    private String getError(String str) {
        String firstMessage = getFirstMessage(str, 0);
        if ("An unexpected error has occurred".equals(firstMessage)) {
            String fieldMessage = getFieldMessage(str);
            if (fieldMessage != null && fieldMessage.length() > 0) {
                return fieldMessage;
            }
        }
        return firstMessage;
    }

    private String getFieldMessage(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        return getFirstMessage(str, str.indexOf("<api-field-errors>"));
    }

    private String getFirstMessage(String str, int i) {
        int indexOf = str.indexOf(MESSAGE, i);
        int indexOf2 = str.indexOf("</message>", indexOf);
        if (indexOf == -1 || indexOf2 == -1) {
            return null;
        }
        return str.substring(MESSAGE.length() + indexOf, indexOf2);
    }
}
