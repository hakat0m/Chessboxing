package com.apptentive.android.sdk;

import android.app.ActivityManager.TaskDescription;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.IntentCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.apptentive.android.sdk.adapter.ApptentiveViewPagerAdapter;
import com.apptentive.android.sdk.model.FragmentFactory;
import com.apptentive.android.sdk.module.engagement.EngagementModule;
import com.apptentive.android.sdk.module.engagement.interaction.fragment.ApptentiveBaseFragment;
import com.apptentive.android.sdk.module.engagement.interaction.fragment.ApptentiveBaseFragment.OnFragmentTransitionListener;
import com.apptentive.android.sdk.module.metric.MetricModule;
import com.apptentive.android.sdk.util.Util;

public class ApptentiveViewActivity extends AppCompatActivity implements OnFragmentTransitionListener {
    private static final String FRAGMENT_TAG = "fragmentTag";
    private View contentView;
    private int current_tab;
    private View decorView;
    private int fragmentType;
    OnGlobalLayoutListener keyboardPresencelLayoutListener = new OnGlobalLayoutListener() {
        public void onGlobalLayout() {
            Rect rect = new Rect();
            ApptentiveViewActivity.this.decorView.getWindowVisibleDisplayFrame(rect);
            int i = ApptentiveViewActivity.this.decorView.getContext().getResources().getDisplayMetrics().heightPixels - rect.bottom;
            if (i != 0) {
                if (ApptentiveViewActivity.this.contentView.getPaddingBottom() != i) {
                    ApptentiveViewActivity.this.contentView.setPadding(0, 0, 0, i);
                }
            } else if (ApptentiveViewActivity.this.contentView.getPaddingBottom() != 0) {
                ApptentiveViewActivity.this.contentView.setPadding(0, 0, 0, 0);
            }
        }
    };
    private Toolbar toolbar;
    private ViewPager viewPager;
    private ApptentiveViewPagerAdapter viewPager_Adapter;

