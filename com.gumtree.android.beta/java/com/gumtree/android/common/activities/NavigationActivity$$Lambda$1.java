package com.gumtree.android.common.activities;

import android.view.View;
import com.mikepenz.materialdrawer.Drawer.OnDrawerItemClickListener;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class NavigationActivity$$Lambda$1 implements OnDrawerItemClickListener {
    private final NavigationActivity arg$1;

    private NavigationActivity$$Lambda$1(NavigationActivity navigationActivity) {
        this.arg$1 = navigationActivity;
    }

    public static OnDrawerItemClickListener lambdaFactory$(NavigationActivity navigationActivity) {
        return new NavigationActivity$$Lambda$1(navigationActivity);
    }

    @Hidden
    public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
        return this.arg$1.lambda$setupDrawer$0(view, i, iDrawerItem);
    }
}
