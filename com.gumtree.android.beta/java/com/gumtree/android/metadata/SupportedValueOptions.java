package com.gumtree.android.metadata;

import java.io.Serializable;

public class SupportedValueOptions implements Serializable {
    private boolean isDisabled;
    private boolean isSelected;
    private String linkBodyButtonsActions;
    private String linkBodyButtonsLabels;
    private String linkBodyText;
    private String linkTitle;
    private String popupBodyButtonsActions;
    private String popupBodyButtonsLabels;
    private String popupBodyText;
    private String supportedValue;

    public SupportedValueOptions(String str) {
        this.supportedValue = str;
    }

    public String getSupportedValue() {
        return this.supportedValue;
    }

    public void setSupportedValue(String str) {
        this.supportedValue = str;
    }

    public String getLinkTitle() {
        return this.linkTitle;
    }

    public void setLinkTitle(String str) {
        this.linkTitle = str;
    }

    public String getLinkBodyText() {
        return this.linkBodyText;
    }

    public void setLinkBodyText(String str) {
        this.linkBodyText = str;
    }

    public String getLinkBodyButtonsLabels() {
        return this.linkBodyButtonsLabels;
    }

    public void setLinkBodyButtonsLabels(String str) {
        this.linkBodyButtonsLabels = str;
    }

    public String getLinkBodyButtonsActions() {
        return this.linkBodyButtonsActions;
    }

    public void setLinkBodyButtonsActions(String str) {
        this.linkBodyButtonsActions = str;
    }

    public String getPopupBodyText() {
        return this.popupBodyText;
    }

    public void setPopupBodyText(String str) {
        this.popupBodyText = str;
    }

    public String getPopupBodyButtonsLabels() {
        return this.popupBodyButtonsLabels;
    }

    public void setPopupBodyButtonsLabels(String str) {
        this.popupBodyButtonsLabels = str;
    }

    public String getPopupBodyButtonsActions() {
        return this.popupBodyButtonsActions;
    }

    public void setPopupBodyButtonsActions(String str) {
        this.popupBodyButtonsActions = str;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }

    public boolean isDisabled() {
        return this.isDisabled;
    }

    public void setDisabled(boolean z) {
        this.isDisabled = z;
    }
}
