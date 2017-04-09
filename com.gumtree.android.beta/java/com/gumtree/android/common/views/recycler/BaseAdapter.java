package com.gumtree.android.common.views.recycler;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BaseAdapter<VH extends ViewHolder, T> extends Adapter<VH> {
    private final List<T> items = new ArrayList();

    public int getItemCount() {
        return this.items.size();
    }

    public void addItems(List<T> list) {
        if (list != null) {
            this.items.addAll(list);
            notifyItemRangeInserted(this.items.size(), list.size());
        }
    }

    public List<T> getItems() {
        return this.items;
    }

    public void setItems(List<T> list) {
        int size = this.items.size();
        if (list != null) {
            this.items.clear();
            this.items.addAll(list);
        }
        if (size == 0) {
            notifyItemRangeInserted(0, this.items.size());
        } else {
            notifyDataSetChanged();
        }
    }

    public T getItem(int i) {
        return this.items.get(i);
    }

    public T removeItem(int i) {
        T remove = this.items.remove(i);
        notifyItemRemoved(i);
        return remove;
    }

    public void removeItems(int i, int i2) {
        if (i >= 0 && i2 != 0) {
            for (int i3 = (i + i2) - 1; i3 >= i; i3--) {
                this.items.remove(i3);
            }
            notifyItemRangeRemoved(i, i2);
        }
    }

    public void clear() {
        this.items.clear();
        notifyDataSetChanged();
    }

    public T removeItem(T t) {
        if (t == null) {
            return null;
        }
        int indexOf = this.items.indexOf(t);
        if (indexOf >= 0) {
            return removeItem(indexOf);
        }
        return t;
    }

    public void updateItem(T t) {
        if (t != null) {
            int indexOf = this.items.indexOf(t);
            if (indexOf >= 0) {
                this.items.remove(indexOf);
                this.items.add(indexOf, t);
                notifyDataSetChanged();
            }
        }
    }

    public void insertItem(int i, T t) {
        this.items.add(i, t);
        notifyItemInserted(i);
    }

    public void insertItems(int i, Collection<T> collection) {
        if (collection != null) {
            this.items.addAll(i, collection);
            notifyItemRangeInserted(i, collection.size());
        }
    }

    public int getItemPosition(T t) {
        for (int i = 0; i < this.items.size(); i++) {
            if (this.items.get(i).equals(t)) {
                return i;
            }
        }
        return -1;
    }
}
