package com.gumtree.android.savedsearches;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SavedSearchesFragment$$Lambda$1 implements OnClickListener {
    private final SavedSearchesFragment arg$1;
    private final ViewGroup arg$2;
    private final ViewGroup arg$3;

    private SavedSearchesFragment$$Lambda$1(SavedSearchesFragment savedSearchesFragment, ViewGroup viewGroup, ViewGroup viewGroup2) {
        this.arg$1 = savedSearchesFragment;
        this.arg$2 = viewGroup;
        this.arg$3 = viewGroup2;
    }

    public static OnClickListener lambdaFactory$(SavedSearchesFragment savedSearchesFragment, ViewGroup viewGroup, ViewGroup viewGroup2) {
        return new SavedSearchesFragment$$Lambda$1(savedSearchesFragment, viewGroup, viewGroup2);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$displayEducateNotification$0(this.arg$2, this.arg$3, view);
    }
}
