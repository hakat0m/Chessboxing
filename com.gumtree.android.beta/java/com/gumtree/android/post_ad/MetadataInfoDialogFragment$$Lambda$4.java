package com.gumtree.android.post_ad;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class MetadataInfoDialogFragment$$Lambda$4 implements OnClickListener {
    private final MetadataInfoDialogFragment arg$1;

    private MetadataInfoDialogFragment$$Lambda$4(MetadataInfoDialogFragment metadataInfoDialogFragment) {
        this.arg$1 = metadataInfoDialogFragment;
    }

    public static OnClickListener lambdaFactory$(MetadataInfoDialogFragment metadataInfoDialogFragment) {
        return new MetadataInfoDialogFragment$$Lambda$4(metadataInfoDialogFragment);
    }

    @Hidden
    public void onClick(DialogInterface dialogInterface, int i) {
        this.arg$1.lambda$onCreateDialog$3(dialogInterface, i);
    }
}
