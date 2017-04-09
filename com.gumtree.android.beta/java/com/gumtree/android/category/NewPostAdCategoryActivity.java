package com.gumtree.android.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.gumtree.android.category.di.CategoryComponent;
import com.gumtree.android.category.di.CategoryModule;
import com.gumtree.android.category.di.DaggerCategoryComponent;
import com.gumtree.android.category.model.DraftCategory;
import com.gumtree.android.category.suggest.presenter.PostAdCategoryPresenter;
import com.gumtree.android.category.suggest.presenter.PostAdCategoryPresenter.View;
import com.gumtree.android.category.suggest.service.converter.CategoryTreeConverter;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.dagger.components.ApplicationComponent;
import java.util.List;
import javax.inject.Inject;

public class NewPostAdCategoryActivity extends BaseActivity implements View {
    public static final String EXTRA_TITLE = "title";
    public static final int REQUEST_CATEGORY = 100;
    @Inject
    CategoryTreeConverter categoryConverter;
    @Bind({2131624117})
    FrameLayout loadingView;
    @Inject
    PostAdCategoryPresenter presenter;
    @Bind({2131624119})
    android.view.View selectCategoryManuallyView;
    @Bind({2131624118})
    TextView suggestedCategoriesTitle;
    private CategorySuggestionAdapter suggestionAdapter;
    @Bind({2131624120})
    RecyclerView suggestionsView;

    public class CategorySuggestionSelectionListener {
        public void getSuggestionAt(int i) {
            NewPostAdCategoryActivity.this.presenter.onSuggestionSelected(i);
        }
    }

    public final class Result {
        private final String categoryId;
        private final String categoryName;
        private final String categoryPathNames;

        public class ResultBuilder {
            private String categoryId;
            private String categoryName;
            private String categoryPathNames;

            ResultBuilder() {
            }

            public Result build() {
                return new Result(this.categoryId, this.categoryName, this.categoryPathNames);
            }

            public ResultBuilder categoryId(String str) {
                this.categoryId = str;
                return this;
            }

            public ResultBuilder categoryName(String str) {
                this.categoryName = str;
                return this;
            }

            public ResultBuilder categoryPathNames(String str) {
                this.categoryPathNames = str;
                return this;
            }

            public String toString() {
                return "NewPostAdCategoryActivity.Result.ResultBuilder(categoryId=" + this.categoryId + ", categoryName=" + this.categoryName + ", categoryPathNames=" + this.categoryPathNames + ")";
            }
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof Result)) {
                return false;
            }
            Result result = (Result) obj;
            String categoryId = getCategoryId();
            String categoryId2 = result.getCategoryId();
            if (categoryId != null ? !categoryId.equals(categoryId2) : categoryId2 != null) {
                return false;
            }
            categoryId = getCategoryName();
            categoryId2 = result.getCategoryName();
            if (categoryId != null ? !categoryId.equals(categoryId2) : categoryId2 != null) {
                return false;
            }
            categoryId = getCategoryPathNames();
            categoryId2 = result.getCategoryPathNames();
            if (categoryId == null) {
                if (categoryId2 == null) {
                    return true;
                }
            } else if (categoryId.equals(categoryId2)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            int i = 43;
            String categoryId = getCategoryId();
            int hashCode = (categoryId == null ? 43 : categoryId.hashCode()) + 59;
            String categoryName = getCategoryName();
            hashCode = (categoryName == null ? 43 : categoryName.hashCode()) + (hashCode * 59);
            categoryName = getCategoryPathNames();
            hashCode *= 59;
            if (categoryName != null) {
                i = categoryName.hashCode();
            }
            return hashCode + i;
        }

        public String toString() {
            return "NewPostAdCategoryActivity.Result(categoryId=" + getCategoryId() + ", categoryName=" + getCategoryName() + ", categoryPathNames=" + getCategoryPathNames() + ")";
        }

        Result(String str, String str2, String str3) {
            this.categoryId = str;
            this.categoryName = str2;
            this.categoryPathNames = str3;
        }

        public static ResultBuilder builder() {
            return new ResultBuilder();
        }

        public String getCategoryId() {
            return this.categoryId;
        }

        public String getCategoryName() {
            return this.categoryName;
        }

