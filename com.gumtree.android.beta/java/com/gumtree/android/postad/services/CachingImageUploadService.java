package com.gumtree.android.postad.services;

import android.support.annotation.NonNull;
import com.gumtree.android.postad.services.Cache.TimestampedAssociation;
import com.gumtree.android.postad.services.Upload.Status;
import java.io.File;
import javax.inject.Inject;
import org.apache.commons.lang3.Validate;
import rx.Observable;

public class CachingImageUploadService implements ImageUploadService {
    private final Cache cache;
    private final ImageUploadService decorated;

    @Inject
    public CachingImageUploadService(@NonNull ImageUploadService imageUploadService, @NonNull Cache cache) {
        this.cache = (Cache) Validate.notNull(cache);
        this.decorated = (ImageUploadService) Validate.notNull(imageUploadService);
    }

    /* synthetic */ Observable lambda$upload$0(File file) {
        return handle(file);
    }

    public Observable<Upload> upload(Observable<File> observable) {
        return observable.concatMap(CachingImageUploadService$$Lambda$1.lambdaFactory$(this));
    }

    public void reset() {
        throw new UnsupportedOperationException("Not coded yet");
    }

    private Observable<? extends Upload> handle(File file) {
        TimestampedAssociation association = this.cache.getAssociation(file);
        if (association == null) {
            return this.decorated.upload(Observable.just(file)).doOnNext(CachingImageUploadService$$Lambda$2.lambdaFactory$(this));
        }
        return buildUploadSequence(association.getAssociation());
    }

    /* synthetic */ void lambda$handle$1(Upload upload) {
        if (upload.getStatus() == Status.COMPLETE) {
            this.cache.put(upload.getAssociation());
        }
    }

    private Observable<? extends Upload> buildUploadSequence(Association association) {
        return Observable.just(Upload.remoteless(association.getLocal(), Status.QUEUED), Upload.remoteless(association.getLocal(), Status.IN_PROGRESS), new Upload(association, Status.COMPLETE));
    }
}
