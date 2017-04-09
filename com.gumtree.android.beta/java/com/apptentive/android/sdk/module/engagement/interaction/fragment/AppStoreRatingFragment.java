package com.apptentive.android.sdk.module.engagement.interaction.fragment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.apptentive.android.sdk.ApptentiveInternal;
import com.apptentive.android.sdk.ApptentiveLog;
import com.apptentive.android.sdk.R;
import com.apptentive.android.sdk.model.Configuration;
import com.apptentive.android.sdk.module.engagement.interaction.model.AppStoreRatingInteraction;
import com.apptentive.android.sdk.module.rating.IRatingProvider;
import com.apptentive.android.sdk.module.rating.InsufficientRatingArgumentsException;
import com.apptentive.android.sdk.view.ApptentiveAlertDialog;
import com.apptentive.android.sdk.view.ApptentiveAlertDialog.OnDismissListener;
import com.gumtree.android.category.NewPostAdCategoryActivity;
import java.util.HashMap;
import java.util.Map;

public class AppStoreRatingFragment extends ApptentiveBaseFragment<AppStoreRatingInteraction> implements OnDismissListener {
    public static AppStoreRatingFragment newInstance(Bundle bundle) {
        AppStoreRatingFragment appStoreRatingFragment = new AppStoreRatingFragment();
        appStoreRatingFragment.setArguments(bundle);
        return appStoreRatingFragment;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return null;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        try {
            Map hashMap;
            IRatingProvider ratingProvider = ApptentiveInternal.getInstance().getRatingProvider();
            String activityNotFoundMessage = ratingProvider.activityNotFoundMessage(activity);
            String appDisplayName = Configuration.load().getAppDisplayName();
            Map ratingProviderArgs = ApptentiveInternal.getInstance().getRatingProviderArgs();
            if (ratingProviderArgs != null) {
                hashMap = new HashMap(ratingProviderArgs);
            } else {
                hashMap = new HashMap();
            }
            if (!(hashMap.containsKey("package") || activity == null)) {
                hashMap.put("package", activity.getPackageName());
            }
            if (!hashMap.containsKey("name")) {
                hashMap.put("name", appDisplayName);
            }
            ratingProvider.startRating(activity, hashMap);
        } catch (ActivityNotFoundException e) {
            displayError(activity, activity.getResources().getString(R.string.apptentive_rating_error));
        } catch (InsufficientRatingArgumentsException e2) {
            ApptentiveLog.e(e2.getMessage(), new Object[0]);
            displayError(activity, activity.getString(R.string.apptentive_rating_error));
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1) {
            transit();
        }
    }

    public void onPause() {
        super.onPause();
        transit();
    }

    public boolean onBackPressed(boolean z) {
        return false;
    }

    public void onDismissAlert() {
        transit();
    }

    private void displayError(Activity activity, String str) {
        ApptentiveLog.e(str, new Object[0]);
        Bundle bundle = new Bundle();
        bundle.putString(NewPostAdCategoryActivity.EXTRA_TITLE, activity.getString(R.string.apptentive_oops));
        bundle.putString("message", str);
        bundle.putString("positive", activity.getString(R.string.apptentive_ok));
        ApptentiveAlertDialog.show(this, bundle, 0);
    }

    protected void sendLaunchEvent(Activity activity) {
    }
}
