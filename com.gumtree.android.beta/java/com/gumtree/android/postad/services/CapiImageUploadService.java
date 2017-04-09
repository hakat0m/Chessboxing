package com.gumtree.android.postad.services;

import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.ads.Picture;
import com.ebay.classifieds.capi.ads.PictureLink;
import com.ebay.classifieds.capi.pictures.PicturesApi;
import com.ebay.classifieds.capi.utils.PictureLinkUtils;
import com.gumtree.android.common.http.model.CapiApiException;
import com.gumtree.android.postad.services.Upload.Status;
import java.io.File;
import java.net.URL;
import java.util.Iterator;
import javax.inject.Inject;
import org.apache.commons.lang3.Validate;
import retrofit.mime.TypedFile;
import rx.Observable;

public class CapiImageUploadService implements ImageUploadService {
    private static final String LINK_KEY = "extrabig";
    private final PicturesApi api;

    @Inject
    public CapiImageUploadService(@NonNull PicturesApi picturesApi) {
        this.api = (PicturesApi) Validate.notNull(picturesApi);
    }

    /* synthetic */ Observable lambda$upload$0(File file) {
        return upload(file);
    }

    public Observable<Upload> upload(Observable<File> observable) {
        return observable.flatMap(CapiImageUploadService$$Lambda$1.lambdaFactory$(this));
    }

    public void reset() {
        throw new UnsupportedOperationException("Not coded yet");
    }

    @NonNull
    private Observable<Upload> upload(File file) {
        return this.api.upload(new TypedFile("image/jpeg", file)).flatMap(new 1(this, file)).startWith(Upload.remoteless(file, Status.QUEUED), Upload.remoteless(file, Status.IN_PROGRESS));
    }

    @NonNull
    private Observable<Upload> createUpload(File file, Picture picture) {
        try {
            Iterator it = picture.getLinks().iterator();
            while (it.hasNext()) {
                PictureLink pictureLink = (PictureLink) it.next();
                if (LINK_KEY.equals(pictureLink.getRel())) {
                    return Observable.just(new Upload(new Association(file, new URL(PictureLinkUtils.getPictureLink20(pictureLink.getHref()))), Status.COMPLETE));
                }
            }
            return Observable.error(new CapiApiException("Link extrabig is missing in picture " + picture));
        } catch (Throwable e) {
            return Observable.error(e);
        }
    }
}
