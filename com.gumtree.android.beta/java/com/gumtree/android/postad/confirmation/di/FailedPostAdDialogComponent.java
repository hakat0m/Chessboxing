package com.gumtree.android.postad.confirmation.di;

import com.gumtree.android.dagger.components.BaseComponent;
import com.gumtree.android.postad.confirmation.dialogs.FailedPostDialog;
import dagger.Component;

@FailedPostAdDialogScope
@Component(dependencies = {PostAdConfirmationScreenComponent.class}, modules = {FailedPostAdDialogModule.class})
public interface FailedPostAdDialogComponent extends BaseComponent {
    public static final String KEY = FailedPostAdDialogComponent.class.getSimpleName();

    void inject(FailedPostDialog failedPostDialog);
}
