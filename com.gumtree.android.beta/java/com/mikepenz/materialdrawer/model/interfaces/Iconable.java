package com.mikepenz.materialdrawer.model.interfaces;

import android.graphics.drawable.Drawable;
import com.mikepenz.iconics.typeface.IIcon;
import com.mikepenz.materialdrawer.holder.ImageHolder;

public interface Iconable<T> {
    ImageHolder getIcon();

    T withIcon(Drawable drawable);

    T withIcon(IIcon iIcon);

    T withIcon(ImageHolder imageHolder);
}
