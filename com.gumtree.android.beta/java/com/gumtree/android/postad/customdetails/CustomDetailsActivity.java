package com.gumtree.android.postad.customdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.postad.customdetails.CustomDetailsPresenter.View;
import com.gumtree.android.postad.customdetails.di.CustomDetailsComponent;
import com.gumtree.android.postad.customdetails.di.CustomDetailsModule;
import com.gumtree.android.postad.customdetails.di.DaggerCustomDetailsComponent;
import com.gumtree.android.postad.customdetails.models.CustomAttributeData;
import com.gumtree.android.postad.customdetails.models.CustomDetailsData;
import com.gumtree.android.postad.views.attribute.AttributeView;
import com.gumtree.android.postad.views.attribute.AttributeViewFactory;
import com.gumtree.android.postad.views.attribute.FSBOAttributeView;
import com.gumtree.android.postad.views.attribute.VRMMileageWrapperAttributeView;
import com.gumtree.android.postad.views.attribute.VRMWrapperAttributeView;
import com.gumtree.android.postad.views.attribute.VRMWrapperView;
import java.util.List;
import javax.inject.Inject;

public class CustomDetailsActivity extends BaseActivity implements View {
    private static final String EXTRA_CUSTOM_DETAILS_AUTOVALIDATE = "com.gumtree.android.postad.customdetails.EXTRA_CUSTOM_DETAILS_AUTOVALIDATE";
    private static final String EXTRA_CUSTOM_DETAILS_CATEGORY_ID = "com.gumtree.android.postad.customdetails.EXTRA_CUSTOM_DETAILS_CATEGORY_ID";
    public static final String EXTRA_CUSTOM_DETAILS_DATA = "com.gumtree.android.postad.customdetails.CUSTOM_DETAILS_DATA";
    @Bind({2131624128})
    LinearLayout customDetailsContainer;
    @Inject
    CustomDetailsPresenter presenter;
    @Bind({2131624129})
    android.view.View progressBar;
    @Bind({2131624127})
    android.view.View root;

    public static Intent createIntent(Context context, CustomDetailsData customDetailsData, boolean z, String str) {
        return new Intent(context, CustomDetailsActivity.class).putExtra(EXTRA_CUSTOM_DETAILS_DATA, customDetailsData).putExtra(EXTRA_CUSTOM_DETAILS_AUTOVALIDATE, z).putExtra(EXTRA_CUSTOM_DETAILS_CATEGORY_ID, str);
    }

