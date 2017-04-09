package com.gumtree.android.common.utils;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.postad.DraftAd;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Set;

public class AppUtils {
    private static final String ELLIPSIZE = "...";
    private static final int FOUR = 4;
    private static final String MONTHLY = "MONTHLY";
    private static final String NON_THIN = "[^iIl1\\.,']";
    private static final String SPACE = " ";
    private static final int THREE = 3;
    private static final String WEEKLY = "WEEKLY";

    private static int textWidth(String str) {
        return str.length() - (str.replaceAll(NON_THIN, BuildConfig.FLAVOR).length() / 2);
    }

    public static String ellipsize(String str, int i) {
        if (textWidth(str) <= i) {
            return str;
        }
        int lastIndexOf = str.lastIndexOf(32, i - 3);
        if (lastIndexOf == -1) {
            return str.substring(0, i - 3) + ELLIPSIZE;
        }
        while (true) {
            int indexOf = str.indexOf(32, lastIndexOf + 1);
            if (indexOf == -1) {
                indexOf = str.length();
            }
            if (textWidth(str.substring(0, indexOf) + ELLIPSIZE) >= i) {
                return str.substring(0, lastIndexOf) + ELLIPSIZE;
            }
            lastIndexOf = indexOf;
        }
    }

    public static boolean isPhoneNumber(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        if (str.startsWith(DraftAd.NEW_AD_ID)) {
            str = str.substring(1);
        }
        try {
            Long.parseLong(str.replace(SPACE, BuildConfig.FLAVOR).replace("+", BuildConfig.FLAVOR));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isCellphoneNumber(String str) {
        if (isPhoneNumber(str)) {
            String replace = str.replace(SPACE, BuildConfig.FLAVOR);
            if (replace.startsWith("0044")) {
                replace = replace.substring(FOUR);
            } else if (replace.startsWith("044")) {
                replace = replace.substring(THREE);
            } else if (replace.startsWith("+44")) {
                replace = replace.substring(THREE);
            } else if (replace.startsWith("44")) {
                replace = replace.substring(2);
            } else {
                replace = replace.substring(1);
            }
            if (replace.startsWith("7")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmail(String str) {
        if (str.contains("@") && str.contains(".")) {
            return true;
        }
        return false;
    }

    public static String getFormattedPrice(String str) {
        if (!(str.contains("\u00a3") || TextUtils.isEmpty(str))) {
            try {
                str = NumberFormat.getCurrencyInstance(GumtreeApplication.getAPI().getLocale()).format(Double.parseDouble(str)).replace(".00", BuildConfig.FLAVOR);
            } catch (NumberFormatException e) {
            }
        }
        return str;
    }

    public static String getFormattedNumber(int i) {
        return NumberFormat.getInstance(GumtreeApplication.getAPI().getLocale()).format((long) i);
    }

    public static String[] getListOfKeyNames(Bundle bundle) {
        Set keySet = bundle.keySet();
        return (String[]) keySet.toArray(new String[keySet.size()]);
    }

    public static boolean hasPhoneAbility(Context context) {
        if (((TelephonyManager) context.getSystemService("phone")).getPhoneType() == 0) {
            return false;
        }
        return true;
    }

    public static double round(double d, int i, int i2) {
        return new BigDecimal(d).setScale(i, i2).doubleValue();
    }

    public static String round(double d) {
        return String.format(Locale.getDefault(), "%.2f", new Object[]{Double.valueOf(round(d, 2, FOUR))});
    }

    public static String formatPriceFrequency(Context context, String str) {
        String str2 = BuildConfig.FLAVOR;
        if (str == null || str.isEmpty()) {
            return str2;
        }
        if (WEEKLY.equals(str)) {
            return context.getString(2131165885);
        }
        if (MONTHLY.equals(str)) {
            return context.getString(2131165884);
        }
        return str2;
    }

    public static boolean isTablet() {
        return GumtreeApplication.getContext().getResources().getBoolean(2131427332);
    }

    public static String getOrientation() {
        int i = GumtreeApplication.getContext().getResources().getConfiguration().orientation;
        if (i == 2) {
            return "landscape";
        }
        if (i == 1) {
            return "portrait";
        }
        return "undefined";
    }
}
