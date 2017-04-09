package com.gumtree.android.category;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.gumtree.android.category.NewPostAdCategoryActivity.CategorySuggestionSelectionListener;
import com.gumtree.android.category.model.DraftCategory;
import com.gumtree.android.category.suggest.service.converter.CategoryTreeConverter;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.Validate;

public class CategorySuggestionAdapter extends Adapter<ViewHolder> {
    private final CategoryTreeConverter categoryTreeConverter;
    private final CategorySuggestionSelectionListener listener;
    @NonNull
    private List<DraftCategory> suggestions = Collections.emptyList();

    public CategorySuggestionAdapter(@NonNull CategorySuggestionSelectionListener categorySuggestionSelectionListener, @NonNull CategoryTreeConverter categoryTreeConverter) {
        this.listener = (CategorySuggestionSelectionListener) Validate.notNull(categorySuggestionSelectionListener);
        this.categoryTreeConverter = (CategoryTreeConverter) Validate.notNull(categoryTreeConverter);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(this, LayoutInflater.from(viewGroup.getContext()).inflate(2130903309, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bindSuggestion(this.suggestions, i);
    }

    public int getItemCount() {
        return this.suggestions.size();
    }

    public void setSuggestions(@NonNull List<DraftCategory> list) {
        this.suggestions = list;
        notifyDataSetChanged();
    }
}
