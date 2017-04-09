package com.gumtree.android.srp.suggestion;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.gumtree.android.srp.suggestion.AutoCompleteSuggestion.AutoCompleteSuggestionBuilder;
import com.gumtree.androidapi.model.CategoryItem;
import com.gumtree.androidapi.model.KeywordSuggestion;
import com.gumtree.androidapi.model.Suggestion;
import javax.inject.Inject;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.Validate;
import rx.Observable;

public class CallistoSuggestionProvider implements SuggestionProvider {
    private final SuggestionsService suggestionsService;

    @Inject
    public CallistoSuggestionProvider(@NonNull SuggestionsService suggestionsService) {
        this.suggestionsService = (SuggestionsService) Validate.notNull(suggestionsService);
    }

    @Nullable
    public AutoCompleteSuggestions suggestFor(String str, String str2) {
        return (AutoCompleteSuggestions) this.suggestionsService.suggestions(str, str2).map(CallistoSuggestionProvider$$Lambda$1.lambdaFactory$(this)).onErrorResumeNext(Observable.empty()).toBlocking().firstOrDefault(null);
    }

    /* synthetic */ AutoCompleteSuggestions lambda$suggestFor$0(KeywordSuggestion keywordSuggestion) {
        return convert(keywordSuggestion);
    }

    @NonNull
    private AutoCompleteSuggestions convert(@NonNull KeywordSuggestion keywordSuggestion) {
        return AutoCompleteSuggestions.builder().trackingId(keywordSuggestion.getSuggestionTrackingId()).suggestions(IterableUtils.toList(IterableUtils.transformedIterable(keywordSuggestion.getSuggestions(), CallistoSuggestionProvider$$Lambda$2.lambdaFactory$(this)))).build();
    }

    /* synthetic */ AutoCompleteSuggestion lambda$convert$1(Suggestion suggestion) {
        return convert(suggestion);
    }

    @NonNull
    private AutoCompleteSuggestion convert(Suggestion suggestion) {
        AutoCompleteSuggestionBuilder suggestedTrackingId = AutoCompleteSuggestion.builder().suggestedKeyword(suggestion.getSuggestedKeyword()).suggestedTrackingId(suggestion.getSuggestedTrackingId());
        CategoryItem categoryItem = suggestion.getCategoryItem();
        if (categoryItem != null) {
            suggestedTrackingId.categoryItemId(categoryItem.getId()).categoryItemLocalizedName(categoryItem.getLocalizedName());
        }
        return suggestedTrackingId.build();
    }
}
