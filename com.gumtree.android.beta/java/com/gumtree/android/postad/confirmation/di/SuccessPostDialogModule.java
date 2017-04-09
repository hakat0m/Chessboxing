package com.gumtree.android.postad.confirmation.di;

import com.gumtree.android.postad.confirmation.PostAdConfirmationScreenPresenter;
import com.gumtree.android.postad.confirmation.dialogs.DefaultSuccessPostDialogPresenter;
import com.gumtree.android.postad.confirmation.dialogs.GatedSuccessPostDialogView;
import com.gumtree.android.postad.confirmation.dialogs.SuccessPostDialogPresenter;
import com.gumtree.android.postad.confirmation.models.SuccessPostResult;
import dagger.Module;
import dagger.Provides;

@Module
public class SuccessPostDialogModule {
    private SuccessPostResult postDialogData;

    public SuccessPostDialogModule(SuccessPostResult successPostResult) {
        this.postDialogData = successPostResult;
    }

    @Provides
    @SuccessPostDialogScope
    SuccessPostDialogPresenter provideSuccessPostDialogPresenter(GatedSuccessPostDialogView gatedSuccessPostDialogView, PostAdConfirmationScreenPresenter postAdConfirmationScreenPresenter) {
        return new DefaultSuccessPostDialogPresenter(gatedSuccessPostDialogView, this.postDialogData, postAdConfirmationScreenPresenter);
    }
}
