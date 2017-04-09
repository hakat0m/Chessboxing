package com.gumtree.android.vip;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.NestedScrollView.OnScrollChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.apptentive.android.sdk.Apptentive;
import com.apptentive.android.sdk.R;
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.api.tracking.ApptentiveEvent;
import com.gumtree.android.category.NewPostAdCategoryActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.common.views.ThemeUtils;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.favourites.FavouriteIntentService;
import com.gumtree.android.logging.Timber;
import com.gumtree.android.vip.api.VipDetailsIntentService;
import com.gumtree.android.vip.api.VipDetailsIntentService.AdEvent;
import com.gumtree.android.vip.bing.BingVIPCache;
import com.gumtree.android.vip.bing.BingVIPIntentService;
import com.gumtree.android.vip.carcheck.CarHistoryButtonFragment;
import com.gumtree.android.vip.model.Advert;
import com.gumtree.android.vip.model.VIPRefreshFragment;
import com.squareup.otto.Subscribe;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;
import uk.co.senab.photoview.IPhotoView;

public class VIPMainFragment extends BaseFragment implements OnOffsetChangedListener, LoaderCallbacks<Cursor>, OnLayoutChangeListener {
    private static final String SELLERS_OTHER_ITEMS_VISIBLE = "sellersOtherItemsVisible";
    private static IntentFilter mFavIntentFilter = new IntentFilter(FavouriteIntentService.ACTION_AD_FAVOURITED);
    private AppBarLayout appBarLayout;
    @Inject
    BingVIPCache bingVIPCache;
    private String categoryId;
    private View contactHolder;
    @Inject
    EventBus eventBus;
    private float imagePagerHeight;
    private boolean isFav;
    private boolean isSellersOtherItemsVisible;
    private BroadcastReceiver mFavBroadcastReceiver = new 1(this);
    private MenuItem mFavouriteButton;
    private View pagerHolder;
    private VIPMainProvider provider;
    private int screenHeight;
    private View scrollContent;
    private NestedScrollView scrollView;
    private int statusBarHeight;
    private View textLinkView;
    private Toolbar toolbar;
    private Drawable toolbarGradientBackground;
    private float toolbarHeight;
    private TextView toolbarTitle;
    private String webUrl;

    public interface VIPMainProvider {
        long getVipId();

        boolean isFromMessage();

        boolean isList();

        boolean isNearBySearch();
    }

    public static VIPMainFragment newInstance(boolean z) {
        VIPMainFragment vIPMainFragment = new VIPMainFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(SELLERS_OTHER_ITEMS_VISIBLE, Boolean.valueOf(z));
        vIPMainFragment.setArguments(bundle);
        return vIPMainFragment;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.provider = (VIPMainProvider) activity;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        GumtreeApplication.component().inject(this);
        this.isSellersOtherItemsVisible = getArguments().getBoolean(SELLERS_OTHER_ITEMS_VISIBLE, true);
        if (bundle == null) {
            VipDetailsIntentService.start(this.provider.getVipId(), this.provider.isNearBySearch(), this.provider.isList(), getActivity());
        }
    }

