package com.gumtree.android.savedsearches;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.gumtree.android.savedsearches.SavedSearchesEndlessAdapter.SavedSearchesAdapter;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SavedSearchesEndlessAdapter$SavedSearchesAdapter$$Lambda$1 implements OnCheckedChangeListener {
    private final SavedSearchesAdapter arg$1;
    private final int arg$2;

    private SavedSearchesEndlessAdapter$SavedSearchesAdapter$$Lambda$1(SavedSearchesAdapter savedSearchesAdapter, int i) {
        this.arg$1 = savedSearchesAdapter;
        this.arg$2 = i;
    }

    public static OnCheckedChangeListener lambdaFactory$(SavedSearchesAdapter savedSearchesAdapter, int i) {
        return new SavedSearchesEndlessAdapter$SavedSearchesAdapter$$Lambda$1(savedSearchesAdapter, i);
    }

    @Hidden
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.arg$1.lambda$getView$0(this.arg$2, compoundButton, z);
    }
}
