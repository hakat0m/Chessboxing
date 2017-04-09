package com.gumtree.android.startup;

import android.content.Context;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.gson.LocalJsonParserFactory;
import com.gumtree.android.common.http.GumtreeRestExecutor;
import com.gumtree.android.handler.DataStorage.Batch;
import com.gumtree.android.handler.DataStorageBatchJsonHandler;
import com.gumtree.android.model.Categories;
import java.io.IOException;

public class CategoryUpdate {
    public int executeLiveRequest(long j) {
        GumtreeRestExecutor gumtreeRestExecutor = new GumtreeRestExecutor();
        gumtreeRestExecutor.prepareRequest(GumtreeApplication.getAPI().getHttpIntentForCategories(j, null));
        return gumtreeRestExecutor.execute();
    }

    public void executeCachedRequest(long j, Context context, Batch batch) throws IOException {
        new LocalJsonParserFactory().getJsonParserFor(0, String.valueOf(j)).inflateStream(context.getAssets().open("categories.json"), new DataStorageBatchJsonHandler(batch));
    }

    public void cleanStaleContent(Long l, Context context) {
        context.getContentResolver().delete(Categories.URI, "inserted_at<>?", new String[]{l + BuildConfig.FLAVOR});
    }
}
