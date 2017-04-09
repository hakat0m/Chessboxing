package com.mikepenz.materialdrawer.model.interfaces;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import com.mikepenz.fastadapter.IExpandable;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.ISubItem;
import java.util.List;

public interface IDrawerItem<T, VH extends ViewHolder> extends IExpandable<T, IDrawerItem>, IItem<T, VH>, ISubItem<IDrawerItem, IDrawerItem> {
    void bindView(VH vh, List<Object> list);

    boolean equals(long j);

    View generateView(Context context);

    View generateView(Context context, ViewGroup viewGroup);

    int getLayoutRes();

    Object getTag();

    int getType();

    VH getViewHolder(ViewGroup viewGroup);

    boolean isEnabled();

    boolean isSelectable();

    boolean isSelected();

    void unbindView(VH vh);

    T withSelectable(boolean z);

    T withSetSelected(boolean z);
}
