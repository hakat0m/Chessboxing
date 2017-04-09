package com.gumtree.android.postad.confirmation.dialogs;

import com.gumtree.android.mvp.PresenterView;

public interface SuccessPostDialogPresenter$View extends PresenterView {
    void showItemImage(String str);

    void showItemPrice(String str);

    void showItemSubTitle(String str);

    void showItemTitle(String str);
}
