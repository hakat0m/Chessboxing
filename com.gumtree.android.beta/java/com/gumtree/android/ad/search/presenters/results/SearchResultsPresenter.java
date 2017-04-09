package com.gumtree.android.ad.search.presenters.results;

import com.gumtree.android.mvp.Presenter;
import com.gumtree.android.mvp.PresenterView;

public interface SearchResultsPresenter extends Presenter<View> {

    public interface View extends PresenterView {
        void showMessage(String str);
    }
}
