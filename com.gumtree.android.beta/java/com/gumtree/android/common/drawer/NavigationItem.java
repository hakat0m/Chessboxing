package com.gumtree.android.common.drawer;

public enum NavigationItem {
    HOME(2131165645, 2130837881),
    SEARCH(2131165654, 2130837884),
    POST_AD(2131165650, 2130837882),
    MESSAGES(2131165649, 2130837878),
    FAVORITES(2131165646, 2130837886),
    SAVED_SEARCHES(2131165653, 2130837813),
    MANAGE_ADS(2131165648, 2130837879),
    DIVIDER(0, 0),
    SETTINGS(2131165615, 2130837885),
    HELP(2131165599, 2130837880);
    
    private int iconRes;
    private int titleRes;

    private NavigationItem(int i, int i2) {
        this.titleRes = i;
        this.iconRes = i2;
    }

    public int getTitleRes() {
        return this.titleRes;
    }

    public int getIconRes() {
        return this.iconRes;
    }
}
