package com.gumtree.android.post_ad.steps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import com.gumtree.DevelopmentFlags;
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.AuthenticatorActivity;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.locations.LocationActivity;
import com.gumtree.android.login.AuthActivity;
import com.gumtree.android.model.Ads;
import com.gumtree.android.post_ad.IPostAdDataRefresh;
import com.gumtree.android.post_ad.PostAdBaseActivity;
import com.gumtree.android.post_ad.model.PostAdData;
import com.gumtree.android.post_ad.model.PostAdMemDAO;
import javax.inject.Inject;

public class PostAdStepsActivity extends PostAdBaseActivity implements OnClickListener {
    public static final String EXTRA_STEP_ID = "com.gumtree.android.post_ad.step.step_id";
    private static final String ONE = "1";
    private static final String TWO = "2";
    private static final String ZERO = "0";
    @Inject
    BaseAccountManager accountManager;
    private PostAdFlowManager mPostAdFlowManager;

    public static Intent createIntent(int i) {
        Intent intent = new Intent("android.intent.action.INSERT", Ads.URI);
        intent.putExtra(EXTRA_STEP_ID, i);
        intent.setFlags(131072);
        intent.setPackage(GumtreeApplication.getPackageNameForIntent());
        return intent;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        GumtreeApplication.component().inject(this);
        setContentView(2130903097);
        findViewById(2131624914).setOnClickListener(this);
        findViewById(2131624915).setOnClickListener(this);
        initPostAdFlowManager(bundle);
        checkInitPostAdLoginActivity(bundle);
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    protected void onSaveInstanceState(Bundle bundle) {
        this.mPostAdFlowManager.onSaveInstanceState(bundle);
        super.onSaveInstanceState(bundle);
    }

    public void refreshContent(PostAdMemDAO postAdMemDAO) {
        int i = 0;
        getSupportActionBar().setTitle(postAdMemDAO.isManageAd() ? 2131165846 : 2131165650);
        String[] strArr = new String[]{ZERO, ONE, TWO};
        PostAdData postAdData = postAdMemDAO.getPostAdData();
        if (!postAdData.getAttributeMap().isEmpty() || ZERO.equals(postAdData.getAdId())) {
            int length = strArr.length;
            while (i < length) {
                Fragment findFragmentByTag = getSupportFragmentManager().findFragmentByTag(strArr[i]);
                if (findFragmentByTag != null && findFragmentByTag.isVisible()) {
                    ((IPostAdDataRefresh) findFragmentByTag).refreshContent(postAdData);
                }
                i++;
            }
        }
    }

    private void checkInitPostAdLoginActivity(Bundle bundle) {
        if (!this.accountManager.isUserLoggedIn() && bundle == null) {
            if (DevelopmentFlags.getInstance().isNewLoginEnabled()) {
                startActivityForResult(AuthActivity.createIntent(AuthIdentifier.POST_AD, this), LocationActivity.ACTIVITY_REQUEST_CODE);
            } else {
                AuthenticatorActivity.startActivity((Activity) this, 1);
            }
        }
    }

    private void initPostAdFlowManager(Bundle bundle) {
        int i = 0;
        if (bundle == null) {
            showProgress(false);
            i = getIntent().getIntExtra(EXTRA_STEP_ID, 0);
        }
        this.mPostAdFlowManager = new PostAdFlowManagerImpl(i, getSupportFragmentManager(), bundle);
    }

    public void showProgress(boolean z) {
        if (getProgressView() != null) {
            getProgressView().setVisibility(z ? 0 : 8);
        }
    }

    private View getProgressView() {
        return findViewById(2131624105);
    }

    public void onClick(View view) {
        hideKeyboard();
        switch (view.getId()) {
            case 2131624914:
                finish();
                return;
            case 2131624915:
                if (!this.mPostAdFlowManager.onNext()) {
                    finish();
                    return;
                }
                return;
            default:
                Log.e(getClass().getSimpleName(), "case not supported " + view.getId());
                return;
        }
    }

    public void onBackPressed() {
        hideKeyboard();
        super.onBackPressed();
        if (!this.mPostAdFlowManager.onPrevious()) {
            finish();
        }
    }
}
