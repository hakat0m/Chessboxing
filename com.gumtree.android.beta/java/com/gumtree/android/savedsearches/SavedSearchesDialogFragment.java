package com.gumtree.android.savedsearches;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import com.apptentive.android.sdk.Apptentive;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.api.tracking.ApptentiveEvent;
import com.gumtree.android.common.views.fields.RestorableSwitch;

public class SavedSearchesDialogFragment extends DialogFragment {
    private static String EXTRA_SEARCH_ID = "SearchId";
    private static String EXTRA_SEARCH_URL = "SearchUrl";
    private Context context;
    private boolean emailAlert = true;

    public static SavedSearchesDialogFragment newInstance(String str, long j) {
        SavedSearchesDialogFragment savedSearchesDialogFragment = new SavedSearchesDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_SEARCH_ID, j);
        bundle.putString(EXTRA_SEARCH_URL, str);
        savedSearchesDialogFragment.setArguments(bundle);
        return savedSearchesDialogFragment;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity.getApplicationContext();
    }

    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
        }
    }

    public void onDismiss(DialogInterface dialogInterface) {
        if (getActivity() != null) {
            Apptentive.engage(getActivity(), ApptentiveEvent.SAVED_SEARCHES_SUCCESS.getValue());
        }
        super.onDismiss(dialogInterface);
    }

    private void changeEmailAlert(String str, long j) {
        new SavedSearchesDao(this.context).changeEmailAlert(j, isEmailAlert(), 0);
        SavedSearchesPostIntentService.saveCapiSavedSearch(str, String.valueOf(j), isEmailAlert());
    }

    public Dialog onCreateDialog(Bundle bundle) {
        Bundle arguments = getArguments();
        long j = arguments != null ? arguments.getLong(EXTRA_SEARCH_ID, -1) : -1;
        String string = arguments != null ? arguments.getString(EXTRA_SEARCH_URL) : BuildConfig.FLAVOR;
        View inflate = LayoutInflater.from(getActivity()).inflate(2130903236, null);
        RestorableSwitch restorableSwitch = (RestorableSwitch) inflate.findViewById(16908311);
        restorableSwitch.setChecked(Boolean.TRUE.booleanValue());
        restorableSwitch.setOnCheckedChangeListener(SavedSearchesDialogFragment$$Lambda$1.lambdaFactory$(this));
        Builder builder = new Builder(getActivity());
        builder.setView(inflate);
        builder.setPositiveButton(2131165672, SavedSearchesDialogFragment$$Lambda$2.lambdaFactory$(this, string, j));
        return builder.create();
    }

    /* synthetic */ void lambda$onCreateDialog$0(CompoundButton compoundButton, boolean z) {
        setEmailAlert(z);
    }

    /* synthetic */ void lambda$onCreateDialog$1(String str, long j, DialogInterface dialogInterface, int i) {
        changeEmailAlert(str, j);
    }

    public boolean isEmailAlert() {
        return this.emailAlert;
    }

    public void setEmailAlert(boolean z) {
        this.emailAlert = z;
    }
}
