package com.gumtree.android.vip_treebay;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.apptentive.android.sdk.BuildConfig;
import com.apptentive.android.sdk.R;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.ad.treebay.services.TrackingTreebayService;
import com.gumtree.android.ad.treebay.services.item.TreebayAdDetailsService;
import com.gumtree.android.api.treebay.ItemSummary;
import com.gumtree.android.api.treebay.TreebayAd;
import com.gumtree.android.category.NewPostAdCategoryActivity;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.common.views.ThemeUtils;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.logging.Timber;
import com.gumtree.android.vip.api.VipDetailsIntentService.AdEvent;
import com.squareup.otto.Subscribe;
import java.net.UnknownHostException;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;
import retrofit2.adapter.rxjava.HttpException;
import uk.co.senab.photoview.IPhotoView;

public class VIPMainFragmentTreebay extends BaseFragment implements OnOffsetChangedListener, OnLayoutChangeListener {
    private static final int UNAUTHORIZED_ERROR_CODE = 403;
    private AppBarLayout appBarLayout;
    private View buttonBuyItNow;
    private View contactHolder;
    private String errorNetworkTreebay;
    private String errorServerTreebay;
    private String errorTokenTreebay;
    @Inject
    EventBus eventBus;
    private float imagePagerHeight;
    private View loading;
    private View pagerHolder;
    private VIPMainProvider provider;
    private int screenHeight;
    private View scrollContent;
    private int statusBarHeight;
    private Toolbar toolbar;
    private Drawable toolbarGradientBackground;
    private float toolbarHeight;
    private TextView toolbarTitle;
    @Inject
    TrackingTreebayService trackingService;
    @Inject
    TreebayAdDetailsService treebayService;
    private String webUrl;

    public interface VIPMainProvider {
        long getVipId();

        boolean isFromMessage();

        boolean isList();

        boolean isNearBySearch();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.provider = (VIPMainProvider) activity;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        GumtreeApplication.component().inject(this);
    }

    public void onResume() {
        super.onResume();
        this.eventBus.register(this);
    }

    private void initialize(TreebayAd treebayAd) {
        this.toolbarTitle.setText(treebayAd.getTitle());
        this.scrollContent.addOnLayoutChangeListener(this);
        this.buttonBuyItNow.setOnClickListener(VIPMainFragmentTreebay$$Lambda$1.lambdaFactory$(this, treebayAd));
        refreshFragments(treebayAd, Tags.REFRESHABLE_FRAGMENTS);
    }

    /* synthetic */ void lambda$initialize$0(TreebayAd treebayAd, View view) {
        startActivity(new Intent("android.intent.action.VIEW").setData(Uri.parse(treebayAd.getItemUrl())));
        this.trackingService.eventTreebayR2SExternalBegin(treebayAd);
    }

    public void onPause() {
        super.onPause();
        this.eventBus.unregister(this);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(2130903255, viewGroup, false);
        this.appBarLayout = (AppBarLayout) inflate.findViewById(2131624614);
        this.toolbar = (Toolbar) inflate.findViewById(2131624616);
        this.toolbarTitle = (TextView) inflate.findViewById(2131624617);
        this.pagerHolder = inflate.findViewById(2131624266);
        this.scrollContent = inflate.findViewById(2131624618);
        this.contactHolder = inflate.findViewById(2131624270);
        this.buttonBuyItNow = inflate.findViewById(2131624648);
        this.loading = inflate.findViewById(2131624191);
        this.treebayService.getTreebayAdDetails(getArguments().getString(VIPActivityTreebay.TREEBAY_AD)).doOnSubscribe(VIPMainFragmentTreebay$$Lambda$2.lambdaFactory$(this)).doOnTerminate(VIPMainFragmentTreebay$$Lambda$3.lambdaFactory$(this)).filter(VIPMainFragmentTreebay$$Lambda$4.lambdaFactory$()).map(VIPMainFragmentTreebay$$Lambda$5.lambdaFactory$()).subscribe(VIPMainFragmentTreebay$$Lambda$6.lambdaFactory$(this), VIPMainFragmentTreebay$$Lambda$7.lambdaFactory$(this));
        return inflate;
    }

    /* synthetic */ void lambda$onCreateView$1() {
        this.loading.setVisibility(0);
    }

    /* synthetic */ void lambda$onCreateView$2() {
        this.loading.setVisibility(8);
    }

    static /* synthetic */ Boolean lambda$onCreateView$3(ItemSummary itemSummary) {
        return Boolean.valueOf(itemSummary != null);
    }

    /* synthetic */ void lambda$onCreateView$5(TreebayAd treebayAd) {
        initialize(treebayAd);
    }

