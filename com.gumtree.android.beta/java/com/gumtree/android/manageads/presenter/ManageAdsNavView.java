package com.gumtree.android.manageads.presenter;

public enum ManageAdsNavView {
    ACTIVE(0),
    INACTIVE(1);
    
    private final int value;

    private ManageAdsNavView(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }
}
