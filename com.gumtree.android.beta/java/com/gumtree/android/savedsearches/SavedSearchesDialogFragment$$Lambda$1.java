package com.gumtree.android.savedsearches;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SavedSearchesDialogFragment$$Lambda$1 implements OnCheckedChangeListener {
    private final SavedSearchesDialogFragment arg$1;

    private SavedSearchesDialogFragment$$Lambda$1(SavedSearchesDialogFragment savedSearchesDialogFragment) {
        this.arg$1 = savedSearchesDialogFragment;
    }

    public static OnCheckedChangeListener lambdaFactory$(SavedSearchesDialogFragment savedSearchesDialogFragment) {
        return new SavedSearchesDialogFragment$$Lambda$1(savedSearchesDialogFragment);
    }

    @Hidden
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        this.arg$1.lambda$onCreateDialog$0(compoundButton, z);
    }
}
