package com.gumtree.android.srp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.categories.CategoryActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.fragments.BaseListFragment;
import com.gumtree.android.common.views.fields.BaseField;
import com.gumtree.android.common.views.fields.ButtonField;
import com.gumtree.android.common.views.fields.OnFieldValueChangeListener;
import com.gumtree.android.locations.SetLocationActivity;
import com.gumtree.android.srp.suggestion.SuggestionProvider;
import javax.inject.Inject;
import javax.inject.Provider;

public abstract class BaseDynAttributeFragment extends BaseListFragment implements OnClickListener, OnFieldValueChangeListener, MetadataFragment {
    @Inject
    Provider<SuggestionProvider> suggestionProvider;

    protected abstract String getCategoryId();

    public void updateListViewCursor(Cursor cursor) {
        if (getListAdapter() == null) {
            setListAdapter(new PrePopulatedMetadataAdapter(getActivity(), this.suggestionProvider, cursor, this, getCategoryId()));
        } else {
            getListAdapter().changeCursor(cursor);
        }
        setListShown(true);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        GumtreeApplication.component().inject(this);
    }

    public void manipulateView(String str, BaseField baseField) {
        setListeners(baseField);
    }

    private void setListeners(BaseField baseField) {
        if (baseField instanceof ButtonField) {
            baseField.setOnClickListener(this);
        }
        baseField.setOnFieldValueChangeListener(this);
    }

    public void onClick(View view) {
        onClickRefineItems(view);
    }

    private void onClickRefineItems(View view) {
        hideKeyboard();
        if (view instanceof ButtonField) {
            ButtonField buttonField = (ButtonField) view;
            if (buttonField.getKey().equals(StatefulActivity.NAME_CATEGORY_ID)) {
                CategoryActivity.startForResult(this, "android.intent.action.PICK", getCategoryId());
            } else if (buttonField.getKey().equals(StatefulActivity.NAME_LOCATION_ID)) {
                startActivityForResult(SetLocationActivity.buildIntent(false, "android.intent.action.PICK"), 0);
            }
        }
    }

    public PrePopulatedMetadataAdapter getListAdapter() {
        return (PrePopulatedMetadataAdapter) super.getListAdapter();
    }
}
