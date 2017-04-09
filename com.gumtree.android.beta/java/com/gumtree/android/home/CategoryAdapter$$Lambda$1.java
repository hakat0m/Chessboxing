package com.gumtree.android.home;

import android.view.View;
import android.view.View.OnClickListener;
import com.gumtree.android.home.CategoryAdapter.Category;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class CategoryAdapter$$Lambda$1 implements OnClickListener {
    private final CategoryAdapter arg$1;
    private final Category arg$2;

    private CategoryAdapter$$Lambda$1(CategoryAdapter categoryAdapter, Category category) {
        this.arg$1 = categoryAdapter;
        this.arg$2 = category;
    }

    public static OnClickListener lambdaFactory$(CategoryAdapter categoryAdapter, Category category) {
        return new CategoryAdapter$$Lambda$1(categoryAdapter, category);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$inflate$0(this.arg$2, view);
    }
}
