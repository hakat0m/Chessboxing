package com.gumtree.android.common.activities;

import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.gumtree.android.common.views.ThemeUtils;

public abstract class BaseModalActivity extends BaseActivity {
    private boolean showClose = true;

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                if (this.showClose) {
                    onCancel();
                }
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void setContentView(int i) {
        super.setContentView(i);
        setupToolbarActions();
    }

    public void setContentView(View view) {
        super.setContentView(view);
        setupToolbarActions();
    }

    public void setContentView(View view, LayoutParams layoutParams) {
        super.setContentView(view, layoutParams);
        setupToolbarActions();
    }

    private void setupToolbarActions() {
        Toolbar toolbar = getToolbar();
        if (toolbar != null && this.showClose) {
            Drawable drawable = ContextCompat.getDrawable(this, 2130837829);
            if (drawable != null) {
                drawable = drawable.mutate();
                drawable.setColorFilter(ThemeUtils.getColor(toolbar.getContext(), 16842806), Mode.SRC_ATOP);
                toolbar.setNavigationIcon(drawable);
            }
        }
    }

    protected void onCancel() {
        setResult(0);
    }

    protected void setShowClose(boolean z) {
        this.showClose = z;
    }
}
