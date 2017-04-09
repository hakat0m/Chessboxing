package com.mikepenz.materialdrawer.model;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import com.mikepenz.materialdrawer.holder.ColorHolder;
import com.mikepenz.materialdrawer.holder.ImageHolder;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;

public abstract class BaseDescribeableDrawerItem<T, VH extends BaseViewHolder> extends BaseDrawerItem<T, VH> {
    private StringHolder description;
    private ColorHolder descriptionTextColor;

    public T withDescription(String str) {
        this.description = new StringHolder(str);
        return this;
    }

    public T withDescription(@StringRes int i) {
        this.description = new StringHolder(i);
        return this;
    }

    public T withDescriptionTextColor(@ColorInt int i) {
        this.descriptionTextColor = ColorHolder.fromColor(i);
        return this;
    }

    public T withDescriptionTextColorRes(@ColorRes int i) {
        this.descriptionTextColor = ColorHolder.fromColorRes(i);
        return this;
    }

    public StringHolder getDescription() {
        return this.description;
    }

    public ColorHolder getDescriptionTextColor() {
        return this.descriptionTextColor;
    }

    protected void bindViewHelper(BaseViewHolder baseViewHolder) {
        Context context = baseViewHolder.itemView.getContext();
        baseViewHolder.itemView.setId(hashCode());
        baseViewHolder.itemView.setSelected(isSelected());
        baseViewHolder.itemView.setEnabled(isEnabled());
        baseViewHolder.itemView.setTag(this);
        int selectedColor = getSelectedColor(context);
        ColorStateList textColorStateList = getTextColorStateList(getColor(context), getSelectedTextColor(context));
        int iconColor = getIconColor(context);
        int selectedIconColor = getSelectedIconColor(context);
        UIUtils.setBackground(baseViewHolder.view, UIUtils.getSelectableBackground(context, selectedColor, true));
        com.mikepenz.materialize.holder.StringHolder.applyTo(getName(), baseViewHolder.name);
        com.mikepenz.materialize.holder.StringHolder.applyToOrHide(getDescription(), baseViewHolder.description);
        baseViewHolder.name.setTextColor(textColorStateList);
        ColorHolder.applyToOr(getDescriptionTextColor(), baseViewHolder.description, textColorStateList);
        if (getTypeface() != null) {
            baseViewHolder.name.setTypeface(getTypeface());
            baseViewHolder.description.setTypeface(getTypeface());
        }
        Drawable decideIcon = ImageHolder.decideIcon(getIcon(), context, iconColor, isIconTinted(), 1);
        if (decideIcon != null) {
            ImageHolder.applyMultiIconTo(decideIcon, iconColor, ImageHolder.decideIcon(getSelectedIcon(), context, selectedIconColor, isIconTinted(), 1), selectedIconColor, isIconTinted(), baseViewHolder.icon);
        } else {
            ImageHolder.applyDecidedIconOrSetGone(getIcon(), baseViewHolder.icon, iconColor, isIconTinted(), 1);
        }
        DrawerUIUtils.setDrawerVerticalPadding(baseViewHolder.view, this.level);
    }
}
