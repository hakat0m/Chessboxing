package com.gumtree.android.ad.search.results;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ToolbarSearchView extends FrameLayout {
    @Bind({2131624925})
    TextView searchTextView;

    public ToolbarSearchView(Context context) {
        super(context);
        init(context);
    }

    public ToolbarSearchView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public ToolbarSearchView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(2130903425, this);
        ButterKnife.bind(this);
    }

    public void setSearchText(String str) {
        this.searchTextView.setText(str);
    }
}
