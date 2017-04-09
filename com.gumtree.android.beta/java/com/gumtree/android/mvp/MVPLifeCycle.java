package com.gumtree.android.mvp;

import android.app.Activity;

public class MVPLifeCycle {

    public interface PresenterProvider {
        Presenter getPresenter();

        <T extends PresenterView> T getPresenterView();
    }

    public void onAttachView(Presenter presenter, PresenterView presenterView) {
        if (presenter != null && presenterView != null) {
            presenter.attachView(presenterView);
        }
    }

    public void onDetachView(Presenter presenter) {
        if (presenter != null) {
            presenter.detachView();
        }
    }

    public void onDestroy(Activity activity, Presenter presenter) {
        if (presenter != null) {
            onDetachView(presenter);
            if (activity.isFinishing()) {
                presenter.destroy();
            }
        }
    }
}
