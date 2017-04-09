package com.afollestad.materialdialogs.util;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.support.annotation.ColorInt;

@TargetApi(21)
public class RippleHelper {
    public static void applyColor(Drawable drawable, @ColorInt int i) {
        if (drawable instanceof RippleDrawable) {
            ((RippleDrawable) drawable).setColor(ColorStateList.valueOf(i));
        }
    }
}
