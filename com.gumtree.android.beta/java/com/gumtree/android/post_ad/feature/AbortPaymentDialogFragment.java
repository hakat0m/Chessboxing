package com.gumtree.android.post_ad.feature;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import com.apptentive.android.sdk.Apptentive;
import com.gumtree.DevelopmentFlags;
import com.gumtree.android.api.tracking.ApptentiveEvent;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.managead.ManageAdActivity;
import com.gumtree.android.manageads.ManageAdsActivity;
import com.gumtree.android.post_ad.PostAdBaseActivity;
import com.gumtree.android.post_ad.PostAdDBIntentService;

public class AbortPaymentDialogFragment extends DialogFragment {
    private Context context;
    private PostAdBaseActivity mActivity;

    public static AbortPaymentDialogFragment newInstance() {
        return new AbortPaymentDialogFragment();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity.getApplicationContext();
        this.mActivity = (PostAdBaseActivity) activity;
    }

    public Dialog onCreateDialog(Bundle bundle) {
        return new Builder(getActivity()).setIcon(2130837801).setTitle(2131165924).setMessage(2131165624).setPositiveButton(2131165351, AbortPaymentDialogFragment$$Lambda$1.lambdaFactory$(this)).setNegativeButton(2131165350, AbortPaymentDialogFragment$$Lambda$2.lambdaFactory$(this)).create();
    }

    /* synthetic */ void lambda$onCreateDialog$0(DialogInterface dialogInterface, int i) {
        if (this.mActivity.getDao().getPostAdData().getSelectedFeatures() != null && this.mActivity.getDao().getPostAdData().getSelectedFeatures().size() > 0) {
            Apptentive.engage(this.mActivity, ApptentiveEvent.FEATURE_AD_CANCEL.getValue());
        }
        Track.eventPostAdCancel(this.mActivity.getDao().getPostAdData());
        PostAdDBIntentService.reset(this.mActivity.getDao().getAdId());
        this.mActivity.resetDao();
        if (DevelopmentFlags.getInstance().isNewManageAdsEnabled()) {
            startActivity(ManageAdsActivity.createIntent(getActivity()));
        } else {
            startActivity(ManageAdActivity.createIntent());
        }
        getActivity().setResult(-1);
        getActivity().finish();
    }

    /* synthetic */ void lambda$onCreateDialog$1(DialogInterface dialogInterface, int i) {
        dismiss();
    }
}
