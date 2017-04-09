package com.gumtree.android.favourites;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import com.apptentive.android.sdk.Apptentive;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.DevelopmentFlags;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.api.tracking.ApptentiveEvent;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.AuthenticatorActivity;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.AbundanceActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.fragments.BaseGridFragment;
import com.gumtree.android.common.fragments.TwoOptionsDialogFragment;
import com.gumtree.android.common.loaders.AsyncQuery$QueryListener;
import com.gumtree.android.common.utils.AppUtils;
import com.gumtree.android.locations.LocationActivity;
import com.gumtree.android.login.AuthActivity;
import com.gumtree.android.srp.OnFavouriteClickListener;
import com.gumtree.android.vip.VIPActivity;
import javax.inject.Inject;

public class FavouritesFragment extends BaseGridFragment implements OnClickListener, LoaderCallbacks<Cursor>, OnItemClickListener, AsyncQuery$QueryListener, OnFavouriteClickListener {
    public static final int DIALOG_FRAGMENT = 1;
    public static final String LOGIN_SUGGESTION_DIALOG = "LoginSuggestionDialog";
    private static IntentFilter mFavIntentFilter = new IntentFilter(FavouriteIntentService.ACTION_AD_FAVOURITED);
    private static IntentFilter mSyncFailIntentFilter = new IntentFilter(FavouritesSyncIntentService.ACTION_FAVOURITES_FAILURE_SYNC);
    private static IntentFilter mSyncIntentFilter = new IntentFilter(FavouritesSyncIntentService.ACTION_FAVOURITES_FINISHED_SYNC);
    private static IntentFilter mSyncNetworkErrorIntentFilter = new IntentFilter(FavouritesSyncIntentService.ACTION_FAVOURITES_NETWORK_ERROR_SYNC);
    @Inject
    BaseAccountManager customerAccountManager;
    private BroadcastReceiver mFavBroadcastReceiver = new 1(this);
    private BroadcastReceiver mSyncBroadcastReceiver;
    private FavouritesQueryHelper queryHelper;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.queryHelper = new FavouritesQueryHelper();
    }

    public void onCreate(Bundle bundle) {
        GumtreeApplication.component().inject(this);
        setHasOptionsMenu(true);
        super.onCreate(bundle);
        this.mSyncBroadcastReceiver = new 2(this);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(2131755026, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 2131624953:
                Track.eventFavouriteRefresh();
                startFavouritesSynchronization(true, null);
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void onStart() {
        super.onStart();
        Track.viewFavourites();
    }

    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(this.mSyncBroadcastReceiver, mSyncIntentFilter);
        getActivity().registerReceiver(this.mSyncBroadcastReceiver, mSyncFailIntentFilter);
        getActivity().registerReceiver(this.mSyncBroadcastReceiver, mSyncNetworkErrorIntentFilter);
        getActivity().registerReceiver(this.mFavBroadcastReceiver, mFavIntentFilter);
    }

    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(this.mSyncBroadcastReceiver);
        getActivity().unregisterReceiver(this.mFavBroadcastReceiver);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getGridView().setOnItemClickListener(this);
    }

    public FavouritesEndlessAdapter getGridAdapter() {
        return (FavouritesEndlessAdapter) super.getGridAdapter();
    }

    protected int getLayoutId() {
        return 2130903200;
    }

    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        CursorLoader cursorLoader = new CursorLoader(this.context, this.queryHelper.getUri(), null, this.queryHelper.getWhereQuery(), this.queryHelper.getWhereParams(new String[0]), this.queryHelper.getSortOrder());
        cursorLoader.setUpdateThrottle(1000);
        return cursorLoader;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        setAdapter(cursor);
        if (cursor != null) {
            int count = cursor.getCount();
            setAbundanceTitle("<b>" + AppUtils.getFormattedNumber(count) + "</b>" + " " + getResources().getQuantityString(2131689472, count));
            return;
        }
        setAbundanceTitle(BuildConfig.FLAVOR);
    }

    private void setAbundanceTitle(String str) {
        FragmentActivity activity = getActivity();
        if (activity instanceof AbundanceActivity) {
            ((AbundanceActivity) activity).setAbundanceInTitle(str);
        }
    }

    private void setAdapter(Cursor cursor) {
        if (getGridAdapter() == null) {
            FavouritesEndlessAdapter favouritesEndlessAdapter = new FavouritesEndlessAdapter(getActivity(), cursor);
            favouritesEndlessAdapter.setListener(this);
            setGridAdapter(favouritesEndlessAdapter);
            setGridShown(true);
            return;
        }
        getGridAdapter().changeCursor(cursor);
        setGridShown(true);
    }

    protected String getEmptyText() {
        return getString(2131165850);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        if (getGridAdapter() != null) {
            getGridAdapter().changeCursor(null);
            setGridAdapter(null);
        }
    }

    public void onComplete(int i, Object obj) {
        Toast.makeText(this.context, (String) obj, 0).show();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        startActivity(VIPActivity.createIntentForFavourites(this.context, j));
    }

    public void onQueryComplete(int i, Object obj, Cursor cursor) {
    }

    public void removeItem(long j) {
        Apptentive.engage(getActivity(), ApptentiveEvent.WATCHLIST_DELETE.getValue());
        Track.eventFavouriteRemove(j);
        FavouriteIntentService.unfavourite(String.valueOf(j));
    }

    public void onFavouriteClick(long j, boolean z) {
        removeItem(j);
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        if (i != -1) {
            return;
        }
        if (DevelopmentFlags.getInstance().isNewLoginEnabled()) {
            startActivityForResult(AuthActivity.createIntent(AuthIdentifier.FAVOURITES, getActivity()), LocationActivity.ACTIVITY_REQUEST_CODE);
        } else {
            AuthenticatorActivity.startActivity(getActivity(), 5);
        }
    }

    public void startFavouritesSynchronization(boolean z, Bundle bundle) {
        if (!this.customerAccountManager.isUserLoggedIn()) {
            if (bundle == null) {
                TwoOptionsDialogFragment newInstance = TwoOptionsDialogFragment.newInstance(getString(2131165804), getString(2131165803), getString(2131165801), getString(2131165802));
                newInstance.setTargetFragment(this, DIALOG_FRAGMENT);
                newInstance.show(getActivity().getSupportFragmentManager(), LOGIN_SUGGESTION_DIALOG);
            }
            if (!z) {
                resetLoader(false);
            }
        } else if (bundle == null) {
            setGridShown(false);
            setAbundanceTitle(getString(2131165866));
            FavouritesSyncIntentService.start();
        } else {
            resetLoader(false);
        }
    }

    public void resetLoader(boolean z) {
        setGridShown(false);
        setAbundanceTitle(getString(2131165866));
        if (!z) {
            getLoaderManager().restartLoader(0, null, this);
        }
    }
}
