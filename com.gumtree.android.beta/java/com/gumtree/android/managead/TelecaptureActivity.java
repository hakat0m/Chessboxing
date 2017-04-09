package com.gumtree.android.managead;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import com.gumtree.Log;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.analytics.Track;

public class TelecaptureActivity extends BaseActivity implements OnClickListener {
    public static Intent createIntent(Context context) {
        return new Intent(context, TelecaptureActivity.class);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2130903115);
        findViewById(2131624259).setOnClickListener(this);
        findViewById(2131624258).setOnClickListener(this);
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    protected void onStart() {
        super.onStart();
        Track.viewBumpUpByPhone();
    }

    public void onStop() {
        super.onStop();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                finish();
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case 2131624258:
                Track.eventTelecaptureCancel();
                cancel();
                return;
            case 2131624259:
                Track.eventTelecaptureAttempt();
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://my.gumtree.com/manage/ads?utm_source=GumtreeApp&utm_medium=Android&utm_term=Telecapture&utm_content=Telecapture")));
                return;
            default:
                Log.e(getClass().getSimpleName(), "case not supported " + view.getId());
                return;
        }
    }

    private void cancel() {
        onBackPressed();
    }
}
