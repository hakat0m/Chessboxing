package com.gumtree.android.common.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.utils.crashlytics.CrashlyticsHelper;
import com.gumtree.android.mvp.MVPLifeCycle;
import com.gumtree.android.mvp.MVPLifeCycle.PresenterProvider;
import com.gumtree.android.mvp.Presenter;
import com.gumtree.android.mvp.PresenterView;

public abstract class BaseFragment extends Fragment implements StatefulActivity, PresenterProvider {
    protected Context context;
    private MVPLifeCycle mvpLifecycle = new MVPLifeCycle();

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity.getApplicationContext();
    }

    protected String getTitle() {
        return getActionBar().getTitle().toString();
    }

    public void startService(Intent intent) {
        this.context.startService(intent);
    }

    public void startActivity(Intent intent) {
        if (getActivity() != null) {
            super.startActivity(intent);
        }
    }

    public ActionBar getActionBar() {
        return ((BaseActivity) getActivity()).getSupportActionBar();
    }

    protected void onBackPressed() {
        if (isAdded()) {
            getActivity().onBackPressed();
        }
    }

    protected void finish() {
        if (isAdded()) {
            getActivity().finish();
        }
    }

    protected void hideKeyboard() {
        if (isAdded() && getView() != null) {
            hideKeyboard(getView());
        }
    }

    protected void hideKeyboard(View view) {
        ((InputMethodManager) this.context.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.mvpLifecycle.onAttachView(getPresenter(), getPresenterView());
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.mvpLifecycle.onDetachView(getPresenter());
    }

    public void onResume() {
        super.onResume();
        CrashlyticsHelper.getInstance().logBreadcrumb(getFragmentName());
    }

    public void onDestroy() {
        super.onDestroy();
        this.mvpLifecycle.onDestroy(getActivity(), getPresenter());
    }

    public void showSnackBar(String str) {
        if (isAdded() && getView() != null) {
            Snackbar.make(getView(), str, 0).show();
        }
    }

    private String getFragmentName() {
        return getClass().getSimpleName();
    }

    public Presenter getPresenter() {
        return null;
    }

    public <T extends PresenterView> T getPresenterView() {
        return null;
    }
}
