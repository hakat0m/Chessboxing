package com.gumtree.android.sellersotheritems;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.gumtree.android.common.utils.DateTimeDataProcessor;
import com.gumtree.android.common.views.recycler.BaseAdapter;
import com.gumtree.android.sellersotheritems.models.SellerAd;
import org.apache.commons.lang3.Validate;

class SellerAdsAdapter extends BaseAdapter<ViewHolder, SellerAd> {
    private Context context;
    @NonNull
    private final SellersOtherItemsClickListener listener;
    private final DateTimeDataProcessor processor = new DateTimeDataProcessor();

    interface SellersOtherItemsClickListener {
        void onAdClicked(String str);
    }

    SellerAdsAdapter(Context context, @NonNull SellersOtherItemsClickListener sellersOtherItemsClickListener) {
        this.context = context;
        this.listener = (SellersOtherItemsClickListener) Validate.notNull(sellersOtherItemsClickListener);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(this, LayoutInflater.from(viewGroup.getContext()).inflate(2130903271, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ViewHolder.access$000(viewHolder, getItems(), i);
    }
}
