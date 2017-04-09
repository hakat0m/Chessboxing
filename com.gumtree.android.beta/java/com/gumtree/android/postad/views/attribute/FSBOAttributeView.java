package com.gumtree.android.postad.views.attribute;

public interface FSBOAttributeView extends AttributeView {

    public interface PopupListener {
        void popupShown();
    }

    void setDialogAttributeValue(String str);

    void setDialogBody(String str);

    void setDialogTitle(String str);

    void setPopupAttributeValue(String str);

    void setPopupListener(PopupListener popupListener);

    void setPopupShown(boolean z);

    void setPopupText(String str);
}
