package com.gumtree.android.postad.promote;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.Validate;

class PromotionAdapter extends Adapter<PromotionViewHolder> {
    private List<PromotionFeature> data = Collections.emptyList();
    private final PromotionPresenter presenter;

    public PromotionAdapter(@NonNull PromotionPresenter promotionPresenter) {
        this.presenter = (PromotionPresenter) Validate.notNull(promotionPresenter);
    }

    public void setData(List<PromotionFeature> list) {
        this.data = new ArrayList(list);
        notifyDataSetChanged();
    }

    public PromotionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new PromotionViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(2130903315, viewGroup, false), this.presenter);
    }

    public void onBindViewHolder(PromotionViewHolder promotionViewHolder, int i) {
        promotionViewHolder.fillWith((PromotionFeature) this.data.get(i));
    }

    public int getItemCount() {
        return this.data.size();
    }
}
