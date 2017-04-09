package com.mikepenz.materialdrawer.model.interfaces;

import com.mikepenz.materialdrawer.holder.StringHolder;

public interface Nameable<T> {
    StringHolder getName();

    T withName(int i);

    T withName(StringHolder stringHolder);

    T withName(String str);
}
