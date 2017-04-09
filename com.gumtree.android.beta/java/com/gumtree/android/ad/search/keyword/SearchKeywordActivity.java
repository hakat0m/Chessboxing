package com.gumtree.android.ad.search.keyword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.gumtree.android.ad.search.keyword.ToolbarSearchKeywordView.ToolbarSearchKeywordListener;
import com.gumtree.android.ad.search.keyword.di.DaggerSearchKeywordComponent;
import com.gumtree.android.ad.search.keyword.di.SearchKeywordComponent;
import com.gumtree.android.ad.search.keyword.di.SearchKeywordModule;
import com.gumtree.android.ad.search.models.SuggestionItem;
import com.gumtree.android.ad.search.presenters.keyword.SearchKeywordPresenter;
import com.gumtree.android.ad.search.presenters.keyword.SearchKeywordPresenter.View;
import com.gumtree.android.ad.search.refine.RefineSearchActivity;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.dagger.ComponentsManager;
import java.util.List;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

public class SearchKeywordActivity extends BaseActivity implements KeywordSuggestionListener, ToolbarSearchKeywordListener, View {
    private static final String INITIAL_KEYWORD = "com.gumtree.android.ad.search.keyword.initial_keyword";
    @Inject
    SearchKeywordPresenter presenter;
    private KeywordSuggestionAdapter suggestionsAdapter;
    @Bind({2131624228})
    RecyclerView suggestionsRecyclerView;
    @Bind({2131624104})
    Toolbar toolbar;
    @Bind({2131624227})
    ToolbarSearchKeywordView toolbarSearchKeywordView;
    @Bind({2131624192})
    android.view.View viewRoot;

    public static Intent createLaunchIntent(Context context, @Nullable String str) {
        Intent intent = new Intent(context, SearchKeywordActivity.class);
        intent.putExtra(INITIAL_KEYWORD, str);
        return intent;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2130903109);
        getComponent().inject(this);
        ButterKnife.bind(this);
        setSupportActionBar(this.toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        this.toolbarSearchKeywordView.setToolbarSearchKeywordListener(this);
        this.suggestionsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.suggestionsAdapter = new KeywordSuggestionAdapter(this);
        this.suggestionsRecyclerView.setAdapter(this.suggestionsAdapter);
        if (bundle == null) {
            Object stringExtra = getIntent().getStringExtra(INITIAL_KEYWORD);
            if (StringUtils.isNotBlank(stringExtra)) {
                this.toolbarSearchKeywordView.setText(stringExtra);
            }
        }
        handleIntent(getIntent());
    }

    protected boolean isHomeAsUp() {
        return true;
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
            ComponentsManager.get().removeBaseComponent(SearchKeywordComponent.KEY);
            this.presenter.destroy();
        }
    }

    private SearchKeywordComponent getComponent() {
        SearchKeywordComponent searchKeywordComponent = (SearchKeywordComponent) ComponentsManager.get().getBaseComponent(SearchKeywordComponent.KEY);
        if (searchKeywordComponent != null) {
            return searchKeywordComponent;
        }
        Object build = DaggerSearchKeywordComponent.builder().applicationComponent(ComponentsManager.get().getAppComponent()).searchKeywordModule(new SearchKeywordModule()).build();
        ComponentsManager.get().putBaseComponent(SearchKeywordComponent.KEY, build);
        return build;
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

    public void showKeywordSuggestions(List<SuggestionItem> list) {
        this.suggestionsAdapter.setSuggestions(list);
        if (list.size() > 0) {
            this.suggestionsRecyclerView.scrollToPosition(0);
        }
    }

    public void openSearchRefine(SuggestionItem suggestionItem) {
        startActivity(RefineSearchActivity.createOpenRefineIntent(this, suggestionItem.getKeyword(), suggestionItem.getCategoryLocalizedName(), String.valueOf(suggestionItem.getCategoryId())));
    }

    public void onKeywordTextChanged(String str) {
        this.presenter.onSearchKeywordChanged(str);
    }

    public void onKeywordTextSubmitted(String str) {
        this.presenter.onSuggestionSubmitted(str);
    }

    public void onKeywordSuggestionSelected(SuggestionItem suggestionItem) {
        this.presenter.onSuggestionSelected(suggestionItem);
    }

    public void onKeywordSuggestionPreSelected(SuggestionItem suggestionItem) {
        this.toolbarSearchKeywordView.setText(suggestionItem.getKeyword());
    }

    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if ("android.intent.action.SEARCH".equals(intent.getAction())) {
            this.toolbarSearchKeywordView.setText(intent.getStringExtra("query"));
        }
    }
}
