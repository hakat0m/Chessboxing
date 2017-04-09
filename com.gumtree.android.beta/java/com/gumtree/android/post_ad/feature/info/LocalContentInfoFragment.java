package com.gumtree.android.post_ad.feature.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ebay.kleinanzeigen.imagepicker.R$id;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.features.Feature.FeatureType;

public class LocalContentInfoFragment extends BaseFragment {
    private static final String CONTENT_TYPE = "contentType";
    private FeatureType contentType;
    private LocalContentFactory localContentFactory;

    public static LocalContentInfoFragment newInstance(FeatureType featureType) {
        LocalContentInfoFragment localContentInfoFragment = new LocalContentInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CONTENT_TYPE, featureType);
        localContentInfoFragment.setArguments(bundle);
        return localContentInfoFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.localContentFactory = new LocalContentFactory();
        this.contentType = (FeatureType) getArguments().getSerializable(CONTENT_TYPE);
        setHasOptionsMenu(true);
    }

    public void onResume() {
        super.onResume();
        getActionBar().setTitle(2131165928);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130903193, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ((TextView) view.findViewById(R$id.title)).setText(this.localContentFactory.getTitle(this.contentType));
        ((ImageView) view.findViewById(R$id.image)).setImageResource(this.localContentFactory.getDrawable(this.contentType));
        this.localContentFactory.renderContentView(this.contentType, view);
    }
}
