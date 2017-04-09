package com.gumtree.android.postad.confirmation.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.postad.confirmation.PostAdConfirmationScreenActivity;
import com.gumtree.android.postad.confirmation.di.DaggerSuccessPostDialogComponent;
import com.gumtree.android.postad.confirmation.di.PostAdConfirmationScreenComponent;
import com.gumtree.android.postad.confirmation.di.SuccessPostDialogComponent;
import com.gumtree.android.postad.confirmation.di.SuccessPostDialogModule;
import com.gumtree.android.postad.confirmation.models.SuccessPostResult;
import javax.inject.Inject;

public class SuccessPostDialog extends DialogFragment implements SuccessPostDialogPresenter$View {
    @Bind({2131624454})
    ImageView itemImage;
    @Bind({2131624457})
    TextView itemPrice;
    @Bind({2131624456})
    TextView itemSubTitle;
    @Bind({2131624455})
    TextView itemTitle;
    @Inject
    SuccessPostDialogPresenter presenter;
    View view;

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.view = View.inflate(getContext(), 2130903196, null);
        ButterKnife.bind(this, this.view);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getComponent().inject(this);
    }

    private SuccessPostDialogComponent getComponent() {
        SuccessPostDialogComponent successPostDialogComponent = (SuccessPostDialogComponent) ComponentsManager.get().getBaseComponent(SuccessPostDialogComponent.KEY);
        if (successPostDialogComponent != null) {
            return successPostDialogComponent;
        }
        Object build = DaggerSuccessPostDialogComponent.builder().postAdConfirmationScreenComponent((PostAdConfirmationScreenComponent) ComponentsManager.get().getBaseComponent(PostAdConfirmationScreenComponent.KEY)).successPostDialogModule(new SuccessPostDialogModule((SuccessPostResult) getArguments().getSerializable(PostAdConfirmationScreenActivity.EXTRA_SUCCESS_POST_DIALOG_DATA))).build();
        ComponentsManager.get().putBaseComponent(SuccessPostDialogComponent.KEY, build);
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

    @OnClick({2131624459})
    void postAnotherAd() {
        this.presenter.onPostAnotherAdSelected();
        dismiss();
    }

    @OnClick({2131624458, 2131624453})
    void manageAds() {
        this.presenter.onManageAdsSelected();
        dismiss();
    }

    public void showItemTitle(String str) {
        this.itemTitle.setText(str);
    }

    public void showItemSubTitle(String str) {
        this.itemSubTitle.setText(str);
    }

    public void showItemPrice(String str) {
        this.itemPrice.setText(String.format("\u00a3%s", new Object[]{str}));
    }

    public void showItemImage(String str) {
        Glide.with(this).load(str).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).centerCrop().placeholder(2130837645).error(2130837645).into(this.itemImage);
    }

    public void onPause() {
        this.presenter.detachView();
        super.onPause();
    }

    public void onDestroy() {
        super.onDestroy();
        ComponentsManager.get().removeBaseComponent(SuccessPostDialogComponent.KEY);
        this.presenter.destroy();
    }
}
