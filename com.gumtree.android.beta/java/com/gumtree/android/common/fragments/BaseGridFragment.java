package com.gumtree.android.common.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.gumtree.android.common.activities.StatefulActivity;

public abstract class BaseGridFragment extends GridFragment implements StatefulActivity {
    protected Context context;
    private final OnScrollListener scrollListener = new 1(this);

    protected void onScrollToEnd() {
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity.getApplicationContext();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public void onViewStateRestored(Bundle bundle) {
        super.onViewStateRestored(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(getLayoutId(), viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        getGridView().setOnScrollListener(this.scrollListener);
        setEmptyText(getEmptyText());
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    protected int getLayoutId() {
        return 2130903321;
    }

    public void setGridShown(boolean z) {
        try {
            if (getGridView() != null && getLoadingView() != null) {
                setViewStateOfList(z);
            }
        } catch (IllegalStateException e) {
        }
    }

    private void setViewStateOfList(boolean z) {
        View gridView = z ? getGridView() : getLoadingView();
        View loadingView = z ? getLoadingView() : getGridView();
        gridView.setVisibility(0);
        loadingView.setVisibility(8);
    }

    protected boolean isGridViewVisible() {
        try {
            GridView gridView = getGridView();
            if (gridView == null || ((gridView instanceof PositionAware) && !((PositionAware) gridView).isVisible())) {
                return false;
            }
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    protected View getLoadingView() {
        return getView().findViewById(2131624467);
    }

    public TextView getEmptyTextView() {
        return (TextView) getView().findViewById(16908292);
    }

    protected String getEmptyText() {
        return "no items";
    }

    public void setEmptyText(CharSequence charSequence) {
        View findViewById = getView().findViewById(16908292);
        if (findViewById instanceof TextView) {
            ((TextView) findViewById).setText(charSequence);
        }
    }

    public void setGridAdapter(ListAdapter listAdapter) {
        super.setGridAdapter(listAdapter);
    }

    public void startService(Intent intent) {
        this.context.startService(intent);
    }

    public Intent getIntent() {
        if (isAdded()) {
            return getActivity().getIntent();
        }
        return null;
    }

    public void startActivity(Intent intent) {
        if (getActivity() != null) {
            super.startActivity(intent);
        }
    }

    protected void resetScrollPosition() {
        GridView gridView = getGridView();
        if (gridView != null && (gridView instanceof PositionAware)) {
            ((PositionAware) gridView).resetScrollPosition();
        }
    }

    protected void hideKeyboard() {
        getActivity().getWindow().setSoftInputMode(3);
        ((InputMethodManager) this.context.getSystemService("input_method")).toggleSoftInput(1, 0);
    }

    protected void hideKeyboard(View view) {
        ((InputMethodManager) this.context.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void showSnackBar(String str) {
        if (isAdded() && getView() != null) {
            Snackbar.make(getView(), str, 0).show();
        }
    }
}
