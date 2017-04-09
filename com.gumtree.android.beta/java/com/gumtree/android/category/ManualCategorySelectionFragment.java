package com.gumtree.android.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.gumtree.android.category.ManualCategorySelectionAdapter.CategoryItemClickListener;
import com.gumtree.android.category.di.DaggerManualCategorySelectionFragmentComponent;
import com.gumtree.android.category.di.ManualCategorySelectionComponent;
import com.gumtree.android.category.di.ManualCategorySelectionFragmentComponent;
import com.gumtree.android.category.di.ManualCategorySelectionFragmentModule;
import com.gumtree.android.category.manual.presenter.ManualCategorySelectionFragmentPresenter;
import com.gumtree.android.category.manual.presenter.ManualCategorySelectionFragmentPresenter$View;
import com.gumtree.android.category.model.DraftCategory;
import com.gumtree.android.dagger.ComponentsManager;
import java.util.List;
import javax.inject.Inject;

public class ManualCategorySelectionFragment extends Fragment implements ManualCategorySelectionFragmentPresenter$View {
    private static final String PARENT_ID = "PARENT_ID";
    private static final String PARENT_TITLE = "PARENT_TITLE";
    private ManualCategorySelectionAdapter adapter;
    @Bind({2131624516})
    RecyclerView categoriesListView;
    private CategoryItemClickListener categoryItemClickListener = new 1(this);
    @Inject
    ManualCategorySelectionFragmentPresenter presenter;
    @Bind({2131624117})
    View waitingView;

    public static ManualCategorySelectionFragment newInstance(int i, String str) {
        ManualCategorySelectionFragment manualCategorySelectionFragment = new ManualCategorySelectionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(PARENT_ID, i);
        bundle.putString(PARENT_TITLE, str);
        manualCategorySelectionFragment.setArguments(bundle);
        return manualCategorySelectionFragment;
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setRetainInstance(true);
    }

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(2130903216, viewGroup, false);
    }

    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        ButterKnife.bind(this, view);
        this.adapter = new ManualCategorySelectionAdapter(this.categoryItemClickListener);
        this.categoriesListView.setAdapter(this.adapter);
        this.categoriesListView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getManualCategorySelectionFragmentComponent().inject(this);
    }

    private ManualCategorySelectionFragmentComponent getManualCategorySelectionFragmentComponent() {
        ManualCategorySelectionComponent manualCategorySelectionComponent = (ManualCategorySelectionComponent) ComponentsManager.get().getBaseComponent(ManualCategorySelectionComponent.KEY);
        Object build = DaggerManualCategorySelectionFragmentComponent.builder().manualCategorySelectionComponent(manualCategorySelectionComponent).manualCategorySelectionFragmentModule(new ManualCategorySelectionFragmentModule()).build();
        ComponentsManager.get().putBaseComponent(getFragmentComponentKey(), build);
        return build;
    }

    private String getFragmentComponentKey() {
        return ManualCategorySelectionFragmentComponent.KEY + getTag();
    }

    public void onStart() {
        super.onStart();
        this.presenter.attachView(this);
        Bundle arguments = getArguments();
        if (arguments != null) {
            int i = arguments.getInt(PARENT_ID, 0);
            if (i != 0) {
                this.presenter.loadCategories(i);
            }
            String string = arguments.getString(PARENT_TITLE);
            if (string != null) {
                this.presenter.updateTitle(string);
            }
        }
    }

    public void onStop() {
        this.presenter.detachView();
        super.onStop();
    }

    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void onDestroy() {
        super.onDestroy();
        ComponentsManager.get().removeBaseComponent(getFragmentComponentKey());
        if (this.presenter != null) {
            this.presenter.destroy();
        }
    }

    public void onCategoriesUpdated(List<DraftCategory> list) {
        this.adapter.setData(list);
    }

    public void onShowWaiting(boolean z) {
        this.waitingView.setVisibility(z ? 0 : 4);
    }
}
