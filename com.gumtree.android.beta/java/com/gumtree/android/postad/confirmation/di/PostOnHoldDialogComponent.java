package com.gumtree.android.postad.confirmation.di;

import com.gumtree.android.dagger.components.BaseComponent;
import com.gumtree.android.postad.confirmation.dialogs.PostOnHoldDialog;
import dagger.Component;

@Component(dependencies = {PostAdConfirmationScreenComponent.class}, modules = {PostOnHoldDialogModule.class})
@PostOnHoldDialogScope
public interface PostOnHoldDialogComponent extends BaseComponent {
    public static final String KEY = PostOnHoldDialogComponent.class.getSimpleName();

    void inject(PostOnHoldDialog postOnHoldDialog);
}
