package com.gumtree.android.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.DevelopmentFlags;
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.AuthenticatorActivity;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.categories.CategoryActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.common.location.LocationDAO;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.gumloc.Gumloc;
import com.gumtree.android.gumloc.Gumloc.GumlocRequest;
import com.gumtree.android.gumloc.Location;
import com.gumtree.android.gumloc.LocationUpdateEvent;
import com.gumtree.android.gumloc.async.loaders.SupportGeocoderLoader;
import com.gumtree.android.gumloc.exception.LocationServiceNotAvailable;
import com.gumtree.android.gumloc.geocoder.Address;
import com.gumtree.android.gumloc.geocoder.GeocoderResult;
import com.gumtree.android.locations.LocationActivity;
import com.gumtree.android.login.AuthActivity;
import com.squareup.otto.Subscribe;
import java.util.List;
import javax.inject.Inject;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class HomeCustomiseFragment extends BaseFragment implements LoaderCallbacks<GeocoderResult>, OnClickListener {
    public static final String DEFAULT_CATEGORY_ID = "1";
    public static final String DEFAULT_CATEGORY_NAME = "All Categories";
    public static final String DEFAULT_CATEGORY_PATH = "all";
    protected static final String KEY_CATEGORY = "category_pref_id";
    protected static final String NAME_CATEGORY = "category_pref_name";
    protected static final String PATH_CATEGORY = "category_pref_path";
    @Inject
    BaseAccountManager acccountManager;
    private CustomiseCallback customiseCallback;
    @Inject
    EventBus eventBus;
    private Gumloc gumloc;
    private boolean hasCategoryBeenSet;

    public HomeCustomiseFragment() {
        ComponentsManager.get().getAppComponent().inject(this);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        enableLocation();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130903207, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.customiseCallback = (HomeActivity) getActivity();
        view.setOnClickListener(this);
        view.findViewById(2131624492).setOnClickListener(this);
        savePref();
    }

    public void enableLocation() {
        try {
            this.gumloc = Gumloc.with(getActivity()).connectWith(new 1(this));
        } catch (LocationServiceNotAvailable e) {
        }
    }

    @NeedsPermission({"android.permission.ACCESS_FINE_LOCATION"})
    public void startUpdates() {
        if (this.gumloc != null) {
            this.gumloc.startUpdates(GumlocRequest.create());
            getCategoryPicker();
        }
    }

    @OnPermissionDenied({"android.permission.ACCESS_FINE_LOCATION"})
    public void onLocationPermissionDenied() {
        getCategoryPicker();
    }

    public void onStart() {
        super.onStart();
        this.eventBus.register(this);
    }

    public void onStop() {
        super.onStop();
        this.eventBus.unregister(this);
        if (this.gumloc != null) {
            try {
                this.gumloc.stopUpdates();
            } catch (IllegalStateException e) {
            }
        }
    }

    public void onDestroy() {
        disableLocation();
        super.onDestroy();
    }

    public void disableLocation() {
        if (this.gumloc != null) {
            this.gumloc.disconnect();
        }
    }

    private void savePref() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String str = DEFAULT_CATEGORY_NAME;
        String str2 = DEFAULT_CATEGORY_ID;
        String str3 = DEFAULT_CATEGORY_PATH;
        if (this.acccountManager.isUserLoggedIn()) {
            str = defaultSharedPreferences.getString(NAME_CATEGORY, str);
            str2 = defaultSharedPreferences.getString(KEY_CATEGORY, str2);
            str3 = defaultSharedPreferences.getString(PATH_CATEGORY, str3);
        }
        if (!this.hasCategoryBeenSet) {
            this.customiseCallback.onCategoryChanged(str2, str, str3);
        }
        setTitleCustomiseSection(str);
        this.hasCategoryBeenSet = true;
    }

    public void setTitleCustomiseSection(String str) {
        String string;
        CharSequence charSequence;
        TextView textView = (TextView) getView().findViewById(2131624493);
        boolean z = getResources().getBoolean(2131427340);
        if (DEFAULT_CATEGORY_NAME.equals(str)) {
            string = getResources().getString(2131165468);
        } else {
            string = String.format(getResources().getString(2131165466), new Object[]{str});
        }
        if (z) {
            charSequence = string + " " + String.format(getResources().getString(2131165467), new Object[]{((GumtreeApplication) this.context.getApplicationContext()).getGlobalBuyerLocation().getDisplayText(false)});
        } else {
            Object obj = string;
        }
        textView.setText(charSequence);
    }

    public void onClick(View view) {
        if (this.acccountManager.isUserLoggedIn()) {
            HomeCustomiseFragmentPermissionsDispatcher.startUpdatesWithCheck(this);
        } else if (DevelopmentFlags.getInstance().isNewLoginEnabled()) {
            startActivityForResult(AuthActivity.createIntent(AuthIdentifier.SETTINGS, getActivity()), LocationActivity.ACTIVITY_REQUEST_CODE);
        } else {
            AuthenticatorActivity.startActivity(getActivity(), -1);
        }
    }

    private void getCategoryPicker() {
        CategoryActivity.startForResult(this, "android.intent.action.PICK", BuildConfig.FLAVOR);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        switch (i) {
            case HighlightView.GROW_NONE /*1*/:
                if (i2 == -1) {
                    updateSharedPreference(intent);
                    setTitleCustomiseSection(intent.getStringExtra(StatefulActivity.EXTRA_CATEGORY_NAME));
                    return;
                }
                return;
            case LocationActivity.ACTIVITY_REQUEST_CODE /*999*/:
                if (i2 == -1) {
                    if (DevelopmentFlags.getInstance().isNewLoginEnabled() && intent.hasExtra("displayname")) {
                        showSnackBar(getString(2131165552, new Object[]{intent.getStringExtra("displayname")}));
                    }
                    HomeCustomiseFragmentPermissionsDispatcher.startUpdatesWithCheck(this);
                    return;
                }
                return;
            default:
                Log.e(getClass().getSimpleName(), "case not supported " + i);
                return;
        }
    }

    private void updateSharedPreference(Intent intent) {
        String stringExtra = intent.getStringExtra(StatefulActivity.EXTRA_CATEGORY_NAME);
        String stringExtra2 = intent.getStringExtra(StatefulActivity.NAME_CATEGORY_ID);
        String stringExtra3 = intent.getStringExtra(StatefulActivity.EXTRA_CATEGORY_PATH);
        Editor edit = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
        edit.putString(NAME_CATEGORY, stringExtra);
        edit.putString(KEY_CATEGORY, stringExtra2);
        edit.putString(PATH_CATEGORY, stringExtra3);
        edit.apply();
        this.customiseCallback.onCategoryChanged(stringExtra2, stringExtra, stringExtra3);
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        HomeCustomiseFragmentPermissionsDispatcher.onRequestPermissionsResult(this, i, iArr);
    }

    @Subscribe
    public void onLocationUpdateEvent(LocationUpdateEvent locationUpdateEvent) {
        if (locationUpdateEvent.isLocationFound()) {
            Location location = locationUpdateEvent.getLocation();
            SupportGeocoderLoader.restart(location.getLatitude(), location.getLongitude(), getLoaderManager(), this);
        }
    }

    public Loader<GeocoderResult> onCreateLoader(int i, Bundle bundle) {
        return SupportGeocoderLoader.newLoader(getActivity().getApplicationContext(), bundle);
    }

    public void onLoadFinished(Loader<GeocoderResult> loader, GeocoderResult geocoderResult) {
        if (geocoderResult != null && geocoderResult.isSuccessful()) {
            List addresses = geocoderResult.getAddresses();
            if (addresses != null && !addresses.isEmpty()) {
                ((GumtreeApplication) getActivity().getApplicationContext()).setDeviceLocation(LocationDAO.create().from((Address) addresses.get(0)));
            }
        }
    }

    public void onLoaderReset(Loader<GeocoderResult> loader) {
    }
}
