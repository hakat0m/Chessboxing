package com.mikepenz.materialdrawer.model;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Pair;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.materialdrawer.R;
import com.mikepenz.materialdrawer.holder.ColorHolder;
import com.mikepenz.materialdrawer.holder.ImageHolder;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.interfaces.Iconable;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.mikepenz.materialdrawer.model.interfaces.Tagable;
import com.mikepenz.materialdrawer.model.interfaces.Typefaceable;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;

public abstract class BaseDrawerItem<T, VH extends ViewHolder> extends AbstractDrawerItem<T, VH> implements Iconable<T>, Nameable<T>, Tagable<T>, Typefaceable<T> {
    protected Pair<Integer, ColorStateList> colorStateList;
    protected ColorHolder disabledIconColor;
    protected ColorHolder disabledTextColor;
    protected ImageHolder icon;
    protected ColorHolder iconColor;
    protected boolean iconTinted = false;
    protected int level = 1;
    protected StringHolder name;
    protected ColorHolder selectedColor;
    protected ImageHolder selectedIcon;
    protected ColorHolder selectedIconColor;
    protected ColorHolder selectedTextColor;
    protected ColorHolder textColor;
    protected Typeface typeface = null;

    public T withIcon(ImageHolder imageHolder) {
        this.icon = imageHolder;
        return this;
    }

    public T withIcon(Drawable drawable) {
        this.icon = new ImageHolder(drawable);
        return this;
    }

    public T withIcon(@DrawableRes int i) {
        this.icon = new ImageHolder(i);
        return this;
    }

    public T withSelectedIcon(Drawable drawable) {
        this.selectedIcon = new ImageHolder(drawable);
        return this;
    }

    public T withSelectedIcon(@DrawableRes int i) {
        this.selectedIcon = new ImageHolder(i);
        return this;
    }

    public T withIcon(IIcon iIcon) {
        this.icon = new ImageHolder(iIcon);
        if (VERSION.SDK_INT >= 21) {
            this.selectedIcon = new ImageHolder(iIcon);
        } else {
            withIconTintingEnabled(true);
        }
        return this;
    }

    public T withName(StringHolder stringHolder) {
        this.name = stringHolder;
        return this;
    }

    public T withName(String str) {
        this.name = new StringHolder(str);
        return this;
    }

    public T withName(@StringRes int i) {
        this.name = new StringHolder(i);
        return this;
    }

    public T withSelectedColor(@ColorInt int i) {
        this.selectedColor = ColorHolder.fromColor(i);
        return this;
    }

    public T withSelectedColorRes(@ColorRes int i) {
        this.selectedColor = ColorHolder.fromColorRes(i);
        return this;
    }

    public T withTextColor(@ColorInt int i) {
        this.textColor = ColorHolder.fromColor(i);
        return this;
    }

    public T withTextColorRes(@ColorRes int i) {
        this.textColor = ColorHolder.fromColorRes(i);
        return this;
    }

    public T withSelectedTextColor(@ColorInt int i) {
        this.selectedTextColor = ColorHolder.fromColor(i);
        return this;
    }

    public T withSelectedTextColorRes(@ColorRes int i) {
        this.selectedTextColor = ColorHolder.fromColorRes(i);
        return this;
    }

    public T withDisabledTextColor(@ColorInt int i) {
        this.disabledTextColor = ColorHolder.fromColor(i);
        return this;
    }

    public T withDisabledTextColorRes(@ColorRes int i) {
        this.disabledTextColor = ColorHolder.fromColorRes(i);
        return this;
    }

    public T withIconColor(@ColorInt int i) {
        this.iconColor = ColorHolder.fromColor(i);
        return this;
    }

    public T withIconColorRes(@ColorRes int i) {
        this.iconColor = ColorHolder.fromColorRes(i);
        return this;
    }

    public T withSelectedIconColor(@ColorInt int i) {
        this.selectedIconColor = ColorHolder.fromColor(i);
        return this;
    }

    public T withSelectedIconColorRes(@ColorRes int i) {
        this.selectedIconColor = ColorHolder.fromColorRes(i);
        return this;
    }

    public T withDisabledIconColor(@ColorInt int i) {
        this.disabledIconColor = ColorHolder.fromColor(i);
        return this;
    }

    public T withDisabledIconColorRes(@ColorRes int i) {
        this.disabledIconColor = ColorHolder.fromColorRes(i);
        return this;
    }

    public T withIconTintingEnabled(boolean z) {
        this.iconTinted = z;
        return this;
    }

    @Deprecated
    public T withIconTinted(boolean z) {
        this.iconTinted = z;
        return this;
    }

    @Deprecated
    public T withTintSelectedIcon(boolean z) {
        return withIconTintingEnabled(z);
    }

    public T withTypeface(Typeface typeface) {
        this.typeface = typeface;
        return this;
    }

    public T withLevel(int i) {
        this.level = i;
        return this;
    }

    public ColorHolder getSelectedColor() {
        return this.selectedColor;
    }

    public ColorHolder getTextColor() {
        return this.textColor;
    }

    public ColorHolder getSelectedTextColor() {
        return this.selectedTextColor;
    }

    public ColorHolder getDisabledTextColor() {
        return this.disabledTextColor;
    }

    public boolean isIconTinted() {
        return this.iconTinted;
    }

    public ImageHolder getIcon() {
        return this.icon;
    }

    public ImageHolder getSelectedIcon() {
        return this.selectedIcon;
    }

    public StringHolder getName() {
        return this.name;
    }

    public ColorHolder getDisabledIconColor() {
        return this.disabledIconColor;
    }

    public ColorHolder getSelectedIconColor() {
        return this.selectedIconColor;
    }

    public ColorHolder getIconColor() {
        return this.iconColor;
    }

    public Typeface getTypeface() {
        return this.typeface;
    }

    public int getLevel() {
        return this.level;
    }

    protected int getSelectedColor(Context context) {
        return ColorHolder.color(getSelectedColor(), context, R.attr.material_drawer_selected, R.color.material_drawer_selected);
    }

    protected int getColor(Context context) {
        if (isEnabled()) {
            return ColorHolder.color(getTextColor(), context, R.attr.material_drawer_primary_text, R.color.material_drawer_primary_text);
        }
        return ColorHolder.color(getDisabledTextColor(), context, R.attr.material_drawer_hint_text, R.color.material_drawer_hint_text);
    }

    protected int getSelectedTextColor(Context context) {
        return ColorHolder.color(getSelectedTextColor(), context, R.attr.material_drawer_selected_text, R.color.material_drawer_selected_text);
    }

    public int getIconColor(Context context) {
        if (isEnabled()) {
            return ColorHolder.color(getIconColor(), context, R.attr.material_drawer_primary_icon, R.color.material_drawer_primary_icon);
        }
        return ColorHolder.color(getDisabledIconColor(), context, R.attr.material_drawer_hint_icon, R.color.material_drawer_hint_icon);
    }

    protected int getSelectedIconColor(Context context) {
        return ColorHolder.color(getSelectedIconColor(), context, R.attr.material_drawer_selected_text, R.color.material_drawer_selected_text);
    }

    protected ColorStateList getTextColorStateList(@ColorInt int i, @ColorInt int i2) {
        if (this.colorStateList == null || i + i2 != ((Integer) this.colorStateList.first).intValue()) {
            this.colorStateList = new Pair(Integer.valueOf(i + i2), DrawerUIUtils.getTextColorStateList(i, i2));
        }
        return (ColorStateList) this.colorStateList.second;
    }
}
