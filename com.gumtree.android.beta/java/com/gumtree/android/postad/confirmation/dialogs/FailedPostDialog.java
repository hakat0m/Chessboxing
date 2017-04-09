package com.gumtree.android.postad.confirmation.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.postad.confirmation.di.DaggerFailedPostAdDialogComponent;
import com.gumtree.android.postad.confirmation.di.FailedPostAdDialogComponent;
import com.gumtree.android.postad.confirmation.di.FailedPostAdDialogModule;
import com.gumtree.android.postad.confirmation.di.PostAdConfirmationScreenComponent;
import com.gumtree.android.postad.confirmation.di.PostOnHoldDialogComponent;
import javax.inject.Inject;

public class FailedPostDialog extends DialogFragment implements FailedPostAdDialogPresenter$View {
    @Inject
    FailedPostAdDialogPresenter presenter;
    View view;

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.view = View.inflate(getContext(), 2130903197, null);
        ButterKnife.bind(this, this.view);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getComponent().inject(this);
    }

    private FailedPostAdDialogComponent getComponent() {
        FailedPostAdDialogComponent failedPostAdDialogComponent = (FailedPostAdDialogComponent) ComponentsManager.get().getBaseComponent(PostOnHoldDialogComponent.KEY);
        if (failedPostAdDialogComponent != null) {
            return failedPostAdDialogComponent;
        }
        Object build = DaggerFailedPostAdDialogComponent.builder().postAdConfirmationScreenComponent((PostAdConfirmationScreenComponent) ComponentsManager.get().getBaseComponent(PostAdConfirmationScreenComponent.KEY)).failedPostAdDialogModule(new FailedPostAdDialogModule()).build();
        ComponentsManager.get().putBaseComponent(FailedPostAdDialogComponent.KEY, build);
        return build;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle bundle) {
        1 1 = new 1(this, getActivity(), 2131362103);
        1.requestWindowFeature(1);
        1.setContentView(this.view);
        1.setCanceledOnTouchOutside(false);
        1.show();
        return 1;
    }

    public void onResume() {
        super.onResume();
        this.presenter.attachView(this);
    }

    @OnClick({2131624463})
    void findOutMore() {
        this.presenter.onFindOutMoreSelected();
        dismiss();
    }

    @OnClick({2131624462})
    void close() {
        this.presenter.onCloseSelected();
        dismiss();
    }

    public void onPause() {
        this.presenter.detachView();
        super.onPause();
    }

    public void onDestroy() {
        super.onDestroy();
        ComponentsManager.get().removeBaseComponent(FailedPostAdDialogComponent.KEY);
        this.presenter.destroy();
    }
}
