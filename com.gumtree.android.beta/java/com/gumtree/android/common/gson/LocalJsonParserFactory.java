package com.gumtree.android.common.gson;

import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.category.parser.CategoryTreeParser;
import com.gumtree.android.message_box.ConversationsIntentService;

public class LocalJsonParserFactory {
    public JsonParser getJsonParserFor(int i, String str) {
        switch (i) {
            case ConversationsIntentService.DEFAULT_PAGE /*0*/:
            case HighlightView.GROW_NONE /*1*/:
                return new CategoryTreeParser(BuildConfig.FLAVOR, str);
            default:
                throw new IllegalStateException("Parser not found to handle service request");
        }
    }
}
