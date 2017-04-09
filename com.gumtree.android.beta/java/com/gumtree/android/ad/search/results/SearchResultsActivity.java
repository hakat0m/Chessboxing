package com.gumtree.android.ad.search.results;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.gumtree.android.ad.search.presenters.results.SearchResultsPresenter;
import com.gumtree.android.ad.search.presenters.results.SearchResultsPresenter.View;
import com.gumtree.android.ad.search.results.di.DaggerSearchResultsComponent;
import com.gumtree.android.ad.search.results.di.SearchResultsComponent;
import com.gumtree.android.ad.search.results.di.SearchResultsModule;
import com.gumtree.android.common.activities.NavigationActivity;
import com.gumtree.android.common.drawer.NavigationItem;
import com.gumtree.android.dagger.ComponentsManager;
import com.mikepenz.materialdrawer.Drawer;
import javax.inject.Inject;

public class SearchResultsActivity extends NavigationActivity implements View {
    private static final int SNACKBAR_TIMEOUT = 5000;
    @Inject
    SearchResultsPresenter presenter;
    private MenuItem setAlertMenuItem;
    @Bind({2131624104})
    Toolbar toolbar;
    @Bind({2131624226})
    ToolbarSearchView toolbarSearchView;
    @Bind({2131624192})
    android.view.View viewRoot;

    public static Intent createLaunchIntent(Context context) {
        return new Intent(context, SearchResultsActivity.class);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2130903108, bundle);
        getComponent().inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(this.toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
    }

    protected void onResume() {
        super.onResume();
        this.presenter.attachView(this);
    }

    protected void onPause() {
        this.presenter.detachView();
        super.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            ComponentsManager.get().removeBaseComponent(SearchResultsComponent.KEY);
            this.presenter.destroy();
        }
    }

    protected NavigationItem getNavigationItem() {
        return NavigationItem.SEARCH;
    }

    private SearchResultsComponent getComponent() {
        SearchResultsComponent searchResultsComponent = (SearchResultsComponent) ComponentsManager.get().getBaseComponent(SearchResultsComponent.KEY);
        if (searchResultsComponent != null) {
            return searchResultsComponent;
        }
        Object build = DaggerSearchResultsComponent.builder().applicationComponent(ComponentsManager.get().getAppComponent()).searchResultsModule(new SearchResultsModule()).build();
        ComponentsManager.get().putBaseComponent(SearchResultsComponent.KEY, build);
        return build;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(2131755016, menu);
        this.setAlertMenuItem = menu.findItem(2131624937);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                Drawer drawer = getDrawer();
                if (drawer == null) {
                    return true;
                }
                if (drawer.isDrawerOpen()) {
                    drawer.closeDrawer();
                    return true;
                }
                drawer.openDrawer();
                return true;
            case 2131624937:
                this.setAlertMenuItem.setIcon(2130837812);
                this.toolbarSearchView.setSearchText("HELLO!");
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void showMessage(String str) {
        Snackbar.make(this.viewRoot, str, SNACKBAR_TIMEOUT).show();
    }

    protected String getTrackingView() {
        return "ResultsSearch";
    }
}
