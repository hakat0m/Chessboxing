package com.gumtree.android.postad;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gumtree.android.postad.PostAdActivity.GalleryImageEditListener;
import java.util.ArrayList;
import java.util.List;

public final class PostAdSummaryAdapter extends Adapter<ViewHolder> {
    private List<PostAdImage> adImages = new ArrayList();
    private Context context;
    private GalleryImageEditListener galleryImageEditListener;
    private int imageBorderInPx;

    public PostAdSummaryAdapter(Context context, GalleryImageEditListener galleryImageEditListener) {
        this.context = context;
        this.galleryImageEditListener = galleryImageEditListener;
        this.imageBorderInPx = context.getResources().getDimensionPixelSize(2131231156);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(this, LayoutInflater.from(viewGroup.getContext()).inflate(2130903306, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if (i == 0) {
            viewHolder.imageItem.setPadding(this.imageBorderInPx, this.imageBorderInPx, this.imageBorderInPx, this.imageBorderInPx);
        } else {
            viewHolder.imageItem.setPadding(0, 0, 0, 0);
        }
        Glide.with(this.context).load(((PostAdImage) this.adImages.get(i)).getPath()).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).centerCrop().placeholder(2130837645).error(2130837645).into(viewHolder.imageItem);
    }

    public int getItemCount() {
        return this.adImages == null ? 0 : this.adImages.size();
    }

    void setImages(@NonNull List<PostAdImage> list) {
        this.adImages = list;
        notifyDataSetChanged();
    }
}
