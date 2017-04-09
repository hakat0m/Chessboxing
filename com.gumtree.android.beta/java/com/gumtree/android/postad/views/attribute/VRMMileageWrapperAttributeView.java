package com.gumtree.android.postad.views.attribute;

import com.gumtree.android.postad.views.attribute.AttributeView.OnFieldValueChangeListener;

public interface VRMMileageWrapperAttributeView extends AttributeView {

    public interface VRMLookupRequestListener {
        void requestVRMLookup(String str);
    }

    public interface VRMManualEntryListener {
        void onManualEntry();
    }

    OnFieldValueChangeListener getVRMOnFieldValueChangeListener();

    void hideManualEntry();

    void setVRMLookupRequestListener(VRMLookupRequestListener vRMLookupRequestListener);

    void setVRMManualEntryListener(VRMManualEntryListener vRMManualEntryListener);

    void setVRMValue(String str);
}
