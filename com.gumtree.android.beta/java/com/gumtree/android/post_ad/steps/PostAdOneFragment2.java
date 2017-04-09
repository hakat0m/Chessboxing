package com.gumtree.android.post_ad.steps;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.Log;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.auth.UserLocation;
import com.gumtree.android.categories.PostAdCategoryActivity;
import com.gumtree.android.category.NewPostAdCategoryActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.adapters.MetadataViewFactory;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.location.AppLocation;
import com.gumtree.android.common.views.fields.BaseField;
import com.gumtree.android.common.views.fields.ButtonField;
import com.gumtree.android.common.views.fields.DateTimeField;
import com.gumtree.android.common.views.fields.OnFieldValueChangeListener;
import com.gumtree.android.common.views.fields.StringField;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.events.OnAdDeletedEvent;
import com.gumtree.android.events.OnFeaturesReceivedEvent;
import com.gumtree.android.events.OnMetadataReceivedEvent;
import com.gumtree.android.features.Feature;
import com.gumtree.android.features.Feature.FeatureType;
import com.gumtree.android.locations.postad.PostAdLocationActivity;
import com.gumtree.android.message_box.ConversationsIntentService;
import com.gumtree.android.metadata.SearchMetadata;
import com.gumtree.android.post_ad.FeaturesIntentService;
import com.gumtree.android.post_ad.IPostAdDataRefresh;
import com.gumtree.android.post_ad.MetadataIntentService;
import com.gumtree.android.post_ad.model.FeaturesResult;
import com.gumtree.android.post_ad.model.PostAdAttributeItem;
import com.gumtree.android.post_ad.model.PostAdData;
import com.gumtree.android.post_ad.model.PostAdMemDAO;
import com.gumtree.android.postad.services.converter.PaymentConverter;
import com.gumtree.android.vip.VIPContactFragment;
import com.squareup.otto.Subscribe;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.inject.Inject;

