package com.gumtree.android.post_ad;

import com.gumtree.android.post_ad.model.PostAdData;

public interface IPostAdDataRefresh {
    void enableView(boolean z);

    void refreshContent(PostAdData postAdData);
}
