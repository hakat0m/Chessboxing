package com.gumtree.android.srp;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import com.apptentive.android.sdk.BuildConfig;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.gumtree.DevelopmentFlags;
import com.gumtree.Log;
import com.gumtree.android.ad.treebay.TreebayAdManager;
import com.gumtree.android.ad.treebay.TreebayAdvertItem;
import com.gumtree.android.common.utils.Counters;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.home.HomeActivity;
import com.gumtree.android.srp.SRPGridFragment.SRPGridProvider;
import com.gumtree.android.srp.textlinks.TextLinkView;
import com.gumtree.android.srp.treebay.TreebayAdGalleryItemView;
import com.gumtree.android.srp.treebay.TreebayAdGridItemView;
import dev.dworks.libs.astickyheader.DynamicSectionedGridAdapter;
import dev.dworks.libs.astickyheader.Section;
import dev.dworks.libs.astickyheader.SimpleSectionedGridAdapter.AdProviderAdapter;
import javax.inject.Inject;

public class NearByAdvertAdapter extends DynamicSectionedGridAdapter implements AdProviderAdapter {
    private static final String L = "l";
    private static final int LIST_MODE = 0;
    private static final String P = "p";
    private static final int TREEBAY_ADS_PER_PAGE = 2;
    private Context context;
    private final GridView gridView;
    private Counters lastCounters;
    private int lastTreebayPosition;
    private PublisherAdView leaderBoardAdView;
    private int leaderBoardLastPage;
    private int mode;
    private boolean modeChanged;
    private PublisherAdView mpuAdView;
    private int mpuRowLastPage;
    private int mpuSingleLastPage;
    private SRPGridProvider provider;
    private int textLinkLastPage;
    private TextLinkView textLinkView;
    @Inject
    TreebayAdManager treebayAdManager;
    private TreebayAdvertItem treebayAdvertItem;

    public class AdItemAnalyticsInfo {
        public String catId;
        public long id;
        public boolean isFeatured;
        public boolean isUrgent;
    }

    public NearByAdvertAdapter(Context context, Cursor cursor, GridView gridView) {
        this(context, cursor, gridView, LIST_MODE);
    }

    public NearByAdvertAdapter(Context context, Cursor cursor, GridView gridView, int i) {
        super(context, new 1(context, cursor, i, context, gridView));
        ComponentsManager.get().getAppComponent().inject(this);
        this.context = context;
        this.mode = i;
        if (context instanceof SRPGridProvider) {
            this.provider = (SRPGridProvider) context;
            setGridView(gridView);
            this.gridView = gridView;
            setSections(Counters.empty());
            return;
        }
        throw new RuntimeException("Activity using srp should implement SRPGridFragment.SRPGridProvider");
    }

    public static boolean isContentAvailable(AbsListView absListView) {
        return (absListView == null || absListView.getAdapter() == null || ((ListAdapter) absListView.getAdapter()).getCount() == 0) ? false : true;
    }

    public static boolean isAdLimitReached(int i, int i2) {
        return i2 <= Integer.parseInt("20") * (i + 1);
    }

    boolean isList() {
        return this.mode == 0;
    }

    public void changeMode(GridView gridView, int i, Counters counters) {
        this.modeChanged = true;
        this.mode = i;
        getWrappedAdapter().setMode(i);
        setGridView(gridView);
        setSections(counters);
    }

    public void changeCursor(Cursor cursor, Counters counters) {
        getWrappedAdapter().swapCursor(cursor);
        setSections(counters);
    }

    public AdvertItemAdapter getWrappedAdapter() {
        return (AdvertItemAdapter) super.getWrappedAdapter();
    }

    public AdItemAnalyticsInfo getInfoForAnalytics(int i) {
        return getWrappedAdapter().getInfoForAnalytics(i);
    }

    public void setListener(OnFavouriteClickListener onFavouriteClickListener) {
        getWrappedAdapter().setListener(onFavouriteClickListener);
    }

    public void setNearByHeader(Counters counters) {
        if (counters != null && counters.getTotal() == 0) {
            return;
        }
        if (this.lastCounters == null || !this.lastCounters.equals(counters)) {
            this.lastCounters = counters;
            setSections(counters);
        }
    }

