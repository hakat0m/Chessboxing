package com.mikepenz.materialdrawer.model.interfaces;

import com.mikepenz.materialdrawer.holder.BadgeStyle;

public interface ColorfulBadgeable<T> extends Badgeable<T> {
    BadgeStyle getBadgeStyle();

    T withBadgeStyle(BadgeStyle badgeStyle);
}
