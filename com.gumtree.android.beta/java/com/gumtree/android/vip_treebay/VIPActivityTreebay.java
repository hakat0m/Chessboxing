package com.gumtree.android.vip_treebay;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.search.Search;
import com.gumtree.android.srp.NearByAdvertAdapter.AdItemAnalyticsInfo;
import com.gumtree.android.srp.SRPGridFragment;
import com.gumtree.android.srp.SRPGridFragment.SRPGridProvider;
import com.gumtree.android.srp.textlinks.TextLinkView;
import com.gumtree.android.vip_treebay.VIPMainFragmentTreebay.VIPMainProvider;

public class VIPActivityTreebay extends BaseActivity implements SRPGridProvider, VIPMainProvider {
    public static final int INVALID_VIP_ID = -1;
    private static final String IS_FAVOURITES = "favourite";
    private static final String IS_LIST_SEARCH = "isListSearch";
    private static final String IS_MESSAGES = "messages";
    private static final String IS_NEAR_BY_SEARCH = "isNearBySearch";
    private static final String PAGE = "page";
    private static final String POSITION = "position";
    private static final String SEARCH = "search";
    public static final String TREEBAY_AD = "treebayAd";
    private static final String VIP_ID = "vipId";
    private boolean isListSearch;
    private boolean isNearBySearch;
    private int position;
    protected long vipId;

    public static Intent createIntent(Context context, String str) {
        Intent intent = new Intent(context, VIPActivityTreebay.class);
        intent.putExtra(VIP_ID, str);
        return intent;
    }

    public static Intent createIntent(Context context, String str, boolean z, boolean z2) {
        Intent createIntent = createIntent(context, str);
        createIntent.putExtra(IS_NEAR_BY_SEARCH, z);
        createIntent.putExtra(IS_LIST_SEARCH, z2);
        return createIntent;
    }

    public static Intent createIntent(Context context, String str, boolean z, boolean z2, Search search, int i, int i2, String str2) {
        Intent createIntent = createIntent(context, str, z, z2);
        createIntent.putExtra(SEARCH, search.asBundle());
        createIntent.putExtra(POSITION, i);
        createIntent.putExtra(PAGE, i2);
        createIntent.putExtra(TREEBAY_AD, str2);
        return createIntent;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initVipId(bundle);
        this.isNearBySearch = getIntent().getBooleanExtra(IS_NEAR_BY_SEARCH, false);
        this.isListSearch = getIntent().getBooleanExtra(IS_LIST_SEARCH, false);
        setContentView(2130903119);
        this.position = getIntent().getIntExtra(POSITION, 0);
        if (bundle == null) {
            replaceVipFragment();
        }
        if (findViewById(2131624262) != null) {
            addSRPFragment();
        }
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putLong(VIP_ID, this.vipId);
    }

    protected void initVipId(Bundle bundle) {
        if (bundle != null) {
            this.vipId = bundle.getLong(VIP_ID, -1);
            if (this.vipId != -1) {
                return;
            }
        }
        try {
            this.vipId = getIntent().getExtras().getLong(VIP_ID);
        } catch (NumberFormatException e) {
            this.vipId = -1;
        }
    }

    public long getVipId() {
        return this.vipId;
    }

    public boolean isNearBySearch() {
        return this.isNearBySearch;
    }

    public boolean isList() {
        return this.isListSearch;
    }

    public boolean isFromMessage() {
        return getIntent().getBooleanExtra(IS_MESSAGES, false);
    }

    public void onItemSelected(long j, int i, int i2, boolean z, boolean z2, Search search, AdItemAnalyticsInfo adItemAnalyticsInfo) {
        if (this.vipId != j) {
            this.vipId = j;
            replaceVipFragment();
        }
    }

    private void replaceVipFragment() {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        VIPMainFragmentTreebay vIPMainFragmentTreebay = new VIPMainFragmentTreebay();
        vIPMainFragmentTreebay.setArguments(getIntent().getExtras());
        beginTransaction.replace(2131624261, vIPMainFragmentTreebay);
        beginTransaction.commit();
    }

    private void addSRPFragment() {
        if (getSupportFragmentManager().findFragmentById(2131624262) == null) {
            FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
            beginTransaction.add(2131624262, new SRPGridFragment());
            beginTransaction.commit();
        }
    }

    public int getSortSelected() {
        return 0;
    }

    public void doRefine(int i) {
    }

    public boolean isRefreshLocked() {
        return false;
    }

    public void setAbundanceInTitle(String str) {
    }

    public boolean createMenuOptions(Menu menu) {
        return false;
    }

    public void doSave() {
    }

    public boolean areAdsEnabled() {
        return false;
    }

    public PublisherAdView getLeaderBoardAdView() {
        return null;
    }

    public PublisherAdRequest getAdRequest(String str, String str2) {
        return null;
    }

    public PublisherAdView getMpuAdView() {
        return null;
    }

    public PublisherAdView getMpuRowAdView() {
        return null;
    }

    public TextLinkView getTextLinkView() {
        return null;
    }

    public boolean markSelected() {
        return true;
    }

    public boolean isToolBarEnabled() {
        return false;
    }

    public Search getSearch() {
        return Search.create(getApplicationContext(), getIntent().getBundleExtra(SEARCH));
    }

    public long getAdId() {
        return this.vipId;
    }

    public int getInitialPosition() {
        return this.position;
    }

    public int overrideColumnNumber() {
        return 1;
    }

    public boolean disableGalleryMode() {
        return true;
    }

    public boolean hideDescription() {
        return true;
    }

    public int getInitialPage() {
        return getIntent().getIntExtra(PAGE, INVALID_VIP_ID);
    }

    public boolean isScrollingDisabled() {
        return false;
    }

    public int getDefaultMode() {
        return 0;
    }

    public int getNumAds() {
        return 20;
    }

    public boolean hasFooter() {
        return false;
    }

    public void viewAll() {
    }

    public boolean showEndlessProgressLoader() {
        return true;
    }

    public void onRequestComplete() {
    }
}
