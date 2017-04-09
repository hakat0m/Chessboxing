package com.gumtree.android.auth.presenter;

public enum NavView {
    LOGIN(0),
    REGISTRATION(1),
    SPLASH(-1);
    
    private final int value;

    private NavView(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }
}
