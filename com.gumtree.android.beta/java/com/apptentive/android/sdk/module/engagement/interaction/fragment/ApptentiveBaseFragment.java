package com.apptentive.android.sdk.module.engagement.interaction.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.Theme;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentHostCallback;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.apptentive.android.sdk.ApptentiveInternal;
import com.apptentive.android.sdk.ApptentiveLog;
import com.apptentive.android.sdk.R;
import com.apptentive.android.sdk.module.engagement.EngagementModule;
import com.apptentive.android.sdk.module.engagement.interaction.InteractionManager.InteractionUpdateListener;
import com.apptentive.android.sdk.module.engagement.interaction.model.Interaction;
import com.apptentive.android.sdk.module.engagement.interaction.model.Interaction.Factory;
import com.apptentive.android.sdk.util.Util;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class ApptentiveBaseFragment<T extends Interaction> extends DialogFragment implements InteractionUpdateListener {
    protected static final String EVENT_NAME_LAUNCH = "launch";
    private static final String HAS_LAUNCHED = "has_launched";
    private boolean bShownAsModal;
    private Class fragmentImplClass;
    private List fragmentMenuItems = null;
    private final String fragmentName = getClass().getSimpleName();
    protected boolean hasLaunched;
    private Field hostField;
    protected T interaction;
    private boolean isChangingConfigurations;
    private OnFragmentTransitionListener onTransitionListener;
    private FragmentManager retainedChildFragmentManager;
    protected String sectionTitle;
    private Toolbar toolbar = null;
    private int toolbarLayoutId = 0;

    public interface OnFragmentTransitionListener {
        void onFragmentTransition(ApptentiveBaseFragment apptentiveBaseFragment);
    }

    public ApptentiveBaseFragment() {
        try {
            this.fragmentImplClass = Class.forName("android.support.v4.app.FragmentManagerImpl");
            this.hostField = this.fragmentImplClass.getDeclaredField("mHost");
            this.hostField.setAccessible(true);
        } catch (Throwable e) {
            throw new RuntimeException("FragmentManagerImpl is renamed due to the change of Android SDK, this workaround doesn't work any more. See the issue at https://code.google.com/p/android/issues/detail?id=74222", e);
        } catch (Throwable e2) {
            throw new RuntimeException("FragmentManagerImpl.mHost is found due to the change of Android SDK, this workaround doesn't work any more. See the issue at https://code.google.com/p/android/issues/detail?id=74222", e2);
        }
    }

    public FragmentManager getRetainedChildFragmentManager() {
        if (this.retainedChildFragmentManager == null) {
            this.retainedChildFragmentManager = getChildFragmentManager();
        }
        return this.retainedChildFragmentManager;
    }

    public String getFragmentName() {
        return this.fragmentName;
    }

    public boolean isChangingConfigurations() {
        return this.isChangingConfigurations;
    }

    public Context getContext() {
        Context context = super.getContext();
        return context != null ? context : ApptentiveInternal.getInstance().getApplicationContext();
    }

    public void setOnTransitionListener(OnFragmentTransitionListener onFragmentTransitionListener) {
        this.onTransitionListener = onFragmentTransitionListener;
    }

    public void transit() {
        if (this.onTransitionListener != null) {
            this.onTransitionListener.onFragmentTransition(this);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(HAS_LAUNCHED, this.hasLaunched);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (this.retainedChildFragmentManager != null) {
            try {
                Field declaredField = Fragment.class.getDeclaredField("mChildFragmentManager");
                declaredField.setAccessible(true);
                declaredField.set(this, this.retainedChildFragmentManager);
                updateHosts(getFragmentManager(), (FragmentHostCallback) this.hostField.get(getFragmentManager()));
                return;
            } catch (Throwable e) {
                ApptentiveLog.w(e.getMessage(), e, new Object[0]);
                return;
            }
        }
        this.retainedChildFragmentManager = getChildFragmentManager();
    }

    private void updateHosts(FragmentManager fragmentManager, FragmentHostCallback fragmentHostCallback) throws IllegalAccessException {
        if (fragmentManager != null) {
            replaceFragmentManagerHost(fragmentManager, fragmentHostCallback);
        }
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null) {
                    try {
                        Field declaredField = Fragment.class.getDeclaredField("mHost");
                        declaredField.setAccessible(true);
                        declaredField.set(fragment, fragmentHostCallback);
                    } catch (Throwable e) {
                        ApptentiveLog.w(e.getMessage(), e, new Object[0]);
                    }
                    if (fragment.getChildFragmentManager() != null) {
                        updateHosts(fragment.getChildFragmentManager(), fragmentHostCallback);
                    }
                }
            }
        }
    }

    private void replaceFragmentManagerHost(FragmentManager fragmentManager, FragmentHostCallback fragmentHostCallback) throws IllegalAccessException {
        if (fragmentHostCallback != null) {
            this.hostField.set(fragmentManager, fragmentHostCallback);
        }
    }

    public void onDetach() {
        super.onDetach();
        try {
            Field declaredField = Fragment.class.getDeclaredField("mChildFragmentManager");
            declaredField.setAccessible(true);
            declaredField.set(this, null);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } catch (Throwable e2) {
            throw new RuntimeException(e2);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.toolbarLayoutId = arguments.getInt("toolbarLayoutId");
            this.bShownAsModal = arguments.getBoolean("showAsModal", false);
            Object string = arguments.getString("interaction");
            if (!TextUtils.isEmpty(string)) {
                this.interaction = Factory.parseInteraction(string);
            }
        }
        if (!(this.bShownAsModal || this.interaction == null)) {
            this.sectionTitle = this.interaction.getTitle();
        }
        if (!(this.toolbarLayoutId == 0 || getMenuResourceId() == 0)) {
            setHasOptionsMenu(true);
        }
        if (bundle != null) {
            this.hasLaunched = bundle.getBoolean(HAS_LAUNCHED);
        }
        if (!this.hasLaunched) {
            this.hasLaunched = true;
            sendLaunchEvent(getActivity());
        }
    }

    protected void sendLaunchEvent(Activity activity) {
        if (this.interaction != null) {
            EngagementModule.engageInternal((Context) activity, this.interaction, EVENT_NAME_LAUNCH);
        }
    }

    public void onViewCreated(View view, Bundle bundle) {
        int i = 0;
        super.onViewCreated(view, bundle);
        if (this.toolbarLayoutId != 0) {
            this.toolbar = (Toolbar) getActivity().findViewById(this.toolbarLayoutId);
            if (getMenuResourceId() != 0 && this.toolbar != null) {
                Menu menu = this.toolbar.getMenu();
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 < menu.size(); i2++) {
                    arrayList.add(Integer.valueOf(menu.getItem(i2).getItemId()));
                }
                this.toolbar.inflateMenu(getMenuResourceId());
                attachFragmentMenuListeners(this.toolbar.getMenu());
                Menu menu2 = this.toolbar.getMenu();
                this.fragmentMenuItems = new ArrayList();
                int themeColor = Util.getThemeColor(ApptentiveInternal.getInstance().getApptentiveToolbarTheme(), R.attr.colorControlNormal);
                while (i < menu2.size()) {
                    int itemId = menu2.getItem(i).getItemId();
                    if (!arrayList.contains(Integer.valueOf(itemId))) {
                        this.fragmentMenuItems.add(Integer.valueOf(itemId));
                        Drawable icon = menu2.getItem(i).getIcon();
                        if (icon != null) {
                            icon.mutate();
                            icon.setColorFilter(themeColor, Mode.SRC_ATOP);
                        }
                    }
                    i++;
                }
            }
        }
    }

    public void onResume() {
        ApptentiveInternal.getInstance().addInteractionUpdateListener(this);
        super.onResume();
    }

    public void onPause() {
        ApptentiveInternal.getInstance().removeInteractionUpdateListener(this);
        super.onPause();
    }

    public void onInteractionUpdated(boolean z) {
    }

    public void onStop() {
        super.onStop();
        if (VERSION.SDK_INT >= 11 && getActivity() != null) {
            this.isChangingConfigurations = getActivity().isChangingConfigurations();
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (this.toolbar != null && this.fragmentMenuItems != null) {
            Menu menu = this.toolbar.getMenu();
            for (Integer intValue : this.fragmentMenuItems) {
                menu.removeItem(intValue.intValue());
            }
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(getMenuResourceId(), menu);
        int themeColor = Util.getThemeColor(ApptentiveInternal.getInstance().getApptentiveToolbarTheme(), R.attr.colorControlNormal);
        for (int i = 0; i < menu.size(); i++) {
            Drawable icon = menu.getItem(i).getIcon();
            if (icon != null) {
                icon.mutate();
                icon.setColorFilter(themeColor, Mode.SRC_ATOP);
            }
        }
        attachFragmentMenuListeners(menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    protected Bundle getBundle() {
        Bundle bundle = new Bundle();
        if (this.toolbarLayoutId != 0) {
            bundle.putInt("toolbarLayoutId", this.toolbarLayoutId);
        }
        return bundle;
    }

    protected int getMenuResourceId() {
        return 0;
    }

    public int getToolbarNavigationIconResourceId(Theme theme) {
        return 0;
    }

    protected void attachFragmentMenuListeners(Menu menu) {
    }

    protected void updateMenuVisibility() {
    }

    public Activity getActivity(Fragment fragment) {
        if (fragment == null) {
            return null;
        }
        while (fragment.getParentFragment() != null) {
            fragment = fragment.getParentFragment();
        }
        return fragment.getActivity();
    }

    public boolean isShownAsModalDialog() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            return arguments.getBoolean("showAsModal", false);
        }
        return false;
    }

    public String getTitle() {
        return this.sectionTitle;
    }

    public void showToolbarElevation(boolean z) {
        if (VERSION.SDK_INT >= 21) {
            showToolbarElevationLollipop(z);
        } else {
            showToolbarElevationPreLollipop(z);
        }
    }

    public boolean onBackPressed(boolean z) {
        List<Fragment> fragments = getRetainedChildFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible()) {
                    FragmentManager childFragmentManager = fragment.getChildFragmentManager();
                    if (childFragmentManager.getBackStackEntryCount() > 0) {
                        childFragmentManager.popBackStack();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @TargetApi(21)
    private void showToolbarElevationLollipop(boolean z) {
        if (!isAdded()) {
            return;
        }
        if (this.toolbar == null) {
            ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (supportActionBar == null) {
                return;
            }
            if (z) {
                supportActionBar.setElevation(Util.dipsToPixels(getContext(), 4.0f));
            } else {
                supportActionBar.setElevation(0.0f);
            }
        } else if (z) {
            this.toolbar.setElevation(Util.dipsToPixels(getContext(), 4.0f));
        } else {
            this.toolbar.setElevation(0.0f);
        }
    }

    private void showToolbarElevationPreLollipop(boolean z) {
        if (isAdded()) {
            FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.apptentive_vp_container);
            if (frameLayout == null) {
                return;
            }
            if (z) {
                frameLayout.setForeground(ContextCompat.getDrawable(getContext(), R.drawable.apptentive_actionbar_compat_shadow));
            } else {
                frameLayout.setForeground(new ColorDrawable(0));
            }
        }
    }

    public static void replaceFragment(FragmentManager fragmentManager, int i, Fragment fragment, String str, String str2, boolean z, boolean z2) {
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        if (z) {
            beginTransaction.setCustomAnimations(R.anim.apptentive_slide_right_in, R.anim.apptentive_slide_left_out, R.anim.apptentive_slide_left_in, R.anim.apptentive_slide_right_out);
        }
        beginTransaction.replace(i, fragment, str);
        if (!TextUtils.isEmpty(str2)) {
            beginTransaction.addToBackStack(str2);
        }
        beginTransaction.commit();
        if (z2) {
            fragmentManager.executePendingTransactions();
        }
    }

    public static void popBackStack(FragmentManager fragmentManager, String str) {
        fragmentManager.popBackStack(str, 1);
    }

    public static void popBackStackImmediate(FragmentManager fragmentManager, String str) {
        fragmentManager.popBackStackImmediate(str, 1);
    }

    public static void removeFragment(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager.beginTransaction().remove(fragment).commit();
    }
}
