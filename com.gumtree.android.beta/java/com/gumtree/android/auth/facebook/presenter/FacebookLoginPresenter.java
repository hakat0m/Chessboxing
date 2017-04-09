package com.gumtree.android.auth.facebook.presenter;

import com.gumtree.android.mvp.Presenter;

public interface FacebookLoginPresenter extends Presenter<View> {
    void login(String str, String str2, String str3);
}
