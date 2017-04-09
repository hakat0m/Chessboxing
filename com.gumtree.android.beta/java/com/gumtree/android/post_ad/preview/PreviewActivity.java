package com.gumtree.android.post_ad.preview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import com.gumtree.DevelopmentFlags;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.locations.LocationActivity;
import com.gumtree.android.post_ad.PostAdBaseActivity;
import com.gumtree.android.post_ad.PostAdDBIntentService;
import com.gumtree.android.post_ad.model.PostAdData;
import com.gumtree.android.post_ad.model.PostAdMemDAO;
import com.gumtree.android.post_ad.summary.PostAdNavigationActivity;
import java.util.Arrays;
import java.util.List;

public class PreviewActivity extends PostAdBaseActivity {
    private String adId;

    public static Intent newInstance(Context context, String str) {
        Intent intent = new Intent(context, PreviewActivity.class);
        intent.putExtra(PostAdNavigationActivity.EXTRA_POST_AD_ID, str);
        return intent;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2130903118);
        if (bundle != null) {
            this.adId = bundle.getString(PostAdNavigationActivity.EXTRA_POST_AD_ID);
        } else {
            this.adId = getIntent().getStringExtra(PostAdNavigationActivity.EXTRA_POST_AD_ID);
        }
        if (bundle != null) {
            PostAdMemDAO dao = getDao();
            if (dao != null) {
                refreshContent(dao);
            }
        }
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    public void refreshContent(PostAdMemDAO postAdMemDAO) {
        Track.viewPostAdPreview(postAdMemDAO.getAdId());
        refreshFragments(postAdMemDAO.getPostAdData(), Arrays.asList(new String[]{"preview_title", "preview_price", "preview_contact", "preview_desc", "preview_attributes"}));
        addImagePager(postAdMemDAO.getAdId());
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString(PostAdNavigationActivity.EXTRA_POST_AD_ID, this.adId);
        super.onSaveInstanceState(bundle);
    }

    private void refreshFragments(PostAdData postAdData, List<String> list) {
        if (!postAdData.getAttributeMap().isEmpty()) {
            for (String findFragmentByTag : list) {
                Fragment findFragmentByTag2 = getSupportFragmentManager().findFragmentByTag(findFragmentByTag);
                if (findFragmentByTag2 != null) {
                    ((PreviewRefreshBaseFragment) findFragmentByTag2).refreshContent(postAdData);
                } else {
                    return;
                }
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void addImagePager(String str) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(2131624266, PreviewImagePagerFragment.newInstance(str), "pager");
        beginTransaction.commitAllowingStateLoss();
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i != LocationActivity.ACTIVITY_REQUEST_CODE) {
            return;
        }
        if (i2 == -1) {
            if (DevelopmentFlags.getInstance().isNewLoginEnabled() && intent.hasExtra("displayname")) {
                showSnackBar(getString(2131165552, new Object[]{intent.getStringExtra("displayname")}));
            }
            PostAdDBIntentService.load(getPostAdId());
            return;
        }
        finish();
    }
}
