package com.gumtree.android.vip;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.sellersotheritems.SellersOtherItemsActivity;
import com.gumtree.android.sellersotheritems.models.SellerData;
import com.gumtree.android.userprofile.services.UserProfileService;
import com.gumtree.android.vip.model.Advert;
import com.gumtree.android.vip.model.VIPRefreshFragment;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

public class VIPSellerOtherItemsFragment extends BaseFragment implements VIPRefreshFragment {
    private static final String ADVERTISER = "Advertiser";
    private static final String ONE = "1";
    private String lastUserActiveAdsCount;
    private String sellerId;
    private String sellerName = BuildConfig.FLAVOR;
    private View sellerOtherItemsLayout;
    private String sellerPostingSince;
    private TextView textDisplayed;
    @Inject
    UserProfileService userProfileService;

    public static VIPSellerOtherItemsFragment newInstance() {
        return new VIPSellerOtherItemsFragment();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        GumtreeApplication.component().inject(this);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130903251, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.textDisplayed = (TextView) view.findViewById(2131624644);
        this.sellerOtherItemsLayout = view.findViewById(2131624643);
    }

    private void openSellerOtherItems() {
        startActivity(SellersOtherItemsActivity.createIntent(getActivity(), SellerData.builder().id(this.sellerId).userName(this.sellerName).numAds(StringUtils.isNotEmpty(this.lastUserActiveAdsCount) ? Integer.parseInt(this.lastUserActiveAdsCount) : 0).postingSince(this.sellerPostingSince).build()));
    }

    public void refreshContent(Advert advert) {
        this.sellerPostingSince = advert.getContactPostingSince();
        if (this.sellerId == null && advert.getAccountId() != null) {
            this.sellerId = advert.getAccountId();
            getLoaderManager().restartLoader(25, null, new UserProfileLoaderCallbacks(this, this.sellerId, advert.getContactName())).forceLoad();
        }
        showSellersOtherItemsText(advert.getContactName(), this.lastUserActiveAdsCount);
        setSellerOtherItemsClickable(this.lastUserActiveAdsCount);
    }

    private void showSellersOtherItemsText(@Nullable String str, @Nullable String str2) {
        if (!StringUtils.isNotBlank(str)) {
            str = ADVERTISER;
        }
        this.sellerName = str;
        if (StringUtils.isEmpty(str2)) {
            this.textDisplayed.setText(String.format(getString(2131165946), new Object[]{this.sellerName}));
            return;
        }
        int parseInt = Integer.parseInt(str2);
        this.textDisplayed.setText(this.context.getResources().getQuantityString(2131689481, parseInt, new Object[]{this.sellerName, Integer.valueOf(parseInt)}));
    }

    private void setSellerOtherItemsClickable(@Nullable String str) {
        if (StringUtils.isNotEmpty(str) && ONE.equals(str)) {
            this.sellerOtherItemsLayout.setClickable(false);
        } else {
            this.sellerOtherItemsLayout.setOnClickListener(VIPSellerOtherItemsFragment$$Lambda$1.lambdaFactory$(this));
        }
    }

    /* synthetic */ void lambda$setSellerOtherItemsClickable$0(View view) {
        openSellerOtherItems();
    }
}
