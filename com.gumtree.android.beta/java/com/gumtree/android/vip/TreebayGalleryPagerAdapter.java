package com.gumtree.android.vip;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.apptentive.android.sdk.BuildConfig;
import com.crashlytics.android.Crashlytics;
import com.gumtree.android.events.EventBus;
import java.util.ArrayList;
import uk.co.senab.photoview.IPhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class TreebayGalleryPagerAdapter extends PagerAdapter {
    private static final int THREE_ITEMS = 3;
    protected ArrayList<String> imageList;
    private Context mContext;
    private LayoutInflater mInflater;
    private SparseArray<PhotoViewAttacher> mPhotoViewAttachers = new SparseArray();

    public TreebayGalleryPagerAdapter(Context context, LayoutInflater layoutInflater, ArrayList<String> arrayList, EventBus eventBus) {
        this.mContext = context;
        this.imageList = arrayList;
        this.mInflater = layoutInflater;
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View inflate = this.mInflater.inflate(2130903257, viewGroup, false);
        ImageView imageView = (ImageView) inflate.findViewById(2131624650);
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imageView);
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ImageGlobalLayoutListener(this, getUrl(i), imageView, photoViewAttacher));
        this.mPhotoViewAttachers.put(i, photoViewAttacher);
        viewGroup.addView(inflate);
        return inflate;
    }

    public void zoomOut(int i) {
        PhotoViewAttacher photoViewAttacher = (PhotoViewAttacher) this.mPhotoViewAttachers.get(i);
        try {
            photoViewAttacher.setZoomTransitionDuration(IPhotoView.DEFAULT_ZOOM_DURATION);
            photoViewAttacher.setScale(photoViewAttacher.getMinimumScale(), true);
        } catch (Throwable e) {
            Crashlytics.logException(e);
        }
    }

    protected String getUrl(int i) {
        if (i < 0 || this.imageList.size() <= 0) {
            return BuildConfig.FLAVOR;
        }
        return (String) this.imageList.get(i);
    }

    public int getCount() {
        return this.imageList.size();
    }

    public boolean isViewFromObject(View view, Object obj) {
        return view.equals(obj);
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    public int getItemPosition(Object obj) {
        return -2;
    }

    public int getItemToLoad(int i) {
        int count = getCount();
        if (count == 1 || i == count - 1) {
            return 0;
        }
        if (i == 0) {
            return count - 3;
        }
        return i - 1;
    }

    public void changeCursor(ArrayList<String> arrayList) {
        this.imageList = arrayList;
        notifyDataSetChanged();
    }
}
