package com.gumtree.android.clients;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog.Builder;
import com.gumtree.android.clients.model.SoftUpdate;
import com.gumtree.android.common.activities.BaseActivity;

public class SoftUpdateFragment extends DialogFragment {
    private static final String TAG = SoftUpdateFragment.class.getSimpleName();
    private SoftUpdate message;

    public static void show(SoftUpdate softUpdate, BaseActivity baseActivity) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG, softUpdate);
        FragmentTransaction beginTransaction = baseActivity.getSupportFragmentManager().beginTransaction();
        SoftUpdateFragment softUpdateFragment = (SoftUpdateFragment) baseActivity.getSupportFragmentManager().findFragmentByTag(TAG);
        if (softUpdateFragment != null) {
            beginTransaction.remove(softUpdateFragment);
        }
        softUpdateFragment = new SoftUpdateFragment();
        softUpdateFragment.setArguments(bundle);
        softUpdateFragment.show(baseActivity.getSupportFragmentManager(), TAG);
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (getArguments().containsKey(TAG)) {
            this.message = (SoftUpdate) getArguments().getSerializable(TAG);
        } else {
            this.message = new SoftUpdate();
        }
    }

    public Dialog onCreateDialog(Bundle bundle) {
        CharSequence string = this.message.isLastChance() ? getString(2131165786) : getString(2131165372);
        Builder builder = new Builder(getActivity());
        builder.setTitle(2131165922);
        builder.setMessage(this.message.getMessage()).setPositiveButton(2131165938, SoftUpdateFragment$$Lambda$1.lambdaFactory$(this)).setNegativeButton(string, SoftUpdateFragment$$Lambda$2.lambdaFactory$(this));
        return builder.create();
    }

    /* synthetic */ void lambda$onCreateDialog$0(DialogInterface dialogInterface, int i) {
        gotoPlayStore();
    }

    /* synthetic */ void lambda$onCreateDialog$1(DialogInterface dialogInterface, int i) {
        dismiss();
    }

    private void gotoPlayStore() {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.gumtree.android.beta")));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.gumtree.android.beta")));
        }
    }
}
