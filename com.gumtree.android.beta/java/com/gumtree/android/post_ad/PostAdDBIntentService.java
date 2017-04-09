package com.gumtree.android.post_ad;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import com.ebay.classifieds.capi.executor.ResponseException;
import com.ebay.classifieds.capi.executor.Result;
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.ads.CoreAttribute;
import com.gumtree.android.ads.DynamicAttribute;
import com.gumtree.android.ads.PostAd;
import com.gumtree.android.ads.parser.ManageAdParser;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.http.CapiClientManager;
import com.gumtree.android.common.http.CapiUrl;
import com.gumtree.android.common.http.model.ICapiClient;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnPostAdLoadEvent;
import com.gumtree.android.features.parser.FeaturesParser;
import com.gumtree.android.handler.ContentProviderDataStorage;
import com.gumtree.android.metadata.SearchMetadata;
import com.gumtree.android.metadata.parser.MetadataLimitParser;
import com.gumtree.android.metadata.parser.MetadataParser;
import com.gumtree.android.model.Features;
import com.gumtree.android.model.Metadata;
import com.gumtree.android.model.MetadataSupportedValuesOptions;
import com.gumtree.android.model.PostAds;
import com.gumtree.android.model.PostAdsImages;
import com.gumtree.android.post_ad.model.PostAdDBDAO;
import com.gumtree.android.post_ad.model.PostAdData;
import com.gumtree.android.post_ad.summary.PostAdNavigationActivity;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.services.converter.PaymentConverter;
import com.gumtree.android.vip.VIPContactFragment;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import javax.inject.Inject;

public class PostAdDBIntentService extends IntentService {
    private static final String EXTRA_DB_ACTION = "com.gumtree.android.post_ad.db_action";
    private static final String EXTRA_POST_AD_DATA = "com.gumtree.android.post_ad.postAdData";
    @Inject
    BaseAccountManager accountManager;
    @Inject
    CapiClientManager capiClientManager;
    @Inject
    EventBus mEventBus;
    private PostAdDBDAO mPostAdDAO = new PostAdDBDAO();
    private boolean validation;

    /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$gumtree$android$post_ad$PostAdDBIntentService$DBAction = new int[DBAction.values().length];

