package com.gumtree.android.sellersotheritems;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.paging.PagingConfig;
import com.gumtree.android.common.views.recycler.paging.OnPagingScrollListener;
import com.gumtree.android.common.views.recycler.paging.OnPagingScrollListener.OnPagingListener;
import com.gumtree.android.common.views.recycler.paging.PagingItemsAdapter;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.sellersotheritems.SellerAdsAdapter.ViewHolder;
import com.gumtree.android.sellersotheritems.di.DaggerSellersOtherItemsComponent;
import com.gumtree.android.sellersotheritems.di.SellersOtherItemsComponent;
import com.gumtree.android.sellersotheritems.di.SellersOtherItemsModule;
import com.gumtree.android.sellersotheritems.models.SellerAd;
import com.gumtree.android.sellersotheritems.models.SellerData;
import com.gumtree.android.sellersotheritems.presenters.SellersOtherItemsPresenter;
import com.gumtree.android.sellersotheritems.presenters.SellersOtherItemsPresenter.View;
import com.gumtree.android.vip.VIPActivity;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

public class SellersOtherItemsActivity extends BaseActivity implements View {
    @Bind({2131624242})
    TextView emptyView;
    @Bind({2131624243})
    android.view.View mainLoading;
    private PagingItemsAdapter<ViewHolder, SellerAd> pagedAdapter;
    @Inject
    PagingConfig pagingConfig;
    @Inject
    SellersOtherItemsPresenter presenter;
    @Bind({2131624241})
    RecyclerView recyclerView;
    @Bind({2131624192})
    android.view.View rootView;
    private OnPagingScrollListener scrollListener;
    @Bind({2131624240})
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind({2131624104})
    Toolbar toolbar;

    public static Intent createIntent(Context context, SellerData sellerData) {
        Intent intent = new Intent(context, SellersOtherItemsActivity.class);
        intent.putExtra(StatefulActivity.EXTRA_SELLER_DATA, sellerData);
        return intent;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2130903111);
        getComponent().inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(this.toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        initPaging();
        this.swipeRefreshLayout.setOnRefreshListener(SellersOtherItemsActivity$$Lambda$1.lambdaFactory$(this));
        if (bundle == null) {
            this.presenter.onLoad();
        }
    }

    /* synthetic */ void lambda$onCreate$0() {
        this.presenter.onRefresh();
    }

    protected void onResume() {
        super.onResume();
        this.presenter.attachView(this);
    }

    protected void onPause() {
        this.presenter.detachView();
        super.onPause();
    }

    protected void onStop() {
        if (isFinishing()) {
            ComponentsManager.get().removeBaseComponent(SellersOtherItemsComponent.KEY);
            this.presenter.destroy();
        }
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.pagedAdapter.unwrap();
    }

    private SellersOtherItemsComponent getComponent() {
        SellersOtherItemsComponent sellersOtherItemsComponent = (SellersOtherItemsComponent) ComponentsManager.get().getBaseComponent(SellersOtherItemsComponent.KEY);
        if (sellersOtherItemsComponent != null) {
            return sellersOtherItemsComponent;
        }
        Object build = DaggerSellersOtherItemsComponent.builder().applicationComponent(ComponentsManager.get().getAppComponent()).sellersOtherItemsModule(new SellersOtherItemsModule((SellerData) getIntent().getSerializableExtra(StatefulActivity.EXTRA_SELLER_DATA))).build();
        ComponentsManager.get().putBaseComponent(SellersOtherItemsComponent.KEY, build);
        return build;
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

    public void showTitle(String str) {
        this.toolbar.setTitle(str);
    }

    public void showSubtitle(String str) {
        this.toolbar.setSubtitle(str);
    }

    public void showAds(List<SellerAd> list) {
        this.pagedAdapter.setItems(list);
        this.scrollListener.resetPagingArea();
    }

    public void showError(String str) {
        Snackbar.make(this.rootView, str, 0).show();
    }

    public void showMainLoading(boolean z) {
        this.mainLoading.setVisibility(z ? 0 : 8);
    }

    public void showRefreshLoading(boolean z) {
        this.swipeRefreshLayout.setRefreshing(z);
    }

    public void showNextPageLoading(boolean z) {
        this.pagedAdapter.hasNextPage(z);
    }

    public void showEmpty(String str) {
        this.emptyView.setVisibility(StringUtils.isBlank(str) ? 8 : 0);
        if (!StringUtils.isBlank(str)) {
            this.emptyView.setText(str);
        }
    }

    public void openVIP(String str) {
        Intent createIntent = VIPActivity.createIntent(this, Long.parseLong(str));
        createIntent.putExtra(VIPActivity.IS_FROM_SELLER_OTHER_ITEMS, true);
        startActivity(createIntent);
    }

    private void initPaging() {
        this.pagedAdapter = new PagingItemsAdapter();
        this.scrollListener = new OnPagingScrollListener(this.pagingConfig, new OnPagingListener() {
            public void onEnteredPagingArea(RecyclerView recyclerView) {
                SellersOtherItemsActivity.this.presenter.onBottomPageReached();
            }

            public void onLeftPagingArea(RecyclerView recyclerView) {
            }
        });
        this.recyclerView.addOnScrollListener(this.scrollListener);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this, 1, false));
        this.pagedAdapter.wrap(new SellerAdsAdapter(this, SellersOtherItemsActivity$$Lambda$2.lambdaFactory$(this)));
        this.recyclerView.setAdapter(this.pagedAdapter);
    }

    /* synthetic */ void lambda$initPaging$1(String str) {
        this.presenter.onAdClicked(str);
    }
}
