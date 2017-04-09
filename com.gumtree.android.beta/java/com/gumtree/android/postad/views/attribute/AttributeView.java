package com.gumtree.android.postad.views.attribute;

import java.util.Map;

public interface AttributeView {

    public interface OnFieldValueChangeListener {
        void onFieldValueChange(String str, String str2, String str3);
    }

    void doValidation();

    void setAttributeName(String str);

    void setAttributes(Map<String, String> map);

    void setEnabled(boolean z);

    void setOnFieldValueChangeListener(OnFieldValueChangeListener onFieldValueChangeListener);

    void setRequired(boolean z);

    void showTitle(String str);

    void showValue(String str, String str2);
}
