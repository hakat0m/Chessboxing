package com.gumtree.android.post_ad;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.text.TextUtils;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.classifieds.capi.executor.Result;
import com.ebay.classifieds.capi.executor.ResultError;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.ads.PostAdResult;
import com.gumtree.android.ads.parser.AdStatusParser;
import com.gumtree.android.ads.parser.PostAdResponseParser;
import com.gumtree.android.ads.parser.PostAdXmlBuilder;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.http.CapiClientManager;
import com.gumtree.android.common.http.CapiUrl;
import com.gumtree.android.common.http.JsonRestExecutor;
import com.gumtree.android.common.http.model.ICapiClient;
import com.gumtree.android.common.transport.Response;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnPostAdEvent;
import com.gumtree.android.model.PostAdsImages;
import com.gumtree.android.post_ad.gallery.upload.CancellableUploadTask;
import com.gumtree.android.post_ad.gallery.utils.PostAdImageStorage;
import com.gumtree.android.post_ad.model.PostAdDBDAO;
import com.gumtree.android.post_ad.model.PostAdData;
import com.gumtree.android.post_ad.model.PostAdDataAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import uk.co.senab.photoview.IPhotoView;

public class PostAdAPIIntentService extends IntentService {
    private static final String EQUAL_QUESTION_MARK = "=?";
    private static final String EXTRA_POST_AD_DATA = "com.gumtree.android.post_ad.postAdData";
    private static final int RETRY_LIMIT = 3;
    @Inject
    BaseAccountManager accountManager;
    private ICapiClient capiClient = this.capiClientManager.getCapiClient();
    @Inject
    CapiClientManager capiClientManager;
    private int imageUploadCounter;
    @Inject
    EventBus mEventBus;

    class AdStatusExecutor extends JsonRestExecutor {
        public AdStatusExecutor(String str) {
            super(GumtreeApplication.getAPI().getHttpIntentForAdStatus(str, null));
        }

        public String getStatus() {
            String str = BuildConfig.FLAVOR;
            Response response = null;
            try {
                response = execute();
                if (response.getStatusCode() == IPhotoView.DEFAULT_ZOOM_DURATION) {
                    str = new AdStatusParser().parse(response.getContent());
                }
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (response != null) {
                    response.close();
                }
            } catch (Throwable th) {
                if (response != null) {
                    response.close();
                }
            }
            return str;
        }
    }

    public PostAdAPIIntentService() {
        super("PostAdAPIIntentService");
        GumtreeApplication.component().inject(this);
    }

    public static void post(PostAdData postAdData) {
        Context context = GumtreeApplication.getContext();
        Intent intent = new Intent(context, PostAdAPIIntentService.class);
        intent.putExtra(EXTRA_POST_AD_DATA, postAdData);
        context.startService(intent);
    }

    protected void onHandleIntent(Intent intent) {
        try {
            PostAdData postAdData = (PostAdData) intent.getSerializableExtra(EXTRA_POST_AD_DATA);
            String adId = postAdData.getAdId();
            while (this.imageUploadCounter < RETRY_LIMIT && areThereImagesToPost(adId)) {
                this.imageUploadCounter++;
                postImages(adId);
            }
            if (areThereImagesToPost(adId)) {
                this.mEventBus.post(new OnPostAdEvent(new Result(ResultError.unknown())));
                return;
            }
            this.capiClient = this.capiClientManager.getCapiClient();
            try {
                if (this.accountManager.getUsername() == null || this.accountManager.getUserId() == null) {
                    this.mEventBus.post(new OnPostAdEvent(new Result(ResultError.authorization())));
                    return;
                }
                String postAdRequestXml = getPostAdRequestXml(postAdData, this.accountManager.getUsername(), this.accountManager.getUserId());
                adId = CapiUrl.getUrl(CapiUrl.POST_AD, this.accountManager.getUsername());
                if (!postAdData.isPost()) {
                    adId = CapiUrl.getUrl(CapiUrl.EDIT_AD, this.accountManager.getUsername(), postAdData.getAdId());
                }
                this.capiClient.authorize(this.accountManager.getAuthentication());
                this.capiClient.withContent(postAdRequestXml);
                if (postAdData.isPost()) {
                    this.capiClient.post(adId);
                } else {
                    this.capiClient.put(adId);
                }
                Result execute = this.capiClient.execute(new PostAdResponseParser());
                if (!execute.hasError()) {
                    ((PostAdResult) execute.getData()).setAdStatus(getStatus((PostAdResult) execute.getData()));
                    new PostAdDBDAO().cleanPictures(getContentResolver(), postAdData.getAdId());
                    new PostAdImageStorage(getApplicationContext()).clear();
                }
                this.mEventBus.post(new OnPostAdEvent(execute));
            } catch (IOException e) {
                this.mEventBus.post(new OnPostAdEvent(new Result(ResultError.unknown())));
            }
        } catch (Throwable e2) {
            Log.e("Error when trying to post ad in CAPI", e2);
            this.mEventBus.post(new OnPostAdEvent(null));
        }
    }

    private String getPostAdRequestXml(PostAdData postAdData, String str, String str2) throws IOException {
        return new PostAdXmlBuilder(new PostAdDataAdapter(getApplicationContext(), postAdData, str, str2)).createPostAdRequestXml();
    }

    private String getStatus(PostAdResult postAdResult) {
        return new AdStatusExecutor(postAdResult.getAdId()).getStatus();
    }

    private boolean areThereImagesToPost(String str) {
        Cursor query = getContentResolver().query(PostAdsImages.URI, null, "ad_id=?", new String[]{str}, null);
        while (query.moveToNext()) {
            if (toDownload(query.getString(query.getColumnIndex("upload_state")), query.getString(query.getColumnIndex("storage_path")))) {
                return true;
            }
        }
        return false;
    }

    private boolean toDownload(String str, String str2) {
        if (PostAdsImages.SUCCESS.equals(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        return true;
    }

    private void postImages(String str) {
        uploadRemainingImages(getImagesNotUploaded(str));
    }

    private void uploadRemainingImages(List<String> list) {
        for (String cancellableUploadTask : list) {
            new CancellableUploadTask(cancellableUploadTask).execute();
        }
    }

    private List<String> getImagesNotUploaded(String str) {
        Cursor query = getContentResolver().query(PostAdsImages.URI, null, "ad_id=?", new String[]{str}, null);
        List<String> arrayList = new ArrayList();
        while (query.moveToNext()) {
            String string = query.getString(query.getColumnIndex("storage_path"));
            if (toDownload(query.getString(query.getColumnIndex("upload_state")), string)) {
                arrayList.add(string);
            }
        }
        query.close();
        return arrayList;
    }
}
