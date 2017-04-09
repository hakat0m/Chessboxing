package com.gumtree.android.srp.suggestion;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.ebay.classifieds.capi.executor.Result;
import com.gumtree.android.category.CategoryItem;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.http.CapiClientManager;
import com.gumtree.android.common.http.CapiUrl;
import com.gumtree.android.common.http.model.ICapiClient;
import com.gumtree.android.srp.suggestion.AutoCompleteSuggestion.AutoCompleteSuggestionBuilder;
import com.gumtree.android.suggestions.KeywordSuggestion;
import com.gumtree.android.suggestions.Suggestion;
import com.gumtree.android.suggestions.parser.KeywordSuggestionParser;
import org.apache.commons.collections4.IterableUtils;

public class CapiSuggestionProvider implements SuggestionProvider {
    private final CapiClientManager capiClientManager;

    public CapiSuggestionProvider(CapiClientManager capiClientManager) {
        this.capiClientManager = capiClientManager;
    }

    @Nullable
    public AutoCompleteSuggestions suggestFor(String str, String str2) {
        ICapiClient withParam = this.capiClientManager.getCapiClient().get(CapiUrl.SUGGESTION_ADS).withParam(StatefulActivity.NAME_QUERY, str);
        if (str2.length() > 0) {
            withParam.withParam(StatefulActivity.NAME_CATEGORY_ID, str2);
        }
        Result execute = withParam.execute(new KeywordSuggestionParser());
        if (execute.hasError()) {
            return null;
        }
        KeywordSuggestion keywordSuggestion;
        if (execute.getData() == null) {
            keywordSuggestion = new KeywordSuggestion();
        } else {
            keywordSuggestion = (KeywordSuggestion) execute.getData();
        }
        return convert(keywordSuggestion);
    }

    @NonNull
    private AutoCompleteSuggestions convert(@NonNull KeywordSuggestion keywordSuggestion) {
        return AutoCompleteSuggestions.builder().trackingId(keywordSuggestion.getSuggestionTrackingId()).suggestions(IterableUtils.toList(IterableUtils.transformedIterable(keywordSuggestion.getSuggestions(), CapiSuggestionProvider$$Lambda$1.lambdaFactory$(this)))).build();
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
