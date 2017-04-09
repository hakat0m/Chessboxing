package com.mikepenz.fastadapter;

public interface ISubItem<T, S extends IItem & IExpandable> {
    S getParent();

    T withParent(S s);
}
