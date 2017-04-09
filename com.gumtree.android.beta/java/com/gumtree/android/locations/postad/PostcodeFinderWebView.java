package com.gumtree.android.locations.postad;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.gumtree.android.common.activities.BaseActivity;

public class PostcodeFinderWebView extends BaseActivity {
    private static final int PROGRESS_LENGTH = 100;
    private WebView webView;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2130903101);
        this.webView = (WebView) findViewById(2131624216);
        this.webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView webView, int i) {
                if (i == PostcodeFinderWebView.PROGRESS_LENGTH) {
                    PostcodeFinderWebView.this.getProgressView().setVisibility(8);
                }
            }
        });
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView webView, int i, String str, String str2) {
            }

            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                webView.loadUrl(str);
                return true;
            }
        });
        if (bundle == null) {
            this.webView.loadUrl(getResources().getString(2131165726));
            getProgressView().setVisibility(0);
            return;
        }
        this.webView.restoreState(bundle);
    }

    private View getProgressView() {
        return findViewById(2131624105);
    }

    protected void onSaveInstanceState(Bundle bundle) {
        this.webView.saveState(bundle);
        super.onSaveInstanceState(bundle);
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i == 4 && this.webView.canGoBack()) {
            this.webView.goBack();
            return true;
        }
        finish();
        return super.onKeyDown(i, keyEvent);
    }
}
