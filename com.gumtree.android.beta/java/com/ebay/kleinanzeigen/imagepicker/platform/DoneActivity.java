package com.ebay.kleinanzeigen.imagepicker.platform;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import com.ebay.kleinanzeigen.imagepicker.R$id;
import com.ebay.kleinanzeigen.imagepicker.R$layout;

public abstract class DoneActivity extends ActionBarActivity {
    private View doneButton;

    protected abstract void onDone();

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        attachCustomActionBarWithDoneButton();
        setupActionBar();
        setActionBarCustomViewVisibility(true);
    }

    private void setupActionBar() {
        getSupportActionBar().setDisplayShowHomeEnabled(false);
    }

    private void setActionBarCustomViewVisibility(boolean z) {
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayShowCustomEnabled(z);
        supportActionBar.setDisplayShowTitleEnabled(!z);
    }

    private void attachCustomActionBarWithDoneButton() {
        getSupportActionBar().setCustomView(createActionBarView());
    }

    private View createActionBarView() {
        View inflate = ((LayoutInflater) getSupportActionBar().getThemedContext().getSystemService("layout_inflater")).inflate(R$layout.actionbar_done_view, null);
        this.doneButton = inflate.findViewById(R$id.actionbar_done);
        this.doneButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DoneActivity.this.onDone();
            }
        });
        return inflate;
    }
}
