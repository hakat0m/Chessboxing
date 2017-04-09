package com.gumtree.android.vip;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.crashlytics.android.Crashlytics;
import com.gumtree.android.events.EventBus;
import uk.co.senab.photoview.IPhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class GalleryPagerAdapter extends PagerAdapter {
    private static final int THREE_ITEMS = 3;
    private Context mContext;
    protected Cursor mCursor;
    private LayoutInflater mInflater;
    private SparseArray<PhotoViewAttacher> mPhotoViewAttachers = new SparseArray();

    public GalleryPagerAdapter(Context context, LayoutInflater layoutInflater, Cursor cursor, EventBus eventBus) {
        this.mContext = context;
        this.mCursor = cursor;
        this.mInflater = layoutInflater;
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        View inflate = this.mInflater.inflate(2130903257, viewGroup, false);
        ImageView imageView = (ImageView) inflate.findViewById(2131624650);
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imageView);
        this.mCursor.moveToPosition(getItemToLoad(i));
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ImageGlobalLayoutListener(this, getUrl(), imageView, photoViewAttacher));
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

    protected String getUrl() {
        return this.mCursor.getString(this.mCursor.getColumnIndex("href"));
    }

    public int getCount() {
        if (this.mCursor == null) {
            return 0;
        }
        if (this.mCursor.getCount() != 1) {
            return this.mCursor.getCount() + 2;
        }
        return 1;
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

    public void changeCursor(Cursor cursor) {
        Cursor cursor2 = this.mCursor;
        this.mCursor = cursor;
        notifyDataSetChanged();
        if (cursor2 != null) {
            cursor2.close();
        }
    }
}
