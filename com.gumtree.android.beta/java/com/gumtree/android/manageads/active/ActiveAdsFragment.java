package com.gumtree.android.manageads.active;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
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
import com.gumtree.android.manageads.active.presenter.ActiveAdsPresenter;
import com.gumtree.android.manageads.active.presenter.ActiveAdsPresenter.View;
import com.gumtree.android.manageads.di.DaggerManageAdsComponent;
import com.gumtree.android.manageads.di.ManageAdsComponent;
import com.gumtree.android.manageads.di.ManageAdsModule;
import com.gumtree.android.post_ad.summary.PostAdSummaryActivity;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.PostAdActivity;
import com.gumtree.android.vip.VIPActivity;
import java.util.List;
import javax.inject.Inject;

public class ActiveAdsFragment extends BaseFragment implements ManageAdsListener, View {
    @Bind({2131624423})
    RecyclerView activeAdsView;
    @Bind({2131624424})
    android.view.View emptyPage;
    @Bind({2131624736})
    TextView emptyPageTitle;
    private PagingItemsAdapter<ViewHolder, Ad> pagedAdapter;
    @Inject
    PagingConfig pagingConfig;
    @Inject
    ActiveAdsPresenter presenter;
    @Bind({2131624425})
    ProgressBar progress;
    @Bind({2131624422})
    SwipeRefreshLayout refreshLayout;
    private OnPagingScrollListener scrollListener;

    @Nullable
    public android.view.View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(2130903188, viewGroup, false);
    }

    public void onViewCreated(android.view.View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        getManageAdsComponent().inject(this);
        ButterKnife.bind(this, view);
        initUI();
        setupPaging();
        if (bundle == null) {
            this.presenter.loadActiveAds();
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
        this.emptyPageTitle.setText(2131165572);
        this.activeAdsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.refreshLayout.setColorSchemeColors(new int[]{ThemeUtils.getColor(getActivity(), 2130772472)});
        this.refreshLayout.setOnRefreshListener(ActiveAdsFragment$$Lambda$1.lambdaFactory$(this));
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
        this.activeAdsView.addOnScrollListener(this.scrollListener);
        this.activeAdsView.setAdapter(this.pagedAdapter);
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
        this.pagedAdapter.unwrap();
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

    public void showAds(List<Ad> list) {
        this.pagedAdapter.setItems(list);
        this.scrollListener.resetPagingArea();
    }

    public void hideAds() {
        this.pagedAdapter.clear();
    }

    public void showDeleteConfirmationDialog(String str) {
        new Builder(getActivity()).setTitle(2131165412).setMessage(2131165410).setPositiveButton(2131165411, ActiveAdsFragment$$Lambda$2.lambdaFactory$(this, str)).setNegativeButton(17039360, ActiveAdsFragment$$Lambda$3.lambdaFactory$()).setOnCancelListener(ActiveAdsFragment$$Lambda$4.lambdaFactory$(this)).setCancelable(true).create().show();
    }

    /* synthetic */ void lambda$showDeleteConfirmationDialog$1(String str, DialogInterface dialogInterface, int i) {
        this.presenter.onDeleteConfirmed(str);
    }

    /* synthetic */ void lambda$showDeleteConfirmationDialog$3(DialogInterface dialogInterface) {
        this.presenter.onDeleteDialogCancelled();
    }

    public void showError(String str) {
        showSnackBar(str);
    }

    public void hideRefreshLayout() {
        this.refreshLayout.setRefreshing(false);
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

    public void showEditAdActivity(String str) {
        Intent createIntent;
        if (DevelopmentFlags.getInstance().isNewPostAdEnabled()) {
            createIntent = PostAdActivity.createIntent(this.context, str);
        } else {
            createIntent = PostAdSummaryActivity.createIntent(this.context, str);
        }
        startActivity(createIntent);
    }

    public void onLearnMore() {
        this.presenter.onLearnMore();
    }

    public void onEditAd(String str) {
        this.presenter.onEditAd(str);
    }

    public void onAdStatusClicked(Ad ad) {
        this.presenter.onAdStatusClicked(ad);
    }

    public void onDelete(String str) {
        this.presenter.onDelete(str);
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

    public void showVIPActivity(String str) {
        startActivity(VIPActivity.createIntent(getActivity(), (long) Integer.valueOf(str).intValue()));
    }

    public void onAdClicked(String str) {
        this.presenter.onAdClicked(str);
    }

    public void setHasMoreItems(boolean z) {
        this.pagedAdapter.hasNextPage(z);
    }
}
