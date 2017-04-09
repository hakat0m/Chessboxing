package com.mikepenz.materialdrawer.holder;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.widget.TextView;
import com.mikepenz.materialdrawer.R;
import com.mikepenz.materialdrawer.model.utils.BadgeDrawableBuilder;
import com.mikepenz.materialize.util.UIUtils;

public class BadgeStyle {
    private Drawable mBadgeBackground;
    private ColorHolder mColor;
    private ColorHolder mColorPressed;
    private DimenHolder mCorners;
    private int mGradientDrawable = R.drawable.material_drawer_badge;
    private DimenHolder mMinWidth = DimenHolder.fromDp(20);
    private DimenHolder mPaddingLeftRight = DimenHolder.fromDp(3);
    private DimenHolder mPaddingTopBottom = DimenHolder.fromDp(2);
    private ColorHolder mTextColor;

    public int getGradientDrawable() {
        return this.mGradientDrawable;
    }

    public BadgeStyle withGradientDrawable(@DrawableRes int i) {
        this.mGradientDrawable = i;
        this.mBadgeBackground = null;
        return this;
    }

    public Drawable getBadgeBackground() {
        return this.mBadgeBackground;
    }

    public BadgeStyle withBadgeBackground(Drawable drawable) {
        this.mBadgeBackground = drawable;
        this.mGradientDrawable = -1;
        return this;
    }

    public ColorHolder getColor() {
        return this.mColor;
    }

    public BadgeStyle withColor(@ColorInt int i) {
        this.mColor = ColorHolder.fromColor(i);
        return this;
    }

    public BadgeStyle withColorRes(@ColorRes int i) {
        this.mColor = ColorHolder.fromColorRes(i);
        return this;
    }

    public ColorHolder getColorPressed() {
        return this.mColorPressed;
    }

    public BadgeStyle withColorPressed(@ColorInt int i) {
        this.mColorPressed = ColorHolder.fromColor(i);
        return this;
    }

    public BadgeStyle withColorPressedRes(@ColorRes int i) {
        this.mColorPressed = ColorHolder.fromColorRes(i);
        return this;
    }

    public ColorHolder getTextColor() {
        return this.mTextColor;
    }

    public BadgeStyle withTextColor(@ColorInt int i) {
        this.mTextColor = ColorHolder.fromColor(i);
        return this;
    }

    public BadgeStyle withTextColorRes(@ColorRes int i) {
        this.mTextColor = ColorHolder.fromColorRes(i);
        return this;
    }

    public DimenHolder getCorners() {
        return this.mCorners;
    }

    public BadgeStyle withCorners(int i) {
        this.mCorners = DimenHolder.fromPixel(i);
        return this;
    }

    public BadgeStyle withCornersDp(int i) {
        this.mCorners = DimenHolder.fromDp(i);
        return this;
    }

    public DimenHolder getPaddingLeftRight() {
        return this.mPaddingLeftRight;
    }

    public BadgeStyle withPaddingLeftRightPx(int i) {
        this.mPaddingLeftRight = DimenHolder.fromPixel(i);
        return this;
    }

    public BadgeStyle withPaddingLeftRightDp(int i) {
        this.mPaddingLeftRight = DimenHolder.fromDp(i);
        return this;
    }

    public DimenHolder getPaddingTopBottom() {
        return this.mPaddingTopBottom;
    }

    public BadgeStyle withPaddingTopBottomPx(int i) {
        this.mPaddingTopBottom = DimenHolder.fromPixel(i);
        return this;
    }

    public BadgeStyle withPaddingTopBottomDp(int i) {
        this.mPaddingTopBottom = DimenHolder.fromDp(i);
        return this;
    }

    public BadgeStyle withPadding(int i) {
        this.mPaddingLeftRight = DimenHolder.fromPixel(i);
        this.mPaddingTopBottom = DimenHolder.fromPixel(i);
        return this;
    }

    public DimenHolder getMinWidth() {
        return this.mMinWidth;
    }

    public BadgeStyle withMinWidth(int i) {
        this.mMinWidth = DimenHolder.fromPixel(i);
        return this;
    }

    public BadgeStyle(@ColorInt int i, @ColorInt int i2) {
        this.mColor = ColorHolder.fromColor(i);
        this.mColorPressed = ColorHolder.fromColor(i2);
    }

    public BadgeStyle(@DrawableRes int i, @ColorInt int i2, @ColorInt int i3, @ColorInt int i4) {
        this.mGradientDrawable = i;
        this.mColor = ColorHolder.fromColor(i2);
        this.mColorPressed = ColorHolder.fromColor(i3);
        this.mTextColor = ColorHolder.fromColor(i4);
    }

    public void style(TextView textView) {
        style(textView, null);
    }

    public void style(TextView textView, ColorStateList colorStateList) {
        Context context = textView.getContext();
        if (this.mBadgeBackground == null) {
            UIUtils.setBackground(textView, new BadgeDrawableBuilder(this).build(context));
        } else {
            UIUtils.setBackground(textView, this.mBadgeBackground);
        }
        if (this.mTextColor != null) {
            ColorHolder.applyToOr(this.mTextColor, textView, null);
        } else if (colorStateList != null) {
            textView.setTextColor(colorStateList);
        }
        int asPixel = this.mPaddingLeftRight.asPixel(context);
        int asPixel2 = this.mPaddingTopBottom.asPixel(context);
        textView.setPadding(asPixel, asPixel2, asPixel, asPixel2);
        textView.setMinWidth(this.mMinWidth.asPixel(context));
    }
}
