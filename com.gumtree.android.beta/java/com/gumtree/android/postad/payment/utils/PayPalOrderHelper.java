package com.gumtree.android.postad.payment.utils;

import android.support.annotation.NonNull;
import java.util.regex.Pattern;
import org.apache.commons.lang3.Validate;

public class PayPalOrderHelper {
    private static final String CANCEL_URL = "https://www.cancel.com/";
    private static final String EMAIL = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+";
    private static final Pattern EMAIL_MATCHER = Pattern.compile(EMAIL);
    private static final String SUCCESS_URL = "https://www.success.com/";
    public static final String TEXT_HTML_CHARSET_UTF_8 = "text/html; charset=UTF-8";

    public boolean isEmail(@NonNull String str) {
        Validate.notNull(str);
        return EMAIL_MATCHER.matcher(str).matches();
    }

    public String generateHtmlPost(String str, double d, double d2, String str2, String str3) {
        return "<html><body><form action=\"https://securepayments.paypal.com/webapps/HostedSoleSolutionApp/webflow/sparta/hostedSoleSolutionProcess\" method=\"post\" style=\"display:none\" name=\"form_iframe\"><input type=\"hidden\" name=\"cmd\" value=\"_hosted-payment\"><input type=\"hidden\" name=\"paymentaction\" value=\"sale\"><input type=\"hidden\" name=\"template\" value=\"mobile-iframe\"><input type=\"hidden\" name=\"currency_code\" value=\"GBP\"><input type=\"hidden\" name=\"showBillingAddress\" value=\"false\"><input type=\"hidden\" name=\"showShippingAddress\" value=\"false\"><input type=\"hidden\" name=\"billing_first_name\" value=\"\"><input type=\"hidden\" name=\"billing_last_name\" value=\"\"><input type=\"hidden\" name=\"billing_country\" value=\"GB\"><input type=\"hidden\" name=\"business\" value=\"" + str + "\">" + "<input type=\"hidden\" name=\"showHostedThankyouPage\" value=\"false\">" + "<input type=\"hidden\" name=\"return\" value=\"" + SUCCESS_URL + "\">" + "<input type=\"hidden\" name=\"cancel_return\" value=\"" + CANCEL_URL + "\">" + "<input type=\"hidden\" name=\"tax\" value=\"" + d + "\">" + "<input type=\"hidden\" name=\"subtotal\" value=\"" + d2 + "\">" + "<input type=\"hidden\" name=\"custom\" value=\"" + str2 + "\">" + "<input type=\"hidden\" name=\"invoice\" value=\"" + str3 + "\">" + "<input type=\"submit\">" + "</form>" + "</body>" + "</html>" + "<script type=\"text/javascript\">" + "document.form_iframe.submit();" + "</script>";
    }
}