    private void setSections(Counters counters) {
        SectionBuilder sectionBuilder;
        if (this.provider.areAdsEnabled()) {
            sectionBuilder = new SectionBuilder(this, new AdConfiguration(this.context, getWrappedAdapter().isList()));
        } else {
            sectionBuilder = new SectionBuilder(this);
        }
        setSections(sectionBuilder.buildSections(getWrappedAdapter().getCursor(), counters));
    }

    public void injectAd(Section section, View view) {
        int i = 8;
        if (this.provider.areAdsEnabled()) {
            switch (section.getLayoutId()) {
                case 2130903270:
                    setLeaderboardAdView(view, section.getPosition());
                    return;
                case 2130903272:
                    setMpuRowAdView(view, section.getPosition());
                    return;
                case 2130903273:
                    setMpuSingleAdView(view, section.getPosition());
                    return;
                case 2130903274:
                    setTextLinkAd(view, section.getPosition());
                    return;
                case 2130903275:
                    if (showTreebayAds()) {
                        if (this.lastTreebayPosition == 8) {
                            i = 19;
                        }
                        this.lastTreebayPosition = i;
                        setTreebayAd(view, section.getPosition(), section, this.lastTreebayPosition);
                        return;
                    }
                    return;
                case 2130903339:
                    setFooterView(view);
                    return;
                default:
                    Log.e(getClass().getSimpleName(), "case not supported " + section.getLayoutId());
                    return;
            }
        }
    }

