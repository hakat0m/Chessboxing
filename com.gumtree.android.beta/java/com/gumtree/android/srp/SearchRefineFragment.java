package com.gumtree.android.srp;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.Constants;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.http.DetachableResultReceiver;
import com.gumtree.android.common.http.DetachableResultReceiver$Receiver;
import com.gumtree.android.common.location.AppLocation;
import com.gumtree.android.common.location.GeocoderLocation;
import com.gumtree.android.common.location.GumtreeLocation;
import com.gumtree.android.common.location.GumtreePostcodeLocation;
import com.gumtree.android.common.location.RadiusDAO;
import com.gumtree.android.common.search.Search;
import com.gumtree.android.common.views.fields.BaseField;
import com.gumtree.android.common.views.fields.ButtonField;
import com.gumtree.android.message_box.ConversationsIntentService;
import com.gumtree.android.model.SearchMetadata;
import com.gumtree.android.postad.services.converter.PaymentConverter;

public class SearchRefineFragment extends BaseDynAttributeFragment implements LoaderCallbacks<Cursor>, DetachableResultReceiver$Receiver {
    private static final String AND = "<>? AND ";
    private static final String CASE_NOT_SUPPORTED = "case not supported ";
    private static final int MAX_LOCATION_LENGTH = 5;
    private static final String TRACKING = "tracking";
    private final DetachableResultReceiver receiver = new DetachableResultReceiver(new Handler());
    private boolean savedSearches;
    private Bundle stateBundle;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onNewSearch(activity.getIntent());
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.receiver.setReceiver(this);
    }

    private void initializeState(Bundle bundle) {
        this.stateBundle = new Bundle();
        for (String str : bundle.keySet()) {
            if (!isUnsupportedKey(bundle, str)) {
                this.stateBundle.putString(str, bundle.getString(str));
            }
        }
    }

    private boolean isUnsupportedKey(Bundle bundle, String str) {
        return str.equals(StatefulActivity.EXTRA_SESSION_TIMESTAMP) || !(bundle.get(str) instanceof String);
    }

    private void onNewSearch(Intent intent) {
        initializeState(Search.create(this.context, intent).asBundle());
        this.savedSearches = intent.getBooleanExtra("SAVED_SEARCHES", false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        getListView().setItemsCanFocus(true);
        if (bundle == null) {
            doServiceCall();
        } else {
            initializeState(bundle);
        }
        getLoaderManager().initLoader(8, null, this);
        initializeButton(view);
    }

    private void initializeButton(View view) {
        view.findViewById(2131624562).setOnClickListener(SearchRefineFragment$$Lambda$1.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$initializeButton$0(View view) {
        Track.eventSRPFireRefineSearch();
        ((SRPActivity) getActivity()).doRefineSearch();
        ((SRPActivity) getActivity()).getMenuDrawer().closeDrawer(MAX_LOCATION_LENGTH);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getListView().addFooterView(((LayoutInflater) getActivity().getSystemService("layout_inflater")).inflate(2130903231, null, false));
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        switch (i) {
            case ConversationsIntentService.DEFAULT_PAGE /*0*/:
                updateLocationOnLocationPickerResult();
                return;
            case HighlightView.GROW_NONE /*1*/:
                if (i2 == -1) {
                    try {
                        updateCategoryOnCategoryPickerResult(intent);
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                return;
            default:
                Log.e(getClass().getSimpleName(), CASE_NOT_SUPPORTED + i);
                return;
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putAll(this.stateBundle);
    }

    public void onDestroy() {
        super.onDestroy();
        this.receiver.clearReceiver();
    }

    public Bundle getStateBundle() {
        return this.stateBundle;
    }

    protected int getLayoutId() {
        return 2130903230;
    }

    private void updateLocationOnLocationPickerResult() {
        addLocationInfoToBundle();
        notifyAdapterDataChanged();
    }

    private void updateCategoryOnCategoryPickerResult(Intent intent) {
        this.stateBundle.putString(StatefulActivity.NAME_CATEGORY_ID, intent.getStringExtra(StatefulActivity.NAME_CATEGORY_ID));
        this.stateBundle.putString(StatefulActivity.EXTRA_CATEGORY_NAME, intent.getStringExtra(StatefulActivity.EXTRA_CATEGORY_NAME));
        getLoaderManager().restartLoader(8, null, this);
        doServiceCall();
    }

    public void updateCategoryPicker(String str, String str2) {
        this.stateBundle.putString(StatefulActivity.NAME_CATEGORY_ID, str);
        this.stateBundle.putString(StatefulActivity.EXTRA_CATEGORY_NAME, str2);
        getLoaderManager().restartLoader(8, null, this);
        doServiceCall();
    }

    private void doServiceCall() {
        startService(GumtreeApplication.getAPI().getHttpIntentForSearchMetadata(getCategoryId(), this.receiver));
    }

    protected void notifyAdapterDataChanged() {
        if (getListAdapter() != null) {
            getListAdapter().notifyDataSetChanged();
        }
    }

    public final Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this.context, SearchMetadata.URI, null, "category_id=? AND search_param<>? AND name<>? AND name<>?", new String[]{getCategoryId(), "unsupported", "featuredWith", "includeTopAds"}, "_id");
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        updateListViewCursor(cursor);
    }

    public final void manipulateView(String str, BaseField baseField) {
        super.manipulateView(str, baseField);
        baseField.setValue(this.stateBundle.getString(str));
        if (!(baseField instanceof ButtonField)) {
            return;
        }
        if (baseField.getKey().equals(StatefulActivity.NAME_CATEGORY_ID)) {
            ((ButtonField) baseField).setDescription(this.stateBundle.getString(StatefulActivity.EXTRA_CATEGORY_NAME));
        } else if (baseField.getKey().equals(StatefulActivity.NAME_LOCATION_ID)) {
            ((ButtonField) baseField).setDescription(this.stateBundle.getString(StatefulActivity.EXTRA_LOCATION_NAME));
        }
    }

    public void doSearch() {
        if (getActivity() != null) {
            getActivity().getWindow().getDecorView().findViewById(2131624933).performClick();
        }
    }

    private void addLocationInfoToBundle() {
        this.stateBundle.putString(StatefulActivity.EXTRA_LOCATION_NAME, getLocation().getDisplayText(false));
        switch (1.$SwitchMap$com$gumtree$android$common$location$AppLocation$LocationType[getLocation().getType().ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                this.stateBundle.putString(StatefulActivity.NAME_LATITUDE, BuildConfig.FLAVOR + ((GeocoderLocation) getLocation()).getLat());
                this.stateBundle.putString(StatefulActivity.NAME_LONGITUDE, BuildConfig.FLAVOR + ((GeocoderLocation) getLocation()).getLng());
                this.stateBundle.putString(StatefulActivity.NAME_ZIPCODE, BuildConfig.FLAVOR + ((GeocoderLocation) getLocation()).getPostCode());
                this.stateBundle.remove(StatefulActivity.NAME_LOCATION_ID);
                return;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                GumtreeLocation gumtreeLocation = (GumtreeLocation) getLocation();
                if (gumtreeLocation instanceof GumtreePostcodeLocation) {
                    this.stateBundle.putString(StatefulActivity.NAME_ZIPCODE, BuildConfig.FLAVOR + gumtreeLocation.getName());
                    this.stateBundle.remove(StatefulActivity.NAME_LOCATION_ID);
                } else {
                    this.stateBundle.putString(StatefulActivity.NAME_LOCATION_ID, BuildConfig.FLAVOR + ((GumtreeLocation) getLocation()).getLocationId());
                    this.stateBundle.remove(StatefulActivity.NAME_ZIPCODE);
                }
                this.stateBundle.remove(StatefulActivity.NAME_LATITUDE);
                this.stateBundle.remove(StatefulActivity.NAME_LONGITUDE);
                return;
            default:
                Log.e(getClass().getSimpleName(), CASE_NOT_SUPPORTED + getLocation().getType());
                return;
        }
    }

    public final void onLoaderReset(Loader<Cursor> loader) {
        setListAdapter(null);
    }

    public void onFieldValueChange(BaseField baseField, String str, String str2, String str3, boolean z) {
        boolean z2 = true;
        if (!TRACKING.equals(str)) {
            this.stateBundle.putString(str, str2);
        }
        if (StatefulActivity.NAME_DISTANCE.equals(str)) {
            if (!this.savedSearches) {
                PreferenceManager.getDefaultSharedPreferences(GumtreeApplication.getContext()).edit().putString(RadiusDAO.RADIUS_PREF, str2).commit();
            }
            boolean z3 = (getLocation() instanceof GumtreePostcodeLocation) && getLocation().getName().length() < MAX_LOCATION_LENGTH;
            if (z3) {
                baseField.setValue(RadiusDAO.GUMTREE_DEFAULT_RADIUS);
            }
            if (z3) {
                z2 = false;
            }
            baseField.setEnabled(z2);
        }
        if (TRACKING.equals(str)) {
            PreferenceManager.getDefaultSharedPreferences(GumtreeApplication.getContext()).edit().putString(Constants.PREF_AUTO_COMPLETE_TRACKING, str2).commit();
        }
    }

    public final void doUpdate(Intent intent) {
        onNewSearch(intent);
        resetList();
        getLoaderManager().restartLoader(8, null, this);
    }

    private void resetList() {
        setListShown(false);
        setListAdapter(null);
    }

    protected String getCategoryId() {
        String string = this.stateBundle.getString(StatefulActivity.NAME_CATEGORY_ID);
        if (TextUtils.isEmpty(string)) {
            return PaymentConverter.DEFAULT_PAYMENT_METHOD_ID;
        }
        return string;
    }

    private AppLocation getLocation() {
        return ((GumtreeApplication) this.context.getApplicationContext()).getGlobalBuyerLocation();
    }

    protected String getEmptyText() {
        return BuildConfig.FLAVOR;
    }

    public void onReceiveResult(int i, Bundle bundle) {
    }
}
