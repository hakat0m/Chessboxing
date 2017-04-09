package com.gumtree.android.help;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.gumtree.android.common.activities.BaseActivity;

public class HelpActivity extends BaseActivity {
    public static Intent createIntent(Context context) {
        return new Intent(context, HelpActivity.class);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2130903081);
    }

    protected boolean isHomeAsUp() {
        return true;
    }
}
