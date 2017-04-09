package com.gumtree.android.postad.payment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.gumtree.DevelopmentFlags;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.managead.ManageAdActivity;
import com.gumtree.android.manageads.ManageAdsActivity;
import com.gumtree.android.postad.payment.PostAdPaymentPresenter.View;
import com.gumtree.android.postad.payment.di.DaggerPostAdPaymentComponent;
import com.gumtree.android.postad.payment.di.PostAdPaymentComponent;
import com.gumtree.android.postad.payment.di.PostAdPaymentModule;
import com.gumtree.android.postad.payment.dialogs.PaymentAbortDialog;
import com.gumtree.android.postad.payment.models.OrderData;
import com.gumtree.android.postad.payment.utils.PayPalOrderHelper;
import com.gumtree.android.postad.payment.utils.PayPalUrlParser;
import javax.inject.Inject;

public class PostAdPaymentActivity extends BaseActivity implements View {
    public static final String EXTRA_PAYMENT_DATA = "com.gumtree.android.postad.payment.payment_data";
    @Bind({2131624215})
    android.view.View loadingView;
    @Inject
    PostAdPaymentPresenter presenter;
    @Inject
    PayPalUrlParser urlParser;
    @Bind({2131624214})
    WebView webView;

    class PayPalWebViewClient extends WebViewClient {
        private PayPalWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (!PostAdPaymentActivity.this.urlParser.isSuccessUrl(str)) {
                return false;
            }
            PostAdPaymentActivity.this.presenter.onPayPalSuccess(str);
            return true;
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            if (!PostAdPaymentActivity.this.urlParser.isSuccessUrl(str)) {
                PostAdPaymentActivity.this.presenter.onWebViewLoadingStarted();
                super.onPageStarted(webView, str, bitmap);
            }
        }

        public void onPageFinished(WebView webView, String str) {
            PostAdPaymentActivity.this.presenter.onWebViewLoadingFinished();
            if (PostAdPaymentActivity.this.urlParser.isSuccessUrl(str)) {
                PostAdPaymentActivity.this.presenter.onPayPalSuccess(str);
            } else if (PostAdPaymentActivity.this.urlParser.isCancelUrl(str)) {
                PostAdPaymentActivity.this.presenter.onPayPalFailure();
            } else {
                super.onPageFinished(webView, str);
            }
        }
    }

    public static Intent createIntent(Context context, OrderData orderData) {
        Intent intent = new Intent(context, PostAdPaymentActivity.class);
        intent.putExtra(EXTRA_PAYMENT_DATA, orderData);
        return intent;
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getPostAdPaymentComponent().inject(this);
        if (VERSION.SDK_INT >= 18) {
            setRequestedOrientation(14);
        } else {
            setRequestedOrientation(1);
        }
        getWindow().setFlags(8192, 8192);
        setContentView(2130903100);
        ButterKnife.bind(this);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.setWebViewClient(new PayPalWebViewClient());
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                showAbortDialog();
                break;
        }
        return true;
    }

    public void onBackPressed() {
        showAbortDialog();
    }

    private PostAdPaymentComponent getPostAdPaymentComponent() {
        PostAdPaymentComponent postAdPaymentComponent = (PostAdPaymentComponent) ComponentsManager.get().getBaseComponent(PostAdPaymentComponent.KEY);
        if (postAdPaymentComponent != null) {
            return postAdPaymentComponent;
        }
        Object build = DaggerPostAdPaymentComponent.builder().applicationComponent(ComponentsManager.get().getAppComponent()).postAdPaymentModule(new PostAdPaymentModule((OrderData) getIntent().getSerializableExtra(EXTRA_PAYMENT_DATA))).build();
        ComponentsManager.get().putBaseComponent(PostAdPaymentComponent.KEY, build);
        return build;
    }

    protected void onResume() {
        super.onResume();
        this.presenter.attachView(this);
    }

    protected void onPause() {
        this.presenter.detachView();
        super.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            ComponentsManager.get().removeBaseComponent(PostAdPaymentComponent.KEY);
            this.presenter.destroy();
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.webView != null) {
            this.webView.saveState(bundle);
        }
    }

    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        if (this.webView != null) {
            this.webView.restoreState(bundle);
        }
    }

    public void showPayPalWebView(String str, boolean z) {
        if (z) {
            this.webView.loadData(str, PayPalOrderHelper.TEXT_HTML_CHARSET_UTF_8, null);
        } else {
            this.webView.loadUrl(str);
        }
    }

    public void showPaymentSuccessful() {
        Toast.makeText(this, 2131165698, 1).show();
    }

    public void showPaymentIncludedInPackage() {
        Toast.makeText(this, 2131165397, 1).show();
    }

    /* synthetic */ void lambda$showAbortDialog$0() {
        this.presenter.onAbortConfirmed();
    }

    public void showAbortDialog() {
        new PaymentAbortDialog(this, PostAdPaymentActivity$$Lambda$1.lambdaFactory$(this)).showDialog();
    }

    public void showLoading(boolean z) {
        this.loadingView.setVisibility(z ? 0 : 8);
    }

    public void showError(String str) {
        Toast.makeText(this, str, 1).show();
    }

    public void openManageAds() {
        if (DevelopmentFlags.getInstance().isNewManageAdsEnabled()) {
            Intent createIntent = ManageAdsActivity.createIntent(this);
            createIntent.putExtra(ManageAdsActivity.REFRESH_ADS, true);
            startActivity(createIntent);
        } else {
            startActivity(ManageAdActivity.createIntent());
        }
        finish();
    }
}
