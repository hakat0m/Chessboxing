package com.gumtree.android.vip;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.List;
import uk.co.senab.photoview.IPhotoView;

public class PicturesPagerAdapter extends FragmentStatePagerAdapter {
    private List<Image> images;
    private final int pagerHeight;
    private final int pagerWidth;
    private int totalWidth;

    @Deprecated
    public PicturesPagerAdapter(FragmentManager fragmentManager, Context context, Cursor cursor) {
        super(fragmentManager);
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        int dimensionInPixel = getDimensionInPixel(context, 2131230790);
        if (dimensionInPixel != 0) {
            dimensionInPixel += 2;
        }
        dimensionInPixel = displayMetrics.widthPixels - dimensionInPixel;
        this.pagerHeight = getDimensionInPixel(context, 2131230722);
        this.pagerWidth = dimensionInPixel;
        prepareImages(cursor);
    }

    @Deprecated
    public PicturesPagerAdapter(FragmentManager fragmentManager, Context context, List<String> list) {
        super(fragmentManager);
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        int dimensionInPixel = getDimensionInPixel(context, 2131230790);
        if (dimensionInPixel != 0) {
            dimensionInPixel += 2;
        }
        dimensionInPixel = displayMetrics.widthPixels - dimensionInPixel;
        this.pagerHeight = getDimensionInPixel(context, 2131230722);
        this.pagerWidth = dimensionInPixel;
        prepareImagesFromUrl(list);
    }

    @Deprecated
    private void prepareImages(Cursor cursor) {
        this.images = new ArrayList();
        while (cursor.moveToNext()) {
            Image image = new Image();
            image.setUrl(cursor.getString(cursor.getColumnIndex("href")), this.pagerWidth, this.pagerHeight);
            this.images.add(image);
            this.totalWidth = image.getWidth() + this.totalWidth;
        }
    }

    private void prepareImagesFromUrl(List<String> list) {
        this.images = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            Image image = new Image();
            image.setUrl((String) list.get(i), this.pagerWidth, this.pagerHeight);
            this.images.add(image);
            this.totalWidth += image.getWidth();
        }
    }

    private int getDimensionInPixel(Context context, int i) {
        return context.getResources().getDimensionPixelSize(i);
    }

    public Fragment getItem(int i) {
        return VIPPictureFragment.newInstance((Image) this.images.get(i));
    }

    public Parcelable saveState() {
        try {
            return super.saveState();
        } catch (Exception e) {
            e.printStackTrace();
            return new Bundle();
        }
    }

    public boolean areImagesToSmall() {
        return this.pagerWidth > this.totalWidth;
    }

    public int getCount() {
        if (this.images == null) {
            return 0;
        }
        return this.images.size();
    }

    public float getPageWidth(int i) {
        if (getCount() == 1) {
            return IPhotoView.DEFAULT_MIN_SCALE;
        }
        return new Float((float) ((Image) this.images.get(i)).getWidth()).floatValue() / new Float((float) this.pagerWidth).floatValue();
    }

    public int getImageWidth(int i) {
        if (getCount() != 1) {
            return ((Image) this.images.get(i)).getWidth();
        }
        Image image = (Image) this.images.get(0);
        float height = ((float) this.pagerHeight) / ((float) image.getHeight());
        if (height <= ((float) this.pagerWidth) / ((float) image.getWidth())) {
            return (int) (((float) image.getWidth()) * height);
        }
        return this.pagerWidth;
    }

    public int getImageHeight(int i) {
        if (getCount() != 1) {
            return ((Image) this.images.get(i)).getHeight();
        }
        Image image = (Image) this.images.get(0);
        float width = ((float) this.pagerWidth) / ((float) image.getWidth());
        if (width <= ((float) this.pagerHeight) / ((float) image.getHeight())) {
            return (int) (((float) image.getHeight()) * width);
        }
        return this.pagerHeight;
    }

    public int getImageLeftX(int i) {
        int count = getCount();
        if (count == 1) {
            Image image = (Image) this.images.get(0);
            float height = ((float) this.pagerHeight) / ((float) image.getHeight());
            if (height > ((float) this.pagerWidth) / ((float) image.getWidth())) {
                return 0;
            }
            return ((int) (((float) this.pagerWidth) - (((float) image.getWidth()) * height))) / 2;
        }
        int width;
        int i2 = 0;
        while (i2 < this.pagerWidth) {
            width = ((Image) this.images.get(i)).getWidth() + i2;
            i++;
            if (i > count - 1) {
                break;
            }
            i2 = width;
        }
        width = i2;
        if (width >= this.pagerWidth || width == this.totalWidth) {
            return 0;
        }
        if (this.totalWidth < this.pagerWidth) {
            return this.totalWidth - width;
        }
        return this.pagerWidth - width;
    }

    public int getImageTopY(int i) {
        if (getCount() == 1) {
            Image image = (Image) this.images.get(0);
            float width = ((float) this.pagerWidth) / ((float) image.getWidth());
            if (width > ((float) this.pagerHeight) / ((float) image.getHeight())) {
                return 0;
            }
            return ((int) (((float) this.pagerHeight) - (((float) image.getHeight()) * width))) / 2;
        }
        int height = ((Image) this.images.get(i)).getHeight();
        return height < this.pagerHeight ? (this.pagerHeight - height) / 2 : 0;
    }
}
