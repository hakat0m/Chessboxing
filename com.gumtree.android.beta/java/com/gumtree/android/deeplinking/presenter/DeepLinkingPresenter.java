package com.gumtree.android.deeplinking.presenter;

import com.gumtree.android.mvp.Presenter;

public interface DeepLinkingPresenter extends Presenter<DeepLinkingView> {
    void analyse();
}
