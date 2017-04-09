package com.gumtree.android.postad.views.attribute;

import com.gumtree.android.postad.views.attribute.AttributeView.OnFieldValueChangeListener;

public interface VRMWrapperAttributeView extends AttributeView {
    String getVRMValue();

    void registrationNotRecognised();

    void setVRMOnFieldValueChangeListener(OnFieldValueChangeListener onFieldValueChangeListener);
}
