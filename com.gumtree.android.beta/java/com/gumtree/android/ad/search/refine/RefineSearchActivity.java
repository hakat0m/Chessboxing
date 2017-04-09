package com.gumtree.android.ad.search.refine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.gumtree.android.ad.search.presenters.refine.RefineSearchPresenter;
import com.gumtree.android.ad.search.presenters.refine.RefineSearchPresenter.View;
import com.gumtree.android.ad.search.refine.di.DaggerRefineSearchComponent;
import com.gumtree.android.ad.search.refine.di.RefineSearchComponent;
import com.gumtree.android.ad.search.refine.di.RefineSearchModule;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.dagger.ComponentsManager;
import javax.inject.Inject;

public class RefineSearchActivity extends BaseActivity implements View {
    private static final String CATEGORY_ID = "com.gumtree.android.ad.search.refine.category_id";
    private static final String CATEGORY_NAME = "com.gumtree.android.ad.search.refine.category_name";
    private static final String KEYWORD = "com.gumtree.android.ad.search.refine.keyword";
    @Bind({2131624235})
    ImageView categoryIcon;
    @Bind({2131624236})
    TextView categoryName;
    @Bind({2131624231})
    android.view.View keyword;
    @Bind({2131624233})
    TextView keywordView;
    @Bind({2131624237})
    android.view.View locationView;
    @Inject
    RefineSearchPresenter presenter;
    @Bind({2131624239})
    Button submitSearch;
    @Bind({2131624229})
    Toolbar toolbar;

    public static Intent createOpenRefineIntent(Context context, @Nullable String str, @Nullable String str2, @NonNull String str3) {
        Intent intent = new Intent(context, RefineSearchActivity.class);
        intent.putExtra(KEYWORD, str);
        intent.putExtra(CATEGORY_NAME, str2);
        intent.putExtra(CATEGORY_ID, str3);
        return intent;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2130903110);
        ButterKnife.bind(this);
        getComponent().inject(this);
        setSupportActionBar(this.toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(false);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        setKeywordInfo(getIntent().getStringExtra(KEYWORD));
        setCategoryInfo(getIntent().getStringExtra(CATEGORY_NAME));
        setLocationInfo();
    }

    private void setLocationInfo() {
    }

    private void setKeywordInfo(String str) {
        this.keywordView.setText(str);
    }

    private void setCategoryInfo(String str) {
        this.categoryIcon.setColorFilter(ContextCompat.getColor(this, 2131493018));
        TextView textView = this.categoryName;
        if (str == null) {
            str = getString(2131165747);
        }
        textView.setText(str);
    }

    private RefineSearchComponent getComponent() {
        RefineSearchComponent refineSearchComponent = (RefineSearchComponent) ComponentsManager.get().getBaseComponent(RefineSearchComponent.KEY);
        if (refineSearchComponent != null) {
            return refineSearchComponent;
        }
        Object build = DaggerRefineSearchComponent.builder().applicationComponent(ComponentsManager.get().getAppComponent()).refineSearchModule(new RefineSearchModule()).build();
        ComponentsManager.get().putBaseComponent(RefineSearchComponent.KEY, build);
        return build;
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
            ComponentsManager.get().removeBaseComponent(RefineSearchComponent.KEY);
            this.presenter.destroy();
        }
    }

    @OnClick({2131624234})
    public void refineCategory() {
        Toast.makeText(this, "GTALL-5400", 1).show();
    }

    @OnClick({2131624237})
    public void refineLocation() {
        Toast.makeText(this, "GTALL-5225", 1).show();
    }

    @OnClick({2131624239})
    public void showSearchResults() {
        Toast.makeText(this, "Open search results GTALL-7535", 1).show();
    }

    @OnClick({2131624231})
    public void backToSearchKeywordScreen() {
        finish();
    }
}
