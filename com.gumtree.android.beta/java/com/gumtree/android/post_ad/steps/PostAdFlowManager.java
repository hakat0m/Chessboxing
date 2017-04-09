package com.gumtree.android.post_ad.steps;

import android.os.Bundle;

public interface PostAdFlowManager {
    boolean onNext();

    boolean onPrevious();

    void onSaveInstanceState(Bundle bundle);
}
