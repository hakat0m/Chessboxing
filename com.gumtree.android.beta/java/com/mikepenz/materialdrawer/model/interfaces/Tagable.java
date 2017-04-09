package com.mikepenz.materialdrawer.model.interfaces;

public interface Tagable<T> {
    Object getTag();

    T withTag(Object obj);
}
