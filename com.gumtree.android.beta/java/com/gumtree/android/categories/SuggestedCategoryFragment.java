package com.gumtree.android.categories;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.categories.SuggestedCategoryLoader.Result;
import com.gumtree.android.category.CategoryItem;
import com.gumtree.android.common.contracts.HorizontalProgressDisplay;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.common.http.CapiClientManager;
import javax.inject.Inject;

public class SuggestedCategoryFragment extends BaseFragment implements LoaderCallbacks<Result> {
    private static final String COUNT = "count";
    private static final int LOADER_CATEGORY_RESULTS = 1;
    private static final int MIN_SUGGESTION = 3;
    private static final String SUGGESTION_SIZE = "suggestionSize";
    private String adTitle;
    @Inject
    CapiClientManager capiClientManager;
    private int count;
    private OnClickListener onClickListener = SuggestedCategoryFragment$$Lambda$1.lambdaFactory$(this);
    private OnClickListener onPrimaryClickListener = SuggestedCategoryFragment$$Lambda$2.lambdaFactory$(this);
    private HorizontalProgressDisplay progressActivity;
    private int suggestionsSize;

    /* synthetic */ void lambda$new$0(View view) {
        ((PostAdCategoryActivity) getActivity()).onLeafCategorySelect((CategoryItem) view.getTag(2131623948), Boolean.valueOf(true), Integer.valueOf(((Integer) view.getTag(2131623949)).intValue()));
    }

    /* synthetic */ void lambda$new$1(View view) {
        ((PostAdCategoryActivity) getActivity()).onTreeCategorySelect((CategoryItem) view.getTag());
    }

    public static SuggestedCategoryFragment newInstance(String str) {
        SuggestedCategoryFragment suggestedCategoryFragment = new SuggestedCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(PostAdCategoryActivity.ADTITLE, str);
        suggestedCategoryFragment.setArguments(bundle);
        return suggestedCategoryFragment;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        GumtreeApplication.component().inject(this);
        this.progressActivity = (HorizontalProgressDisplay) activity;
        this.adTitle = activity.getIntent().getStringExtra(PostAdCategoryActivity.ADTITLE);
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(COUNT, this.count);
        bundle.putInt(SUGGESTION_SIZE, this.suggestionsSize);
        bundle.putString(PostAdCategoryActivity.ADTITLE, this.adTitle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130903205, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (bundle == null) {
            this.count = 0;
        } else {
            this.count = bundle.getInt(COUNT);
            this.suggestionsSize = bundle.getInt(SUGGESTION_SIZE);
        }
        initLoader();
    }

    private void initLoader() {
        getLoaderManager().initLoader(LOADER_CATEGORY_RESULTS, null, this);
    }

    public Loader<Result> onCreateLoader(int i, Bundle bundle) {
        this.progressActivity.showProgress(true);
        return new SuggestedCategoryLoader(this.context, this.adTitle, this.capiClientManager);
    }

    public void onLoadFinished(Loader<Result> loader, Result result) {
        getSuggestedCategoryView().removeAllViews();
        getShowMoreView().removeAllViews();
        getPrimaryCategoryView().removeAllViews();
        getSuggestionTitleHolder().removeAllViews();
        getPrimaryTitleHolder().removeAllViews();
        showPrimaryCategories(result);
        showSuggestions(result);
        this.progressActivity.showProgress(false);
    }

    private void showSuggestions(Result result) {
        int i = MIN_SUGGESTION;
        addView(getSuggestionTitleHolder(), 2130903323);
        if (result.getCategories() == null || result.getCategories().size() <= 0) {
            addView(getSuggestedCategoryView(), 2130903288);
            return;
        }
        this.suggestionsSize = result.getCategories().size();
        if (this.count == 0) {
            if (this.suggestionsSize < MIN_SUGGESTION) {
                i = this.suggestionsSize;
            }
            this.count = i;
        }
        addSuggestions(result);
        addShowMore();
    }

    private void addSuggestions(Result result) {
        for (int i = 0; i < this.count; i += LOADER_CATEGORY_RESULTS) {
            CategoryItem categoryItem = (CategoryItem) result.getCategories().get(i);
            View inflate = LayoutInflater.from(getActivity()).inflate(2130903338, getSuggestedCategoryView(), false);
            ((TextView) inflate.findViewById(16908308)).setText(Html.fromHtml(categoryItem.getPathInString()));
            inflate.setTag(2131623948, categoryItem);
            inflate.setTag(2131623949, Integer.valueOf(i + LOADER_CATEGORY_RESULTS));
            inflate.setOnClickListener(this.onClickListener);
            getSuggestedCategoryView().addView(inflate);
        }
    }

    private void addShowMore() {
        if (this.suggestionsSize - this.count >= MIN_SUGGESTION) {
            View inflate = LayoutInflater.from(getActivity()).inflate(2130903322, getShowMoreView(), false);
            ((TextView) inflate.findViewById(2131624487)).setText(this.context.getString(2131165643) + " (" + (this.suggestionsSize - this.count) + ")");
            inflate.setOnClickListener(SuggestedCategoryFragment$$Lambda$3.lambdaFactory$(this));
            getShowMoreView().addView(inflate);
        }
    }

    /* synthetic */ void lambda$addShowMore$2(View view) {
        this.count = this.suggestionsSize;
        initLoader();
    }

    private void showPrimaryCategories(Result result) {
        addView(getPrimaryTitleHolder(), 2130903311);
        for (CategoryItem categoryItem : result.getPrimaryCategories()) {
            View inflate = LayoutInflater.from(getActivity()).inflate(2130903357, getPrimaryCategoryView(), false);
            ((TextView) inflate.findViewById(16908308)).setText(categoryItem.getName());
            inflate.setTag(categoryItem);
            inflate.setOnClickListener(this.onPrimaryClickListener);
            getPrimaryCategoryView().addView(inflate);
        }
    }

    public void onLoaderReset(Loader<Result> loader) {
    }

    public LinearLayout getSuggestedCategoryView() {
        return (LinearLayout) getView().findViewById(2131624486);
    }

    public LinearLayout getPrimaryCategoryView() {
        return (LinearLayout) getView().findViewById(2131624489);
    }

    public FrameLayout getShowMoreView() {
        return (FrameLayout) getView().findViewById(2131624487);
    }

    public FrameLayout getSuggestionTitleHolder() {
        return (FrameLayout) getView().findViewById(2131624485);
    }

    public FrameLayout getPrimaryTitleHolder() {
        return (FrameLayout) getView().findViewById(2131624488);
    }

    private void addView(ViewGroup viewGroup, int i) {
        viewGroup.addView(LayoutInflater.from(getActivity()).inflate(i, viewGroup, false));
    }
}
