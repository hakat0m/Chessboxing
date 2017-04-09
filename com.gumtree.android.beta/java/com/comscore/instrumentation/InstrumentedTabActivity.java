package com.comscore.instrumentation;

import android.app.TabActivity;
import android.os.Bundle;
import com.comscore.analytics.comScore;

@Deprecated
public class InstrumentedTabActivity extends TabActivity {
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        comScore.setAppContext(getApplicationContext());
    }

    protected void onPause() {
        super.onPause();
        comScore.onExitForeground();
    }

    protected void onResume() {
        super.onResume();
        comScore.getCore().setCurrentActivityName(getClass().getSimpleName());
        comScore.onEnterForeground();
    }
}
