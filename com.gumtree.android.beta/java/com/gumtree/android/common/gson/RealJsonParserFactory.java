package com.gumtree.android.common.gson;

import com.gumtree.android.UrlMatcher;
import com.gumtree.android.ads.parser.AdParser;
import com.gumtree.android.ads.parser.AdsListParser;
import com.gumtree.android.ads.parser.ManageAdParser;
import com.gumtree.android.ads.parser.ManageAdsListParser;
import com.gumtree.android.category.parser.CategoryTreeParser;
import com.gumtree.android.logging.Timber;
import com.gumtree.android.message_box.ConversationsIntentService;
import com.gumtree.android.metadata.parser.MetadataParser;
import com.gumtree.android.metadata.parser.SearchParser;

public class RealJsonParserFactory implements JsonParserFactory {
    private final UrlMatcher urlMatcher;

    public RealJsonParserFactory(String str) {
        this.urlMatcher = new UrlMatcher(str);
        this.urlMatcher.addUrl(str + "/api/ads.json", Integer.valueOf(4));
        this.urlMatcher.addUrl(str + "/api/ads/%s.json", Integer.valueOf(5));
        this.urlMatcher.addUrl(str + "/api/categories.json", Integer.valueOf(0));
        this.urlMatcher.addUrl(str + "/api/locations/all.json", Integer.valueOf(2));
        this.urlMatcher.addUrl(str + "/api/locations/%s.json", Integer.valueOf(2));
        this.urlMatcher.addUrl(str + "/api/categories/%s.json", Integer.valueOf(0));
        this.urlMatcher.addUrl(str + "/api/locations/top-locations.json", Integer.valueOf(2));
        this.urlMatcher.addUrl(str + "/api/ads/search-metadata/%s.json", Integer.valueOf(26));
        this.urlMatcher.addUrl(str + "/api/ads/metadata/%s.json", Integer.valueOf(28));
        this.urlMatcher.addUrl(str + "/api/users/*/ads.json", Integer.valueOf(UrlMatcher.URI_CODE_MANAGED_ADS));
        this.urlMatcher.addUrl(str + "/api/users/*/ads/%s.json", Integer.valueOf(UrlMatcher.URI_CODE_MANAGED_AD));
        this.urlMatcher.addUrl(str + "/api/features/ad.json", Integer.valueOf(UrlMatcher.URI_CODE_FEATURES));
    }

    public JsonParser getJsonParserFor(String str, String str2) {
        if (this.urlMatcher.matchManageAdsList(str)) {
            return new ManageAdsListParser(str, str2);
        }
        if (this.urlMatcher.matchManageAd(str)) {
            return new ManageAdParser(str, str2);
        }
        switch (this.urlMatcher.getUriCode(str)) {
            case ConversationsIntentService.DEFAULT_PAGE /*0*/:
            case HighlightView.GROW_NONE /*1*/:
                return new CategoryTreeParser(str, str2);
            case HighlightView.GROW_RIGHT_EDGE /*4*/:
                return new AdsListParser(str, str2);
            case Timber.WARN /*5*/:
                return new AdParser();
            case UrlMatcher.URI_CODE_SEARCH_METADATA /*26*/:
                return new SearchParser(str, this.urlMatcher.getIdFromUrl(str));
            case UrlMatcher.URI_CODE_POST_METADATA /*28*/:
                return new MetadataParser(str, this.urlMatcher.getIdFromUrl(str));
            default:
                throw new IllegalStateException("Parser not found to handle service request " + str);
        }
    }
}
