package com.gumtree.android.savedsearches;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SavedSearchesFragment$$Lambda$2 implements OnClickListener {
    private final SavedSearchesFragment arg$1;
    private final long arg$2;

    private SavedSearchesFragment$$Lambda$2(SavedSearchesFragment savedSearchesFragment, long j) {
        this.arg$1 = savedSearchesFragment;
        this.arg$2 = j;
    }

    public static OnClickListener lambdaFactory$(SavedSearchesFragment savedSearchesFragment, long j) {
        return new SavedSearchesFragment$$Lambda$2(savedSearchesFragment, j);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$removeItem$1(this.arg$2, view);
    }
}
