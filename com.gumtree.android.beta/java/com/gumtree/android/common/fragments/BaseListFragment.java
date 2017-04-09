package com.gumtree.android.common.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView.OnScrollListener;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.gumtree.android.common.activities.StatefulActivity;

public abstract class BaseListFragment extends ListFragment implements StatefulActivity {
    private static final String KEY_LIST_POSITION = "key_list_position";
    public static final String KEY_LIST_STATE = "key_list_state";
    protected Context context;
    private View endlessFooter;
    private int firstVisible;
    private boolean isListShow;
    private final OnScrollListener scrollListener = new 1(this);

    protected void onScrollToEnd() {
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity.getApplicationContext();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            this.isListShow = false;
            this.firstVisible = 0;
            return;
        }
        this.firstVisible = bundle.getInt(KEY_LIST_POSITION);
        this.isListShow = bundle.getBoolean(KEY_LIST_STATE);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(getLayoutId(), viewGroup, false);
        attachHeaderView(onCreateHeaderView(layoutInflater, viewGroup), (ListView) inflate.findViewById(16908298));
        return inflate;
    }

    protected void onHeaderViewCreated(View view) {
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(KEY_LIST_POSITION, this.firstVisible);
        bundle.putBoolean(KEY_LIST_STATE, this.isListShow);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        getListView().setOnScrollListener(this.scrollListener);
        setEmptyText(getEmptyText());
    }

    protected void setListContentDescription(String str) {
        if (getListView() != null) {
            getListView().setContentDescription(str);
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    private void attachHeaderView(View view, ListView listView) {
        if (view != null && listView != null) {
            listView.addHeaderView(view);
            onHeaderViewCreated(view);
        }
    }

    protected View onCreateHeaderView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        return null;
    }

    protected int getLayoutId() {
        return 2130903321;
    }

    protected void setChoiceMode(int i) {
        getListView().setChoiceMode(i);
    }

    public void setListShown(boolean z) {
        this.isListShow = z;
        if (getListView() != null && getLoadingView() != null) {
            setViewStateOfList(this.isListShow);
        }
    }

    private void setViewStateOfList(boolean z) {
        View listView = z ? getListView() : getLoadingView();
        View loadingView = z ? getLoadingView() : getListView();
        listView.setVisibility(0);
        loadingView.setVisibility(8);
    }

    protected View getLoadingView() {
        return getView().findViewById(2131624467);
    }

    protected String getEmptyText() {
        return "no items";
    }

    public void setEmptyText(CharSequence charSequence) {
        if (getView().findViewById(16908292) != null) {
            ((TextView) getView().findViewById(16908292)).setText(charSequence);
        }
    }

    public void setListAdapter(ListAdapter listAdapter) {
        super.setListAdapter(listAdapter);
        restoreSelection(listAdapter);
    }

    private void restoreSelection(ListAdapter listAdapter) {
        if (listAdapter != null) {
            getListView().setSelection(this.firstVisible);
        }
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

    protected void addEndlessFooter() {
        if (this.endlessFooter == null) {
            this.endlessFooter = LayoutInflater.from(this.context).inflate(2130903336, getListView(), false);
            getListView().addFooterView(this.endlessFooter);
        }
    }

    protected void removeEndlessFooter() {
        ListView listView = getListView();
        if (listView != null && (listView.getAdapter() instanceof HeaderViewListAdapter)) {
            listView.removeFooterView(this.endlessFooter);
            this.endlessFooter = null;
        }
    }

    protected void hideKeyboard() {
        getActivity().getWindow().setSoftInputMode(3);
        ((InputMethodManager) this.context.getSystemService("input_method")).hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}
