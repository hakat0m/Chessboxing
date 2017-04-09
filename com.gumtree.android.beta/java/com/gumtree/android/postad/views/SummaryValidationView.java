package com.gumtree.android.postad.views;

public interface SummaryValidationView {
    void reset();

    void showError(String str);

    void showTitle(String str);

    void showValid(boolean z);

    void showValue(String str);
}
