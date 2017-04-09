package com.gumtree.android.post_ad.payment;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.features.Feature;
import com.gumtree.android.post_ad.IPostAdDataRefresh;
import com.gumtree.android.post_ad.PostAdBaseActivity;
import com.gumtree.android.post_ad.feature.AbortPaymentDialogFragment;
import com.gumtree.android.post_ad.model.PostAdMemDAO;
import com.gumtree.android.post_ad.summary.PostAdNavigationActivity;

public class PostAdPaypalActivity extends PostAdBaseActivity {
    private static final String ZERO = "0";

    public static Intent createIntent(Context context, String str) {
        Intent intent = new Intent(context, PostAdPaypalActivity.class);
        intent.putExtra(PostAdNavigationActivity.EXTRA_POST_AD_ID, str);
        return intent;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        GumtreeApplication.component().inject(this);
        getWindow().setFlags(8192, 8192);
        if (VERSION.SDK_INT >= 18) {
            setRequestedOrientation(14);
        } else {
            setRequestedOrientation(1);
        }
        setContentView(2130903096);
        initUI();
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    private void initUI() {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(2131624197, new PostAdPaymentFragment(), ZERO);
        beginTransaction.commit();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                break;
        }
        return true;
    }

    public void onBackPressed() {
        AbortPaymentDialogFragment.newInstance().show(getSupportFragmentManager(), "DIALOG");
    }

    public void refreshContent(PostAdMemDAO postAdMemDAO) {
        for (Feature feature : postAdMemDAO.getPostAdData().getFeaturesResult().getFeatures()) {
            if (feature.isSelected()) {
                postAdMemDAO.getPostAdData().getSelectedFeatures().add(feature);
            }
        }
        if (postAdMemDAO.getPostAdData().getFeaturesResult().getInsertion() != null) {
            postAdMemDAO.getPostAdData().getSelectedFeatures().add(postAdMemDAO.getPostAdData().getFeaturesResult().getInsertion());
        }
        Fragment findFragmentByTag = getSupportFragmentManager().findFragmentByTag(ZERO);
        if (findFragmentByTag != null) {
            ((IPostAdDataRefresh) findFragmentByTag).refreshContent(postAdMemDAO.getPostAdData());
        }
    }
}