    public void onResume() {
        super.onResume();
        this.eventBus.register(this);
        getActivity().registerReceiver(this.mFavBroadcastReceiver, mFavIntentFilter);
    }

    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(this.mFavBroadcastReceiver);
        this.eventBus.unregister(this);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(2130903244, viewGroup, false);
        if (this.isSellersOtherItemsVisible) {
            inflate.findViewById(2131624621).setVisibility(0);
            inflate.findViewById(2131624625).setVisibility(0);
        }
        this.appBarLayout = (AppBarLayout) inflate.findViewById(2131624614);
        this.toolbar = (Toolbar) inflate.findViewById(2131624616);
        this.toolbarTitle = (TextView) inflate.findViewById(2131624617);
        this.pagerHolder = inflate.findViewById(2131624266);
        this.scrollContent = inflate.findViewById(2131624618);
        this.contactHolder = inflate.findViewById(2131624270);
        this.scrollView = (NestedScrollView) inflate.findViewById(R.id.scroll);
        this.textLinkView = inflate.findViewById(2131624626);
        this.scrollView.setOnScrollChangeListener(VIPMainFragment$$Lambda$1.lambdaFactory$(this));
        return inflate;
    }

    /* synthetic */ void lambda$onCreateView$0(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4) {
        updateTextLinkVisible();
    }

    private void updateTextLinkVisible() {
        Rect rect = new Rect();
        this.textLinkView.getHitRect(rect);
        if (Rect.intersects(new Rect(this.scrollView.getScrollX(), this.scrollView.getScrollY(), this.scrollView.getScrollX() + this.scrollView.getWidth(), this.scrollView.getScrollY() + this.scrollView.getHeight()), rect)) {
            this.bingVIPCache.setAdsVisited(true);
            BingVIPIntentService.sendImpressions();
            this.scrollView.setOnScrollChangeListener((OnScrollChangeListener) null);
        }
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        refreshContent();
        initToolBar();
        this.imagePagerHeight = getResources().getDimension(2131230722);
        this.toolbarHeight = (float) ThemeUtils.getDimensionPixelSize(getActivity(), R.attr.actionBarSize);
        this.statusBarHeight = getStatusBarHeight();
        this.toolbarGradientBackground = getToolbarGradientBackground();
        Display defaultDisplay = getActivity().getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        this.screenHeight = point.y;
    }

    private void initToolBar() {
        FragmentActivity activity = getActivity();
        if (activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).setSupportActionBar(this.toolbar);
            ActionBar supportActionBar = ((AppCompatActivity) activity).getSupportActionBar();
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeButtonEnabled(true);
            setHasOptionsMenu(true);
        }
    }

    private int getStatusBarHeight() {
        int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return identifier > 0 ? getResources().getDimensionPixelSize(identifier) : 0;
    }

    public void onStart() {
        super.onStart();
        this.appBarLayout.addOnOffsetChangedListener(this);
    }

    public void onStop() {
        super.onStop();
        this.appBarLayout.removeOnOffsetChangedListener(this);
    }

    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (getResources().getConfiguration().orientation != 2 || ((this.imagePagerHeight + this.toolbarHeight) + ((float) this.statusBarHeight)) + ((float) this.contactHolder.getMeasuredHeight()) <= ((float) this.screenHeight)) {
            if (((float) i) > (-(this.pagerHolder.getVisibility() == 8 ? 0.0f : this.imagePagerHeight - (2.0f * this.toolbarHeight)))) {
                this.toolbarTitle.setAlpha(0.0f);
                setToolbarBackground(this.toolbarGradientBackground);
                return;
            }
            this.toolbarTitle.setAlpha(IPhotoView.DEFAULT_MIN_SCALE);
            setToolbarBackground(null);
            return;
        }
        this.toolbarTitle.setAlpha(IPhotoView.DEFAULT_MIN_SCALE);
        setToolbarBackground(null);
    }

    private void setToolbarBackground(Drawable drawable) {
        if (VERSION.SDK_INT >= 16) {
            this.toolbar.setBackground(drawable);
        } else {
            this.toolbar.setBackgroundDrawable(drawable);
        }
    }

    private Drawable getToolbarGradientBackground() {
        if (VERSION.SDK_INT >= 21) {
            return getResources().getDrawable(2130837979, null);
        }
        return getResources().getDrawable(2130837979);
    }

    private void refreshContent() {
        if (getActivity() != null) {
            FragmentTransaction beginTransaction = getChildFragmentManager().beginTransaction();
            addAllFragments(beginTransaction);
            beginTransaction.commitAllowingStateLoss();
            getLoaderManager().restartLoader(7, null, this);
            getLoaderManager().restartLoader(6, null, this);
        }
    }

    private void addAllFragments(FragmentTransaction fragmentTransaction) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        long vipId = this.provider.getVipId();
        addVipImage(childFragmentManager, fragmentTransaction, vipId);
        addTitle(fragmentTransaction, childFragmentManager);
        addPrice(childFragmentManager, fragmentTransaction);
        addAttributes(childFragmentManager, fragmentTransaction, vipId);
        addDescription(childFragmentManager, fragmentTransaction, vipId);
        if (this.isSellersOtherItemsVisible) {
            addSellerOtherItems(childFragmentManager, fragmentTransaction);
        }
        addCarHistory(fragmentTransaction);
        addMap(childFragmentManager, fragmentTransaction, vipId);
        addContact(childFragmentManager, fragmentTransaction, vipId);
        addReportAdd(childFragmentManager, fragmentTransaction, vipId);
        if (this.isSellersOtherItemsVisible) {
            addAdRef(childFragmentManager, fragmentTransaction, vipId);
        }
        addMpu(childFragmentManager, fragmentTransaction, vipId);
        addTextLink(childFragmentManager, fragmentTransaction, vipId);
        addPartnership(childFragmentManager, fragmentTransaction, vipId);
    }

    private void addPartnership(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, long j) {
        FragmentManager fragmentManager2 = fragmentManager;
        FragmentTransaction fragmentTransaction2 = fragmentTransaction;
        addFragment(fragmentManager2, fragmentTransaction2, "partnership", 2131624619, VIPMainFragment$$Lambda$2.lambdaFactory$(j));
    }

    private void addTextLink(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, long j) {
        Provider lambdaFactory$;
        if (getResources().getBoolean(2131427350)) {
            lambdaFactory$ = VIPMainFragment$$Lambda$3.lambdaFactory$(j);
        } else {
            lambdaFactory$ = VIPMainFragment$$Lambda$4.lambdaFactory$(j);
        }
        addFragment(fragmentManager, fragmentTransaction, "textlink", 2131624626, lambdaFactory$);
    }

    private void addMpu(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, long j) {
        FragmentManager fragmentManager2 = fragmentManager;
        FragmentTransaction fragmentTransaction2 = fragmentTransaction;
        addFragment(fragmentManager2, fragmentTransaction2, "mpu", 2131624622, VIPMainFragment$$Lambda$5.lambdaFactory$(j));
    }

    private void addReportAdd(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, long j) {
        FragmentManager fragmentManager2 = fragmentManager;
        FragmentTransaction fragmentTransaction2 = fragmentTransaction;
        addFragment(fragmentManager2, fragmentTransaction2, "reportad", 2131624624, VIPMainFragment$$Lambda$6.lambdaFactory$(j));
    }

    private void addContact(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, long j) {
        FragmentManager fragmentManager2 = fragmentManager;
        FragmentTransaction fragmentTransaction2 = fragmentTransaction;
        addFragment(fragmentManager2, fragmentTransaction2, "contact", 2131624270, VIPMainFragment$$Lambda$7.lambdaFactory$(this, j));
    }

    /* synthetic */ Fragment lambda$addContact$6(long j) {
        return VIPContactFragment.newInstance(j, this.categoryId, this.provider.isNearBySearch(), this.provider.isList(), this.provider.isFromMessage());
    }

    private void addMap(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, long j) {
        FragmentManager fragmentManager2 = fragmentManager;
        FragmentTransaction fragmentTransaction2 = fragmentTransaction;
        addFragment(fragmentManager2, fragmentTransaction2, "map", 2131624623, VIPMainFragment$$Lambda$8.lambdaFactory$(j));
    }

    private void addCarHistory(FragmentTransaction fragmentTransaction) {
        fragmentTransaction.replace(2131624620, CarHistoryButtonFragment.newInstance(this.categoryId), "car");
    }

    private void addDescription(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, long j) {
        FragmentManager fragmentManager2 = fragmentManager;
        FragmentTransaction fragmentTransaction2 = fragmentTransaction;
        addFragment(fragmentManager2, fragmentTransaction2, "desc", 2131624269, VIPMainFragment$$Lambda$9.lambdaFactory$(this, j));
    }

    /* synthetic */ Fragment lambda$addDescription$8(long j) {
        return VIPDescriptionFragment.newInstance(j, this.isSellersOtherItemsVisible);
    }

    private void addAdRef(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, long j) {
        FragmentManager fragmentManager2 = fragmentManager;
        FragmentTransaction fragmentTransaction2 = fragmentTransaction;
        addFragment(fragmentManager2, fragmentTransaction2, "adRef", 2131624625, VIPMainFragment$$Lambda$10.lambdaFactory$(j));
    }

    private void addSellerOtherItems(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction) {
        FragmentManager fragmentManager2 = fragmentManager;
        FragmentTransaction fragmentTransaction2 = fragmentTransaction;
        addFragment(fragmentManager2, fragmentTransaction2, "sellerOtherItems", 2131624621, VIPMainFragment$$Lambda$11.lambdaFactory$());
    }

    private void addAttributes(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, long j) {
        FragmentManager fragmentManager2 = fragmentManager;
        FragmentTransaction fragmentTransaction2 = fragmentTransaction;
        addFragment(fragmentManager2, fragmentTransaction2, "attr", 2131624268, VIPMainFragment$$Lambda$12.lambdaFactory$(j));
    }

    private void addPrice(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction) {
        FragmentManager fragmentManager2 = fragmentManager;
        FragmentTransaction fragmentTransaction2 = fragmentTransaction;
        addFragment(fragmentManager2, fragmentTransaction2, "price", 2131624267, VIPMainFragment$$Lambda$13.lambdaFactory$());
    }

    private void addTitle(FragmentTransaction fragmentTransaction, FragmentManager fragmentManager) {
        FragmentManager fragmentManager2 = fragmentManager;
        FragmentTransaction fragmentTransaction2 = fragmentTransaction;
        addFragment(fragmentManager2, fragmentTransaction2, NewPostAdCategoryActivity.EXTRA_TITLE, 2131624271, VIPMainFragment$$Lambda$14.lambdaFactory$());
    }

    private void addVipImage(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, long j) {
        FragmentManager fragmentManager2 = fragmentManager;
        FragmentTransaction fragmentTransaction2 = fragmentTransaction;
        addFragment(fragmentManager2, fragmentTransaction2, "pager", 2131624266, VIPMainFragment$$Lambda$15.lambdaFactory$(j));
    }

    private <T extends Fragment> void addFragment(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, String str, @IdRes int i, Provider<T> provider) {
        if (fragmentManager.findFragmentByTag(str) == null && getView().findViewById(i) != null) {
            fragmentTransaction.replace(i, (Fragment) provider.get(), str);
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        MenuItem findItem = menu.findItem(2131624947);
        MenuItem findItem2 = menu.findItem(2131624946);
        if (findItem == null || findItem2 == null) {
            menuInflater.inflate(2131755018, menu);
            findItem2 = menu.findItem(2131624946);
        }
        this.mFavouriteButton = findItem2;
        setFavUIState(this.isFav);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;
            case 2131624946:
                updateAdFavouriteState();
                getActivity().setResult(-1);
                return true;
            case 2131624947:
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.TEXT", this.webUrl);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void updateAdFavouriteState() {
        String valueOf = String.valueOf(this.provider.getVipId());
        if (this.isFav) {
            sendTrackingForFavourites(false);
            FavouriteIntentService.unfavourite(String.valueOf(valueOf));
            return;
        }
        sendTrackingForFavourites(true);
        FavouriteIntentService.favourite(String.valueOf(valueOf));
    }

    private void setFavouriteState(Cursor cursor) {
        boolean z = false;
        if (cursor.getCount() == 0) {
            this.isFav = false;
        } else {
            cursor.moveToFirst();
            if (cursor.getInt(cursor.getColumnIndex("sync_status")) != 2) {
                z = true;
            }
            this.isFav = z;
        }
        getActivity().invalidateOptionsMenu();
    }

    private void sendTrackingForFavourites(boolean z) {
        long vipId = this.provider.getVipId();
        if (z) {
            Apptentive.engage(getActivity(), ApptentiveEvent.WATCHLIST_ADD.getValue());
            Track.eventFavouriteAddVIP(this.categoryId, vipId);
            return;
        }
        Apptentive.engage(getActivity(), ApptentiveEvent.WATCHLIST_DELETE.getValue());
        Track.eventFavouriteRemoveVIP(this.categoryId, vipId);
    }

    private void setFavUIState(boolean z) {
        if (this.mFavouriteButton != null) {
            MenuItem icon;
            if (z) {
                icon = this.mFavouriteButton.setIcon(2130837907);
            } else {
                icon = this.mFavouriteButton.setIcon(2130837908);
            }
            this.mFavouriteButton = icon;
        }
    }

    @Subscribe
    public void onVipDownload(AdEvent adEvent) {
        if (adEvent.isSuccess()) {
            refreshContent();
        }
    }

    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new VIPLoaderFactory().getLoader(i, this.provider.getVipId(), this.context);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case Timber.ERROR /*6*/:
                break;
            case Timber.ASSERT /*7*/:
                setFavouriteState(cursor);
                return;
            default:
                Log.e(getClass().getSimpleName(), "case not supported " + loader.getId());
                return;
        }
        while (cursor.moveToNext()) {
            Advert advert = new Advert(cursor);
            this.categoryId = advert.getCategoryId();
            this.toolbarTitle.setText(advert.getTitle());
            this.webUrl = advert.getWebUrl();
            getActivity().invalidateOptionsMenu();
            this.scrollContent.addOnLayoutChangeListener(this);
            refreshFragments(advert, Tags.REFRESHABLE_FRAGMENTS);
        }
    }

    private void refreshFragments(Advert advert, List<String> list) {
        for (String findFragmentByTag : list) {
            Fragment findFragmentByTag2 = getChildFragmentManager().findFragmentByTag(findFragmentByTag);
            if (findFragmentByTag2 != null) {
                ((VIPRefreshFragment) findFragmentByTag2).refreshContent(advert);
            }
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int i9 = i4 - i2;
        if (((float) i9) < (((float) this.screenHeight) - this.toolbarHeight) - ((float) this.statusBarHeight)) {
            updateContentPadding(i9);
        }
        this.scrollContent.removeOnLayoutChangeListener(this);
    }

    private void updateContentPadding(int i) {
        this.scrollContent.setPadding(0, 0, 0, ((int) ((((float) (this.screenHeight - i)) - this.toolbarHeight) - ((float) this.statusBarHeight))) + 1);
    }
}
