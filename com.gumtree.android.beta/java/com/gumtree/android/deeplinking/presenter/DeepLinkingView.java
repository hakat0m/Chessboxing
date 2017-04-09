package com.gumtree.android.deeplinking.presenter;

import com.gumtree.android.deeplinking.SearchParameters;
import com.gumtree.android.mvp.PresenterView;

public interface DeepLinkingView extends PresenterView {
    void showError(String str);

    void showHomeScreen();

    void showLogin();

    void showPostAd(String str);

    void showRegistration();

    void showResetPassword(String str, String str2, String str3);

    void showSRP(SearchParameters searchParameters);
}
