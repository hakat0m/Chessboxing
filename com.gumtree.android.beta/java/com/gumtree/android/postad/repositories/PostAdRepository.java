package com.gumtree.android.postad.repositories;

import com.google.gson.Gson;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.repositories.Repository;
import java.io.File;
import rx.Observable;

public class PostAdRepository implements Repository<DraftAd> {
    private static final String POST_AD_FILE_EXTENSION = ".json";
    private static final String POST_AD_FILE_PATH = "postad";
    private static final String TAG = PostAdRepository.class.getSimpleName();
    private final File filesDir;
    private Gson gson;

    public PostAdRepository(File file, Gson gson) {
        this.filesDir = file;
        this.gson = gson;
    }

    public Observable<Void> save(DraftAd draftAd) {
        return Observable.create(new 1(this, draftAd));
    }

    public Observable<DraftAd> load(String str) {
        return Observable.create(new 2(this, str));
    }

    public Observable<Void> clear(String str) {
        return Observable.create(new 3(this, str));
    }
}
