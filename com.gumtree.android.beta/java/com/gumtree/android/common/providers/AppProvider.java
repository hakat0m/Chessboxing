package com.gumtree.android.common.providers;

import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import com.gumtree.android.UrlMatcher;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.utils.crashlytics.CrashlyticsHelper;
import com.gumtree.android.logging.Timber;
import com.gumtree.android.message_box.ConversationsIntentService;
import com.gumtree.android.model.Ads;
import com.gumtree.android.model.AttributesForVip;
import com.gumtree.android.model.Categories;
import com.gumtree.android.model.Configurations;
import com.gumtree.android.model.Locations;
import com.gumtree.android.model.ManagedAds;
import com.gumtree.android.model.Messages;
import com.gumtree.android.model.PostAds;
import com.gumtree.android.model.SavedSearches;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;
import novoda.lib.sqliteprovider.provider.SQLiteContentProviderImpl;
import novoda.lib.sqliteprovider.util.UriUtils;
import uk.co.senab.photoview.IPhotoView;

public class AppProvider extends SQLiteContentProviderImpl {
    public static final String AUTHORITY = "com.gumtree.android.beta";
    public static final UriMatcher URI_MATCHER = new UriMatcher(-1);

    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case ConversationsIntentService.DEFAULT_PAGE /*0*/:
                return Categories.DIR_CONTENT_TYPE;
            case HighlightView.GROW_NONE /*1*/:
                return Categories.ITEM_CONTENT_TYPE;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                return Locations.DIR_CONTENT_TYPE;
            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                return Locations.ITEM_CONTENT_TYPE;
            case HighlightView.GROW_RIGHT_EDGE /*4*/:
            case Timber.ERROR /*6*/:
                return Ads.DIR_CONTENT_TYPE;
            case Timber.WARN /*5*/:
                return Ads.ITEM_CONTENT_TYPE;
            case UrlMatcher.URI_CODE_POST_METADATA /*28*/:
                return "vnd.android.cursor.item/vnd.com.gumtree.locations.city";
            case UrlMatcher.URI_CODE_POST_AD /*199*/:
                return PostAds.ITEM_CONTENT_TYPE;
            case UrlMatcher.URI_CODE_MANAGED_ADS_VIEW /*202*/:
                return ManagedAds.ITEM_CONTENT_TYPE;
            case UrlMatcher.URI_CODE_CONFIGURATIONS /*210*/:
                return Configurations.ITEM_CONTENT_TYPE;
            case UrlMatcher.URI_CODE_SAVED_SEARCHES /*500*/:
            case UrlMatcher.URI_CODE_SAVED_SEARCHES_VIEW /*501*/:
                return SavedSearches.ITEM_CONTENT_TYPE;
            case UrlMatcher.URI_CODE_NEW_SAVED_SEARCHES /*502*/:
                return "vnd.android.cursor.dir/vnd.com.gumtree.newsavedsearches";
            case UrlMatcher.URI_CODE_CONVERSATIONS /*503*/:
            case UrlMatcher.URI_CODE_CONVERSATIONS_VIEW /*504*/:
                return "vnd.android.cursor.dir/vnd.com.gumtree.conversations";
            case UrlMatcher.URI_CODE_MESSAGES /*505*/:
                return Messages.ITEM_CONTENT_TYPE;
            default:
                throw new IllegalStateException("uri not known: " + uri);
        }
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        try {
            return super.query(uri, strArr, str, strArr2, str2);
        } catch (Throwable e) {
            CrashlyticsHelper.getInstance().catchGumtreeException(e);
            throw e;
        }
    }

    public int delete(Uri uri, String str, String[] strArr) {
        long currentTimeMillis = System.currentTimeMillis();
        String itemDirID = UriUtils.getItemDirID(uri);
        int delete = super.delete(uri, str, strArr);
        Track.dbDeleteEvent(itemDirID, System.currentTimeMillis() - currentTimeMillis);
        return delete;
    }

    public Uri getNotificationUri(Uri uri) {
        Uri uri2;
        switch (URI_MATCHER.match(uri)) {
            case Timber.ERROR /*6*/:
                uri2 = Ads.URI;
                break;
            case UrlMatcher.URI_CODE_ATTR_VIP_DISPLAY /*109*/:
                uri2 = AttributesForVip.URI;
                break;
            case UrlMatcher.URI_CODE_MANAGED_ADS_VIEW /*202*/:
                uri2 = ManagedAds.URI;
                break;
            case UrlMatcher.URI_CODE_SAVED_SEARCHES_VIEW /*501*/:
                uri2 = SavedSearches.URI;
                break;
            case UrlMatcher.URI_CODE_CONVERSATIONS_VIEW /*504*/:
                uri2 = Messages.URI;
                break;
            default:
                return uri;
        }
        return uri2;
    }

    static {
        URI_MATCHER.addURI(AUTHORITY, "categories/", 0);
        URI_MATCHER.addURI(AUTHORITY, "categories/*", 1);
        URI_MATCHER.addURI(AUTHORITY, "locations/", 2);
        URI_MATCHER.addURI(AUTHORITY, "locations/*", 3);
        URI_MATCHER.addURI(AUTHORITY, "ads/", 4);
        URI_MATCHER.addURI(AUTHORITY, "ads/*", 5);
        URI_MATCHER.addURI(AUTHORITY, "post_ads_images/", IPhotoView.DEFAULT_ZOOM_DURATION);
        URI_MATCHER.addURI(AUTHORITY, "ads_view/", 6);
        URI_MATCHER.addURI(AUTHORITY, "managed_ads_view/", UrlMatcher.URI_CODE_MANAGED_ADS_VIEW);
        URI_MATCHER.addURI(AUTHORITY, "saved_ads_view/", 7);
        URI_MATCHER.addURI(AUTHORITY, "service_call_registry/", 99);
        URI_MATCHER.addURI(AUTHORITY, "attributes_for_vip/", UrlMatcher.URI_CODE_ATTR_VIP_DISPLAY);
        URI_MATCHER.addURI(AUTHORITY, "search_metadata/", 26);
        URI_MATCHER.addURI(AUTHORITY, "metadata/", 28);
        URI_MATCHER.addURI(AUTHORITY, "post_ads/*", UrlMatcher.URI_CODE_POST_AD);
        URI_MATCHER.addURI(AUTHORITY, "conversations/", UrlMatcher.URI_CODE_CONVERSATIONS);
        URI_MATCHER.addURI(AUTHORITY, "conversations_view/", UrlMatcher.URI_CODE_CONVERSATIONS_VIEW);
        URI_MATCHER.addURI(AUTHORITY, "messages/", UrlMatcher.URI_CODE_MESSAGES);
        URI_MATCHER.addURI(AUTHORITY, "new_saved_searches/", UrlMatcher.URI_CODE_NEW_SAVED_SEARCHES);
        URI_MATCHER.addURI(AUTHORITY, "saved_searches/", UrlMatcher.URI_CODE_SAVED_SEARCHES);
        URI_MATCHER.addURI(AUTHORITY, "saved_searches_view/", UrlMatcher.URI_CODE_SAVED_SEARCHES_VIEW);
        URI_MATCHER.addURI(AUTHORITY, "configurations/", UrlMatcher.URI_CODE_CONFIGURATIONS);
    }
}
