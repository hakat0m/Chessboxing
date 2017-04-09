package com.gumtree.android.configuration;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp3.OkHttpGlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader.Factory;
import com.bumptech.glide.load.model.GlideUrl;
import com.gumtree.android.GumtreeApplication;
import java.io.InputStream;
import javax.inject.Inject;
import okhttp3.OkHttpClient;

public class GumtreeGlideModule extends OkHttpGlideModule {
    @Inject
    OkHttpClient okHttpClient;

    public GumtreeGlideModule() {
        GumtreeApplication.component().inject(this);
    }

    public void registerComponents(Context context, Glide glide) {
        glide.register(GlideUrl.class, InputStream.class, new Factory(this.okHttpClient));
    }
}