        static {
            try {
                $SwitchMap$com$gumtree$android$post_ad$PostAdDBIntentService$DBAction[DBAction.RESET_AND_LOAD.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$gumtree$android$post_ad$PostAdDBIntentService$DBAction[DBAction.LOAD.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$gumtree$android$post_ad$PostAdDBIntentService$DBAction[DBAction.SAVE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$gumtree$android$post_ad$PostAdDBIntentService$DBAction[DBAction.RESET.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public enum DBAction {
        LOAD,
        SAVE,
        RESET,
        RESET_AND_LOAD
    }

    public PostAdDBIntentService() {
        super("PostAdDBIntentService");
        GumtreeApplication.component().inject(this);
    }

    public static void load(String str) {
        Context context = GumtreeApplication.getContext();
        Intent intent = new Intent(context, PostAdDBIntentService.class);
        intent.putExtra(PostAdNavigationActivity.EXTRA_POST_AD_ID, str);
        intent.putExtra(EXTRA_DB_ACTION, DBAction.LOAD);
        context.startService(intent);
    }

    public static void save(PostAdData postAdData) {
        Context context = GumtreeApplication.getContext();
        Intent intent = new Intent(context, PostAdDBIntentService.class);
        intent.putExtra(EXTRA_POST_AD_DATA, postAdData);
        intent.putExtra(EXTRA_DB_ACTION, DBAction.SAVE);
        context.startService(intent);
    }

    public static void reset(String str) {
        Context context = GumtreeApplication.getContext();
        Intent intent = new Intent(context, PostAdDBIntentService.class);
        intent.putExtra(PostAdNavigationActivity.EXTRA_POST_AD_ID, str);
        intent.putExtra(EXTRA_DB_ACTION, DBAction.RESET);
        context.startService(intent);
    }

    public static void resetAndLoad(String str) {
        Context context = GumtreeApplication.getContext();
        Intent intent = new Intent(context, PostAdDBIntentService.class);
        intent.putExtra(PostAdNavigationActivity.EXTRA_POST_AD_ID, str);
        intent.putExtra(EXTRA_DB_ACTION, DBAction.RESET_AND_LOAD);
        context.startService(intent);
    }

    protected void onHandleIntent(Intent intent) {
        DBAction dBAction = (DBAction) intent.getSerializableExtra(EXTRA_DB_ACTION);
        try {
            switch (AnonymousClass1.$SwitchMap$com$gumtree$android$post_ad$PostAdDBIntentService$DBAction[dBAction.ordinal()]) {
                case HighlightView.GROW_NONE /*1*/:
                    cleanPostAdTable();
                    break;
                case HighlightView.GROW_LEFT_EDGE /*2*/:
                    break;
                case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                    this.mPostAdDAO.saveContent(getApplicationContext(), (PostAdData) intent.getSerializableExtra(EXTRA_POST_AD_DATA));
                    return;
                case HighlightView.GROW_RIGHT_EDGE /*4*/:
                    cleanPostAdTable();
                    String stringExtra = intent.getStringExtra(PostAdNavigationActivity.EXTRA_POST_AD_ID);
                    if (stringExtra.length() > 0) {
                        this.mEventBus.post(new OnPostAdLoadEvent(PostAdData.newInstance(stringExtra), null));
                        return;
                    }
                    return;
                default:
                    Log.e(getClass().getSimpleName(), "case not supported " + dBAction);
                    return;
            }
            String stringExtra2 = intent.getStringExtra(PostAdNavigationActivity.EXTRA_POST_AD_ID);
            PostAdData content = this.mPostAdDAO.getContent(getApplicationContext(), stringExtra2);
            if (content.getAttributeMap().isEmpty() && content.getDefaultPicture() == null) {
                if (stringExtra2.equals(DraftAd.NEW_AD_ID)) {
                    content = PostAdData.newInstance(stringExtra2);
                } else {
                    content = getPostAdFromCAPI(stringExtra2, content);
                }
                if (!this.validation) {
                    setDescriptionMinSize(content);
                }
            }
            this.mEventBus.post(new OnPostAdLoadEvent(content, null));
        } catch (Throwable e) {
            com.gumtree.android.common.utils.Log.e("Error when trying to " + dBAction + " from PostAd DB", e);
            this.mEventBus.post(new OnPostAdLoadEvent(null, e));
        }
    }

    private void setDescriptionMinSize(PostAdData postAdData) {
        String categoryId = postAdData.getCategoryId();
        if (categoryId.length() <= 0) {
            categoryId = PaymentConverter.DEFAULT_PAYMENT_METHOD_ID;
        }
        String url = CapiUrl.getUrl(CapiUrl.METADATA_POST, categoryId);
        Result execute = this.capiClientManager.getCapiClient().get(url).execute(new MetadataLimitParser(url, categoryId));
        if (!execute.hasError()) {
            int minSize = ((SearchMetadata) ((List) execute.getData()).get(0)).getMinSize();
            Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
            edit.putInt("metadata_size", minSize);
            edit.apply();
            this.validation = true;
        }
    }

    private PostAdData getPostAdFromCAPI(String str, PostAdData postAdData) {
        String valueOf = String.valueOf(System.currentTimeMillis());
        ICapiClient capiClient = this.capiClientManager.getCapiClient();
        try {
            cleanPostAdTable();
            String url = CapiUrl.getUrl(CapiUrl.MANAGE_AD, this.accountManager.getUsername(), str);
            capiClient.authorize(this.accountManager.getAuthentication());
            PostAd postAd = (PostAd) capiClient.get(url).executeDatabase(new ManageAdParser(url, valueOf), new ContentProviderDataStorage(getApplicationContext())).getData();
            CoreAttribute coreAttributeByKey = postAd.getCoreAttributeByKey(VIPContactFragment.CATEGORY_ID);
            if (coreAttributeByKey == null) {
                return postAdData;
            }
            url = CapiUrl.getUrl(CapiUrl.METADATA_POST, coreAttributeByKey.getValue());
            HashMap priceSensitiveAttributes = getPriceSensitiveAttributes(getPriceSensitiveAttributeNames((List) capiClient.get(url).executeDatabase(new MetadataParser(url, coreAttributeByKey.getValue()), new ContentProviderDataStorage(getApplicationContext())).getData()), postAd);
            CoreAttribute coreAttributeByKey2 = postAd.getCoreAttributeByKey("location");
            url = "features/ad.json";
            ICapiClient withParam = capiClient.get("features/ad.json").withParam(StatefulActivity.NAME_LOCATION_ID, coreAttributeByKey2.getValue().isEmpty() ? "10000392" : coreAttributeByKey2.getValue()).withParam(StatefulActivity.NAME_CATEGORY_ID, coreAttributeByKey.getValue().isEmpty() ? PaymentConverter.DEFAULT_PAYMENT_METHOD_ID : coreAttributeByKey.getValue());
            String id = postAd.getId();
            if (!(id == null || id.equals(DraftAd.NEW_AD_ID))) {
                withParam.withParam("adId", id);
            }
            addAttributeParams(withParam, priceSensitiveAttributes);
            capiClient.executeDatabase(new FeaturesParser(coreAttributeByKey.getValue(), coreAttributeByKey2.getValue()), new ContentProviderDataStorage(getApplicationContext())).getData();
            return this.mPostAdDAO.getContent(getApplicationContext(), postAd.getId());
        } catch (Exception e) {
            if (!(e instanceof ResponseException)) {
                return postAdData;
            }
            throw ((ResponseException) e);
        }
    }

    private Set<String> getPriceSensitiveAttributeNames(List<SearchMetadata> list) {
        Set<String> hashSet = new HashSet();
        if (list == null) {
            return hashSet;
        }
        for (SearchMetadata searchMetadata : list) {
            if (searchMetadata.isPriceSensitive()) {
                hashSet.add(searchMetadata.getName());
            }
        }
        return hashSet;
    }

    private HashMap<String, String> getPriceSensitiveAttributes(Set<String> set, PostAd postAd) {
        HashMap<String, String> hashMap = new HashMap();
        List<DynamicAttribute> dynamicAttributes = postAd.getDynamicAttributes();
        for (String str : set) {
            for (DynamicAttribute dynamicAttribute : dynamicAttributes) {
                if (dynamicAttribute.getKey().equals(str)) {
                    hashMap.put(str, dynamicAttribute.getValue());
                }
            }
        }
        return hashMap;
    }

    private void addAttributeParams(ICapiClient iCapiClient, HashMap<String, String> hashMap) {
        for (Entry entry : hashMap.entrySet()) {
            String str = (String) entry.getKey();
            iCapiClient.withParam(getAttributeKey(str), (String) entry.getValue());
        }
    }

    private String getAttributeKey(String str) {
        return String.format("attr[%s]", new Object[]{str});
    }

    private void cleanPostAdTable() {
        try {
            getContentResolver().delete(PostAds.URI, null, null);
            getContentResolver().delete(Metadata.URI, null, null);
            getContentResolver().delete(MetadataSupportedValuesOptions.URI, null, null);
            getContentResolver().delete(PostAdsImages.URI, null, null);
            getContentResolver().delete(MetadataSupportedValuesOptions.URI, null, null);
            getContentResolver().delete(Features.URI, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
