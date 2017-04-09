package com.gumtree.android.post_ad;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class MetadataInfoDialogFragment$$Lambda$2 implements OnClickListener {
    private final MetadataInfoDialogFragment arg$1;
    private final Intent arg$2;

    private MetadataInfoDialogFragment$$Lambda$2(MetadataInfoDialogFragment metadataInfoDialogFragment, Intent intent) {
        this.arg$1 = metadataInfoDialogFragment;
        this.arg$2 = intent;
    }

    public static OnClickListener lambdaFactory$(MetadataInfoDialogFragment metadataInfoDialogFragment, Intent intent) {
        return new MetadataInfoDialogFragment$$Lambda$2(metadataInfoDialogFragment, intent);
    }

    @Hidden
    public void onClick(DialogInterface dialogInterface, int i) {
        this.arg$1.lambda$onCreateDialog$1(this.arg$2, dialogInterface, i);
    }
}
