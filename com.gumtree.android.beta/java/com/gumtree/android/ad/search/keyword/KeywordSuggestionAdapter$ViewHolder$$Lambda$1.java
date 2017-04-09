package com.gumtree.android.ad.search.keyword;

import android.view.View;
import android.view.View.OnClickListener;
import com.gumtree.android.ad.search.keyword.KeywordSuggestionAdapter.ViewHolder;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class KeywordSuggestionAdapter$ViewHolder$$Lambda$1 implements OnClickListener {
    private final ViewHolder arg$1;

    private KeywordSuggestionAdapter$ViewHolder$$Lambda$1(ViewHolder viewHolder) {
        this.arg$1 = viewHolder;
    }

    public static OnClickListener lambdaFactory$(ViewHolder viewHolder) {
        return new KeywordSuggestionAdapter$ViewHolder$$Lambda$1(viewHolder);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$new$0(view);
    }
}
