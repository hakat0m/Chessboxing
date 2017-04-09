package com.gumtree.android.savedsearches;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SavedSearchesDialogFragment$$Lambda$2 implements OnClickListener {
    private final SavedSearchesDialogFragment arg$1;
    private final String arg$2;
    private final long arg$3;

    private SavedSearchesDialogFragment$$Lambda$2(SavedSearchesDialogFragment savedSearchesDialogFragment, String str, long j) {
        this.arg$1 = savedSearchesDialogFragment;
        this.arg$2 = str;
        this.arg$3 = j;
    }

    public static OnClickListener lambdaFactory$(SavedSearchesDialogFragment savedSearchesDialogFragment, String str, long j) {
        return new SavedSearchesDialogFragment$$Lambda$2(savedSearchesDialogFragment, str, j);
    }

    @Hidden
    public void onClick(DialogInterface dialogInterface, int i) {
        this.arg$1.lambda$onCreateDialog$1(this.arg$2, this.arg$3, dialogInterface, i);
    }
}
