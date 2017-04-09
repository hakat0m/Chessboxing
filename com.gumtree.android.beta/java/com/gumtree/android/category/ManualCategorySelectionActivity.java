package com.gumtree.android.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import butterknife.ButterKnife;
import com.gumtree.android.category.di.DaggerManualCategorySelectionComponent;
import com.gumtree.android.category.di.ManualCategorySelectionComponent;
import com.gumtree.android.category.di.ManualCategorySelectionModule;
import com.gumtree.android.category.manual.presenter.ManualCategorySelectionPresenter;
import com.gumtree.android.category.manual.presenter.ManualCategorySelectionPresenter.View;
import com.gumtree.android.category.model.DraftCategory;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.dagger.components.ApplicationComponent;
import javax.inject.Inject;

public class ManualCategorySelectionActivity extends BaseActivity implements View {
    private static String EXTRA_CATEGORY_ID = StatefulActivity.NAME_CATEGORY_ID;
    private static String EXTRA_CATEGORY_NAME = StatefulActivity.EXTRA_CATEGORY_NAME;
    public static String EXTRA_EMPTY_SUGGESTIONS_DISPLAYED = "emptySuggestionsDisplayed";
    public static final int REQUEST_MANUAL_CATEGORY = 1001;
    private static final int ROOT_CATEGORY_ID = 1;
    @Inject
    ManualCategorySelectionPresenter presenter;

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
                return "ManualCategorySelectionActivity.Result.ResultBuilder(categoryId=" + this.categoryId + ", categoryName=" + this.categoryName + ", categoryPathNames=" + this.categoryPathNames + ")";
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
            return "ManualCategorySelectionActivity.Result(categoryId=" + getCategoryId() + ", categoryName=" + getCategoryName() + ", categoryPathNames=" + getCategoryPathNames() + ")";
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

    public static Intent createIntent(Context context) {
        return new Intent(context, ManualCategorySelectionActivity.class);
    }

    @Nullable
    public static Result analyseResult(@Nullable Intent intent) {
        if (intent == null) {
            return null;
        }
        return Result.builder().categoryId(intent.getStringExtra(EXTRA_CATEGORY_ID)).categoryName(intent.getStringExtra(EXTRA_CATEGORY_NAME)).categoryPathNames(intent.getStringExtra(StatefulActivity.EXTRA_CATEGORY_PATH)).build();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getManualCategorySelectionComponent().inject(this);
        setContentView(2130903091);
        ButterKnife.bind(this);
        replaceFragment(ROOT_CATEGORY_ID, getString(2131165708));
        updateSuggestionsDisplayedFlag();
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
            ComponentsManager.get().removeBaseComponent(ManualCategorySelectionComponent.KEY);
            this.presenter.destroy();
        }
    }

    private ManualCategorySelectionComponent getManualCategorySelectionComponent() {
        ManualCategorySelectionComponent manualCategorySelectionComponent = (ManualCategorySelectionComponent) ComponentsManager.get().getBaseComponent(ManualCategorySelectionComponent.KEY);
        if (manualCategorySelectionComponent != null) {
            return manualCategorySelectionComponent;
        }
        ApplicationComponent appComponent = ComponentsManager.get().getAppComponent();
        Object build = DaggerManualCategorySelectionComponent.builder().applicationComponent(appComponent).manualCategorySelectionModule(new ManualCategorySelectionModule()).build();
        ComponentsManager.get().putBaseComponent(ManualCategorySelectionComponent.KEY, build);
        return build;
    }

    protected boolean isHomeAsUp() {
        return true;
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

    private void updateSuggestionsDisplayedFlag() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_EMPTY_SUGGESTIONS_DISPLAYED, getIntent().getBooleanExtra(EXTRA_EMPTY_SUGGESTIONS_DISPLAYED, false));
        setResult(0, intent);
    }

    public void onShowNextLevel(int i, String str) {
        replaceFragment(i, str);
    }

    public void onCategorySelected(DraftCategory draftCategory) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CATEGORY_ID, draftCategory.getId());
        intent.putExtra(EXTRA_CATEGORY_NAME, draftCategory.getLocalisedName());
        intent.putExtra(StatefulActivity.EXTRA_CATEGORY_PATH, draftCategory.getPath());
        setResult(-1, intent);
        finish();
    }

    public void onUpdateTitle(String str) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(str);
        }
    }

    private void replaceFragment(int i, String str) {
        ManualCategorySelectionFragment newInstance = ManualCategorySelectionFragment.newInstance(i, str);
        String str2 = newInstance.getClass().getName() + i;
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        if (supportFragmentManager.findFragmentByTag(str2) == null) {
            FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
            beginTransaction.setCustomAnimations(2130968591, 2130968590, 2130968589, 2130968592);
            beginTransaction.replace(2131624163, newInstance, str2);
            beginTransaction.addToBackStack(str2);
            beginTransaction.commit();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (isFragmentBackstackEmpty()) {
            finish();
        }
    }
}
