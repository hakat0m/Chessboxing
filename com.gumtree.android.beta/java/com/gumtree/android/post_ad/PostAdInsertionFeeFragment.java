package com.gumtree.android.post_ad;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnInsertionFeeUpdateEvent;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class PostAdInsertionFeeFragment extends BaseFragment {
    @Inject
    EventBus eventBus;
    private TextView mInsertionFeeTV;

    public void onResume() {
        super.onResume();
        this.eventBus.register(this);
    }

    public void onPause() {
        super.onPause();
        this.eventBus.unregister(this);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        GumtreeApplication.component().inject(this);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(2130903218, viewGroup, false);
        this.mInsertionFeeTV = (TextView) inflate.findViewById(2131624198);
        return inflate;
    }

    @Subscribe
    public void onInsertionFeeUpdateEvent(OnInsertionFeeUpdateEvent onInsertionFeeUpdateEvent) {
        this.mInsertionFeeTV.setText(onInsertionFeeUpdateEvent.getInsertionFee());
    }
}
