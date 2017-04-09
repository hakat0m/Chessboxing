package com.gumtree.android.srp;

import android.view.MenuItem;
import android.widget.PopupMenu.OnMenuItemClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SRPGridFragment$$Lambda$3 implements OnMenuItemClickListener {
    private final SRPGridFragment arg$1;

    private SRPGridFragment$$Lambda$3(SRPGridFragment sRPGridFragment) {
        this.arg$1 = sRPGridFragment;
    }

    public static OnMenuItemClickListener lambdaFactory$(SRPGridFragment sRPGridFragment) {
        return new SRPGridFragment$$Lambda$3(sRPGridFragment);
    }

    @Hidden
    public boolean onMenuItemClick(MenuItem menuItem) {
        return this.arg$1.lambda$onSort$2(menuItem);
    }
}
