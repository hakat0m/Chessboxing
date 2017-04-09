package com.gumtree.android.post_ad.preview;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.vip.GalleryPagerAdapter;

public class PreviewGalleryPagerAdapter extends GalleryPagerAdapter {
    public PreviewGalleryPagerAdapter(Context context, LayoutInflater layoutInflater, Cursor cursor, EventBus eventBus) {
        super(context, layoutInflater, cursor, eventBus);
    }

    public String getUrl() {
        return this.mCursor.getString(this.mCursor.getColumnIndex("href"));
    }
}
