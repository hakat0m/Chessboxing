package com.gumtree.android.postad.views.attribute;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class FSBODecoratorView$$Lambda$2 implements OnDismissListener {
    private final FSBODecoratorView arg$1;

    private FSBODecoratorView$$Lambda$2(FSBODecoratorView fSBODecoratorView) {
        this.arg$1 = fSBODecoratorView;
    }

    public static OnDismissListener lambdaFactory$(FSBODecoratorView fSBODecoratorView) {
        return new FSBODecoratorView$$Lambda$2(fSBODecoratorView);
    }

    @Hidden
    public void onDismiss(DialogInterface dialogInterface) {
        this.arg$1.lambda$createPopupDialog$1(dialogInterface);
    }
}
