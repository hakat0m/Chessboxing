package com.gumtree.android.postad.services;

import com.gumtree.android.postad.DraftAd;
import rx.Observable;

public interface PostAdService {
    Observable<DraftAd> editDraftAd(DraftAd draftAd);

    Observable<DraftAd> getDraftAd(String str);

    Observable<DraftAd> postDraftAd(DraftAd draftAd);
}
