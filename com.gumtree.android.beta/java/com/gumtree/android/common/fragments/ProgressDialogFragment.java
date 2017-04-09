package com.gumtree.android.common.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ProgressDialogFragment extends DialogFragment {
    private static final String TAG = ProgressDialogFragment.class.getSimpleName();

    public static void show(FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
            Fragment findFragmentByTag = fragmentManager.findFragmentByTag(TAG);
            if (findFragmentByTag != null) {
                beginTransaction.remove(findFragmentByTag);
            }
            new ProgressDialogFragment().show(beginTransaction, TAG);
        }
    }

    public static void dismiss(FragmentManager fragmentManager) {
        if (fragmentManager != null) {
            DialogFragment dialogFragment = (DialogFragment) fragmentManager.findFragmentByTag(TAG);
            if (dialogFragment != null) {
                dialogFragment.dismiss();
            }
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setCancelable(false);
    }

    @NonNull
    public Dialog onCreateDialog(Bundle bundle) {
        Dialog progressDialog = new ProgressDialog(getActivity(), getTheme());
        progressDialog.setTitle(getString(2131165679));
        progressDialog.setMessage(getString(2131165866));
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(0);
        return progressDialog;
    }
}