    public static CustomDetailsData analyseResult(Intent intent) {
        return intent == null ? null : (CustomDetailsData) intent.getSerializableExtra(EXTRA_CUSTOM_DETAILS_DATA);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getCustomDetailsComponent().inject(this);
        setContentView(2130903077);
        ButterKnife.bind(this);
        this.presenter.onInitialise();
        this.root.setOnFocusChangeListener(CustomDetailsActivity$$Lambda$1.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$onCreate$0(android.view.View view, boolean z) {
        if (z) {
            hideKeyboard();
        }
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
            ComponentsManager.get().removeBaseComponent(CustomDetailsComponent.KEY);
            this.presenter.destroy();
        }
    }

    public void onBackPressed() {
        this.presenter.onClosingScreen();
        super.onBackPressed();
    }

    private CustomDetailsComponent getCustomDetailsComponent() {
        CustomDetailsComponent customDetailsComponent = (CustomDetailsComponent) ComponentsManager.get().getBaseComponent(CustomDetailsComponent.KEY);
        if (customDetailsComponent != null) {
            return customDetailsComponent;
        }
        ApplicationComponent appComponent = ComponentsManager.get().getAppComponent();
        Object build = DaggerCustomDetailsComponent.builder().applicationComponent(appComponent).customDetailsModule(new CustomDetailsModule((CustomDetailsData) getIntent().getSerializableExtra(EXTRA_CUSTOM_DETAILS_DATA), getIntent().getBooleanExtra(EXTRA_CUSTOM_DETAILS_AUTOVALIDATE, false), getIntent().getStringExtra(EXTRA_CUSTOM_DETAILS_CATEGORY_ID))).build();
        ComponentsManager.get().putBaseComponent(CustomDetailsComponent.KEY, build);
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
                onBackPressed();
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void populateCustomAttributes(List<CustomAttributeData> list) {
        VRMWrapperView vRMWrapperView = null;
        for (CustomAttributeData customAttributeData : list) {
            VRMWrapperView vRMWrapperView2;
            AttributeView createAttributeView = AttributeViewFactory.createAttributeView(this, customAttributeData);
            createAttributeView.setEnabled(!customAttributeData.isLocked());
            if (createAttributeView instanceof FSBOAttributeView) {
                FSBOAttributeView fSBOAttributeView = (FSBOAttributeView) createAttributeView;
                fSBOAttributeView.setDialogAttributeValue(customAttributeData.getDialogAttributeValue());
                fSBOAttributeView.setDialogTitle(customAttributeData.getDialogTitle());
                fSBOAttributeView.setDialogBody(customAttributeData.getDialogBody());
                fSBOAttributeView.setPopupAttributeValue(customAttributeData.getPopupAttributeValue());
                fSBOAttributeView.setPopupText(customAttributeData.getPopupText());
                fSBOAttributeView.setPopupListener(CustomDetailsActivity$$Lambda$2.lambdaFactory$(this));
                fSBOAttributeView.setPopupShown(this.presenter.getPopupShown());
            }
            createAttributeView.showTitle(customAttributeData.getLocalisedLabel());
            createAttributeView.setAttributeName(customAttributeData.getAttributeName());
            createAttributeView.setRequired(customAttributeData.isRequired());
            createAttributeView.setAttributes(customAttributeData.getAttributeValues());
            createAttributeView.setOnFieldValueChangeListener(CustomDetailsActivity$$Lambda$3.lambdaFactory$(this, customAttributeData));
            createAttributeView.showValue(customAttributeData.getDefaultValue(), customAttributeData.getDefaultLocalisedValue());
            android.view.View view = (android.view.View) createAttributeView;
            view.setVisibility(customAttributeData.isHidden() ? 8 : 0);
            if (createAttributeView instanceof VRMWrapperView) {
                vRMWrapperView2 = (VRMWrapperView) createAttributeView;
            } else {
                vRMWrapperView2 = vRMWrapperView;
            }
            if ((createAttributeView instanceof VRMMileageWrapperAttributeView) && vRMWrapperView2 != null) {
                VRMMileageWrapperAttributeView vRMMileageWrapperAttributeView = (VRMMileageWrapperAttributeView) createAttributeView;
                vRMWrapperView2.setVRMOnFieldValueChangeListener(vRMMileageWrapperAttributeView.getVRMOnFieldValueChangeListener());
                vRMMileageWrapperAttributeView.setVRMLookupRequestListener(CustomDetailsActivity$$Lambda$4.lambdaFactory$(this));
                vRMMileageWrapperAttributeView.setVRMValue(vRMWrapperView2.getVRMValue());
                vRMMileageWrapperAttributeView.setVRMManualEntryListener(CustomDetailsActivity$$Lambda$5.lambdaFactory$(this));
            }
            this.customDetailsContainer.addView(view);
            vRMWrapperView = vRMWrapperView2;
        }
    }

    /* synthetic */ void lambda$populateCustomAttributes$1() {
        this.presenter.onPopupShown();
    }

    /* synthetic */ void lambda$populateCustomAttributes$2(CustomAttributeData customAttributeData, String str, String str2, String str3) {
        this.presenter.onAttributeValueUpdated(str, customAttributeData.getLocalisedLabel(), str2, str3);
    }

    /* synthetic */ void lambda$populateCustomAttributes$3(String str) {
        this.presenter.onVrmLookupRequested(str);
    }

    /* synthetic */ void lambda$populateCustomAttributes$4() {
        this.presenter.onVrmManualEntry();
    }

    public void closingScreen(CustomDetailsData customDetailsData) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CUSTOM_DETAILS_DATA, customDetailsData);
        setResult(-1, intent);
    }

    public void validateAllFields() {
        int childCount = this.customDetailsContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = this.customDetailsContainer.getChildAt(i);
            if (childAt instanceof AttributeView) {
                ((AttributeView) childAt).doValidation();
            }
        }
    }

    public void showHiddenFields() {
        int childCount = this.customDetailsContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            this.customDetailsContainer.getChildAt(i).setVisibility(0);
        }
    }

    public void clearScreen() {
        this.customDetailsContainer.removeAllViews();
    }

    public void showLoading(boolean z) {
        this.progressBar.setVisibility(z ? 0 : 8);
    }

    public void showErrorMessage(String str) {
        Snackbar.make(this.root, str, 0).show();
    }

    public void showVrmNotRecognised() {
        int childCount = this.customDetailsContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = this.customDetailsContainer.getChildAt(i);
            if (childAt instanceof VRMWrapperAttributeView) {
                ((VRMWrapperAttributeView) childAt).registrationNotRecognised();
                return;
            }
        }
    }

    public void hideVrmManualEntry() {
        int childCount = this.customDetailsContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            android.view.View childAt = this.customDetailsContainer.getChildAt(i);
            if (childAt instanceof VRMMileageWrapperAttributeView) {
                ((VRMMileageWrapperAttributeView) childAt).hideManualEntry();
                return;
            }
        }
    }
}
