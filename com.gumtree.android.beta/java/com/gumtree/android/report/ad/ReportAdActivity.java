package com.gumtree.android.report.ad;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.dagger.components.ApplicationComponent;

public class ReportAdActivity extends BaseActivity {
    public static final String EXTRA_VIP_ID = "extra_vip_id";

    public static Intent createIntent(Context context, long j) {
        return new Intent(context, ReportAdActivity.class).putExtra(EXTRA_VIP_ID, j);
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initReporAdComponent();
        setContentView(2130903105);
    }

    private void initReporAdComponent() {
        if (((ReportAdComponent) ComponentsManager.get().getBaseComponent(ReportAdComponent.KEY)) == null) {
            ApplicationComponent appComponent = ComponentsManager.get().getAppComponent();
            ComponentsManager.get().putBaseComponent(ReportAdComponent.KEY, DaggerReportAdComponent.builder().applicationComponent(appComponent).reportAdModule(new ReportAdModule(getVipId())).build());
        }
    }

    private long getVipId() {
        return getIntent().getLongExtra(EXTRA_VIP_ID, 0);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                Track.eventReportAdCancel(String.valueOf(getVipId()));
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void onBackPressed() {
        Track.eventReportAdCancel(String.valueOf(getVipId()));
        super.onBackPressed();
    }

    protected void onDestroy() {
        if (isFinishing()) {
            ComponentsManager.get().removeBaseComponent(ReportAdComponent.KEY);
        }
        super.onDestroy();
    }
}
