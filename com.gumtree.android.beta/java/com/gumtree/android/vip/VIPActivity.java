package com.gumtree.android.vip;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import com.apptentive.android.sdk.BuildConfig;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.gumtree.DevelopmentFlags;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.search.Search;
import com.gumtree.android.model.Ads;
import com.gumtree.android.srp.NearByAdvertAdapter.AdItemAnalyticsInfo;
import com.gumtree.android.srp.SRPGridFragment;
import com.gumtree.android.srp.SRPGridFragment.SRPGridProvider;
import com.gumtree.android.srp.textlinks.TextLinkView;
import com.gumtree.android.vip.VIPMainFragment.VIPMainProvider;

public class VIPActivity extends BaseActivity implements SRPGridProvider, VIPMainProvider {
    public static final int INVALID_VIP_ID = -1;
    private static final String IS_FAVOURITES = "favourite";
    public static final String IS_FROM_SELLER_OTHER_ITEMS = "seller_other_items";
    private static final String IS_LIST_SEARCH = "isListSearch";
    private static final String IS_MESSAGES = "messages";
    private static final String IS_NEAR_BY_SEARCH = "isNearBySearch";
    private static final String PAGE = "page";
    private static final String POSITION = "position";
    private static final String SEARCH = "search";
    private static final String VIP_ID = "vipId";
    private boolean isListSearch;
    private boolean isNearBySearch;
    private int position;
    protected long vipId;

    public static Intent createIntent(Context context, long j) {
        Context applicationContext = context.getApplicationContext();
        Intent intent = new Intent(getAction(), Ads.URI.buildUpon().appendPath(BuildConfig.FLAVOR + j).build());
        intent.setPackage(applicationContext.getPackageName());
        return intent;
    }

    public static Intent createIntent(Context context, long j, boolean z, boolean z2) {
        Intent createIntent = createIntent(context, j);
        createIntent.putExtra(IS_NEAR_BY_SEARCH, z);
        createIntent.putExtra(IS_LIST_SEARCH, z2);
        return createIntent;
    }

    public static Intent createIntent(Context context, long j, boolean z, boolean z2, Search search, int i, int i2) {
        Intent createIntent = createIntent(context, j, z, z2);
        createIntent.putExtra(SEARCH, search.asBundle());
        createIntent.putExtra(POSITION, i);
        createIntent.putExtra(PAGE, i2);
        return createIntent;
    }

    public static Intent createIntentForFavourites(Context context, long j) {
        Intent createIntent = createIntent(context, j);
        createIntent.putExtra(IS_FAVOURITES, true);
        return createIntent;
    }

    public static Intent createIntentForMessages(Context context, long j) {
        Intent createIntent = createIntent(context, j);
        createIntent.putExtra(IS_MESSAGES, true);
        return createIntent;
    }

    private static String getAction() {
        String str = StatefulActivity.ACTION_VIEW_WITH_BEAM;
        try {
            Class.forName("android.nfc.NdefMessage");
            Class.forName("android.nfc.NfcAdapter");
            Class.forName("android.nfc.NdefRecord");
            Class.forName("android.nfc.NfcEvent");
            if (GumtreeApplication.hasNFC()) {
                return str;
            }
            throw new ClassNotFoundException();
        } catch (ClassNotFoundException e) {
            return "android.intent.action.VIEW";
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initVipId(bundle);
        this.isNearBySearch = getIntent().getBooleanExtra(IS_NEAR_BY_SEARCH, false);
        this.isListSearch = getIntent().getBooleanExtra(IS_LIST_SEARCH, false);
        if (getIntent().getBooleanExtra(IS_FAVOURITES, false) || getIntent().getBooleanExtra(IS_MESSAGES, false) || getIntent().getBooleanExtra(IS_FROM_SELLER_OTHER_ITEMS, false)) {
            setContentView(2130903120);
        } else {
            setContentView(2130903116);
        }
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
            this.vipId = Long.parseLong(getIntent().getData().getLastPathSegment());
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
        boolean z = false;
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        if (DevelopmentFlags.getInstance().isSellerOtherItemsEnabled() && !getIntent().getBooleanExtra(IS_FROM_SELLER_OTHER_ITEMS, false)) {
            z = true;
        }
        beginTransaction.replace(2131624261, VIPMainFragment.newInstance(z));
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
