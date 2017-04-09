package com.gumtree.android.post_ad.summary;

import android.net.Uri;
import android.os.Bundle;
import com.ebay.classifieds.capi.executor.ResponseException;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.AuthBaseStrategy;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.activities.NavigationActivity;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnPostAdLoadEvent;
import com.gumtree.android.post_ad.PostAdBaseActivity;
import com.gumtree.android.post_ad.PostAdDBIntentService;
import com.gumtree.android.post_ad.model.PostAdMemDAO;
import com.gumtree.android.postad.DraftAd;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public abstract class PostAdNavigationActivity extends NavigationActivity {
    public static final String EXTRA_POST_AD_ID = "com.gumtree.android.post_ad.postAdId";
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

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        GumtreeApplication.component().inject(this);
        initPostAdId();
        loadPostAd(bundle);
        this.mEventBusReceiver = new Object() {
            @Subscribe
            public void onPostAdLoaded(OnPostAdLoadEvent onPostAdLoadEvent) {
                if (!onPostAdLoadEvent.hasError()) {
                    PostAdNavigationActivity.this.mMemDao = PostAdMemDAO.from(onPostAdLoadEvent.getPostAdData());
                    PostAdNavigationActivity.this.refreshContent(PostAdNavigationActivity.this.mMemDao);
                } else if (onPostAdLoadEvent.getmExc() instanceof ResponseException) {
                    ResponseException responseException = (ResponseException) onPostAdLoadEvent.getmExc();
                    if (responseException.getError().isAuthorization()) {
                        PostAdNavigationActivity.this.gumtreeAuthBaseStrategy.renewToken(responseException.getError().getType(), PostAdNavigationActivity.this);
                    }
                }
            }
        };
    }

    protected void loadPostAd(Bundle bundle) {
        if (bundle == null && getIntent().getBooleanExtra(PostAdBaseActivity.EXTRA_RESET, false)) {
            PostAdDBIntentService.resetAndLoad(this.mPostAdId);
        } else {
            PostAdDBIntentService.load(this.mPostAdId);
        }
    }

    private void initPostAdId() {
        this.mPostAdId = getIntent().getStringExtra(EXTRA_POST_AD_ID);
        if (this.mPostAdId == null) {
            Uri data = getIntent().getData();
            if (data != null) {
                this.mPostAdId = data.getQueryParameter("advertId");
            }
        }
        if (this.mPostAdId == null) {
            this.mPostAdId = DraftAd.NEW_AD_ID;
        }
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

    public String getPostAdId() {
        return this.mPostAdId;
    }

    public EventBus getEventBus() {
        return this.mEventBus;
    }

    public void resetDao() {
        this.mMemDao = null;
    }
}
