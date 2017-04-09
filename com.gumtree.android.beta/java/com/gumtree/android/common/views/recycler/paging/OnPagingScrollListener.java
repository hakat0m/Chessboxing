package com.gumtree.android.common.views.recycler.paging;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;
import com.gumtree.android.common.paging.PagingConfig;

public class OnPagingScrollListener extends OnScrollListener {
    private boolean isInPagingArea;
    private final OnPagingListener onPagingListener;
    private int pagingThreshold;

    public interface OnPagingListener {
        void onEnteredPagingArea(RecyclerView recyclerView);

        void onLeftPagingArea(RecyclerView recyclerView);
    }

    public OnPagingScrollListener(PagingConfig pagingConfig, OnPagingListener onPagingListener) {
        this.onPagingListener = onPagingListener;
        setPagingThreshold(pagingConfig.getPagingThreshold());
    }

    public void onScrolled(RecyclerView recyclerView, int i, int i2) {
        super.onScrolled(recyclerView, i, i2);
        LayoutManager layoutManager = recyclerView.getLayoutManager();
        int childCount = layoutManager.getChildCount();
        int itemCount = layoutManager.getItemCount();
        View childAt = layoutManager.getChildAt(0);
        if (childAt != null) {
            if (layoutManager.getPosition(childAt) + childCount >= itemCount - this.pagingThreshold) {
                if (!this.isInPagingArea) {
                    this.onPagingListener.onEnteredPagingArea(recyclerView);
                }
                this.isInPagingArea = true;
                return;
            }
            if (this.isInPagingArea) {
                this.onPagingListener.onLeftPagingArea(recyclerView);
            }
            this.isInPagingArea = false;
        }
    }

    public void resetPagingArea() {
        this.isInPagingArea = false;
    }

    public int getPagingThreshold() {
        return this.pagingThreshold;
    }

    public void setPagingThreshold(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("Paging threshold must be >= 0.");
        }
        this.pagingThreshold = i;
        this.isInPagingArea = false;
    }
}
