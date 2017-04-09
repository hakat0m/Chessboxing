package com.gumtree.android.postad.confirmation.di;

import com.gumtree.android.postad.confirmation.PostAdConfirmationScreenPresenter;
import com.gumtree.android.postad.confirmation.dialogs.DefaultFailedPostAdDialogPresenter;
import com.gumtree.android.postad.confirmation.dialogs.FailedPostAdDialogPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class FailedPostAdDialogModule {
    @Provides
    @FailedPostAdDialogScope
    FailedPostAdDialogPresenter provideFailedPostAdDialogPresenter(PostAdConfirmationScreenPresenter postAdConfirmationScreenPresenter) {
        return new DefaultFailedPostAdDialogPresenter(postAdConfirmationScreenPresenter);
    }
}
