package com.gumtree.android.post_ad;

import android.content.Intent;
import android.os.Bundle;
import com.ebay.classifieds.capi.executor.ResponseException;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.AuthBaseStrategy;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnPostAdLoadEvent;
import com.gumtree.android.post_ad.model.PostAdData;
import com.gumtree.android.post_ad.model.PostAdMemDAO;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public abstract class PostAdBaseActivity extends BaseActivity {
    public static final String EXTRA_POST_AD_ID = "com.gumtree.android.post_ad.postAdId";
    public static final String EXTRA_POST_AD_OBJ = "com.gumtree.android.post_ad.postAd";
    public static final String EXTRA_RESET = "com.gumtree.android.post_ad.reset";
    @Inject
    BaseAccountManager accountManager;
    @Inject
    AuthBaseStrategy gumtreeAuthBaseStrategy;
    @Inject
    protected EventBus mEventBus;
    private Object mEventBusReceiver;
    private PostAdMemDAO mMemDao;
    private String mPostAdId;

    public abstract void refreshContent(PostAdMemDAO postAdMemDAO);

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        GumtreeApplication.component().inject(this);
        this.mPostAdId = getIntent().getStringExtra(EXTRA_POST_AD_ID);
        if (bundle != null) {
            PostAdData postAdData = (PostAdData) bundle.getSerializable(EXTRA_POST_AD_OBJ);
            if (postAdData != null) {
                this.mMemDao = PostAdMemDAO.from(postAdData);
            }
        }
        if (bundle == null || (this.mPostAdId != null && this.mMemDao == null)) {
            if (getIntent().getBooleanExtra(EXTRA_RESET, false)) {
                PostAdDBIntentService.resetAndLoad(this.mPostAdId);
            } else {
                PostAdDBIntentService.load(this.mPostAdId);
            }
        }
        this.mEventBusReceiver = new Object() {
            @Subscribe
            public void onPostAdLoaded(OnPostAdLoadEvent onPostAdLoadEvent) {
                PostAdBaseActivity.this.mMemDao = PostAdMemDAO.from(onPostAdLoadEvent.getPostAdData());
                if (!onPostAdLoadEvent.hasError()) {
                    PostAdBaseActivity.this.refreshContent(PostAdBaseActivity.this.mMemDao);
                } else if (onPostAdLoadEvent.getmExc() instanceof ResponseException) {
                    ResponseException responseException = (ResponseException) onPostAdLoadEvent.getmExc();
                    if (responseException.getError().isAuthorization()) {
                        PostAdBaseActivity.this.gumtreeAuthBaseStrategy.renewToken(responseException.getError().getType(), PostAdBaseActivity.this);
                    }
                }
            }
        };
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }

    public void onResume() {
        super.onResume();
        this.mEventBus.register(this.mEventBusReceiver);
    }

    public void onPause() {
        super.onPause();
        if (!(this.accountManager.getUsername() == null || this.mMemDao == null)) {
            PostAdDBIntentService.save(this.mMemDao.getPostAdData());
        }
        this.mEventBus.unregister(this.mEventBusReceiver);
    }

    public BaseAccountManager getAccount() {
        return this.accountManager;
    }

    public PostAdMemDAO getDao() {
        return this.mMemDao;
    }

    public void resetDao() {
        this.mMemDao = null;
    }

    public String getPostAdId() {
        return this.mPostAdId;
    }

    public EventBus getEventBus() {
        return this.mEventBus;
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.mMemDao != null) {
            bundle.putSerializable(EXTRA_POST_AD_OBJ, this.mMemDao.getPostAdData());
        }
    }
}
