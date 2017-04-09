package com.gumtree.android.manageads.inactive;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.gumtree.DevelopmentFlags;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.common.paging.PagingConfig;
import com.gumtree.android.common.views.ThemeUtils;
import com.gumtree.android.common.views.recycler.paging.OnPagingScrollListener;
import com.gumtree.android.common.views.recycler.paging.PagingItemsAdapter;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.manageads.Ad;
import com.gumtree.android.manageads.AdsAdapter;
import com.gumtree.android.manageads.ManageAdsActivity;
import com.gumtree.android.manageads.ManageAdsListener;
import com.gumtree.android.manageads.di.DaggerManageAdsComponent;
import com.gumtree.android.manageads.di.ManageAdsComponent;
import com.gumtree.android.manageads.di.ManageAdsModule;
import com.gumtree.android.manageads.inactive.presenter.InactiveAdsPresenter;
import com.gumtree.android.manageads.inactive.presenter.InactiveAdsPresenter.View;
import com.gumtree.android.post_ad.summary.PostAdSummaryActivity;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.PostAdActivity;
import java.util.List;
import javax.inject.Inject;

public class InactiveAdsFragment extends BaseFragment implements ManageAdsListener, View {
    @Bind({2131624424})
    android.view.View emptyPage;
    @Bind({2131624736})
    TextView emptyPageTitle;
    @Bind({2131624427})
    RecyclerView inactiveAdsView;
    private PagingItemsAdapter<ViewHolder, Ad> pagedAdapter;
    @Inject
    PagingConfig pagingConfig;
    @Inject
    InactiveAdsPresenter presenter;
    @Bind({2131624428})
    ProgressBar progress;
    @Bind({2131624426})
    SwipeRefreshLayout refreshLayout;
    private OnPagingScrollListener scrollListener;

    @Nullable
    public android.view.View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(2130903189, viewGroup, false);
    }

    public void onViewCreated(android.view.View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        getManageAdsComponent().inject(this);
        ButterKnife.bind(this, view);
        setupPaging();
        initUI();
        if (bundle == null) {
            this.presenter.loadInactiveAds();
        }
    }

    private ManageAdsComponent getManageAdsComponent() {
        ManageAdsComponent manageAdsComponent = (ManageAdsComponent) ComponentsManager.get().getBaseComponent(ManageAdsComponent.KEY);
        if (manageAdsComponent != null) {
            return manageAdsComponent;
        }
        Object build = DaggerManageAdsComponent.builder().applicationComponent(ComponentsManager.get().getAppComponent()).manageAdsModule(new ManageAdsModule()).build();
        ComponentsManager.get().putBaseComponent(ManageAdsComponent.KEY, build);
        return build;
    }

    private void initUI() {
        this.emptyPageTitle.setText(2131165573);
        this.inactiveAdsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.refreshLayout.setColorSchemeColors(new int[]{ThemeUtils.getColor(getActivity(), 2130772472)});
        this.refreshLayout.setOnRefreshListener(InactiveAdsFragment$$Lambda$1.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$initUI$0() {
        refreshContent();
    }

    public void refreshContent() {
        this.presenter.refreshAdsList();
    }

    private void setupPaging() {
        AdsAdapter adsAdapter = new AdsAdapter(this);
        this.pagedAdapter = new PagingItemsAdapter();
        this.scrollListener = new OnPagingScrollListener(this.pagingConfig, new 1(this));
        this.inactiveAdsView.addOnScrollListener(this.scrollListener);
        this.inactiveAdsView.setAdapter(this.pagedAdapter);
        this.pagedAdapter.wrap(adsAdapter);
    }

    public void onResume() {
        super.onResume();
        this.presenter.attachView(this);
    }

    public void onPause() {
        super.onPause();
        this.presenter.detachView();
    }

    public void onDestroy() {
        if (isAdded() && getActivity().isFinishing()) {
            this.presenter.destroy();
        }
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @OnClick({2131624738})
    void onPostAnAdClick() {
        this.presenter.onPostAnAdClick();
    }

    public void showError(String str) {
        showSnackBar(str);
    }

    public void showAds(List<Ad> list) {
        this.pagedAdapter.setItems(list);
        this.scrollListener.resetPagingArea();
    }

    public void hideAds() {
        this.pagedAdapter.clear();
    }

    public void showEditAdActivity(String str) {
        Intent createIntent;
        if (DevelopmentFlags.getInstance().isNewPostAdEnabled()) {
            createIntent = PostAdActivity.createIntent(this.context, str);
        } else {
            createIntent = PostAdSummaryActivity.createIntent(this.context, str);
        }
        startActivity(createIntent);
    }

    public void hideRefreshLayout() {
        this.refreshLayout.setRefreshing(false);
    }

    public void onAdClicked(String str) {
    }

    public void setHasMoreItems(boolean z) {
        this.pagedAdapter.hasNextPage(z);
    }

    public void showEmptyPage() {
        this.emptyPage.setVisibility(0);
    }

    public void showPostAdActivity() {
        Intent createIntent;
        if (DevelopmentFlags.getInstance().isNewPostAdEnabled()) {
            createIntent = PostAdActivity.createIntent(this.context, DraftAd.NEW_AD_ID);
        } else {
            createIntent = PostAdSummaryActivity.createIntent(this.context, DraftAd.NEW_AD_ID);
        }
        getActivity().startActivityForResult(createIntent, 1);
    }

    public void showLearnMorePage() {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(ManageAdsActivity.LEARN_MORE_URL)));
    }

    public void hideEmptyPage() {
        this.emptyPage.setVisibility(8);
    }

    public void showProgressBar() {
        this.progress.setVisibility(0);
    }

    public void hideProgressBar() {
        this.progress.setVisibility(8);
    }

    public void onLearnMore() {
        this.presenter.onLearnMoreClick();
    }

    public void onEditAd(String str) {
        this.presenter.onEditAd(str);
    }

    public void onAdStatusClicked(Ad ad) {
        this.presenter.onAdStatusClicked(ad);
    }

    public void onDelete(String str) {
    }
}
