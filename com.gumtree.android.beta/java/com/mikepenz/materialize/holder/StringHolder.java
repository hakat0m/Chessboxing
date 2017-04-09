package com.mikepenz.materialize.holder;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.TextView;
import com.apptentive.android.sdk.BuildConfig;

public class StringHolder {
    private String mText;
    private int mTextRes = -1;

    public StringHolder(String str) {
        this.mText = str;
    }

    public StringHolder(@StringRes int i) {
        this.mTextRes = i;
    }

    public String getText() {
        return this.mText;
    }

    public void setText(String str) {
        this.mText = str;
    }

    public int getTextRes() {
        return this.mTextRes;
    }

    public void setTextRes(int i) {
        this.mTextRes = i;
    }

    public void applyTo(TextView textView) {
        if (this.mText != null) {
            textView.setText(this.mText);
        } else if (this.mTextRes != -1) {
            textView.setText(this.mTextRes);
        } else {
            textView.setText(BuildConfig.FLAVOR);
        }
    }

    public boolean applyToOrHide(TextView textView) {
        if (this.mText != null) {
            textView.setText(this.mText);
            textView.setVisibility(0);
            return true;
        } else if (this.mTextRes != -1) {
            textView.setText(this.mTextRes);
            textView.setVisibility(0);
            return true;
        } else {
            textView.setVisibility(8);
            return false;
        }
    }

    public String getText(Context context) {
        if (this.mText != null) {
            return this.mText;
        }
        if (this.mTextRes != -1) {
            return context.getString(this.mTextRes);
        }
        return null;
    }

    public static void applyTo(StringHolder stringHolder, TextView textView) {
        if (stringHolder != null && textView != null) {
            stringHolder.applyTo(textView);
        }
    }

    public static boolean applyToOrHide(StringHolder stringHolder, TextView textView) {
        if (stringHolder != null && textView != null) {
            return stringHolder.applyToOrHide(textView);
        }
        if (textView == null) {
            return false;
        }
        textView.setVisibility(8);
        return false;
    }

    public String toString() {
        return this.mText;
    }
}
