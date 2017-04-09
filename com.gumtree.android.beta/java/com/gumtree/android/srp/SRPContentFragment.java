package com.gumtree.android.srp;

public interface SRPContentFragment {
    String getActionbarSubtitle();

    String getContentUrl();

    String getTreebayItemTemplateUrl();

    String getTreebaySearchUrl();

    void onActivityNewIntent();

    void restartLoaders();
}