public class PostAdOneFragment2 extends PostAdBaseFragment implements OnClickListener, OnFieldValueChangeListener, IPostAdDataRefresh {
    public static final String KEY_CATEGORY_ID = "key_category_id";
    public static final String KEY_LOCATION_ID = "key_location_id";
    public static final String KEY_LOCATION_ON_MAP = "key_location_on_map";
    public static final String KEY_LOCATION_POSTCODE = "key_location_postcode";
    @Inject
    BaseAccountManager accountManager;
    private ViewGroup mAttrContainer;
    private ButtonField mCategoryField;
    private String mCategoryId;
    private String mCategoryText;
    private boolean mHasCategoryChanged;
    private ButtonField mLocationField;
    private String mLocationId;
    private String mLocationName;
    private String mLocationOnMap;
    private String mLocationPostcode;
    private String mLocationText;
    private StringField mTitleView;
    private final MetadataViewFactory viewFactory = new MetadataViewFactory();

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ComponentsManager.get().getAppComponent().inject(this);
        if (bundle != null) {
            this.mLocationPostcode = bundle.getString(KEY_LOCATION_POSTCODE);
            this.mLocationOnMap = bundle.getString(KEY_LOCATION_ON_MAP);
            this.mLocationId = bundle.getString(KEY_LOCATION_ID);
            this.mCategoryId = bundle.getString(KEY_CATEGORY_ID);
        }
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130903220, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mTitleView = (StringField) getView().findViewById(2131624524);
        this.mCategoryField = (ButtonField) getView().findViewById(2131624526);
        this.mLocationField = (ButtonField) getView().findViewById(2131624525);
        this.mAttrContainer = (ViewGroup) getView().findViewById(16908298);
        this.mTitleView.setKey(NewPostAdCategoryActivity.EXTRA_TITLE);
        this.mTitleView.setTitle(getString(2131165905));
        this.mTitleView.setOnFieldValueChangeListener(this);
        this.mCategoryField.setOnClickListener(this);
        this.mCategoryField.setKey(VIPContactFragment.CATEGORY_ID);
        this.mCategoryField.setTitle(getString(2131165838));
        this.mCategoryField.setDescription(getString(2131165838));
        this.mCategoryField.setOnFieldValueChangeListener(this);
        this.mLocationField.setOnClickListener(this);
        this.mLocationField.setKey("location");
        this.mLocationField.setTitle(getString(2131165831));
        this.mLocationField.setOnFieldValueChangeListener(this);
        enableView(false);
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString(KEY_LOCATION_POSTCODE, this.mLocationPostcode);
        bundle.putString(KEY_LOCATION_ON_MAP, this.mLocationOnMap);
        bundle.putString(KEY_LOCATION_ID, this.mLocationId);
        bundle.putString(KEY_CATEGORY_ID, this.mCategoryId);
        super.onSaveInstanceState(bundle);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        try {
            handleActivityResult(i, i2, intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getTitleValues(PostAdData postAdData) {
        PostAdAttributeItem attribute = postAdData.getAttribute(this.mTitleView.getKey());
        if (attribute != null) {
            this.mTitleView.setValue(attribute.getValue());
            this.mTitleView.setLabel(postAdData.getAttribute(this.mTitleView.getKey()).getLabel());
        }
    }

    private void getCategoryViewValues() {
        String categoryName = getCategoryName();
        String categoryId = getCategoryId();
        this.mCategoryField.setDescription(categoryName);
        this.mCategoryField.setValue(categoryId);
        setCategoryValues(categoryId, categoryName);
        if (this.mActivity.getDao().isManageAd()) {
            this.mCategoryField.setEnabled(false);
        }
    }

    private String getCategoryName() {
        PostAdMemDAO dao = this.mActivity.getDao();
        String str = null;
        if (dao != null) {
            str = dao.getLabelPostAdData(VIPContactFragment.CATEGORY_ID);
        }
        if (this.mCategoryText == null || this.mCategoryText.length() <= 0) {
            return TextUtils.isEmpty(str) ? getString(2131165838) : str;
        } else {
            return this.mCategoryText;
        }
    }

    private String getCategoryId() {
        PostAdMemDAO dao = this.mActivity.getDao();
        String str = null;
        if (dao != null) {
            str = dao.getValueFromPostAdData(VIPContactFragment.CATEGORY_ID);
        }
        if (this.mCategoryId == null || this.mCategoryId.length() <= 0) {
            return TextUtils.isEmpty(str) ? PaymentConverter.DEFAULT_PAYMENT_METHOD_ID : str;
        } else {
            return this.mCategoryId;
        }
    }

    private boolean isCategorySelected() {
        return !this.mCategoryField.getValue().equals(PaymentConverter.DEFAULT_PAYMENT_METHOD_ID);
    }

    private void getLocationViewValues() {
        String locMap;
        String str = null;
        UserLocation userLocation = this.accountManager.getUserLocation();
        String locPostcode = userLocation != null ? userLocation.getLocPostcode() : null;
        if (userLocation != null) {
            locMap = userLocation.getLocMap();
        } else {
            locMap = null;
        }
        if (userLocation != null) {
            str = userLocation.getLocText();
        }
        this.mLocationPostcode = getLocationAttribute("zip-code", locPostcode);
        this.mLocationOnMap = getLocationAttribute("visible-on-map", locMap);
        this.mLocationText = getLocationAttribute("neighborhood", str);
        this.mLocationField.setDescription(getLocationText() + getLocationName());
        this.mLocationField.setValue(getLocationId());
        if (this.mActivity.getDao() != null) {
            this.mLocationField.setEnabled(!this.mActivity.getDao().isManageAd());
        }
    }

    public String getLocationText() {
        String str = null;
        if (this.mActivity.getDao() != null) {
            str = this.mActivity.getDao().getLabelPostAdData("neighborhood");
        }
        if (this.mLocationText == null || this.mLocationText.length() <= 0) {
            return (str == null || str.length() <= 0) ? BuildConfig.FLAVOR : str + ", ";
        } else {
            return this.mLocationText + ", ";
        }
    }

    private String getLocationId() {
        CharSequence valueFromPostAdData;
        String str = null;
        if (this.mActivity.getDao() != null) {
            valueFromPostAdData = this.mActivity.getDao().getValueFromPostAdData("location");
        } else {
            valueFromPostAdData = null;
        }
        UserLocation userLocation = this.mActivity.getAccount().getUserLocation();
        if (userLocation != null) {
            str = userLocation.getLocId();
        }
        if (!TextUtils.isEmpty(valueFromPostAdData)) {
            CharSequence charSequence = valueFromPostAdData;
        }
        if (TextUtils.isEmpty(str)) {
            str = "10000392";
        }
        return (this.mLocationId == null || this.mLocationId.length() <= 0) ? str : this.mLocationId;
    }

    private String getLocationName() {
        CharSequence labelPostAdData;
        String str = null;
        if (this.mActivity.getDao() != null) {
            labelPostAdData = this.mActivity.getDao().getLabelPostAdData("location");
        } else {
            labelPostAdData = null;
        }
        UserLocation userLocation = this.mActivity.getAccount().getUserLocation();
        if (userLocation != null) {
            str = userLocation.getLocName();
        }
        if (!TextUtils.isEmpty(labelPostAdData)) {
            CharSequence charSequence = labelPostAdData;
        }
        if (TextUtils.isEmpty(str)) {
            str = getString(2131165867);
        }
        if (str.contains(", ")) {
            str = str.split(", ")[1];
        }
        return (this.mLocationName == null || this.mLocationName.length() <= 0) ? str : this.mLocationName;
    }

    private String getLocationAttribute(String str, String str2) {
        CharSequence charSequence = null;
        if (this.mActivity.getDao() != null) {
            charSequence = this.mActivity.getDao().getLabelPostAdData(str);
        }
        return TextUtils.isEmpty(charSequence) ? str2 : charSequence;
    }

    private void handleActivityResult(int i, int i2, Intent intent) {
        switch (i) {
            case ConversationsIntentService.DEFAULT_PAGE /*0*/:
                if (i2 == -1) {
                    onNewLocation(intent);
                    return;
                }
                return;
            case HighlightView.GROW_NONE /*1*/:
                if (i2 == -1) {
                    this.mHasCategoryChanged = true;
                    setCategoryValues(intent.getStringExtra(StatefulActivity.NAME_CATEGORY_ID), intent.getStringExtra(StatefulActivity.EXTRA_CATEGORY_NAME));
                    return;
                }
                return;
            default:
                Log.e(getClass().getSimpleName(), "case not supported " + i);
                return;
        }
    }

    @Subscribe
    public void onAdDeletedEvent(OnAdDeletedEvent onAdDeletedEvent) {
        if (this.mActivity.getDao() != null) {
            getCategoryViewValues();
        }
    }

    private void setCategoryValues(String str, String str2) {
        this.mCategoryField.setDescription(str2);
        this.mCategoryField.setValue(str);
        this.mCategoryId = str;
        this.mCategoryText = str2;
        onCategoryChanged(str);
    }

    private void onNewLocation(Intent intent) {
        this.mLocationText = intent.getStringExtra(StatefulActivity.EXTRA_LOCATION_TEXT);
        this.mLocationPostcode = intent.getStringExtra(AppLocation.ADMOB_DATA_POSTCODE);
        this.mLocationOnMap = intent.getStringExtra(StatefulActivity.EXTRA_VISIBLE_MAP);
        this.mLocationId = intent.getStringExtra(StatefulActivity.NAME_LOCATION_ID);
        this.mLocationName = intent.getStringExtra(StatefulActivity.EXTRA_LOCATION_NAME);
        this.mActivity.getAccount().setUserLocation(new UserLocation(this.mLocationId, this.mLocationName, this.mLocationText, this.mLocationOnMap, this.mLocationPostcode));
        this.mLocationField.setDescription(this.mLocationName);
        this.mLocationField.setValue(this.mLocationId);
        this.mActivity.getDao().addAttributeToData("zip-code", this.mLocationPostcode, null, null, null, false);
        this.mActivity.getDao().addAttributeToData("neighborhood", this.mLocationText, null, null, null, false);
        this.mActivity.getDao().addAttributeToData("visible-on-map", this.mLocationOnMap, null, null, null, false);
        this.mActivity.getDao().addAttributeToData("location", this.mLocationId, this.mLocationName, null, null, false);
        trackPostcodeSelection();
        fetchFeatures();
    }

    private void trackPostcodeSelection() {
        if (TextUtils.isEmpty(this.mLocationPostcode)) {
            Track.eventManualLocationSelected(getLocationName());
        } else {
            Track.eventPostcodeSelected();
        }
    }

    private void onCategoryChanged(String str) {
        if (str != null) {
            fetchMetadata(str);
            fetchFeatures();
        }
    }

    private void fetchFeatures() {
        FeaturesIntentService.start(getCategoryId(), getLocationId(), this.mActivity.getDao().getAdId(), getPriceSensitiveAttributes());
    }

    private HashMap<String, String> getPriceSensitiveAttributes() {
        HashMap<String, String> hashMap = null;
        PostAdMemDAO dao = this.mActivity.getDao();
        if (dao != null) {
            hashMap = dao.getPostAdData().getPriceSensitiveAttributes();
        }
        return hashMap == null ? new HashMap() : hashMap;
    }

    private void fetchMetadata(String str) {
        if (isCategorySelected()) {
            if (this.mHasCategoryChanged) {
                this.mActivity.getDao().resetAttributeData();
            }
            MetadataIntentService.start(str);
        }
    }

    private void showAttributes(List<PostAdAttributeItem> list) {
        this.mAttrContainer.removeAllViews();
        for (PostAdAttributeItem postAdAttributeItem : list) {
            if (!postAdAttributeItem.getKey().equals("price-frequency")) {
                BaseField view = this.viewFactory.getView(this.viewFactory.getType(postAdAttributeItem), getActivity(), postAdAttributeItem, Integer.parseInt(PaymentConverter.DEFAULT_PAYMENT_METHOD_ID));
                this.viewFactory.setListeners(view, this, this);
                Object preSelectedValue = view.getPreSelectedValue();
                if (!TextUtils.isEmpty(preSelectedValue)) {
                    view.setValue(preSelectedValue);
                }
                view.setSupportedValueOptionsList(postAdAttributeItem.getSupportedValueOptionsList());
                view.setValue(this.mActivity.getDao().getPostAdData().getAttribute(view.getKey()).getValue());
                view.setLabel(this.mActivity.getDao().getPostAdData().getAttribute(view.getKey()).getLabel());
                this.mAttrContainer.addView(view);
            }
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case 2131624525:
                PostAdLocationActivity.startForResult(this, this.mLocationField.getValue(), this.mLocationField.getLabel(), this.mLocationPostcode, this.mLocationOnMap, this.mLocationText);
                return;
            case 2131624526:
                PostAdCategoryActivity.startForResult(this, this.mActivity.getDao().getPostAdData().getTitle());
                return;
            default:
                if (view instanceof DateTimeField) {
                    ((DateTimeField) view).showDateTimePicker(getFragmentManager());
                    return;
                }
                return;
        }
    }

    public void onFieldValueChange(BaseField baseField, String str, String str2, String str3, boolean z) {
        if (this.mActivity.getDao() != null) {
            this.mActivity.getDao().addAttributeToData(str, str2, str3, baseField.getDisclaimer(), baseField.getTag(), z);
            showErrorMessageIfAny(baseField, str);
            if (z && str2 != null && !str2.isEmpty()) {
                fetchFeatures();
            }
        }
    }

    protected void showErrorMessageIfAny(BaseField baseField, String str) {
        Object verifyAttribute = this.mActivity.getDao().verifyAttribute(str);
        if (TextUtils.isEmpty(verifyAttribute)) {
            baseField.hideError();
        } else {
            baseField.showError(verifyAttribute);
        }
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (this.mActivity.getDao() != null) {
            refreshContent(this.mActivity.getDao().getPostAdData());
        }
    }

    @Subscribe
    public void onMetadataReceivedEvent(OnMetadataReceivedEvent onMetadataReceivedEvent) {
        if (this.mActivity.getDao() != null) {
            this.mHasCategoryChanged = false;
            List<SearchMetadata> searchMetadata = onMetadataReceivedEvent.getSearchMetadata();
            if (onMetadataReceivedEvent.getResultError() == null) {
                Map attributesValues = getAttributesValues();
                this.mActivity.getDao().resetAttributeData();
                for (SearchMetadata searchMetadata2 : searchMetadata) {
                    String name = searchMetadata2.getName();
                    if (!"unsupported".equalsIgnoreCase(searchMetadata2.getWrite())) {
                        this.mActivity.getDao().getPostAdData().getWriteStatusMap().put(searchMetadata2.getName(), searchMetadata2.getWrite());
                        if (!name.equalsIgnoreCase("price")) {
                            PostAdAttributeItem postAdAttributeItem = new PostAdAttributeItem(searchMetadata2.getName(), BuildConfig.FLAVOR, searchMetadata2.getLocalizedName(), searchMetadata2.getDescription(), 1, searchMetadata2.isPriceSensitive());
                            postAdAttributeItem.setType(searchMetadata2.getType());
                            postAdAttributeItem.setSupportedValues(searchMetadata2.getSupportedValues());
                            postAdAttributeItem.setSupportedLocalizedValues(searchMetadata2.getSupportedLabels());
                            postAdAttributeItem.setLocalizedLabel(searchMetadata2.getLocalizedName());
                            postAdAttributeItem.setDisabled(searchMetadata2.isDisabled());
                            postAdAttributeItem.setPreSelectedValue(searchMetadata2.getPreSelectedValue());
                            postAdAttributeItem.setSupportedValueOptionsList(searchMetadata2.getSupportedValueOptions());
                            String str = (String) attributesValues.get(searchMetadata2.getName());
                            if (str != null) {
                                postAdAttributeItem.setValue(str);
                            }
                            this.mActivity.getDao().getPostAdData().addPostAttribute(postAdAttributeItem);
                        }
                    } else if (name.equals("price")) {
                        this.mActivity.getDao().getPostAdData().getWriteStatusMap().put(searchMetadata2.getName(), searchMetadata2.getWrite());
                        this.mActivity.getDao().getPostAdData().removeAttribute("price");
                    }
                }
                this.mActivity.getDao().addAttributeToData(VIPContactFragment.CATEGORY_ID, this.mCategoryId, this.mCategoryText, null, null, false);
                showAttributes(this.mActivity.getDao().getListOfAttributes());
                return;
            }
            setCategoryValues(PaymentConverter.DEFAULT_PAYMENT_METHOD_ID, getString(2131165838));
            Toast.makeText(this.context, this.context.getString(2131165623), 1).show();
        }
    }

    private Map<String, String> getAttributesValues() {
        Map<String, String> hashMap = new HashMap();
        for (Entry entry : this.mActivity.getDao().getPostAdData().getAttributeMap().entrySet()) {
            CharSequence value = ((PostAdAttributeItem) entry.getValue()).getValue();
            if (!TextUtils.isEmpty(value)) {
                hashMap.put(entry.getKey(), value);
            }
        }
        return hashMap;
    }

    @Subscribe
    public void onFeaturesReceivedEvent(OnFeaturesReceivedEvent onFeaturesReceivedEvent) {
        if (this.mActivity.getDao() != null) {
            FeaturesResult featuresResult = new FeaturesResult();
            featuresResult.setFeatures(onFeaturesReceivedEvent.getFeatures());
            if (onFeaturesReceivedEvent.getResultError() != null) {
                featuresResult.setError(onFeaturesReceivedEvent.getResultError().getType());
                setPrice(getString(2131165430));
                return;
            }
            List<Feature> features = onFeaturesReceivedEvent.getFeatures();
            this.mActivity.getDao().getPostAdData().setFeaturesResult(featuresResult);
            Feature feature = null;
            for (Feature feature2 : features) {
                Feature feature22;
                if (FeatureType.INSERTION.getValue().equals(feature22.getName())) {
                    this.mActivity.getDao().getPostAdData().getSelectedFeatures().add(feature22);
                } else {
                    feature22 = feature;
                }
                feature = feature22;
            }
            setInsertionFeePrice(feature);
        }
    }

    public void refreshContent(PostAdData postAdData) {
        enableView(true);
        getTitleValues(postAdData);
        getCategoryViewValues();
        getLocationViewValues();
        Track.viewPostAdStep1(postAdData);
    }

    public void enableView(boolean z) {
        this.mCategoryField.setEnabled(z);
        this.mLocationField.setEnabled(z);
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
}
