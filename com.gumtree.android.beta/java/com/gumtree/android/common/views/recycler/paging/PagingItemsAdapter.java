package com.gumtree.android.common.views.recycler.paging;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import com.gumtree.android.common.views.recycler.BaseAdapter;
import java.util.Collection;
import java.util.List;
import org.apache.commons.lang3.Validate;

public class PagingItemsAdapter<VH extends ViewHolder, T> extends BaseAdapter<ViewHolder, T> {
    private static final int VIEW_TYPE_FOOTER = 1;
    private final AdapterDataObserver dataObserver = new RecyclerObserver(this, null);
    private boolean hasNextPage;
    private BaseAdapter<VH, T> wrapperAdapter;

    public void wrap(@NonNull BaseAdapter<VH, T> baseAdapter) {
        this.wrapperAdapter = (BaseAdapter) Validate.notNull(baseAdapter);
        this.wrapperAdapter.registerAdapterDataObserver(this.dataObserver);
        notifyDataSetChanged();
    }

    public void unwrap() {
        if (this.wrapperAdapter.hasObservers()) {
            this.wrapperAdapter.unregisterAdapterDataObserver(this.dataObserver);
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        switch (i) {
            case VIEW_TYPE_FOOTER /*1*/:
                return new DummyViewHolder(PagingLayout.inflate(viewGroup));
            default:
                return this.wrapperAdapter.onCreateViewHolder(viewGroup, i);
        }
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        switch (getItemViewType(i)) {
            case VIEW_TYPE_FOOTER /*1*/:
                return;
            default:
                this.wrapperAdapter.onBindViewHolder(viewHolder, i);
                return;
        }
    }

    public int getItemCount() {
        if (this.hasNextPage) {
            return this.wrapperAdapter.getItemCount() + VIEW_TYPE_FOOTER;
        }
        return this.wrapperAdapter.getItemCount();
    }

    public void hasNextPage(boolean z) {
        this.hasNextPage = z;
    }

    public int getItemViewType(int i) {
        if (i == this.wrapperAdapter.getItemCount()) {
            return VIEW_TYPE_FOOTER;
        }
        return this.wrapperAdapter.getItemViewType(i);
    }

    public void addItems(List<T> list) {
        this.wrapperAdapter.addItems(list);
    }

    public List<T> getItems() {
        return this.wrapperAdapter.getItems();
    }

    public void setItems(List<T> list) {
        this.wrapperAdapter.setItems(list);
    }

    public T getItem(int i) {
        if (this.hasNextPage && i == getItems().size()) {
            return null;
        }
        return this.wrapperAdapter.getItem(i);
    }

    public T removeItem(int i) {
        if (this.hasNextPage && i == getItems().size()) {
            return null;
        }
        return this.wrapperAdapter.removeItem(i);
    }

    public void removeItems(int i, int i2) {
        if (!this.hasNextPage || i != getItems().size()) {
            this.wrapperAdapter.removeItems(i, i2);
        }
    }

    public void clear() {
        this.wrapperAdapter.clear();
    }

    public T removeItem(T t) {
        return this.wrapperAdapter.removeItem((Object) t);
    }

    public void updateItem(T t) {
        this.wrapperAdapter.updateItem(t);
    }

    public void insertItem(int i, T t) {
        this.wrapperAdapter.insertItem(i, t);
    }

    public void insertItems(int i, Collection<T> collection) {
        this.wrapperAdapter.insertItems(i, collection);
    }

    public int getItemPosition(T t) {
        return this.wrapperAdapter.getItemPosition(t);
    }
}
