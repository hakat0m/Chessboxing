package com.gumtree.android.common.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import com.apptentive.android.sdk.BuildConfig;
import com.apptentive.android.sdk.R;
import com.gumtree.DevelopmentFlags;
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.ad.search.keyword.SearchKeywordActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.drawer.NavigationDrawerItem;
import com.gumtree.android.common.drawer.NavigationItem;
import com.gumtree.android.common.location.AppLocation;
import com.gumtree.android.common.location.PreferenceBasedRadiusDAO;
import com.gumtree.android.common.search.Search;
import com.gumtree.android.common.search.Search.SearchType;
import com.gumtree.android.common.utils.crashlytics.CrashlyticsHelper;
import com.gumtree.android.common.views.ThemeUtils;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.GumtreeBadgeCounterManagerEvent;
import com.gumtree.android.favourites.FavouritesActivity;
import com.gumtree.android.help.HelpActivity;
import com.gumtree.android.home.HomeActivity;
import com.gumtree.android.logging.Timber;
import com.gumtree.android.managead.ManageAdActivity;
import com.gumtree.android.manageads.ManageAdsActivity;
import com.gumtree.android.message_box.InboxActivity;
import com.gumtree.android.notifications.IBadgeCounterManager;
import com.gumtree.android.post_ad.summary.PostAdSummaryActivity;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.PostAdActivity;
import com.gumtree.android.postad.services.converter.PaymentConverter;
import com.gumtree.android.savedsearches.SavedSearchesActivity;
import com.gumtree.android.settings.SettingsActivity;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;
import com.squareup.otto.Subscribe;
import java.util.HashMap;
import javax.inject.Inject;

public abstract class NavigationActivity extends BaseActivity implements SearchableMenu {
    private Context context;
    @Inject
    IBadgeCounterManager counterManager;
    private Drawer drawer;
    private HashMap<NavigationItem, IDrawerItem> drawerItemHashMap;
    private Typeface drawerItemTypeface;
    @Inject
    EventBus eventBus;
    private Object eventBusReceiver;
    private int iconColour;
    private int selectedTextColour;

    /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$gumtree$android$common$drawer$NavigationItem = new int[NavigationItem.values().length];

