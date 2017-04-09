package com.gumtree.android.categories;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PostAdCategoryActivity$$Lambda$1 implements OnClickListener {
    private final PostAdCategoryActivity arg$1;

    private PostAdCategoryActivity$$Lambda$1(PostAdCategoryActivity postAdCategoryActivity) {
        this.arg$1 = postAdCategoryActivity;
    }

    public static OnClickListener lambdaFactory$(PostAdCategoryActivity postAdCategoryActivity) {
        return new PostAdCategoryActivity$$Lambda$1(postAdCategoryActivity);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$initActionBar$0(view);
    }
}
