package com.gumtree.android.ad.search.presenters.refine;

import com.gumtree.android.mvp.Presenter;
import com.gumtree.android.mvp.PresenterView;

public interface RefineSearchPresenter extends Presenter<View> {

    public interface View extends PresenterView {
        void refineCategory();

        void refineLocation();

        void showSearchResults();
    }

    void onRefineCategory();

    void onRefineLocation();

    void onSubmitSearch();
}
