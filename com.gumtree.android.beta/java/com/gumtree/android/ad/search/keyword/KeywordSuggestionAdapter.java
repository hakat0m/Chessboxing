package com.gumtree.android.ad.search.keyword;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.gumtree.android.ad.search.models.SuggestionItem;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.Validate;

class KeywordSuggestionAdapter extends Adapter<ViewHolder> {
    private final KeywordSuggestionListener listener;
    @NonNull
    private List<SuggestionItem> suggestions = Collections.emptyList();

    interface KeywordSuggestionListener {
        void onKeywordSuggestionPreSelected(SuggestionItem suggestionItem);

        void onKeywordSuggestionSelected(SuggestionItem suggestionItem);
    }

    KeywordSuggestionAdapter(@NonNull KeywordSuggestionListener keywordSuggestionListener) {
        this.listener = (KeywordSuggestionListener) Validate.notNull(keywordSuggestionListener);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(this, LayoutInflater.from(viewGroup.getContext()).inflate(2130903340, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bindSuggestion(this.suggestions, i);
    }

    public int getItemCount() {
        return this.suggestions.size();
    }

    public void setSuggestions(@NonNull List<SuggestionItem> list) {
        this.suggestions = list;
        notifyDataSetChanged();
    }
}
