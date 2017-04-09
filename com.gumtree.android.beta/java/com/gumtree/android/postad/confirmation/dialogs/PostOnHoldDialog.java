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
import com.gumtree.android.postad.confirmation.di.DaggerPostOnHoldDialogComponent;
import com.gumtree.android.postad.confirmation.di.PostAdConfirmationScreenComponent;
import com.gumtree.android.postad.confirmation.di.PostOnHoldDialogComponent;
import com.gumtree.android.postad.confirmation.di.PostOnHoldDialogModule;
import javax.inject.Inject;

public class PostOnHoldDialog extends DialogFragment implements PostOnHoldDialogPresenter$View {
    @Inject
    PostOnHoldDialogPresenter presenter;
    View view;

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.view = View.inflate(getContext(), 2130903195, null);
        ButterKnife.bind(this, this.view);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getComponent().inject(this);
    }

    private PostOnHoldDialogComponent getComponent() {
        PostOnHoldDialogComponent postOnHoldDialogComponent = (PostOnHoldDialogComponent) ComponentsManager.get().getBaseComponent(PostOnHoldDialogComponent.KEY);
        if (postOnHoldDialogComponent != null) {
            return postOnHoldDialogComponent;
        }
        Object build = DaggerPostOnHoldDialogComponent.builder().postAdConfirmationScreenComponent((PostAdConfirmationScreenComponent) ComponentsManager.get().getBaseComponent(PostAdConfirmationScreenComponent.KEY)).postOnHoldDialogModule(new PostOnHoldDialogModule()).build();
        ComponentsManager.get().putBaseComponent(PostOnHoldDialogComponent.KEY, build);
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

    @OnClick({2131624451})
    void postAnotherAd() {
        this.presenter.onPostAnotherAdSelected();
        dismiss();
    }

    @OnClick({2131624450})
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
        ComponentsManager.get().removeBaseComponent(PostOnHoldDialogComponent.KEY);
        this.presenter.destroy();
    }
}
