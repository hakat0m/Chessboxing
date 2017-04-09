package com.gumtree.android.post_ad.steps;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.views.fields.BaseField;
import com.gumtree.android.common.views.fields.OnFieldValueChangeListener;
import com.gumtree.android.common.views.fields.StringFieldWithSwitch;
import com.gumtree.android.post_ad.IPostAdDataRefresh;
import com.gumtree.android.post_ad.PostAdBaseActivity;
import com.gumtree.android.post_ad.model.PostAdData;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.services.converter.PaymentConverter;
import com.gumtree.android.userprofile.UserProfileStatusService;
import com.gumtree.android.userprofile.events.OnUserProfileEvent;
import com.gumtree.android.userprofile.services.UserService;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class PostAdThreeFragment2 extends PostAdBaseFragment implements OnFieldValueChangeListener, IPostAdDataRefresh {
    private PostAdBaseActivity mActivity;
    @Inject
    UserProfileStatusService userProfileStatusService;
    @Inject
    UserService userService;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (PostAdBaseActivity) activity;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        GumtreeApplication.component().inject(this);
        setHasOptionsMenu(true);
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

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130903224, viewGroup, false);
    }

    public void onResume() {
        super.onResume();
        if (this.userProfileStatusService.isProfileDirty()) {
            this.userService.update();
        }
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        getPhone().setTitle(getString(2131165859));
        getPhone().setKey("phone");
        getPhone().setChecked(false);
        getPhone().setOnFieldValueChangeListener(this);
        getEmail().setEnabled(false);
        getEmail().setTitle(getString(2131165830));
        getEmail().setKey("poster-contact-email");
        getEmail().setOnFieldValueChangeListener(this);
        getEmail().setOnClickListener(PostAdThreeFragment2$$Lambda$1.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$onViewCreated$0(View view) {
        ChangeUserNameDialog.newInstance(PostAdThreeFragment2$$Lambda$2.lambdaFactory$(this)).show(getFragmentManager(), ChangeUserNameDialog.class.getSimpleName());
    }

    /* synthetic */ void lambda$null$175940b5$1() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(GumtreeApplication.getAPI().getUpdateEmailUrl()));
        startActivity(intent);
        Track.eventPostAdEmailEditBegin();
        this.userProfileStatusService.setProfileDirty(true);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (this.mActivity.getDao() != null) {
            refreshContent(this.mActivity.getDao().getPostAdData());
        } else if (this.mActivity.getAccount() != null) {
            getEmail().setValue(this.mActivity.getAccount().getUsername());
            getEmail().setChecked(true);
        }
    }

    private StringFieldWithSwitch getPhone() {
        if (getView() == null) {
            return null;
        }
        return (StringFieldWithSwitch) getView().findViewById(2131624540);
    }

    private StringFieldWithSwitch getEmail() {
        if (getView() == null) {
            return null;
        }
        return (StringFieldWithSwitch) getView().findViewById(2131624539);
    }

    private void initPhoneNumberView() {
        StringFieldWithSwitch phone = getPhone();
        if (phone != null) {
            phone.setTitle(getString(2131165859));
            phone.setInputType(3);
            phone.setKey("phone");
            phone.setChecked(PaymentConverter.DEFAULT_PAYMENT_METHOD_ID.equals(this.mActivity.getDao().getValueFromPostAdData("phone-enabled")));
            phone.setOnFieldValueChangeListener(this);
            phone.setImeAction(getString(17039370), 6);
            if (this.mActivity.getDao().getPostAdData().getAttribute(phone.getKey()) != null) {
                phone.setValue(this.mActivity.getDao().getValueFromPostAdData(phone.getKey()));
            } else {
                phone.setValue(BuildConfig.FLAVOR);
            }
            phone.setLabel(this.mActivity.getDao().getLabelPostAdData(phone.getKey()));
        }
    }

    private void initEmailView() {
        StringFieldWithSwitch email = getEmail();
        if (email != null) {
            email.setChecked(PaymentConverter.DEFAULT_PAYMENT_METHOD_ID.equals(this.mActivity.getDao().getValueFromPostAdData("poster-contact-email-enabled")));
            email.setTitle(getString(2131165830));
            email.setKey("poster-contact-email");
            email.setOnFieldValueChangeListener(this);
            if (this.mActivity.getDao().getPostAdData().getAttribute(email.getKey()) != null) {
                email.setValue(this.mActivity.getDao().getValueFromPostAdData(email.getKey()));
            } else if (!(this.mActivity.getDao().isManageAd() || this.mActivity.getAccount() == null)) {
                email.setValue(this.mActivity.getAccount().getContactEmail());
                email.setChecked(true);
            }
            email.setLabel(this.mActivity.getDao().getLabelPostAdData(email.getKey()));
        }
    }

    private void updateEmailValue() {
        StringFieldWithSwitch email = getEmail();
        if (email != null && !this.mActivity.getDao().isManageAd() && this.mActivity.getAccount() != null) {
            email.setValue(this.mActivity.getAccount().getContactEmail());
            email.setChecked(true);
        }
    }

    public void refreshContent(PostAdData postAdData) {
        initEmailView();
        initPhoneNumberView();
        if (this.mActivity.getDao().getPostAdData().getFeaturesResult() != null) {
            setInsertionFeePrice(this.mActivity.getDao().getPostAdData().getFeaturesResult().getInsertion());
        }
        Track.viewPostAdStep3(postAdData);
    }

    public void enableView(boolean z) {
    }

    public void onFieldValueChange(BaseField baseField, String str, String str2, String str3, boolean z) {
        if (getView() != null && this.mActivity.getDao() != null) {
            this.mActivity.getDao().addAttributeToData(str, str2, str3, baseField.getDisclaimer(), baseField.getTag(), z);
            if (str.equals("poster-contact-email")) {
                this.mActivity.getDao().addAttributeToData("poster-contact-email-enabled", ((StringFieldWithSwitch) baseField).isChecked() ? PaymentConverter.DEFAULT_PAYMENT_METHOD_ID : DraftAd.NEW_AD_ID, str3, baseField.getDisclaimer(), baseField.getTag(), z);
            }
            if (str.equals("phone")) {
                this.mActivity.getDao().addAttributeToData("phone-enabled", getPhone().isChecked() ? PaymentConverter.DEFAULT_PAYMENT_METHOD_ID : DraftAd.NEW_AD_ID, str3, baseField.getDisclaimer(), baseField.getTag(), z);
            }
            showErrorMessageIfAny(getPhone(), "phone");
            showErrorMessageIfAny(getEmail(), "poster-contact-email");
        }
    }

    protected void showErrorMessageIfAny(BaseField baseField, String str) {
        if (baseField != null && baseField.getKey().equals(str)) {
            String verifyAttribute = this.mActivity.getDao().verifyAttribute(str);
            if (TextUtils.isEmpty(verifyAttribute)) {
                baseField.hideError();
            } else if (str.equals("poster-contact-email")) {
                if (verifyAttribute.equals("Email is not valid.") || verifyAttribute.equals("Contact (Email or Phone) is required.")) {
                    baseField.showError(verifyAttribute);
                }
            } else if (verifyAttribute.equals("Phone is not valid.") || verifyAttribute.equals("Contact (Email or Phone) is required.")) {
                baseField.showError(verifyAttribute);
            }
        }
    }

    @Subscribe
    public void onUserProfileEvent(OnUserProfileEvent onUserProfileEvent) {
        if (onUserProfileEvent.isSuccess()) {
            updateEmailValue();
        }
    }
}
