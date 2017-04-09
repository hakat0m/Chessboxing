package com.mikepenz.materialdrawer.model.interfaces;

import com.mikepenz.materialdrawer.holder.StringHolder;

public interface Badgeable<T> {
    StringHolder getBadge();

    T withBadge(int i);

    T withBadge(StringHolder stringHolder);

    T withBadge(String str);
}
