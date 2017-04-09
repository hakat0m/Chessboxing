package com.gumtree.android.postad.contactdetails;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.location.LocationActivity;
import com.gumtree.android.location.model.LocationData;
import com.gumtree.android.postad.contactdetails.PostAdContactDetailsPresenter.View;
import com.gumtree.android.postad.contactdetails.di.DaggerPostAdContactDetailsComponent;
import com.gumtree.android.postad.contactdetails.di.PostAdContactDetailsComponent;
import com.gumtree.android.postad.contactdetails.di.PostAdContactDetailsModule;
import com.gumtree.android.postad.contactdetails.models.ContactDetailsData;
import com.gumtree.android.postad.views.TextSummaryValidationView;
import javax.inject.Inject;

public class PostAdContactDetailsActivity extends BaseActivity implements View {
    private static final String EXTRA_CONTACT_DETAILS_DATA = "com.gumtree.android.postad.contactdetails.contact_details_data";
    @Bind({2131624207})
    CheckBox emailCheckbox;
    @Bind({2131624205})
    EditText emailEditText;
    @Bind({2131624204})
    TextInputLayout emailInputLayout;
    @Bind({2131624211})
    TextView errorTextView;
    @Bind({2131624210})
    CheckBox phoneCheckbox;
    @Bind({2131624209})
    EditText phoneEditText;
    @Bind({2131624208})
    TextInputLayout phoneInputLayout;
    @Inject
    PostAdContactDetailsPresenter presenter;
    @Bind({2131624203})
    CheckBox showMapOnAdCheckbox;
    @Bind({2131624202})
    android.view.View showMapOnAdContainer;
    @Bind({2131624201})
    TextSummaryValidationView textSummaryValidationView;

    @OnClick({2131624201})
    void onLocationClicked() {
        this.presenter.onLocationClicked();
    }

    @OnClick({2131624206})
    void onEmailClicked() {
        this.presenter.onEmailClicked();
    }

    @OnCheckedChanged({2131624203})
    void onLocationShowMapOnAdCheckedChanged(boolean z) {
        this.presenter.onShowMapInAdChecked(z);
    }

    @OnCheckedChanged({2131624207})
    void onEmailCheckedChanged(boolean z) {
        this.presenter.onEmailChecked(z);
    }

    @OnCheckedChanged({2131624210})
    void onPhoneCheckedChanged(boolean z) {
        this.presenter.onPhoneChecked(z);
    }

    @OnTextChanged({2131624209})
    void onPhoneChanged(CharSequence charSequence) {
        this.presenter.onPhoneChanged(charSequence.toString());
    }

    public static Intent createIntent(Context context, ContactDetailsData contactDetailsData) {
        Intent intent = new Intent(context, PostAdContactDetailsActivity.class);
        intent.putExtra(EXTRA_CONTACT_DETAILS_DATA, contactDetailsData);
        return intent;
    }

    @Nullable
    public static ContactDetailsData analyseResult(Intent intent) {
        return (intent == null || !intent.hasExtra(EXTRA_CONTACT_DETAILS_DATA)) ? null : (ContactDetailsData) intent.getSerializableExtra(EXTRA_CONTACT_DETAILS_DATA);
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 22 && i2 == -1 && intent != null && intent.hasExtra(StatefulActivity.EXTRA_LOCATION_NAME)) {
            this.presenter.onLocationChanged((LocationData) intent.getSerializableExtra(StatefulActivity.EXTRA_LOCATION_NAME));
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getPostAdContactDetailsComponent().inject(this);
        setContentView(2130903098);
        ButterKnife.bind(this);
    }

    protected void onResume() {
        super.onResume();
        this.presenter.attachView(this);
    }

    protected void onPause() {
        this.presenter.detachView();
        super.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            ComponentsManager.get().removeBaseComponent(PostAdContactDetailsComponent.KEY);
            this.presenter.destroy();
        }
    }

    public void finish() {
        this.presenter.onContactDetailsFinished();
        super.finish();
    }

    private PostAdContactDetailsComponent getPostAdContactDetailsComponent() {
        PostAdContactDetailsComponent postAdContactDetailsComponent = (PostAdContactDetailsComponent) ComponentsManager.get().getBaseComponent(PostAdContactDetailsComponent.KEY);
        if (postAdContactDetailsComponent != null) {
            return postAdContactDetailsComponent;
        }
        ApplicationComponent appComponent = ComponentsManager.get().getAppComponent();
        Object build = DaggerPostAdContactDetailsComponent.builder().applicationComponent(appComponent).postAdContactDetailsModule(new PostAdContactDetailsModule((ContactDetailsData) getIntent().getSerializableExtra(EXTRA_CONTACT_DETAILS_DATA))).build();
        ComponentsManager.get().putBaseComponent(PostAdContactDetailsComponent.KEY, build);
        return build;
    }

    protected boolean isHomeAsUp() {
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(2131755014, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 2131624936:
                finish();
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void showLocation(String str, boolean z) {
        this.textSummaryValidationView.showValue(str);
        this.textSummaryValidationView.setEnabled(z);
    }

    public void showLocationError(String str) {
        this.textSummaryValidationView.showError(str);
    }

    public void showMapInAdField(boolean z) {
        this.showMapOnAdContainer.setVisibility(z ? 0 : 8);
    }

    public void showMapInAdChecked(boolean z) {
        this.showMapOnAdCheckbox.setChecked(z);
    }

    public void openLocationSelection(LocationData locationData) {
        startActivityForResult(LocationActivity.createIntent(this, locationData), 22);
    }

    public void setContactDetailsDataResult(ContactDetailsData contactDetailsData) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CONTACT_DETAILS_DATA, contactDetailsData);
        setResult(-1, intent);
    }

    public void showEmail(String str) {
        this.emailEditText.setText(str);
    }

    public void showEmailChecked(boolean z) {
        this.emailCheckbox.setChecked(z);
    }

    public void showPhone(String str) {
        this.phoneEditText.setText(str);
    }

    public void showPhoneChecked(boolean z) {
        this.phoneCheckbox.setChecked(z);
    }

    /* synthetic */ void lambda$openContactEmailDialog$9e4a27f$1() {
        this.presenter.onChangeContactEmailClicked();
    }

    public void openContactEmailDialog() {
        new ChangeContactEmailDialog(this, PostAdContactDetailsActivity$$Lambda$1.lambdaFactory$(this)).showDialog();
    }

    public void openContactEmailChange() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(GumtreeApplication.getAPI().getUpdateEmailUrl()));
        startActivity(intent);
    }

    public void showPhoneError(String str) {
        this.phoneInputLayout.setError(str);
        this.emailInputLayout.setError(null);
        this.errorTextView.setText(str);
    }

    public void showContactDetailsError(String str) {
        this.phoneInputLayout.setError(str);
        this.emailInputLayout.setError(str);
        this.errorTextView.setText(str);
    }
}