    private void setFooterView(View view) {
        view.findViewById(2131624827).setOnClickListener(NearByAdvertAdapter$$Lambda$1.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$setFooterView$0(View view) {
        this.provider.viewAll();
    }

    private void setLeaderboardAdView(View view, int i) {
        ViewGroup viewGroup = (ViewGroup) view.findViewById(2131624697);
        if (this.leaderBoardAdView == null) {
            this.leaderBoardAdView = this.provider.getLeaderBoardAdView();
        }
        if (this.leaderBoardAdView == null) {
            return;
        }
        if (viewGroup.getChildCount() > 0) {
            int page = getPage(i);
            if (page > this.leaderBoardLastPage) {
                this.leaderBoardLastPage = page;
                this.leaderBoardAdView.loadAd(this.provider.getAdRequest(BuildConfig.FLAVOR + page, getContentUrl()));
                return;
            }
            return;
        }
        if (this.leaderBoardAdView.getParent() != null) {
            ((ViewGroup) this.leaderBoardAdView.getParent()).removeAllViews();
        }
        viewGroup.addView(this.leaderBoardAdView);
        viewGroup.setVisibility(LIST_MODE);
        this.leaderBoardAdView.setVisibility(LIST_MODE);
        this.leaderBoardAdView.loadAd(this.provider.getAdRequest(BuildConfig.FLAVOR + getPage(i), getContentUrl()));
    }

    private void setMpuRowAdView(View view, int i) {
        ViewGroup viewGroup = (ViewGroup) view.findViewById(2131624699);
        if (this.mpuAdView == null) {
            this.mpuAdView = this.provider.getMpuRowAdView();
        }
        if (this.mpuAdView != null) {
            this.mpuAdView.setFocusableInTouchMode(false);
            if (viewGroup.getChildCount() > 0) {
                int page = getPage(i);
                if (page > this.mpuRowLastPage) {
                    this.mpuRowLastPage = page;
                    this.mpuAdView.loadAd(this.provider.getAdRequest(P + page, getContentUrl()));
                    return;
                }
                return;
            }
            if (this.mpuAdView.getParent() != null) {
                ((ViewGroup) this.mpuAdView.getParent()).removeAllViews();
            }
            viewGroup.addView(this.mpuAdView);
            this.mpuAdView.setAdListener(new 2(this, viewGroup));
            this.mpuAdView.loadAd(this.provider.getAdRequest(P + getPage(i), getContentUrl()));
        }
    }

    private String getContentUrl() {
        if (this.lastCounters == null) {
            return BuildConfig.FLAVOR;
        }
        return this.lastCounters.getContentUrl();
    }

    private void setMpuSingleAdView(View view, int i) {
        ViewGroup viewGroup = (ViewGroup) view.findViewById(2131624699);
        if (this.mpuAdView == null) {
            this.mpuAdView = this.provider.getMpuAdView();
        }
        if (this.mpuAdView != null) {
            this.mpuAdView.setFocusableInTouchMode(false);
            if (viewGroup.getChildCount() > 0) {
                int page = getPage(i);
                if (page > this.mpuSingleLastPage) {
                    this.mpuSingleLastPage = page;
                    this.mpuAdView.loadAd(this.provider.getAdRequest(L + page, getContentUrl()));
                    return;
                }
                return;
            }
            LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            layoutParams.addRule(14, -1);
            layoutParams.addRule(15, -1);
            if (this.mpuAdView.getParent() != null) {
                ((ViewGroup) this.mpuAdView.getParent()).removeAllViews();
            }
            viewGroup.addView(this.mpuAdView, layoutParams);
            viewGroup.setVisibility(LIST_MODE);
            this.mpuAdView.setVisibility(LIST_MODE);
            this.mpuAdView.loadAd(this.provider.getAdRequest(L + getPage(i), getContentUrl()));
        }
    }

    private void setTreebayAd(View view, int i, Section section, int i2) {
        ViewGroup viewGroup = (ViewGroup) view.findViewById(2131624702);
        if (this.modeChanged) {
            viewGroup.removeAllViews();
            this.treebayAdvertItem = null;
            this.modeChanged = false;
        }
        if (this.treebayAdvertItem == null) {
            if (isList()) {
                this.treebayAdvertItem = new TreebayAdGridItemView(this.context);
            } else {
                this.treebayAdvertItem = new TreebayAdGalleryItemView(this.context);
            }
            LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams.addRule(9, -1);
            layoutParams.addRule(10, -1);
            viewGroup.addView((View) this.treebayAdvertItem, layoutParams);
        }
        int convertPositionForWrappeedAdapter = convertPositionForWrappeedAdapter(i);
        this.treebayAdvertItem.setRelativePosition(i2);
        this.treebayAdvertItem.setAbsolutePosition(convertPositionForWrappeedAdapter);
        this.treebayAdManager.getUntrackedTreebayItemsPositions().add(Integer.valueOf(i2));
        this.treebayAdManager.populateView(convertPositionForWrappeedAdapter, this.treebayAdvertItem);
        this.treebayAdvertItem.vipPayload(this.provider.getSearch());
        if (((SRPGridProvider) this.context).hideDescription()) {
            this.treebayAdvertItem.hideDescription();
        }
    }

    private void setTextLinkAd(View view, int i) {
        ViewGroup viewGroup = (ViewGroup) view.findViewById(2131624700);
        if (this.textLinkView == null) {
            this.textLinkView = this.provider.getTextLinkView();
        }
        if (this.textLinkView != null) {
            this.textLinkView.setContainers(this.gridView, view);
            this.textLinkView.setFocusableInTouchMode(false);
            if (viewGroup.getChildCount() > 0) {
                int page = getPage(i);
                if (page > this.textLinkLastPage) {
                    this.textLinkLastPage = page;
                    this.textLinkView.loadAd(this.provider.getAdRequest(BuildConfig.FLAVOR + page, getContentUrl()));
                }
                updateTextLinkPage(this.textLinkView, view);
                return;
            }
            LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams.addRule(9, -1);
            layoutParams.addRule(10, 1);
            viewGroup.addView(this.textLinkView, layoutParams);
            viewGroup.setVisibility(LIST_MODE);
            this.textLinkView.setVisibility(LIST_MODE);
            this.textLinkView.loadAd(this.provider.getAdRequest(BuildConfig.FLAVOR + getPage(i), getContentUrl()));
            updateTextLinkPage(this.textLinkView, view);
        }
    }

    private void updateTextLinkPage(TextLinkView textLinkView, View view) {
        if (view.getParent() == null) {
            textLinkView.update(-1);
        } else {
            textLinkView.update(this.gridView.getPositionForView(view));
        }
    }

    private int getPage(int i) {
        return (i / 20) + 1;
    }

    public TreebayAdManager getTreebayAdManager() {
        return this.treebayAdManager;
    }

    private boolean showTreebayAds() {
        return DevelopmentFlags.getInstance().isTreeBayEnabled() && !(this.context instanceof HomeActivity);
    }
}
