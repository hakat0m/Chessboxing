package com.gumtree.android.vip.bing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.vip.model.Advert;
import com.gumtree.android.vip.model.VIPRefreshFragment;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class BingAdFragment extends BaseFragment implements VIPRefreshFragment {
    private static final String EXTRA_AD_ID = "vipId";
    @Bind({2131624602})
    BingAdVIPView adView1;
    @Bind({2131624603})
    BingAdVIPView adView2;
    @Bind({2131624604})
    BingAdVIPView adView3;
    @Inject
    BingVIPCache bingVIPCache;
    @Inject
    EventBus eventBus;

    public static BingAdFragment newInstance(long j) {
        BingAdFragment bingAdFragment = new BingAdFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_AD_ID, j);
        bingAdFragment.setArguments(bundle);
        return bingAdFragment;
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        GumtreeApplication.component().inject(this);
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View inflate = layoutInflater.inflate(2130903241, viewGroup, false);
        ButterKnife.bind(this, inflate);
        return inflate;
    }

    public void refreshContent(Advert advert) {
        BingVIPIntentService.fetchPage(advert);
        updateBingAdViews();
    }

    private void updateBingAdViews() {
        this.adView1.updateView(0);
        this.adView2.updateView(1);
        this.adView3.updateView(2);
    }

    @Subscribe
    public void onBingVIPLoadEvent(OnBingVIPLoadEvent onBingVIPLoadEvent) {
        updateBingAdViews();
    }

    public void onStart() {
        super.onStart();
        this.eventBus.register(this);
    }

    public void onStop() {
        this.eventBus.unregister(this);
        super.onStop();
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
