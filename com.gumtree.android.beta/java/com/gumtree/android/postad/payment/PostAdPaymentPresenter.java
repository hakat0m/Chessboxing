package com.gumtree.android.postad.payment;

import com.gumtree.android.mvp.Presenter;
import com.gumtree.android.mvp.PresenterView;

public interface PostAdPaymentPresenter extends Presenter<View> {

    public interface View extends PresenterView {
        void openManageAds();

        void showAbortDialog();

        void showError(String str);

        void showLoading(boolean z);

        void showPayPalWebView(String str, boolean z);

        void showPaymentIncludedInPackage();

        void showPaymentSuccessful();
    }

    void onAbortConfirmed();

    void onPayPalFailure();

    void onPayPalSuccess(String str);

    void onWebViewLoadingFinished();

    void onWebViewLoadingStarted();
}
