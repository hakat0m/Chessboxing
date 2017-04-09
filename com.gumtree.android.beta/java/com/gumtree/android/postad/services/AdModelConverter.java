package com.gumtree.android.postad.services;

import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.users.ads.MyAd;
import com.gumtree.android.postad.DraftAd;

public interface AdModelConverter {
    MyAd draftAdToMyAd(@NonNull DraftAd draftAd);

    DraftAd myAdToDraftAd(@NonNull MyAd myAd);
}
