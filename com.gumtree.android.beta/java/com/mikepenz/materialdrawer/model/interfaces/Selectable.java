package com.mikepenz.materialdrawer.model.interfaces;

public interface Selectable<T> {
    boolean isSelectable();

    T withSelectable(boolean z);
}
