package com.gumtree.android.mvp;

public interface Presenter<T extends PresenterView> {
    void attachView(T t);

    void destroy();

    void detachView();
}