    protected void onCreate(Bundle bundle) {
        ApptentiveBaseFragment apptentiveBaseFragment;
        boolean isShownAsModalDialog;
        ApptentiveBaseFragment apptentiveBaseFragment2;
        Throwable th;
        Throwable th2;
        Toolbar toolbar;
        boolean z;
        ActionBar supportActionBar;
        int toolbarNavigationIconResourceId;
        Drawable drawable;
        super.onCreate(bundle);
        Bundle addDisplayModeToFragmentBundle = FragmentFactory.addDisplayModeToFragmentBundle(getIntent().getExtras());
        boolean z2 = addDisplayModeToFragmentBundle.getBoolean("showAsModal");
        if (bundle != null) {
            apptentiveBaseFragment = (ApptentiveBaseFragment) getSupportFragmentManager().findFragmentByTag(bundle.getString(FRAGMENT_TAG));
            if (apptentiveBaseFragment == null) {
                finish();
                return;
            }
        }
        apptentiveBaseFragment = null;
        try {
            this.fragmentType = addDisplayModeToFragmentBundle.getInt("fragmentType", 0);
            if (this.fragmentType != 0) {
                if (this.fragmentType == 3 || this.fragmentType == 2 || this.fragmentType == 1) {
                    addDisplayModeToFragmentBundle.putInt("toolbarLayoutId", R.id.apptentive_toolbar);
                    if (apptentiveBaseFragment == null) {
                        ApptentiveBaseFragment createFragmentInstance = FragmentFactory.createFragmentInstance(addDisplayModeToFragmentBundle);
                        try {
                            ApptentiveBaseFragment apptentiveBaseFragment3 = createFragmentInstance;
                            isShownAsModalDialog = createFragmentInstance.isShownAsModalDialog();
                            apptentiveBaseFragment2 = apptentiveBaseFragment3;
                        } catch (Throwable e) {
                            th = e;
                            apptentiveBaseFragment = createFragmentInstance;
                            th2 = th;
                            ApptentiveLog.e("Error creating ApptentiveViewActivity.", th2, new Object[0]);
                            MetricModule.sendError(th2, null, null);
                            isShownAsModalDialog = z2;
                            apptentiveBaseFragment2 = apptentiveBaseFragment;
                            setContentView(R.layout.apptentive_viewactivity);
                            this.toolbar = (Toolbar) findViewById(R.id.apptentive_toolbar);
                            setSupportActionBar(this.toolbar);
                            toolbar = this.toolbar;
                            if (isShownAsModalDialog) {
                                z = false;
                            } else {
                                z = true;
                            }
                            toolbar.setPadding(0, getToolbarHeightAdjustment(z), 0, 0);
                            supportActionBar = getSupportActionBar();
                            if (supportActionBar != null) {
                                supportActionBar.setDisplayHomeAsUpEnabled(true);
                                toolbarNavigationIconResourceId = apptentiveBaseFragment2.getToolbarNavigationIconResourceId(getTheme());
                                if (toolbarNavigationIconResourceId != 0) {
                                    drawable = ResourcesCompat.getDrawable(getResources(), toolbarNavigationIconResourceId, getTheme());
                                    drawable.setColorFilter(Util.getThemeColor(ApptentiveInternal.getInstance().getApptentiveToolbarTheme(), R.attr.colorControlNormal), Mode.SRC_ATOP);
                                    supportActionBar.setHomeAsUpIndicator(drawable);
                                }
                            }
                            this.current_tab = 0;
                            addFragmentToAdapter(apptentiveBaseFragment2, apptentiveBaseFragment2.getTitle());
                            this.viewPager = (ViewPager) findViewById(R.id.apptentive_vp);
                            this.viewPager.setAdapter(this.viewPager_Adapter);
                            this.viewPager.addOnPageChangeListener(new OnPageChangeListener() {
                                Boolean first = Boolean.valueOf(true);

                                public void onPageSelected(int i) {
                                    ApptentiveBaseFragment apptentiveBaseFragment = (ApptentiveBaseFragment) ApptentiveViewActivity.this.viewPager_Adapter.getItem(ApptentiveViewActivity.this.viewPager.getCurrentItem());
                                    if (apptentiveBaseFragment.isShownAsModalDialog()) {
                                        ApptentiveViewActivity.this.toolbar.setVisibility(8);
                                    } else {
                                        final String title = apptentiveBaseFragment.getTitle();
                                        ApptentiveViewActivity.this.toolbar.post(new Runnable() {
                                            public void run() {
                                                ApptentiveViewActivity.this.toolbar.setVisibility(0);
                                                ApptentiveViewActivity.this.toolbar.setTitle(title);
                                            }
                                        });
                                    }
                                    ApptentiveViewActivity.this.current_tab = i;
                                }

                                public void onPageScrolled(int i, float f, int i2) {
                                    if (this.first.booleanValue() && f == 0.0f && i2 == 0) {
                                        onPageSelected(ApptentiveViewActivity.this.current_tab);
                                        this.first = Boolean.valueOf(false);
                                    }
                                }

                                public void onPageScrollStateChanged(int i) {
                                }
                            });
                            getWindow().setSoftInputMode(16);
                        }
                    }
                    isShownAsModalDialog = z2;
                    apptentiveBaseFragment2 = apptentiveBaseFragment;
                    if (apptentiveBaseFragment2 != null) {
                        try {
                            applyApptentiveTheme(isShownAsModalDialog);
                            apptentiveBaseFragment2.setOnTransitionListener(this);
                        } catch (Throwable e2) {
                            th = e2;
                            apptentiveBaseFragment = apptentiveBaseFragment2;
                            z2 = isShownAsModalDialog;
                            th2 = th;
                            ApptentiveLog.e("Error creating ApptentiveViewActivity.", th2, new Object[0]);
                            MetricModule.sendError(th2, null, null);
                            isShownAsModalDialog = z2;
                            apptentiveBaseFragment2 = apptentiveBaseFragment;
                            setContentView(R.layout.apptentive_viewactivity);
                            this.toolbar = (Toolbar) findViewById(R.id.apptentive_toolbar);
                            setSupportActionBar(this.toolbar);
                            toolbar = this.toolbar;
                            if (isShownAsModalDialog) {
                                z = true;
                            } else {
                                z = false;
                            }
                            toolbar.setPadding(0, getToolbarHeightAdjustment(z), 0, 0);
                            supportActionBar = getSupportActionBar();
                            if (supportActionBar != null) {
                                supportActionBar.setDisplayHomeAsUpEnabled(true);
                                toolbarNavigationIconResourceId = apptentiveBaseFragment2.getToolbarNavigationIconResourceId(getTheme());
                                if (toolbarNavigationIconResourceId != 0) {
                                    drawable = ResourcesCompat.getDrawable(getResources(), toolbarNavigationIconResourceId, getTheme());
                                    drawable.setColorFilter(Util.getThemeColor(ApptentiveInternal.getInstance().getApptentiveToolbarTheme(), R.attr.colorControlNormal), Mode.SRC_ATOP);
                                    supportActionBar.setHomeAsUpIndicator(drawable);
                                }
                            }
                            this.current_tab = 0;
                            addFragmentToAdapter(apptentiveBaseFragment2, apptentiveBaseFragment2.getTitle());
                            this.viewPager = (ViewPager) findViewById(R.id.apptentive_vp);
                            this.viewPager.setAdapter(this.viewPager_Adapter);
                            this.viewPager.addOnPageChangeListener(/* anonymous class already generated */);
                            getWindow().setSoftInputMode(16);
                        }
                    }
                    apptentiveBaseFragment = apptentiveBaseFragment2;
                    z2 = isShownAsModalDialog;
                }
                if (apptentiveBaseFragment == null) {
                    if (this.fragmentType == 4) {
                        String stringExtra = getIntent().getStringExtra("fragmentExtraData");
                        if (stringExtra != null) {
                            EngagementModule.engageInternal(this, stringExtra);
                        }
                    }
                    finish();
                    return;
                }
            }
            isShownAsModalDialog = z2;
            apptentiveBaseFragment2 = apptentiveBaseFragment;
        } catch (Exception e3) {
            th2 = e3;
            ApptentiveLog.e("Error creating ApptentiveViewActivity.", th2, new Object[0]);
            MetricModule.sendError(th2, null, null);
            isShownAsModalDialog = z2;
            apptentiveBaseFragment2 = apptentiveBaseFragment;
            setContentView(R.layout.apptentive_viewactivity);
            this.toolbar = (Toolbar) findViewById(R.id.apptentive_toolbar);
            setSupportActionBar(this.toolbar);
            toolbar = this.toolbar;
            if (isShownAsModalDialog) {
                z = false;
            } else {
                z = true;
            }
            toolbar.setPadding(0, getToolbarHeightAdjustment(z), 0, 0);
            supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setDisplayHomeAsUpEnabled(true);
                toolbarNavigationIconResourceId = apptentiveBaseFragment2.getToolbarNavigationIconResourceId(getTheme());
                if (toolbarNavigationIconResourceId != 0) {
                    drawable = ResourcesCompat.getDrawable(getResources(), toolbarNavigationIconResourceId, getTheme());
                    drawable.setColorFilter(Util.getThemeColor(ApptentiveInternal.getInstance().getApptentiveToolbarTheme(), R.attr.colorControlNormal), Mode.SRC_ATOP);
                    supportActionBar.setHomeAsUpIndicator(drawable);
                }
            }
            this.current_tab = 0;
            addFragmentToAdapter(apptentiveBaseFragment2, apptentiveBaseFragment2.getTitle());
            this.viewPager = (ViewPager) findViewById(R.id.apptentive_vp);
            this.viewPager.setAdapter(this.viewPager_Adapter);
            this.viewPager.addOnPageChangeListener(/* anonymous class already generated */);
            getWindow().setSoftInputMode(16);
        }
        setContentView(R.layout.apptentive_viewactivity);
        this.toolbar = (Toolbar) findViewById(R.id.apptentive_toolbar);
        setSupportActionBar(this.toolbar);
        toolbar = this.toolbar;
        if (isShownAsModalDialog) {
            z = true;
        } else {
            z = false;
        }
        toolbar.setPadding(0, getToolbarHeightAdjustment(z), 0, 0);
        supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            toolbarNavigationIconResourceId = apptentiveBaseFragment2.getToolbarNavigationIconResourceId(getTheme());
            if (toolbarNavigationIconResourceId != 0) {
                drawable = ResourcesCompat.getDrawable(getResources(), toolbarNavigationIconResourceId, getTheme());
                drawable.setColorFilter(Util.getThemeColor(ApptentiveInternal.getInstance().getApptentiveToolbarTheme(), R.attr.colorControlNormal), Mode.SRC_ATOP);
                supportActionBar.setHomeAsUpIndicator(drawable);
            }
        }
        this.current_tab = 0;
        addFragmentToAdapter(apptentiveBaseFragment2, apptentiveBaseFragment2.getTitle());
        this.viewPager = (ViewPager) findViewById(R.id.apptentive_vp);
        this.viewPager.setAdapter(this.viewPager_Adapter);
        this.viewPager.addOnPageChangeListener(/* anonymous class already generated */);
        getWindow().setSoftInputMode(16);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                exitActivity(false);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void exitActivity(boolean z) {
        ApptentiveBaseFragment apptentiveBaseFragment = (ApptentiveBaseFragment) this.viewPager_Adapter.getItem(this.viewPager.getCurrentItem());
        if (apptentiveBaseFragment != null && apptentiveBaseFragment.isVisible()) {
            if (!apptentiveBaseFragment.onBackPressed(z)) {
                FragmentManager childFragmentManager = apptentiveBaseFragment.getChildFragmentManager();
                if (childFragmentManager.getBackStackEntryCount() > 0) {
                    childFragmentManager.popBackStack();
                }
            } else {
                return;
            }
        }
        super.onBackPressed();
        startLauncherActivityIfRoot();
    }

    public void onBackPressed() {
        exitActivity(true);
    }

    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.apptentive_slide_down_out);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        overridePendingTransition(R.anim.apptentive_slide_up_in, 0);
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString(FRAGMENT_TAG, this.viewPager_Adapter.getFragmentTag(this.current_tab));
        super.onSaveInstanceState(bundle);
    }

    public void onFragmentTransition(ApptentiveBaseFragment apptentiveBaseFragment) {
        if (apptentiveBaseFragment != null) {
            int count = this.viewPager_Adapter.getCount();
            int i = 0;
            while (i < count) {
                if (apptentiveBaseFragment != ((ApptentiveBaseFragment) this.viewPager_Adapter.getItem(i))) {
                    i++;
                } else if (count == 1) {
                    finish();
                    return;
                } else {
                    apptentiveBaseFragment.dismiss();
                    this.viewPager_Adapter.removeItem(i);
                    this.viewPager_Adapter.notifyDataSetChanged();
                    return;
                }
            }
        }
    }

    private void applyApptentiveTheme(boolean z) {
        try {
            ApptentiveInternal.getInstance().updateApptentiveInteractionTheme(getTheme(), this);
            if (z) {
                getTheme().applyStyle(R.style.ApptentiveBaseDialogTheme, true);
                setStatusBarColor();
            }
            if (VERSION.SDK_INT >= 21) {
                setTaskDescription(new TaskDescription(null, null, Util.getThemeColor(getTheme(), R.attr.colorPrimary)));
            }
        } catch (Throwable e) {
            ApptentiveLog.e("Error apply Apptentive Theme.", e, new Object[0]);
        }
    }

    private void addFragmentToAdapter(ApptentiveBaseFragment apptentiveBaseFragment, String str) {
        if (this.viewPager_Adapter == null) {
            this.viewPager_Adapter = new ApptentiveViewPagerAdapter(getSupportFragmentManager());
        }
        this.viewPager_Adapter.add(apptentiveBaseFragment, str);
        this.viewPager_Adapter.notifyDataSetChanged();
    }

    private void startLauncherActivityIfRoot() {
        if (isTaskRoot()) {
            startActivity(IntentCompat.makeRestartActivityTask(getPackageManager().getLaunchIntentForPackage(getPackageName()).getComponent()));
        }
    }

    private int getToolbarHeightAdjustment(boolean z) {
        int i = 1;
        int i2 = 0;
        if (VERSION.SDK_INT >= 19) {
            TypedArray obtainStyledAttributes = getTheme().obtainStyledAttributes(new int[]{16843759});
            boolean z2 = false;
            try {
                z2 = obtainStyledAttributes.getBoolean(z2, false);
                if ((getWindow().getAttributes().flags & 67108864) == 0) {
                    boolean z3 = z2;
                }
                if (i != 0) {
                    if (z) {
                        i = getResources().getIdentifier("status_bar_height", "dimen", "android");
                        if (i > 0) {
                            i2 = getResources().getDimensionPixelSize(i);
                        }
                    }
                    this.decorView = getWindow().getDecorView();
                    this.contentView = this.decorView.findViewById(16908290);
                    this.decorView.getViewTreeObserver().addOnGlobalLayoutListener(this.keyboardPresencelLayoutListener);
                }
            } finally {
                obtainStyledAttributes.recycle();
            }
        }
        return i2;
    }

    private void setStatusBarColor() {
        if (VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Util.alphaMixColors(ApptentiveInternal.getInstance().getDefaultStatusbarColor(), ContextCompat.getColor(this, R.color.apptentive_activity_frame_dark)));
        }
    }
}
