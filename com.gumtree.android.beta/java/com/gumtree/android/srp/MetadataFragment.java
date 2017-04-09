package com.gumtree.android.srp;

import com.gumtree.android.common.views.fields.BaseField;

public interface MetadataFragment {
    void doSearch();

    void manipulateView(String str, BaseField baseField);

    void updateCategoryPicker(String str, String str2);
}