        public String getCategoryPathNames() {
            return this.categoryPathNames;
        }
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i != ManualCategorySelectionActivity.REQUEST_MANUAL_CATEGORY) {
            return;
        }
        if (i2 == -1) {
            com.gumtree.android.category.ManualCategorySelectionActivity.Result analyseResult = ManualCategorySelectionActivity.analyseResult(intent);
            if (analyseResult != null) {
                setResult(-1, new Intent().putExtra(StatefulActivity.NAME_CATEGORY_ID, analyseResult.getCategoryId()).putExtra(StatefulActivity.EXTRA_CATEGORY_NAME, analyseResult.getCategoryName()).putExtra(StatefulActivity.EXTRA_CATEGORY_PATH, analyseResult.getCategoryPathNames()));
                finish();
            }
        } else if (i2 == 0 && intent != null && !intent.getBooleanExtra(ManualCategorySelectionActivity.EXTRA_EMPTY_SUGGESTIONS_DISPLAYED, false)) {
            finish();
        }
    }

    public static Intent createIntent(Context context, String str, String str2) {
        return new Intent(context, NewPostAdCategoryActivity.class).putExtra(StatefulActivity.EXTRA_POST_AD_ID, str).putExtra(EXTRA_TITLE, str2);
    }

    @Nullable
    public static Result analyseResult(@Nullable Intent intent) {
        if (intent == null) {
            return null;
        }
        return Result.builder().categoryId(intent.getStringExtra(StatefulActivity.NAME_CATEGORY_ID)).categoryName(intent.getStringExtra(StatefulActivity.EXTRA_CATEGORY_NAME)).categoryPathNames(intent.getStringExtra(StatefulActivity.EXTRA_CATEGORY_PATH)).build();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getCategoryComponent().inject(this);
        setContentView(2130903073);
        ButterKnife.bind(this);
        initialiseSuggestions(getIntent().getStringExtra(EXTRA_TITLE), bundle);
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
            ComponentsManager.get().removeBaseComponent(CategoryComponent.KEY);
            this.presenter.destroy();
        }
    }

    private CategoryComponent getCategoryComponent() {
        CategoryComponent categoryComponent = (CategoryComponent) ComponentsManager.get().getBaseComponent(CategoryComponent.KEY);
        if (categoryComponent != null) {
            return categoryComponent;
        }
        ApplicationComponent appComponent = ComponentsManager.get().getAppComponent();
        Object build = DaggerCategoryComponent.builder().applicationComponent(appComponent).categoryModule(new CategoryModule(getIntent().getStringExtra(EXTRA_TITLE))).build();
        ComponentsManager.get().putBaseComponent(CategoryComponent.KEY, build);
        return build;
    }

    private void initialiseSuggestions(String str, Bundle bundle) {
        boolean z = true;
        this.suggestionAdapter = new CategorySuggestionAdapter(new CategorySuggestionSelectionListener(), this.categoryConverter);
        this.suggestionsView.setAdapter(this.suggestionAdapter);
        this.suggestionsView.setLayoutManager(new LinearLayoutManager(this, 1, false));
        PostAdCategoryPresenter postAdCategoryPresenter = this.presenter;
        if (bundle == null) {
            z = false;
        }
        postAdCategoryPresenter.onListSuggestions(str, z);
    }

    public void showSuggestions(@NonNull List<DraftCategory> list) {
        this.suggestionAdapter.setSuggestions(list);
    }

    public void completeWithCategory(@Nullable DraftCategory draftCategory) {
        if (draftCategory != null) {
            setResult(-1, new Intent().putExtra(StatefulActivity.NAME_CATEGORY_ID, draftCategory.getId()).putExtra(StatefulActivity.EXTRA_CATEGORY_NAME, draftCategory.getLocalisedName()).putExtra(StatefulActivity.EXTRA_CATEGORY_PATH, draftCategory.getPath()));
        }
        finish();
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                super.finish();
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void showLoading(boolean z) {
        if (z) {
            this.loadingView.setVisibility(0);
        } else {
            this.loadingView.setVisibility(8);
        }
    }

    @OnClick({2131624119})
    public void showManualCategorySelection() {
        showManualCategorySelection(true);
    }

    public void showManualCategorySelection(boolean z) {
        Intent createIntent = ManualCategorySelectionActivity.createIntent(this);
        createIntent.putExtra(ManualCategorySelectionActivity.EXTRA_EMPTY_SUGGESTIONS_DISPLAYED, z);
        startActivityForResult(createIntent, ManualCategorySelectionActivity.REQUEST_MANUAL_CATEGORY);
    }

    public void showSuggestedCategoriesTitle(boolean z) {
        if (z) {
            this.suggestedCategoriesTitle.setVisibility(0);
        } else {
            this.suggestedCategoriesTitle.setVisibility(8);
        }
    }
}
