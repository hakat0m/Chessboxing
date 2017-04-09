package com.gumtree.android.features;

import com.gumtree.android.features.parser.PaypalUrlParser;
import com.gumtree.android.postad.services.converter.PaymentConverter;
import java.io.Serializable;
import java.util.regex.Pattern;

public class OrderConfirmation implements Serializable {
    private static final String EMAIL = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+";
    private static final Pattern EMAIL_MATCHER = Pattern.compile(EMAIL);
    private static final long serialVersionUID = 1;
    private String adId;
    private String error;
    private boolean hasError;
    private Long orderId;
    private String payPalReturnUrl;
    private String payPalUrl;
    private String provider;
    private Double taxAmount;
    private Double total;

    public boolean hasError() {
        return this.hasError;
    }

    public String getAdId() {
        return this.adId;
    }

    public void setAdId(String str) {
        this.adId = str;
    }

    public String getPayPalUrl() {
        return this.payPalUrl;
    }

    public void setPayPalUrl(String str) {
        this.payPalUrl = str;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String str) {
        if (str != null) {
            this.hasError = true;
            this.error = str;
        }
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setOrderId(Long l) {
        this.orderId = l;
    }

    public Double getTotal() {
        return this.total;
    }

    public void setTotal(Double d) {
        this.total = d;
    }

    public Double getTaxAmount() {
        return this.taxAmount;
    }

    public void setTax(Double d) {
        this.taxAmount = d;
    }

    public boolean isHtmlPost() {
        if (this.payPalUrl == null || this.payPalUrl.length() == 0) {
            return false;
        }
        return EMAIL_MATCHER.matcher(this.payPalUrl).matches();
    }

    public String getProvider() {
        return this.provider;
    }

    public void setProvider(String str) {
        this.provider = str;
    }

    public String getPayPalReturnUrl() {
        return this.payPalReturnUrl;
    }

    public void setPayPalReturnUrl(String str) {
        this.payPalReturnUrl = str;
    }

    public String getHtmlForm(String str) {
        return "<html><body><form action=\"https://securepayments.paypal.com/webapps/HostedSoleSolutionApp/webflow/sparta/hostedSoleSolutionProcess\" method=\"post\" style=\"display:none\" name=\"form_iframe\"><input type=\"hidden\" name=\"cmd\" value=\"_hosted-payment\"><input type=\"hidden\" name=\"paymentaction\" value=\"sale\"><input type=\"hidden\" name=\"template\" value=\"mobile-iframe\"><input type=\"hidden\" name=\"currency_code\" value=\"GBP\"><input type=\"hidden\" name=\"showBillingAddress\" value=\"false\"><input type=\"hidden\" name=\"showShippingAddress\" value=\"false\"><input type=\"hidden\" name=\"billing_first_name\" value=\"\"><input type=\"hidden\" name=\"billing_last_name\" value=\"\"><input type=\"hidden\" name=\"billing_country\" value=\"GB\"><input type=\"hidden\" name=\"business\" value=\"" + this.payPalUrl + "\">" + "<input type=\"hidden\" name=\"showHostedThankyouPage\" value=\"false\">" + "<input type=\"hidden\" name=\"return\" value=\"" + PaymentConverter.DEFAULT_PAYMENT_CONFIRM_URL + "\">" + "<input type=\"hidden\" name=\"cancel_return\" value=\"" + PaymentConverter.DEFAULT_PAYMENT_CANCEL_URL + "\">" + "<input type=\"hidden\" name=\"tax\" value=\"" + this.taxAmount + "\">" + "<input type=\"hidden\" name=\"subtotal\" value=\"" + getSubtotal() + "\">" + "<input type=\"hidden\" name=\"custom\" value=\"" + str + "\">" + "<input type=\"hidden\" name=\"invoice\" value=\"" + this.orderId + "\">" + "<input type=\"submit\">" + "</form>" + "</body>" + "</html>" + "<script type=\"text/javascript\">" + "document.form_iframe.submit();" + "</script>";
    }

    private double getSubtotal() {
        double doubleValue;
        double d = 0.0d;
        if (this.total != null) {
            doubleValue = this.total.doubleValue();
        } else {
            doubleValue = 0.0d;
        }
        if (this.taxAmount != null) {
            d = this.taxAmount.doubleValue();
        }
        return doubleValue - d;
    }

    public String getTransactionId() {
        return new PaypalUrlParser().getTransactionId(this.payPalReturnUrl);
    }

    public String toString() {
        return "OrderConfirmation{provider='" + this.provider + '\'' + ", taxAmount=" + this.taxAmount + ", total=" + this.total + ", orderId=" + this.orderId + ", adId='" + this.adId + '\'' + ", payPalUrl='" + this.payPalUrl + '\'' + ", payPalReturnUrl='" + this.payPalReturnUrl + '\'' + '}';
    }
}
