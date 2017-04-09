package com.gumtree.android.postad.confirmation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.postad.confirmation.PostAdConfirmationScreenPresenter.View;
import com.gumtree.android.postad.confirmation.di.DaggerPostAdConfirmationScreenComponent;
import com.gumtree.android.postad.confirmation.di.PostAdConfirmationScreenComponent;
import com.gumtree.android.postad.confirmation.di.PostAdConfirmationScreenModule;
import com.gumtree.android.postad.confirmation.dialogs.FailedPostDialog;
import com.gumtree.android.postad.confirmation.dialogs.PostOnHoldDialog;
import com.gumtree.android.postad.confirmation.dialogs.SuccessPostDialog;
import com.gumtree.android.postad.confirmation.models.ConfirmationScreenResults;
import com.gumtree.android.postad.confirmation.models.SuccessPostResult;
import javax.inject.Inject;

public class PostAdConfirmationScreenActivity extends BaseActivity implements View {
    private static final String EXTRA_AD_ID = "com.gumtree.android.postad.confirmation.string_id";
    public static final String EXTRA_CONFIRMATION_RESULT = "com.gumtree.android.postad.confirmation.result";
    public static final String EXTRA_SUCCESS_POST_DIALOG_DATA = "com.gumtree.android.postad.confirmation.dialogs.string_success_post_dialog_data";
    @Inject
    PostAdConfirmationScreenPresenter presenter;
    @Bind({2131624192})
    android.view.View viewRoot;

    public static Intent createIntent(Context context, String str) {
        Intent intent = new Intent(context, PostAdConfirmationScreenActivity.class);
        intent.putExtra(EXTRA_AD_ID, str);
        return intent;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2130903093);
        getComponent().inject(this);
        ButterKnife.bind(this);
    }

    protected void onResume() {
        super.onResume();
        this.presenter.attachView(this);
    }

    private PostAdConfirmationScreenComponent getComponent() {
        PostAdConfirmationScreenComponent postAdConfirmationScreenComponent = (PostAdConfirmationScreenComponent) ComponentsManager.get().getBaseComponent(PostAdConfirmationScreenComponent.KEY);
        if (postAdConfirmationScreenComponent != null) {
            return postAdConfirmationScreenComponent;
        }
        Object build = DaggerPostAdConfirmationScreenComponent.builder().applicationComponent(ComponentsManager.get().getAppComponent()).postAdConfirmationScreenModule(new PostAdConfirmationScreenModule(getIntent().getStringExtra(EXTRA_AD_ID))).build();
        ComponentsManager.get().putBaseComponent(PostAdConfirmationScreenComponent.KEY, build);
        return build;
    }

    public void showFailedPost() {
        showFragment(new FailedPostDialog());
    }

    public void showPostOnHold() {
        showFragment(new PostOnHoldDialog());
    }

    public void showSuccessPost(SuccessPostResult successPostResult) {
        showFragment(setBundleDataToSuccessFragment(successPostResult));
    }

    private SuccessPostDialog setBundleDataToSuccessFragment(SuccessPostResult successPostResult) {
        SuccessPostDialog successPostDialog = new SuccessPostDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_SUCCESS_POST_DIALOG_DATA, successPostResult);
        successPostDialog.setArguments(bundle);
        return successPostDialog;
    }

    private void showFragment(DialogFragment dialogFragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.setCustomAnimations(2130968591, 2130968590, 2130968589, 2130968592);
        dialogFragment.show(beginTransaction, dialogFragment.getClass().getName());
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void onBackPressed() {
        this.presenter.onCloseSelected();
        super.onBackPressed();
    }

    protected void onPause() {
        this.presenter.detachView();
        super.onPause();
    }

    public void completeWithResult(ConfirmationScreenResults confirmationScreenResults) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CONFIRMATION_RESULT, confirmationScreenResults);
        setResult(-1, intent);
        finish();
    }

    public static ConfirmationScreenResults analyseResult(Intent intent) {
        return (intent == null || !intent.hasExtra(EXTRA_CONFIRMATION_RESULT)) ? null : (ConfirmationScreenResults) intent.getSerializableExtra(EXTRA_CONFIRMATION_RESULT);
    }

    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            ComponentsManager.get().removeBaseComponent(PostAdConfirmationScreenComponent.KEY);
            this.presenter.destroy();
        }
    }
}
