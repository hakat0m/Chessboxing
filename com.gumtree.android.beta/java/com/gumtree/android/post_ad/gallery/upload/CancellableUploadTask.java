package com.gumtree.android.post_ad.gallery.upload;

import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.http.CAPIIntentFactory;
import com.gumtree.android.common.transport.Request;
import com.gumtree.android.common.transport.RequestMethod;
import com.gumtree.android.common.transport.Response;
import com.gumtree.android.common.transport.Transport;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.common.utils.crashlytics.CrashlyticsHelper;
import com.gumtree.android.handler.DataStorage;
import com.gumtree.android.handler.DataStorage.Batch;
import com.gumtree.android.handler.DataStorageBatchJsonHandler;
import com.gumtree.android.model.PostAdsImages;
import com.gumtree.android.post_ad.gallery.upload.ParsedLinks.Link;

public class CancellableUploadTask extends CancellableTask {
    private static final String CAPI_MULTIPART_FILE_IDENTIFIER = "file";
    private final CAPIIntentFactory api;
    private final DataStorage storage;
    private final String storagePath;
    private final Transport transport;

    public /* bridge */ /* synthetic */ void activate() {
        super.activate();
    }

    public /* bridge */ /* synthetic */ void cancel() {
        super.cancel();
    }

    public /* bridge */ /* synthetic */ boolean isActivated() {
        return super.isActivated();
    }

    public CancellableUploadTask(String str) {
        if (Log.verboseLoggingEnabled()) {
            Log.v("image file path for upload (should not be uri!):" + str);
        }
        this.storagePath = str;
        this.api = GumtreeApplication.getAPI();
        this.transport = GumtreeApplication.getHttpTransport();
        this.storage = GumtreeApplication.getDataStorage();
    }

    protected String getCancelId() {
        return this.storagePath;
    }

    public void execute() {
        try {
            uploadImage();
            storeSuccessInPicturesStateTable();
        } catch (Throwable e) {
            storeErrorInPicturesStateTable();
            CrashlyticsHelper.getInstance().catchGumtreeException(e);
        }
    }

    private void uploadImage() throws Exception {
        String picturesUploadRequestUrl = getPicturesUploadRequestUrl();
        Request prepare = this.transport.prepare(RequestMethod.POST, picturesUploadRequestUrl);
        prepare.addPayload(CAPI_MULTIPART_FILE_IDENTIFIER, new FilePayload(this, null));
        Response execute = prepare.execute();
        try {
            if (Log.verboseLoggingEnabled()) {
                Log.v("Response for image : " + picturesUploadRequestUrl + " status code : " + execute.getStatusCode());
            }
            storeParsedThumbLink(getParsedPictureLinks(execute));
        } finally {
            execute.close();
        }
    }

    private String getPicturesUploadRequestUrl() {
        return Uri.parse(this.api.getBaseUrl()).buildUpon().appendPath(this.api.getFixedPathIfAny()).appendPath("pictures").build().toString();
    }

    private ParsedLinks getParsedPictureLinks(Response response) throws Exception {
        ParsedLinks parsedLinks = new ParsedLinks();
        parsedLinks.from(response.getContent());
        return parsedLinks;
    }

    private void storeParsedThumbLink(ParsedLinks parsedLinks) throws Exception {
        if (parsedLinks.hasThumbnailLink()) {
            Link thumbnailLink = parsedLinks.getThumbnailLink();
            storePictureInPicturesTable(thumbnailLink.href, thumbnailLink.rel, this.storagePath);
            return;
        }
        throw new RuntimeException("no thumb/thumbnail rel link found in server response");
    }

    private void storePictureInPicturesTable(String str, String str2, String str3) throws Exception {
        Batch createBatchOperation = this.storage.createBatchOperation();
        new DataStorageBatchJsonHandler(createBatchOperation).onPictureUploaded(str3, str, str2);
        createBatchOperation.execute();
    }

    private void storeSuccessInPicturesStateTable() {
        updateStateInPostAdsImagesTable(PostAdsImages.SUCCESS);
    }

    private void storeErrorInPicturesStateTable() {
        updateStateInPostAdsImagesTable(PostAdsImages.ERROR);
    }

    private void updateStateInPostAdsImagesTable(String str) {
        Throwable e;
        try {
            Batch createBatchOperation = this.storage.createBatchOperation();
            new DataStorageBatchJsonHandler(createBatchOperation).onPictureState(this.storagePath, str);
            createBatchOperation.execute();
            return;
        } catch (RemoteException e2) {
            e = e2;
        } catch (OperationApplicationException e3) {
            e = e3;
        }
        CrashlyticsHelper.getInstance().catchGumtreeException(e);
    }
}