        static {
            try {
                $SwitchMap$com$gumtree$android$common$drawer$NavigationItem[NavigationItem.HOME.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$gumtree$android$common$drawer$NavigationItem[NavigationItem.SEARCH.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$gumtree$android$common$drawer$NavigationItem[NavigationItem.POST_AD.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$gumtree$android$common$drawer$NavigationItem[NavigationItem.MESSAGES.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$gumtree$android$common$drawer$NavigationItem[NavigationItem.FAVORITES.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$gumtree$android$common$drawer$NavigationItem[NavigationItem.SAVED_SEARCHES.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$gumtree$android$common$drawer$NavigationItem[NavigationItem.MANAGE_ADS.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$gumtree$android$common$drawer$NavigationItem[NavigationItem.SETTINGS.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$gumtree$android$common$drawer$NavigationItem[NavigationItem.HELP.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
        }
    }

    protected abstract NavigationItem getNavigationItem();

    protected abstract String getTrackingView();

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        GumtreeApplication.component().inject(this);
        this.context = this;
        this.iconColour = ThemeUtils.getColor(this.context, 2130772474);
        this.selectedTextColour = ThemeUtils.getColor(this.context, 2130772477);
        this.drawerItemTypeface = Typeface.create("sans-serif", 1);
        this.eventBusReceiver = new Object() {
            @Subscribe
            public void onGumtreeBadgeCounterManagerEvent(GumtreeBadgeCounterManagerEvent gumtreeBadgeCounterManagerEvent) {
                NavigationActivity.this.updateUnreadMessagesBadgeCount(gumtreeBadgeCounterManagerEvent.getNumUnreadConversations());
            }
        };
    }

    protected void onResume() {
        super.onResume();
        this.eventBus.register(this.eventBusReceiver);
        if (this.drawer != null) {
            updateDrawer();
        }
    }

    private void updateDrawer() {
        updateLocation();
        updateSelectedItem();
        updateUnreadMessagesBadgeCount(this.counterManager.getNumUnreadConversations());
    }

    protected Drawer getDrawer() {
        return this.drawer;
    }

    private void updateLocation() {
        AppLocation globalBuyerLocation = ((GumtreeApplication) this.context.getApplicationContext()).getGlobalBuyerLocation();
        NavigationDrawerItem navigationDrawerItem = (NavigationDrawerItem) ((NavigationDrawerItem) new NavigationDrawerItem(NavigationItem.SEARCH, this.iconColour, this.selectedTextColour, this.drawerItemTypeface).withDescription(globalBuyerLocation.getDisplayText(false))).withIdentifier(((IDrawerItem) this.drawerItemHashMap.get(NavigationItem.SEARCH)).getIdentifier());
        this.drawer.updateItem(navigationDrawerItem);
        this.drawerItemHashMap.put(NavigationItem.SEARCH, navigationDrawerItem);
    }

    private void updateSelectedItem() {
        this.drawer.setSelection((IDrawerItem) this.drawerItemHashMap.get(getNavigationItem()), false);
    }

    protected void onPause() {
        super.onPause();
        this.eventBus.unregister(this.eventBusReceiver);
    }

    protected void onSaveInstanceState(Bundle bundle) {
        if (this.drawer != null) {
            bundle = this.drawer.saveInstanceState(bundle);
        }
        super.onSaveInstanceState(bundle);
    }

    public void onBackPressed() {
        if (this.drawer == null || !this.drawer.isDrawerOpen()) {
            try {
                super.onBackPressed();
                return;
            } catch (Throwable e) {
                Log.e("NavigationActivity", "Error when doing super.onBackPressed()", e);
                CrashlyticsHelper.getInstance().catchGumtreeException(e);
                supportFinishAfterTransition();
                return;
            }
        }
        this.drawer.closeDrawer();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    protected void setContentView(int i, Bundle bundle) {
        super.setContentView(i);
        if (hasDrawer()) {
            setupDrawer(bundle);
        }
    }

    protected boolean hasDrawer() {
        return true;
    }

    protected void onMenuClose() {
    }

    public void onSearchInvoked() {
        Search.invoke(this, getDefaultSearch());
    }

    public boolean onSearchRequested() {
        onSearchInvoked();
        return false;
    }

    protected Search getDefaultSearch() {
        AppLocation globalBuyerLocation = ((GumtreeApplication) getApplication()).getGlobalBuyerLocation();
        Search create = Search.create(getApplicationContext(), SearchType.SEARCH);
        create.addCategory(PaymentConverter.DEFAULT_PAYMENT_METHOD_ID, getString(2131165816));
        create.addLocation(globalBuyerLocation);
        create.addRadius(getApplicationContext(), PreferenceBasedRadiusDAO.get(getApplicationContext()), globalBuyerLocation);
        return create;
    }

    private void setupDrawer(Bundle bundle) {
        createDrawerItems();
        this.drawer = new DrawerBuilder().withActivity(this).withToolbar(getToolbar()).withTranslucentStatusBar(true).withSavedInstance(bundle).addDrawerItems((IDrawerItem) this.drawerItemHashMap.get(NavigationItem.HOME), (IDrawerItem) this.drawerItemHashMap.get(NavigationItem.SEARCH), (IDrawerItem) this.drawerItemHashMap.get(NavigationItem.POST_AD), (IDrawerItem) this.drawerItemHashMap.get(NavigationItem.MESSAGES), (IDrawerItem) this.drawerItemHashMap.get(NavigationItem.FAVORITES), (IDrawerItem) this.drawerItemHashMap.get(NavigationItem.SAVED_SEARCHES), (IDrawerItem) this.drawerItemHashMap.get(NavigationItem.MANAGE_ADS), (IDrawerItem) this.drawerItemHashMap.get(NavigationItem.DIVIDER), (IDrawerItem) this.drawerItemHashMap.get(NavigationItem.SETTINGS), (IDrawerItem) this.drawerItemHashMap.get(NavigationItem.HELP)).withHasStableIds(true).withOnDrawerItemClickListener(NavigationActivity$$Lambda$1.lambdaFactory$(this)).build();
    }

    /* synthetic */ boolean lambda$setupDrawer$0(View view, int i, IDrawerItem iDrawerItem) {
        NavigationItem navigationItem = (NavigationItem) iDrawerItem.getTag();
        switch (AnonymousClass2.$SwitchMap$com$gumtree$android$common$drawer$NavigationItem[navigationItem.ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                Intent intent = new Intent(this.context, HomeActivity.class);
                intent.addFlags(67108864);
                startActivityWithoutAnimation(intent);
                this.drawer.closeDrawer();
                break;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                if (DevelopmentFlags.getInstance().isNewSearchKeywordEnabled()) {
                    startActivity(SearchKeywordActivity.createLaunchIntent(this, null));
                } else {
                    onSearchInvoked();
                }
                this.drawer.closeDrawer();
                Track.sendGAEvent(getTrackingView(), "NavDrawerSearchBegin", BuildConfig.FLAVOR);
                break;
            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                if (DevelopmentFlags.getInstance().isNewPostAdEnabled()) {
                    startActivityWithoutAnimation(PostAdActivity.createIntent(this.context, DraftAd.NEW_AD_ID));
                } else {
                    startActivityWithoutAnimation(PostAdSummaryActivity.createIntent(this.context, DraftAd.NEW_AD_ID, true, false));
                }
                Track.eventPostAdBeginFromMenu();
                this.drawer.closeDrawer();
                break;
            case HighlightView.GROW_RIGHT_EDGE /*4*/:
                startActivityWithoutAnimation(InboxActivity.createIntent(this.context, InboxActivity.NAV_DRAWER));
                this.drawer.closeDrawer();
                break;
            case Timber.WARN /*5*/:
                startActivityWithoutAnimation(FavouritesActivity.createIntent(this.context));
                this.drawer.closeDrawer();
                break;
            case Timber.ERROR /*6*/:
                startActivityWithoutAnimation(SavedSearchesActivity.createIntent(this.context));
                this.drawer.closeDrawer();
                break;
            case Timber.ASSERT /*7*/:
                if (DevelopmentFlags.getInstance().isNewManageAdsEnabled()) {
                    startActivityWithoutAnimation(ManageAdsActivity.createIntent(this));
                } else {
                    startActivityWithoutAnimation(ManageAdActivity.createIntent());
                }
                this.drawer.closeDrawer();
                break;
            case HighlightView.GROW_TOP_EDGE /*8*/:
                startActivityWithoutAnimation(SettingsActivity.createIntent(this.context));
                this.drawer.closeDrawer();
                break;
            case R.styleable.TextInputLayout_counterOverflowTextAppearance /*9*/:
                startActivityWithoutAnimation(new Intent(this.context, HelpActivity.class));
                this.drawer.closeDrawer();
                break;
            default:
                Log.e(getClass().getSimpleName(), "case not supported " + navigationItem);
                break;
        }
        return false;
    }

    private void createDrawerItems() {
        this.drawerItemHashMap = new HashMap();
        this.drawerItemHashMap.put(NavigationItem.HOME, new NavigationDrawerItem(NavigationItem.HOME, this.iconColour, this.selectedTextColour, this.drawerItemTypeface));
        this.drawerItemHashMap.put(NavigationItem.SEARCH, new NavigationDrawerItem(NavigationItem.SEARCH, this.iconColour, this.selectedTextColour, this.drawerItemTypeface));
        this.drawerItemHashMap.put(NavigationItem.POST_AD, new NavigationDrawerItem(NavigationItem.POST_AD, this.iconColour, this.selectedTextColour, this.drawerItemTypeface));
        this.drawerItemHashMap.put(NavigationItem.MESSAGES, new NavigationDrawerItem(NavigationItem.MESSAGES, this.iconColour, this.selectedTextColour, this.drawerItemTypeface).withBadgeStyle(new BadgeStyle().withTextColor(-1).withColor(this.selectedTextColour)));
        this.drawerItemHashMap.put(NavigationItem.FAVORITES, new NavigationDrawerItem(NavigationItem.FAVORITES, this.iconColour, this.selectedTextColour, this.drawerItemTypeface));
        this.drawerItemHashMap.put(NavigationItem.SAVED_SEARCHES, new NavigationDrawerItem(NavigationItem.SAVED_SEARCHES, this.iconColour, this.selectedTextColour, this.drawerItemTypeface));
        this.drawerItemHashMap.put(NavigationItem.MANAGE_ADS, new NavigationDrawerItem(NavigationItem.MANAGE_ADS, this.iconColour, this.selectedTextColour, this.drawerItemTypeface));
        this.drawerItemHashMap.put(NavigationItem.DIVIDER, new DividerDrawerItem());
        this.drawerItemHashMap.put(NavigationItem.SETTINGS, new NavigationDrawerItem(NavigationItem.SETTINGS, this.iconColour, this.selectedTextColour, this.drawerItemTypeface));
        this.drawerItemHashMap.put(NavigationItem.HELP, new NavigationDrawerItem(NavigationItem.HELP, this.iconColour, this.selectedTextColour, this.drawerItemTypeface));
    }

    private void startActivityWithoutAnimation(Intent intent) {
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void updateUnreadMessagesBadgeCount(int i) {
        if (this.drawer != null) {
            StringHolder stringHolder;
            IDrawerItem iDrawerItem = (IDrawerItem) this.drawerItemHashMap.get(NavigationItem.MESSAGES);
            if (i > 0) {
                stringHolder = new StringHolder(String.valueOf(i));
            } else {
                stringHolder = null;
            }
            this.drawer.updateBadge(iDrawerItem.getIdentifier(), stringHolder);
        }
    }
}
