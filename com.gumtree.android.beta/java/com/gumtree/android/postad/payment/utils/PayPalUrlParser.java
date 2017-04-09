package com.gumtree.android.postad.payment.utils;

import com.gumtree.Log;
import java.net.URL;

public class PayPalUrlParser {
    private static final String CANCEL_URL = "www.cancel.com";
    private static final String END_INDEX = "&";
    private static final String ERROR_PARSING_PAY_PAL_URL = "Error parsing PayPal url";
    private static final String START_INDEX = "tx=";
    private static final String SUCCESS_URL = "www.success.com";

    public String getTransactionId(String str) {
        String str2 = null;
        try {
            String query = new URL(str).getQuery();
            if (query != null) {
                int indexOf = query.indexOf(START_INDEX);
                if (indexOf != -1) {
                    indexOf += START_INDEX.length();
                    int indexOf2 = query.indexOf(END_INDEX, indexOf);
                    if (indexOf2 != -1) {
                        str2 = query.substring(indexOf, indexOf2);
                    }
                }
            }
        } catch (Throwable e) {
            Log.e(PayPalUrlParser.class.getSimpleName(), ERROR_PARSING_PAY_PAL_URL, e);
        }
        return str2;
    }

    public boolean isSuccessUrl(String str) {
        try {
            String host = new URL(str).getHost();
            if (host != null) {
                return host.equals(SUCCESS_URL);
            }
        } catch (Throwable e) {
            Log.e(PayPalUrlParser.class.getSimpleName(), ERROR_PARSING_PAY_PAL_URL, e);
        }
        return false;
    }

    public boolean isCancelUrl(String str) {
        try {
            String host = new URL(str).getHost();
            if (host != null) {
                return host.equals(CANCEL_URL);
            }
        } catch (Throwable e) {
            Log.e(PayPalUrlParser.class.getSimpleName(), ERROR_PARSING_PAY_PAL_URL, e);
        }
        return false;
    }
}
