package com.ebay.kleinanzeigen.imagepicker.image_editing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ebay.kleinanzeigen.imagepicker.R$id;
import com.ebay.kleinanzeigen.imagepicker.R$layout;
import com.ebay.kleinanzeigen.imagepicker.image_library.Image;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import java.util.List;

public class ImageGalleryPagerAdapter extends PagerAdapter {
    private Context context;
    private boolean galleryEnabled;
    private ImageLoader imageLoader;
    private int imageLongestDimension;
    private List<Image> images;

    public ImageGalleryPagerAdapter(List<Image> list, Context context, ImageLoader imageLoader, boolean z, int i) {
        this.images = list;
        this.context = context;
        this.imageLoader = imageLoader;
        this.galleryEnabled = z;
        this.imageLongestDimension = i;
    }

    public int getCount() {
        return this.images.size();
    }

    public boolean isViewFromObject(View view, Object obj) {
        return view.equals(obj);
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    public static Bitmap rotateImage(Bitmap bitmap) {
        return Bitmap.createBitmap(bitmap.getHeight(), bitmap.getWidth(), Config.ARGB_8888);
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(this.context).inflate(R$layout.image_gallery_pager_item, viewGroup, false);
        ImageView imageView = (ImageView) inflate.findViewById(R$id.item_image);
        ImageView imageView2 = (ImageView) inflate.findViewById(R$id.item_image_rotated);
        Bitmap loadImageSync = this.imageLoader.loadImageSync("file://" + getItem(i).getFinalPath(), new ImageSize(this.imageLongestDimension, this.imageLongestDimension));
        imageView.setVisibility(getItem(i).getData().isSaving() ? 4 : 0);
        imageView.setImageBitmap(loadImageSync);
        getItem(i).resetData();
        TextView textView = (TextView) inflate.findViewById(R$id.gallery_frame);
        if (this.galleryEnabled) {
            int i2;
            if (getItem(i).isGalleryImage()) {
                i2 = 0;
            } else {
                i2 = 8;
            }
            textView.setVisibility(i2);
        } else {
            textView.setVisibility(8);
        }
        inflate.setTag(Integer.valueOf(i));
        if (loadImageSync != null) {
            imageView2.setImageBitmap(rotateImage(loadImageSync));
        }
        imageView2.setVisibility(0);
        viewGroup.addView(inflate, 0);
        return inflate;
    }

    public Image getItem(int i) {
        return (Image) this.images.get(i);
    }
}
