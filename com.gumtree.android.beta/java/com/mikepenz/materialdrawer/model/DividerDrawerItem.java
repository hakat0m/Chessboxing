package com.mikepenz.materialdrawer.model;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.view.ViewCompat;
import com.mikepenz.fastadapter.utils.ViewHolderFactory;
import com.mikepenz.materialdrawer.R;
import com.mikepenz.materialize.util.UIUtils;
import java.util.List;

public class DividerDrawerItem extends AbstractDrawerItem<DividerDrawerItem, ViewHolder> {
    public int getType() {
        return R.id.material_drawer_item_divider;
    }

    @LayoutRes
    public int getLayoutRes() {
        return R.layout.material_drawer_item_divider;
    }

    public void bindView(ViewHolder viewHolder, List list) {
        Context context = viewHolder.itemView.getContext();
        viewHolder.itemView.setId(hashCode());
        ViewHolder.access$000(viewHolder).setClickable(false);
        ViewHolder.access$000(viewHolder).setEnabled(false);
        ViewHolder.access$000(viewHolder).setMinimumHeight(1);
        ViewCompat.setImportantForAccessibility(ViewHolder.access$000(viewHolder), 2);
        ViewHolder.access$100(viewHolder).setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(context, R.attr.material_drawer_divider, R.color.material_drawer_divider));
        onPostBindView(this, viewHolder.itemView);
    }

    public ViewHolderFactory<ViewHolder> getFactory() {
        return new ItemFactory();
    }
}
