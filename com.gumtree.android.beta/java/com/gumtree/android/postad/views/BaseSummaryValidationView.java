package com.gumtree.android.postad.views;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.LinearLayout;

public abstract class BaseSummaryValidationView extends LinearLayout {
    private static final String KEY_ERROR = "key_error";
    private static final String KEY_FOCUS = "key_focus";
    private static final String KEY_TITLE = "key_title";
    private static final String KEY_VALID = "key_valid";
    private static final String KEY_VALUE = "key_value";
    private String errorMessage;
    private boolean isValid;
    private String title;
    private String value;

    protected abstract void restoreError(String str);

    protected abstract void restoreFocus(boolean z);

    protected abstract void restoreTitle(String str);

    protected abstract void restoreValid(boolean z);

    protected abstract void restoreValue(String str);

    public BaseSummaryValidationView(Context context) {
        super(context);
    }

    public BaseSummaryValidationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BaseSummaryValidationView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.state = savedState.state == null ? new Bundle() : savedState.state;
        saveState(savedState.state);
        return savedState;
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setContentFromStateBundle(savedState.state);
    }

    protected void dispatchSaveInstanceState(SparseArray sparseArray) {
        super.dispatchFreezeSelfOnly(sparseArray);
    }

    protected void dispatchRestoreInstanceState(SparseArray sparseArray) {
        super.dispatchThawSelfOnly(sparseArray);
    }

    protected void saveState(Bundle bundle) {
        bundle.putString(KEY_TITLE, this.title);
        bundle.putString(KEY_VALUE, this.value);
        bundle.putString(KEY_ERROR, this.errorMessage);
        bundle.putBoolean(KEY_VALID, this.isValid);
        bundle.putBoolean(KEY_FOCUS, hasFocus());
    }

    protected void setContentFromStateBundle(Bundle bundle) {
        this.title = bundle.getString(KEY_TITLE);
        restoreTitle(this.title);
        this.value = bundle.getString(KEY_VALUE);
        restoreValue(this.value);
        this.errorMessage = bundle.getString(KEY_ERROR);
        restoreError(this.errorMessage);
        this.isValid = bundle.getBoolean(KEY_VALID);
        restoreValid(this.isValid);
        restoreFocus(bundle.getBoolean(KEY_FOCUS));
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String str) {
        this.errorMessage = str;
    }

    public boolean isValid() {
        return this.isValid;
    }

    public void setIsValid(boolean z) {
        this.isValid = z;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }
}
