package com.gumtree.android.postad.confirmation.di;

import com.gumtree.android.postad.confirmation.PostAdConfirmationScreenPresenter;
import com.gumtree.android.postad.confirmation.dialogs.DefaultPostOnHoldDialogPresenter;
import com.gumtree.android.postad.confirmation.dialogs.PostOnHoldDialogPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class PostOnHoldDialogModule {
    @Provides
    @PostOnHoldDialogScope
    PostOnHoldDialogPresenter providePostOnHoldDialogPresenter(PostAdConfirmationScreenPresenter postAdConfirmationScreenPresenter) {
        return new DefaultPostOnHoldDialogPresenter(postAdConfirmationScreenPresenter);
    }
}
