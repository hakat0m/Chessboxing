package com.gumtree.android.location;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.fragments.NeverAskAgainPermissionsDialogFragment;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.location.di.DaggerLocationComponent;
import com.gumtree.android.location.di.LocationComponent;
import com.gumtree.android.location.di.LocationModule;
import com.gumtree.android.location.model.LocationData;
import com.gumtree.android.location.presenters.LocationPresenter;
import com.gumtree.android.location.presenters.LocationPresenter.View;
import com.gumtree.android.location.views.CustomAutoCompleteTextView;
import java.util.ArrayList;
import javax.inject.Inject;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class LocationActivity extends BaseActivity implements View {
    private static final String PERMISSIONS_DIALOG_TAG = "permissionsDialog";
    private static final String PREVIOUS_SEARCH = "previousSearch";
    @Bind({2131624156})
    android.view.View currentLocation;
    private LocationsAdapter locationAdapter;
    @Bind({2131624153})
    android.view.View locationContainer;
    @Inject
    LocationPresenter presenter;
    @Bind({2131624155})
    CustomAutoCompleteTextView suggestionsList;

    @OnClick({2131624156})
    void onCurrentLocationClicked() {
        LocationActivityPermissionsDispatcher.getCurrentLocationWithCheck(this);
        this.presenter.onCurrentLocationClicked();
    }

    public static Intent createIntent(Context context, LocationData locationData) {
        Intent intent = new Intent(context, LocationActivity.class);
        intent.putExtra(StatefulActivity.EXTRA_LOCATION_NAME, locationData);
        return intent;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getLocationComponent().inject(this);
        setContentView(2130903087);
        ButterKnife.bind(this);
        initializeSuggestionsList(bundle);
    }

    protected void onResume() {
        super.onResume();
        this.presenter.attachView(this);
    }

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        LocationActivityPermissionsDispatcher.onRequestPermissionsResult(this, i, iArr);
    }

    @NeedsPermission({"android.permission.ACCESS_FINE_LOCATION"})
    public void getCurrentLocation() {
        this.presenter.getCurrentLocation();
    }

    @OnNeverAskAgain({"android.permission.ACCESS_FINE_LOCATION"})
    void showOnNeverAskLocation() {
        NeverAskAgainPermissionsDialogFragment newInstance = NeverAskAgainPermissionsDialogFragment.newInstance(2131165675, 2131165674);
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.add(newInstance, PERMISSIONS_DIALOG_TAG);
        beginTransaction.commitAllowingStateLoss();
    }

    public void enableCurrentLocation(boolean z) {
        this.currentLocation.setEnabled(z);
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(PREVIOUS_SEARCH, this.locationAdapter.getPreviousSearch());
    }

    protected void onPause() {
        this.presenter.detachView();
        super.onPause();
    }

    private LocationComponent getLocationComponent() {
        LocationComponent locationComponent = (LocationComponent) ComponentsManager.get().getBaseComponent(LocationComponent.KEY);
        if (locationComponent != null) {
            return locationComponent;
        }
        ApplicationComponent appComponent = ComponentsManager.get().getAppComponent();
        LocationData locationData = (LocationData) getIntent().getSerializableExtra(StatefulActivity.EXTRA_LOCATION_NAME);
        if (locationData == null) {
            locationData = new LocationData();
        }
        Object build = DaggerLocationComponent.builder().applicationComponent(appComponent).locationModule(new LocationModule(locationData)).build();
        ComponentsManager.get().putBaseComponent(LocationComponent.KEY, build);
        return build;
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                finish();
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void initializeSuggestionsList(Bundle bundle) {
        String string = bundle != null ? bundle.getString(PREVIOUS_SEARCH) : null;
        this.suggestionsList.requestFocus();
        getWindow().setSoftInputMode(4);
        this.locationAdapter = new LocationsAdapter(this, 2130903342, new ArrayList(), this.presenter, string);
        this.suggestionsList.setAdapter(this.locationAdapter);
        this.suggestionsList.setOnItemClickListener(LocationActivity$$Lambda$1.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$initializeSuggestionsList$0(AdapterView adapterView, android.view.View view, int i, long j) {
        if (i == -1) {
            this.presenter.onSuggestionClicked(getLocationDataItem(this.suggestionsList.getText().toString()));
        } else {
            this.presenter.onSuggestionClicked((LocationData) this.locationAdapter.getItem(i));
        }
    }

    protected void onDestroy() {
        if (isFinishing()) {
            ComponentsManager.get().removeBaseComponent(LocationComponent.KEY);
        }
        super.onDestroy();
    }

    public void showErrorMessage(String str) {
        promptSnackbar(str);
    }

    public void completeWithLocation(LocationData locationData) {
        Intent intent = new Intent();
        intent.putExtra(StatefulActivity.EXTRA_LOCATION_NAME, locationData);
        setResult(-1, intent);
        finish();
    }

    private void promptSnackbar(String str) {
        Toast.makeText(getApplicationContext(), str, 0).show();
    }

    private LocationData getLocationDataItem(String str) {
        for (int i = 0; i < this.locationAdapter.getCount(); i++) {
            LocationData locationData = (LocationData) this.locationAdapter.getItem(i);
            if (str.equals(locationData.getDisplayName())) {
                return locationData;
            }
        }
        return new LocationData();
    }
}
