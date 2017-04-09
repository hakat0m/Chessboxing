package com.gumtree.android.ad.search.presenters.keyword;

import android.support.annotation.Nullable;
import com.gumtree.android.ad.search.models.SuggestionItem;
import com.gumtree.android.ad.search.presenters.keyword.SearchKeywordPresenter.View;
import com.gumtree.android.mvp.Gate;
import java.util.List;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class SearchKeywordGatedView implements View {
    private final Gate<SuggestionItem> openSearchRefine = new Gate();
    private final Gate<List<SuggestionItem>> showKeywordSuggestions = new Gate();
    private final Subscription subscription = this.trigger.subscribe(SearchKeywordGatedView$$Lambda$1.lambdaFactory$(this));
    private final BehaviorSubject<View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(View view) {
        if (view == null) {
            close();
        } else {
            open(view);
        }
    }

    public void setDecorated(@Nullable View view) {
        this.trigger.onNext(view);
    }

    public void sealIt() {
        this.subscription.unsubscribe();
    }

    private void open(View view) {
        this.showKeywordSuggestions.open(SearchKeywordGatedView$$Lambda$2.lambdaFactory$(view));
        this.openSearchRefine.open(SearchKeywordGatedView$$Lambda$3.lambdaFactory$(view));
    }

    protected void close() {
        this.showKeywordSuggestions.close();
        this.openSearchRefine.close();
    }

    public void showKeywordSuggestions(List<SuggestionItem> list) {
        this.showKeywordSuggestions.perform(list);
    }

    public void openSearchRefine(SuggestionItem suggestionItem) {
        this.openSearchRefine.perform(suggestionItem);
    }
}
