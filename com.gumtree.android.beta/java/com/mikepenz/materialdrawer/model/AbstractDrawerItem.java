package com.mikepenz.materialdrawer.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mikepenz.fastadapter.utils.IdDistributor;
import com.mikepenz.fastadapter.utils.ViewHolderFactory;
import com.mikepenz.materialdrawer.Drawer.OnDrawerItemClickListener;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.OnPostBindViewListener;
import com.mikepenz.materialdrawer.model.interfaces.Selectable;
import com.mikepenz.materialdrawer.model.interfaces.Tagable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractDrawerItem<T, VH extends ViewHolder> implements IDrawerItem<T, VH>, Selectable<T>, Tagable<T> {
    protected boolean mEnabled = true;
    private boolean mExpanded = false;
    protected long mIdentifier = -1;
    public OnDrawerItemClickListener mOnDrawerItemClickListener = null;
    protected OnPostBindViewListener mOnPostBindViewListener = null;
    private IDrawerItem mParent;
    protected boolean mSelectable = true;
    protected boolean mSelected = false;
    protected List<IDrawerItem> mSubItems;
    protected Object mTag;

    public abstract ViewHolderFactory<VH> getFactory();

    public T withIdentifier(long j) {
        this.mIdentifier = j;
        return this;
    }

    public long getIdentifier() {
        return this.mIdentifier;
    }

    public T withTag(Object obj) {
        this.mTag = obj;
        return this;
    }

    public Object getTag() {
        return this.mTag;
    }

    public T withEnabled(boolean z) {
        this.mEnabled = z;
        return this;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public T withSetSelected(boolean z) {
        this.mSelected = z;
        return this;
    }

    public boolean isSelected() {
        return this.mSelected;
    }

    public T withSelectable(boolean z) {
        this.mSelectable = z;
        return this;
    }

    public boolean isSelectable() {
        return this.mSelectable;
    }

    public OnDrawerItemClickListener getOnDrawerItemClickListener() {
        return this.mOnDrawerItemClickListener;
    }

    public T withOnDrawerItemClickListener(OnDrawerItemClickListener onDrawerItemClickListener) {
        this.mOnDrawerItemClickListener = onDrawerItemClickListener;
        return this;
    }

    public OnPostBindViewListener getOnPostBindViewListener() {
        return this.mOnPostBindViewListener;
    }

    public T withPostOnBindViewListener(OnPostBindViewListener onPostBindViewListener) {
        this.mOnPostBindViewListener = onPostBindViewListener;
        return this;
    }

    public void onPostBindView(IDrawerItem iDrawerItem, View view) {
        if (this.mOnPostBindViewListener != null) {
            this.mOnPostBindViewListener.onBindView(iDrawerItem, view);
        }
    }

    public IDrawerItem getParent() {
        return this.mParent;
    }

    public IDrawerItem withParent(IDrawerItem iDrawerItem) {
        this.mParent = iDrawerItem;
        return this;
    }

    public T withSubItems(List<IDrawerItem> list) {
        this.mSubItems = IdDistributor.checkIds(list);
        return this;
    }

    public T withSubItems(IDrawerItem... iDrawerItemArr) {
        if (this.mSubItems == null) {
            this.mSubItems = new ArrayList();
        }
        Collections.addAll(this.mSubItems, IdDistributor.checkIds(iDrawerItemArr));
        return this;
    }

    public List<IDrawerItem> getSubItems() {
        return this.mSubItems;
    }

    public T withIsExpanded(boolean z) {
        this.mExpanded = z;
        return this;
    }

    public boolean isExpanded() {
        return this.mExpanded;
    }

    public boolean isAutoExpanding() {
        return true;
    }

    public View generateView(Context context) {
        ViewHolder create = getFactory().create(LayoutInflater.from(context).inflate(getLayoutRes(), null, false));
        bindView(create, Collections.emptyList());
        return create.itemView;
    }

    public View generateView(Context context, ViewGroup viewGroup) {
        ViewHolder create = getFactory().create(LayoutInflater.from(context).inflate(getLayoutRes(), viewGroup, false));
        bindView(create, Collections.emptyList());
        return create.itemView;
    }

    public void unbindView(VH vh) {
    }

    public VH getViewHolder(ViewGroup viewGroup) {
        return getFactory().create(LayoutInflater.from(viewGroup.getContext()).inflate(getLayoutRes(), viewGroup, false));
    }

    public boolean equals(long j) {
        return j == this.mIdentifier;
    }

    public boolean equals(int i) {
        return ((long) i) == this.mIdentifier;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (this.mIdentifier != ((AbstractDrawerItem) obj).mIdentifier) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Long.valueOf(this.mIdentifier).hashCode();
    }
}
