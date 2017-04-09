package com.gumtree.android.ad.search.presenters.keyword;

import com.gumtree.android.ad.search.models.SuggestionItem;
import com.gumtree.android.mvp.Presenter;
import com.gumtree.android.mvp.PresenterView;
import java.util.List;

public interface SearchKeywordPresenter extends Presenter<View> {

    public interface View extends PresenterView {
        void openSearchRefine(SuggestionItem suggestionItem);

        void showKeywordSuggestions(List<SuggestionItem> list);
    }

    void onSearchKeywordChanged(String str);

    void onSuggestionSelected(SuggestionItem suggestionItem);

    void onSuggestionSubmitted(String str);
}
