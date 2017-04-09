package com.gumtree.android.savedsearches;

import android.animation.LayoutTransition;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.GridView;
import com.apptentive.android.sdk.Apptentive;
import com.gumtree.android.api.tracking.ApptentiveEvent;
import com.gumtree.android.auth.SyncAdapter;
import com.gumtree.android.common.AbundanceActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.fragments.BaseGridFragment;
import com.gumtree.android.common.utils.AppUtils;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.message_box.conversation.ConversationActivity;
import com.gumtree.android.notifications.NotificationType;
import com.gumtree.android.savedsearches.SavedSearchesEndlessAdapter.ListItemTag;
import java.util.ArrayList;
import java.util.List;
import novoda.lib.sqliteprovider.cursor.EmptyCursor;

public class SavedSearchesFragment extends BaseGridFragment implements LoaderCallbacks<Cursor>, OnClickListener {
    private static final int EDUCATE_NOTIFICATION_DURATION = 180;
    private static final String KEY_ID = "key_id";
    private static final String LOG_TAG = "SavedSearchesFragment";
    public static final String PREFS_SAVED_SEARCHES = "Preferences Saved Searches";
    private static final String STATUS = "status";
    private static IntentFilter syncFailIntentFilter = new IntentFilter(SyncAdapter.ACTION_FAILURE_SYNC);
    private static IntentFilter syncFailNetworkIntentFilter = new IntentFilter(SyncAdapter.ACTION_NETWORK_ERROR_SYNC);
    private static IntentFilter syncIntentFilter = new IntentFilter(SyncAdapter.ACTION_FINISHED_SYNC);
    private static IntentFilter syncLimitReachedIntentFilter = new IntentFilter(SyncAdapter.ACTION_LIMIT_REACHED_SYNC);
    private int abundanceCount;
    private SavedSearchesSectionedAdapter adapter;
    private boolean resultReturned;
    private List<Boolean> statuses = new ArrayList();
    private BroadcastReceiver syncBroadcastReceiver = new 1(this);

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getLoaderManager().initLoader(18, null, this);
    }

    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(this.syncBroadcastReceiver);
    }

    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(this.syncBroadcastReceiver, syncIntentFilter);
        getActivity().registerReceiver(this.syncBroadcastReceiver, syncFailIntentFilter);
        getActivity().registerReceiver(this.syncBroadcastReceiver, syncFailNetworkIntentFilter);
        getActivity().registerReceiver(this.syncBroadcastReceiver, syncLimitReachedIntentFilter);
    }

    private void setActionBarText() {
        if (this.resultReturned) {
            String string;
            String textForAbundance = getTextForAbundance(this.abundanceCount);
            if (this.abundanceCount == -1) {
                string = this.context.getString(2131165866);
            } else {
                string = textForAbundance;
            }
            FragmentActivity activity = getActivity();
            if (activity instanceof AbundanceActivity) {
                ((AbundanceActivity) activity).setAbundanceInTitle(string);
            }
        }
    }

    public void onCreate(Bundle bundle) {
        int i = 0;
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        if (bundle != null) {
            this.statuses.clear();
            boolean[] booleanArray = bundle.getBooleanArray(STATUS);
            int length = booleanArray.length;
            int i2 = 0;
            while (i < length) {
                this.statuses.add(i2, Boolean.valueOf(booleanArray[i]));
                i2++;
                i++;
            }
        }
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (showEducateNotification()) {
            displayEducateNotification(view);
        }
        if (bundle == null) {
            this.abundanceCount = -1;
            this.resultReturned = true;
            return;
        }
        this.abundanceCount = bundle.getInt("key_abundance");
        this.resultReturned = bundle.getBoolean("key_result_returned", false);
    }

    private void displayEducateNotification(View view) {
        ViewGroup viewGroup = (ViewGroup) view.findViewById(2131624584);
        ViewGroup viewGroup2 = (ViewGroup) view.findViewById(2131624585);
        viewGroup2.setVisibility(0);
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.setDuration(180);
        layoutTransition.setInterpolator(3, new DecelerateInterpolator());
        viewGroup.setLayoutTransition(layoutTransition);
        ((Button) view.findViewById(2131624589)).setOnClickListener(SavedSearchesFragment$$Lambda$1.lambdaFactory$(this, viewGroup, viewGroup2));
    }

    /* synthetic */ void lambda$displayEducateNotification$0(ViewGroup viewGroup, ViewGroup viewGroup2, View view) {
        getActivity().getSharedPreferences(PREFS_SAVED_SEARCHES, 0).edit().putBoolean("pref_seen_saved_search_education", true).commit();
        viewGroup.removeView(viewGroup2);
    }

    private boolean showEducateNotification() {
        if (getActivity().getSharedPreferences(PREFS_SAVED_SEARCHES, 0).getBoolean("pref_seen_saved_search_education", false)) {
            return false;
        }
        return true;
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        boolean[] zArr = new boolean[this.statuses.size()];
        int i = 0;
        for (Boolean booleanValue : this.statuses) {
            int i2 = i + 1;
            zArr[i] = booleanValue.booleanValue();
            i = i2;
        }
        bundle.putBooleanArray(STATUS, zArr);
        bundle.putInt("key_abundance", this.abundanceCount);
        bundle.putBoolean("key_result_returned", this.resultReturned);
    }

    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new SavedSearchesDao(this.context).getCursorLoader();
    }

    protected int getLayoutId() {
        return 2130903235;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (getGridAdapter() == null) {
            initAdapter();
        }
        loadList(cursor);
        setAbundanceData(cursor);
        setActionBarText();
        ((NotificationManager) getActivity().getSystemService(ConversationActivity.NOTIFICATION)).cancel(NotificationType.SAVED_SEARCHES.getType());
    }

    private void setAbundanceData(Cursor cursor) {
        this.abundanceCount = cursor.getCount();
    }

    protected void loadList(Cursor cursor) {
        this.adapter.changeCursor(cursor);
        boolean z = isGridViewVisible() || cursor.getCount() >= 0;
        setGridShown(z);
    }

    private void initAdapter() {
        this.adapter = new SavedSearchesSectionedAdapter(this.context, new SavedSearchesEndlessAdapter(getActivity(), new EmptyCursor(), this, this.statuses), getGridView());
        setGridAdapter(this.adapter);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        syncSavedSearches();
    }

    public void onGridItemClick(GridView gridView, View view, int i, long j) {
        super.onGridItemClick(gridView, view, i, j);
        try {
            Track.eventSavedSearchOpen();
            Intent intent = getGridAdapter().getIntent(i);
            if (intent != null) {
                startActivity(intent);
            }
            int intValue = ((Integer) view.getTag(2131623936)).intValue();
            if (intValue != 0) {
                NewSavedSearchesIntentService.deleteNewSavedSearch(intValue);
            }
        } catch (Throwable e) {
            Log.e(LOG_TAG, "Error when trying to get an SRP intent", e);
        }
    }

    public SavedSearchesSectionedAdapter getGridAdapter() {
        return (SavedSearchesSectionedAdapter) super.getGridAdapter();
    }

    protected String getEmptyText() {
        return this.context.getString(2131165634);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case 2131624854:
                ListItemTag listItemTag = (ListItemTag) view.getTag();
                removeItem(listItemTag.getId());
                this.statuses.remove(listItemTag.getPosition());
                return;
            default:
                com.gumtree.Log.e(getClass().getSimpleName(), "case not supported " + view.getId());
                return;
        }
    }

    public void removeItem(long j) {
        new Bundle().putLong(KEY_ID, j);
        new SavedSearchesDao(this.context).syncDelete(j);
        Snackbar.make(getView(), 2131165639, 0).setAction(2131165906, SavedSearchesFragment$$Lambda$2.lambdaFactory$(this, j)).setActionTextColor(ContextCompat.getColor(this.context, 2131493375)).show();
        getLoaderManager().restartLoader(18, null, this);
        Apptentive.engage(getActivity(), ApptentiveEvent.SAVED_SEARCHES_DELETE.getValue());
        Track.eventSavedSearchDeleteSuccess();
    }

    /* synthetic */ void lambda$removeItem$1(long j, View view) {
        new SavedSearchesDao(this.context).syncRestore(j);
        getLoaderManager().restartLoader(18, null, this);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(2131755026, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 2131624953:
                ((SavedSearchesActivity) getActivity()).refreshView();
                setGridShown(false);
                this.abundanceCount = -1;
                setActionBarText();
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private String getTextForAbundance(int i) {
        if (i == -1) {
            return this.context.getString(2131165866);
        }
        return "<b>" + AppUtils.getFormattedNumber(i) + "</b>" + " " + getString(2131165893);
    }

    public void syncSavedSearches() {
        startService(new Intent(getActivity(), SavedSearchesIntentService.class));
        setGridShown(false);
        this.abundanceCount = -1;
        setActionBarText();
    }
}
