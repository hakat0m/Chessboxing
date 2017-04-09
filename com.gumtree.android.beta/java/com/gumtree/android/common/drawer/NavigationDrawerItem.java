package com.gumtree.android.common.drawer;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import com.mikepenz.fastadapter.utils.ViewHolderFactory;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.BaseDescribeableDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.ColorfulBadgeable;
import java.util.List;

public class NavigationDrawerItem extends BaseDescribeableDrawerItem<NavigationDrawerItem, ViewHolder> implements ColorfulBadgeable<NavigationDrawerItem> {
    private static final int PRIMARY_ITEM = 0;
    private final int iconColour;
    protected StringHolder mBadge;
    protected BadgeStyle mBadgeStyle = new BadgeStyle();

    public NavigationDrawerItem(NavigationItem navigationItem, int i, int i2, Typeface typeface) {
        this.iconColour = i;
        withName(navigationItem.getTitleRes());
        withIcon(navigationItem.getIconRes());
        withTag(navigationItem);
        withIconColor(i);
        withSelectedTextColor(i2);
        withTypeface(typeface);
    }

    public NavigationDrawerItem withBadge(StringHolder stringHolder) {
        this.mBadge = stringHolder;
        return this;
    }

    public NavigationDrawerItem withBadge(String str) {
        this.mBadge = new StringHolder(str);
        return this;
    }

    public NavigationDrawerItem withBadge(@StringRes int i) {
        this.mBadge = new StringHolder(i);
        return this;
    }

    public NavigationDrawerItem withBadgeStyle(BadgeStyle badgeStyle) {
        this.mBadgeStyle = badgeStyle;
        return this;
    }

    public StringHolder getBadge() {
        return this.mBadge;
    }

    public BadgeStyle getBadgeStyle() {
        return this.mBadgeStyle;
    }

    public int getType() {
        return 0;
    }

    @LayoutRes
    public int getLayoutRes() {
        return 2130903378;
    }

    public void bindView(ViewHolder viewHolder, List list) {
        Context context = viewHolder.itemView.getContext();
        bindViewHelper(viewHolder);
        viewHolder.getDescription().setTypeface(null);
        viewHolder.getIcon().getDrawable();
        DrawableCompat.setTint(DrawableCompat.wrap(viewHolder.getIcon().getDrawable()), this.iconColour);
        if (com.mikepenz.materialize.holder.StringHolder.applyToOrHide(this.mBadge, ViewHolder.access$000(viewHolder))) {
            this.mBadgeStyle.style(ViewHolder.access$000(viewHolder), getTextColorStateList(getColor(context), getSelectedTextColor(context)));
            ViewHolder.access$100(viewHolder).setVisibility(0);
        } else {
            ViewHolder.access$100(viewHolder).setVisibility(8);
        }
        if (getTypeface() != null) {
            ViewHolder.access$000(viewHolder).setTypeface(getTypeface());
        }
        onPostBindView(this, viewHolder.itemView);
    }

    public ViewHolderFactory getFactory() {
        return new ItemFactory(null);
    }
}
