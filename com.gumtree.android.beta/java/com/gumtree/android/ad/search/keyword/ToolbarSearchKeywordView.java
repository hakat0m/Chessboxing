package com.gumtree.android.ad.search.keyword;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.SearchAutoComplete;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ToolbarSearchKeywordView extends FrameLayout {
    @Bind({2131624096})
    SearchAutoComplete searchAutocomplete;
    @Bind({2131624926})
    SearchView searchView;
    private ToolbarSearchKeywordListener toolbarSearchKeywordListener;

    public interface ToolbarSearchKeywordListener {
        void onKeywordTextChanged(String str);

        void onKeywordTextSubmitted(String str);
    }

    public ToolbarSearchKeywordView(Context context) {
        super(context);
        init(context);
    }

    public ToolbarSearchKeywordView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public ToolbarSearchKeywordView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public void setToolbarSearchKeywordListener(ToolbarSearchKeywordListener toolbarSearchKeywordListener) {
        this.toolbarSearchKeywordListener = toolbarSearchKeywordListener;
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(2130903426, this);
        ButterKnife.bind(this);
        this.searchView.setSearchableInfo(((SearchManager) context.getSystemService("search")).getSearchableInfo(new ComponentName(context, SearchKeywordActivity.class)));
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(2131362159, new int[]{16842901});
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(0, -1);
        obtainStyledAttributes.recycle();
        this.searchAutocomplete.setTextSize(0, (float) dimensionPixelSize);
        this.searchView.setOnQueryTextListener(new 1(this));
        this.searchView.setImeOptions(3);
        this.searchAutocomplete.setOnEditorActionListener(ToolbarSearchKeywordView$$Lambda$1.lambdaFactory$(this));
    }

    /* synthetic */ boolean lambda$init$0(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 3 || this.toolbarSearchKeywordListener == null) {
            return false;
        }
        this.toolbarSearchKeywordListener.onKeywordTextSubmitted(textView.getText().toString());
        return true;
    }

    public void setText(String str) {
        this.searchView.setQuery(str, false);
    }
}
