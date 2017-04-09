package com.gumtree.android.vip.reply;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.MenuItem;
import android.widget.Toast;
import com.apptentive.android.sdk.Apptentive;
import com.ebay.classifieds.capi.executor.Request;
import com.ebay.classifieds.capi.executor.Result;
import com.ebay.classifieds.capi.replies.AdResponse;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.api.executor.RequestLoader;
import com.gumtree.android.api.tracking.ApptentiveEvent;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.contracts.EmailReply;
import com.gumtree.android.common.contracts.HorizontalProgressDisplay;
import com.gumtree.android.reply.ReplyMetadataField;
import com.gumtree.android.terms.TermsDialog;
import com.gumtree.android.vip.reply.api.SendReplyRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReplyToVIPActivity extends BaseActivity implements LoaderCallbacks<Result<AdResponse>>, EmailReply, HorizontalProgressDisplay {
    private static final String KEY_LOADING_STATE = "key_loading_state";
    private String categoryId;
    private boolean isListSearch;
    private boolean isLoading;
    private boolean isNearBySearch;
    private List<ReplyMetadataField> replyFields;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2130903104);
        setupSpinner(bundle);
        this.isNearBySearch = getIntent().getBooleanExtra(StatefulActivity.EXTRA_NEAR_BY_SEARCH, false);
        this.isListSearch = getIntent().getBooleanExtra(StatefulActivity.EXTRA_LIST_SEARCH, false);
        this.categoryId = getIntent().getStringExtra(StatefulActivity.NAME_CATEGORY_ID);
        String stringExtra = getIntent().getStringExtra(StatefulActivity.EXTRA_CATEGORY_NAME);
        setupOnFirstInstall(bundle);
        Track.viewReplyVIP(this.categoryId, stringExtra);
    }

    private void setupOnFirstInstall(Bundle bundle) {
        if (GumtreeApplication.isFirstInstall(getApplicationContext()) && bundle == null) {
            TermsDialog.newInstance().show(getSupportFragmentManager(), "DIALOG");
        }
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    private long getVIPId() {
        Uri data = getIntent().getData();
        if (data == null) {
            return 0;
        }
        return Long.parseLong(data.getLastPathSegment());
    }

    private void setupSpinner(Bundle bundle) {
        if (bundle == null) {
            showProgress(false);
        } else {
            showProgress(bundle.getBoolean(KEY_LOADING_STATE));
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(KEY_LOADING_STATE, this.isLoading);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                finish();
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void sendReply(Collection<ReplyMetadataField> collection) {
        this.replyFields = new ArrayList(collection);
        getSupportLoaderManager().restartLoader(0, null, this);
    }

    private void onSendFail() {
        Toast.makeText(getApplicationContext(), getString(2131165625), 0).show();
    }

    private void onSendSuccessful() {
        Toast.makeText(getApplicationContext(), getString(2131165633), 0).show();
        Apptentive.engage(this, ApptentiveEvent.REPLY_EMAIL_SUCCESS.getValue());
        finish();
    }

    public void showProgress(boolean z) {
        this.isLoading = z;
        findViewById(2131624105).setVisibility(this.isLoading ? 0 : 8);
    }

    public Loader<Result<AdResponse>> onCreateLoader(int i, Bundle bundle) {
        Request sendReplyRequest = new SendReplyRequest(getVIPId(), getIntent().getStringExtra(StatefulActivity.EXTRA_AD_TYPE), this.categoryId, this.isNearBySearch, this.isListSearch, this.replyFields);
        showProgress(true);
        return new RequestLoader(getApplicationContext(), sendReplyRequest);
    }

    public void onLoadFinished(Loader<Result<AdResponse>> loader, Result<AdResponse> result) {
        showProgress(false);
        if (result.hasError()) {
            onSendFail();
        } else {
            onSendSuccessful();
        }
    }

    public void onLoaderReset(Loader<Result<AdResponse>> loader) {
    }
}