    /* synthetic */ void lambda$onCreateView$6(Throwable th) {
        String str = BuildConfig.FLAVOR;
        if (th instanceof UnknownHostException) {
            str = this.errorNetworkTreebay;
        } else if ((th instanceof HttpException) && ((HttpException) th).code() == UNAUTHORIZED_ERROR_CODE) {
            str = this.errorTokenTreebay;
        } else {
            str = this.errorServerTreebay;
        }
        confirmDialog(str);
        Timber.e(th.getMessage(), new Object[0]);
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

    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        this.errorNetworkTreebay = getString(2131165439);
        this.errorTokenTreebay = getString(2131165446);
        this.errorServerTreebay = getString(2131165445);
    }

    private void initToolBar() {
        FragmentActivity activity = getActivity();
        if (activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).setSupportActionBar(this.toolbar);
            ActionBar supportActionBar = ((AppCompatActivity) activity).getSupportActionBar();
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeButtonEnabled(true);
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

    public void onDestroy() {
        super.onDestroy();
        if (getActivity().isFinishing()) {
            this.treebayService.invalidateCache();
        }
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
        }
    }

    private void addAllFragments(FragmentTransaction fragmentTransaction) {
        FragmentManager childFragmentManager = getChildFragmentManager();
        long vipId = this.provider.getVipId();
        addVipImage(childFragmentManager, fragmentTransaction, vipId);
        addTitle(fragmentTransaction, childFragmentManager);
        addPrice(childFragmentManager, fragmentTransaction);
        addDescription(childFragmentManager, fragmentTransaction, vipId);
        addMpu(childFragmentManager, fragmentTransaction, vipId);
        addTextLink(childFragmentManager, fragmentTransaction, vipId);
    }

    private void addTextLink(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, long j) {
        FragmentManager fragmentManager2 = fragmentManager;
        FragmentTransaction fragmentTransaction2 = fragmentTransaction;
        addFragment(fragmentManager2, fragmentTransaction2, "textlink", 2131624626, VIPMainFragmentTreebay$$Lambda$8.lambdaFactory$(j));
    }

    private void addMpu(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, long j) {
        FragmentManager fragmentManager2 = fragmentManager;
        FragmentTransaction fragmentTransaction2 = fragmentTransaction;
        addFragment(fragmentManager2, fragmentTransaction2, "mpu", 2131624622, VIPMainFragmentTreebay$$Lambda$9.lambdaFactory$(j));
    }

    private void addDescription(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, long j) {
        FragmentManager fragmentManager2 = fragmentManager;
        FragmentTransaction fragmentTransaction2 = fragmentTransaction;
        addFragment(fragmentManager2, fragmentTransaction2, "desc", 2131624269, VIPMainFragmentTreebay$$Lambda$10.lambdaFactory$(j));
    }

    private void addPrice(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction) {
        FragmentManager fragmentManager2 = fragmentManager;
        FragmentTransaction fragmentTransaction2 = fragmentTransaction;
        addFragment(fragmentManager2, fragmentTransaction2, "price", 2131624267, VIPMainFragmentTreebay$$Lambda$11.lambdaFactory$());
    }

    private void addTitle(FragmentTransaction fragmentTransaction, FragmentManager fragmentManager) {
        FragmentManager fragmentManager2 = fragmentManager;
        FragmentTransaction fragmentTransaction2 = fragmentTransaction;
        addFragment(fragmentManager2, fragmentTransaction2, NewPostAdCategoryActivity.EXTRA_TITLE, 2131624271, VIPMainFragmentTreebay$$Lambda$12.lambdaFactory$());
    }

    private void addVipImage(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, long j) {
        FragmentManager fragmentManager2 = fragmentManager;
        FragmentTransaction fragmentTransaction2 = fragmentTransaction;
        addFragment(fragmentManager2, fragmentTransaction2, "pager", 2131624266, VIPMainFragmentTreebay$$Lambda$13.lambdaFactory$(j));
    }

    private <T extends Fragment> void addFragment(FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, String str, @IdRes int i, Provider<T> provider) {
        if (fragmentManager.findFragmentByTag(str) == null && getView().findViewById(i) != null) {
            fragmentTransaction.replace(i, (Fragment) provider.get(), str);
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Subscribe
    public void onVipDownload(AdEvent adEvent) {
        if (adEvent.isSuccess()) {
            refreshContent();
        }
    }

    private void refreshFragments(TreebayAd treebayAd, List<String> list) {
        for (String findFragmentByTag : list) {
            Fragment findFragmentByTag2 = getChildFragmentManager().findFragmentByTag(findFragmentByTag);
            if (findFragmentByTag2 != null) {
                ((VIPRefreshFragment) findFragmentByTag2).refreshContent(treebayAd);
            }
        }
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

    private void confirmDialog(String str) {
        Context context = getContext();
        if (context != null) {
            Builder builder = new Builder(context);
            builder.setMessage(str).setCancelable(false).setNeutralButton(getString(2131165670), VIPMainFragmentTreebay$$Lambda$14.lambdaFactory$(this));
            builder.create().show();
        }
    }

    /* synthetic */ void lambda$confirmDialog$13(DialogInterface dialogInterface, int i) {
        finish();
    }
}
