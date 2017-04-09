package com.ebay.classifieds.capi.pictures;

import com.ebay.classifieds.capi.ads.Picture;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;
import rx.Observable;

public interface PicturesApi {
    @POST("/pictures")
    @Multipart
    Observable<Picture> upload(@Part("file") TypedFile typedFile);
}
