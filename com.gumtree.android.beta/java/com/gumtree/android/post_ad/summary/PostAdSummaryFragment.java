package com.gumtree.android.post_ad.summary;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.apptentive.android.sdk.BuildConfig;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.category.NewPostAdCategoryActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.common.utils.AppUtils;
import com.gumtree.android.common.views.ThemeUtils;
import com.gumtree.android.post_ad.IPostAdDataRefresh;
import com.gumtree.android.post_ad.PostAdDBIntentService;
import com.gumtree.android.post_ad.gallery.GalleryActivity;
import com.gumtree.android.post_ad.model.PostAdAttributeItem;
import com.gumtree.android.post_ad.model.PostAdData;
import com.gumtree.android.post_ad.validation.IViewValidator;
import com.gumtree.android.post_ad.validation.ValidationStateColor;
import com.gumtree.android.vip.VIPContactFragment;
import java.io.File;
import javax.inject.Inject;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class PostAdSummaryFragment extends BaseFragment implements IPostAdDataRefresh {
    private static final int REFRESH_SUMMARY = 1;
    private static final int STEP_THREE = 3;
    PostAdNavigationActivity mActivity;
    private final OnClickListener onClickListener = new 1(this);
    @Inject
    IViewValidator validator;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (PostAdNavigationActivity) activity;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (this.mActivity.getDao() != null) {
            refreshContent(this.mActivity.getDao().getPostAdData());
        }
    }

    public void onCreate(Bundle bundle) {
        GumtreeApplication.component().inject(this);
        super.onCreate(bundle);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == REFRESH_SUMMARY) {
            if (intent == null || !intent.hasExtra(PostAdData.class.getSimpleName())) {
                PostAdDBIntentService.load(this.mActivity.getPostAdId());
            } else {
                refreshUI((PostAdData) intent.getSerializableExtra(PostAdData.class.getSimpleName()));
            }
        } else if (i == 15 && i2 == -1) {
            getActivity().setResult(-1);
            getActivity().finish();
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130903223, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setOnClickListener(view, 2131624912);
        setOnClickListener(view, 2131624528);
        setOnClickListener(view, 2131624536);
        setOnClickListener(view, 2131624537);
        setOnClickListener(view, 2131624538);
        setOnClickListener(view, 2131624913);
        enableView(false);
        setDefaultImage();
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        PostAdSummaryFragmentPermissionsDispatcher.onRequestPermissionsResult(this, i, iArr);
    }

    private void setOnClickListener(View view, int i) {
        if (view.findViewById(i) != null) {
            view.findViewById(i).setOnClickListener(this.onClickListener);
        }
    }

    private void refreshUI(PostAdData postAdData) {
        setField(postAdData, getLocationView(), "location");
        setField(postAdData, getCategoryView(), VIPContactFragment.CATEGORY_ID);
        setField(postAdData, getTitleView(), NewPostAdCategoryActivity.EXTRA_TITLE);
        setPrice(postAdData);
        setDescription(postAdData);
        setFieldPhoneEmail(postAdData, getEmailView(), "poster-contact-email", "phone");
        setFieldPhoneEmail(postAdData, getTelephoneView(), "phone", "poster-contact-email");
        if (TextUtils.isEmpty(postAdData.getDefaultPicture())) {
            setDefaultImage();
        } else {
            DrawableTypeRequest load;
            if (postAdData.getDefaultPicture().contains("http")) {
                load = Glide.with(this.context).load(postAdData.getDefaultPicture());
            } else {
                load = Glide.with(this.context).load(new File(postAdData.getDefaultPicture()));
            }
            load.centerCrop().placeholder(2130837634).error(2130837628).into(getImageView());
        }
        this.validator.validateStep(getStepOneView(), getResources(), postAdData, REFRESH_SUMMARY);
        this.validator.validateStep(getStepTwoView(), getResources(), postAdData, 2);
        this.validator.validateStep(getStepThreeView(), getResources(), postAdData, STEP_THREE);
    }

    private void setDefaultImage() {
        Drawable mutate = ContextCompat.getDrawable(getActivity(), 2130837819).mutate();
        mutate.setColorFilter(ThemeUtils.getColor(getActivity(), 2130772476), Mode.MULTIPLY);
        getImageView().setImageDrawable(mutate);
    }

    private void setFieldPhoneEmail(PostAdData postAdData, View view, String str, String str2) {
        PostAdAttributeItem attribute = postAdData.getAttribute(str);
        PostAdAttributeItem attribute2 = postAdData.getAttribute(str2);
        if (view instanceof TextView) {
            if (attribute != null) {
                ((TextView) view).setText(attribute.toString());
            } else if (attribute2 != null) {
                ((TextView) view).setText(BuildConfig.FLAVOR);
            }
            this.validator.validateView(str, (TextView) view, getResources(), postAdData);
        }
    }

    private void setField(PostAdData postAdData, View view, String str) {
        PostAdAttributeItem attribute = postAdData.getAttribute(str);
        if (view instanceof TextView) {
            if (attribute != null) {
                ((TextView) view).setText(attribute.toString());
            }
            this.validator.validateView(str, (TextView) view, getResources(), postAdData);
        }
    }

    private void setDescription(PostAdData postAdData) {
        PostAdAttributeItem attribute = postAdData.getAttribute("description");
        CharSequence string = getResources().getString(2131165845);
        if (!(attribute == null || TextUtils.isEmpty(attribute.getValue()))) {
            string = attribute.getValue();
        }
        getDescriptionView().setText(string);
        this.validator.validateView("description", getDescriptionView(), getResources(), postAdData);
    }

    private void setPrice(PostAdData postAdData) {
        PostAdAttributeItem attribute = postAdData.getAttribute("price");
        CharSequence string = getResources().getString(2131165883);
        if (!(attribute == null || TextUtils.isEmpty(attribute.getValue()))) {
            string = AppUtils.getFormattedPrice(attribute.getValue());
        }
        getPriceView().setText(string);
        this.validator.validateView("price", getPriceView(), getResources(), postAdData);
    }

    private ImageView getImageView() {
        return (ImageView) getView().findViewById(2131624529);
    }

    private TextView getTelephoneView() {
        return (TextView) getView().findViewById(2131624543);
    }

    private TextView getEmailView() {
        return (TextView) getView().findViewById(2131624542);
    }

    private TextView getDescriptionView() {
        return (TextView) getView().findViewById(2131624549);
    }

    private TextView getPriceView() {
        return (TextView) getView().findViewById(2131624548);
    }

    private TextView getCategoryView() {
        return (TextView) getView().findViewById(2131624533);
    }

    private TextView getLocationView() {
        return (TextView) getView().findViewById(2131624532);
    }

    private TextView getTitleView() {
        return (TextView) getView().findViewById(2131624530);
    }

    private TextView getStepOneView() {
        return (TextView) getView().findViewById(2131624531);
    }

    private TextView getStepTwoView() {
        return (TextView) getView().findViewById(2131624547);
    }

    private TextView getStepThreeView() {
        return (TextView) getView().findViewById(2131624541);
    }

    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    @NeedsPermission({"android.permission.WRITE_EXTERNAL_STORAGE"})
    public void openGalleryActivity() {
        Intent intent = new Intent(this.context, GalleryActivity.class);
        intent.putExtra(PostAdNavigationActivity.EXTRA_POST_AD_ID, this.mActivity.getPostAdId());
        startActivityForResult(intent, REFRESH_SUMMARY);
    }

    public void refreshContent(PostAdData postAdData) {
        Track.viewPostAdSummary(postAdData);
        enableView(true);
        refreshUI(postAdData);
    }

    public void enableView(boolean z) {
        getStepOneView().setEnabled(z);
        getStepTwoView().setEnabled(z);
        getStepThreeView().setEnabled(z);
        getImageView().setEnabled(z);
        getView().findViewById(2131624912).setEnabled(z);
        getView().findViewById(2131624913).setEnabled(z);
    }

    public void reset() {
        setDefaultImage();
        getLocationView().setText(getResources().getText(2131165867));
        getLocationView().setTextColor(getResources().getColor(ValidationStateColor.UNINITIALIZED.getColor()));
        getCategoryView().setText(getResources().getString(2131165838));
        getCategoryView().setTextColor(getResources().getColor(ValidationStateColor.UNINITIALIZED.getColor()));
        getTitleView().setText(getResources().getText(2131165905));
        getTitleView().setTextColor(getResources().getColor(ValidationStateColor.UNINITIALIZED.getColor()));
        getPriceView().setText(getResources().getText(2131165883));
        getPriceView().setTextColor(getResources().getColor(ValidationStateColor.UNINITIALIZED.getColor()));
        getDescriptionView().setText(getResources().getText(2131165845));
        getDescriptionView().setTextColor(getResources().getColor(ValidationStateColor.UNINITIALIZED.getColor()));
        getEmailView().setText(getResources().getText(2131165830));
        getEmailView().setTextColor(getResources().getColor(ValidationStateColor.UNINITIALIZED.getColor()));
        getTelephoneView().setText(getResources().getText(2131165833));
        getTelephoneView().setTextColor(getResources().getColor(ValidationStateColor.UNINITIALIZED.getColor()));
        getStepOneView().setTextColor(getResources().getColor(ValidationStateColor.UNINITIALIZED.getColor()));
        getStepTwoView().setTextColor(getResources().getColor(ValidationStateColor.UNINITIALIZED.getColor()));
        getStepThreeView().setTextColor(getResources().getColor(ValidationStateColor.UNINITIALIZED.getColor()));
    }
}
