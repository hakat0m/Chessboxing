package com.gumtree.android.postad.confirmation.di;

import com.gumtree.android.dagger.components.BaseComponent;
import com.gumtree.android.postad.confirmation.dialogs.SuccessPostDialog;
import dagger.Component;

@SuccessPostDialogScope
@Component(dependencies = {PostAdConfirmationScreenComponent.class}, modules = {SuccessPostDialogModule.class})
public interface SuccessPostDialogComponent extends BaseComponent {
    public static final String KEY = SuccessPostDialogComponent.class.getSimpleName();

    void inject(SuccessPostDialog successPostDialog);
}
