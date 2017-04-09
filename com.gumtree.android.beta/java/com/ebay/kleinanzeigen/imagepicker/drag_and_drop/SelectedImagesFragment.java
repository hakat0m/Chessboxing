package com.ebay.kleinanzeigen.imagepicker.drag_and_drop;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import com.ebay.kleinanzeigen.imagepicker.EbkImagePicker;
import com.ebay.kleinanzeigen.imagepicker.EbkImagePickerActivity;
import com.ebay.kleinanzeigen.imagepicker.R$id;
import com.ebay.kleinanzeigen.imagepicker.R$layout;
import com.ebay.kleinanzeigen.imagepicker.image_editing.ImageGalleryActivity;
import com.ebay.kleinanzeigen.imagepicker.image_library.Image;
import com.ebay.kleinanzeigen.imagepicker.platform.ImageLoaderFactory;
import com.ebay.kleinanzeigen.imagepicker.storage.ImageStorageImpl;
import java.util.List;

public class SelectedImagesFragment extends Fragment implements OnItemClickListener, OnItemLongClickListener, SpanVariableGridView$DragAndDropListener {
    private static String DRAG_DROP_READ = "DRAG_DROP_READ";
    private TextView dragDropHint;
    private boolean keepScrolling;
    private CoolDragAndDropGridView mCoolDragAndDropGridView;
    private ImagesAdapter mImagesAdapter;
    private HorizontalScrollView scrollView;
    private SharedPreferences sharedPreferences;

    public interface ImageSelector {
        List<Image> getSelectedImages(boolean z);
    }

    private SharedPreferences getPrefs() {
        if (this.sharedPreferences == null) {
            this.sharedPreferences = getActivity().getSharedPreferences("com.ebay.kleinanzeigen.imagepicker", 0);
        }
        return this.sharedPreferences;
    }

    private void markHintAsRead() {
        if (getPrefs() != null) {
            getPrefs().edit().putBoolean(DRAG_DROP_READ, true).commit();
        }
    }

    private boolean isHintRead() {
        return getPrefs() != null && getPrefs().getBoolean(DRAG_DROP_READ, false);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R$layout.fragment_selected_images, viewGroup, false);
        this.dragDropHint = (TextView) inflate.findViewById(R$id.drag_drop_hint);
        this.scrollView = (HorizontalScrollView) inflate.findViewById(R$id.scrollview);
        this.mCoolDragAndDropGridView = (CoolDragAndDropGridView) inflate.findViewById(R$id.cooldraganddropview);
        setListeners();
        setEdgeBasedOnScreenSize();
        setHintVisibility();
        return inflate;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.mImagesAdapter = new ImagesAdapter(getActivity(), getSelectedImagesFromActivity(false), 0.0f, null, ImageLoaderFactory.getImageLoader(getActivity()), getActivity().getIntent().getBooleanExtra("GALLERY_ENABLED", true));
        this.mCoolDragAndDropGridView.setAdapter(this.mImagesAdapter);
    }

    private List<Image> getSelectedImagesFromActivity(boolean z) {
        return ((ImageSelector) getActivity()).getSelectedImages(z);
    }

    private void setListeners() {
        this.mCoolDragAndDropGridView.setDragAndDropListener(this);
        this.mCoolDragAndDropGridView.setOnItemLongClickListener(this);
        this.mCoolDragAndDropGridView.setOnItemClickListener(this);
        this.scrollView.setOnTouchListener(new 1(this));
    }

    public void destroyTransparentView() {
        this.mCoolDragAndDropGridView.destroyDragImageView();
    }

    private void setEdgeBasedOnScreenSize() {
        Display defaultDisplay = getActivity().getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        this.mCoolDragAndDropGridView.setEdgeX(point.x);
    }

    public void recreateAdapter() {
        this.mImagesAdapter = new ImagesAdapter(getActivity(), getSelectedImagesFromActivity(false), this.mImagesAdapter.getDegrees(), this.mImagesAdapter.getRotateAnimation(), ImageLoaderFactory.getImageLoader(getActivity()), getActivity().getIntent().getBooleanExtra("GALLERY_ENABLED", true));
        this.mCoolDragAndDropGridView.setAdapter(this.mImagesAdapter);
        setHintVisibility();
        this.keepScrolling = false;
    }

    private void setHintVisibility() {
        if (isHintRead()) {
            this.dragDropHint.setVisibility(8);
        } else if (this.mImagesAdapter != null) {
            this.dragDropHint.setVisibility(this.mImagesAdapter.getCount() != 0 ? 0 : 4);
        }
    }

    public void rotateViews(float f, float f2) {
        this.mImagesAdapter.rotateImages(f, f2, this.mCoolDragAndDropGridView.getStartingPosition());
        if (isHintRead()) {
            this.dragDropHint.setVisibility(8);
            return;
        }
        TextView textView = this.dragDropHint;
        int i = (f2 > 0.0f || f2 < 0.0f || this.mImagesAdapter.getCount() == 0) ? 4 : 0;
        textView.setVisibility(i);
    }

    public void onDragItem(int i) {
    }

    public void onDraggingItem(int i, int i2) {
    }

    public void onDropItem(int i, int i2) {
        markHintAsRead();
        setHintVisibility();
        this.keepScrolling = false;
        if (i != i2) {
            getSelectedImagesFromActivity(false).add(i2, getSelectedImagesFromActivity(false).remove(i));
        }
        this.mImagesAdapter.setAllItemsVisible();
        recreateAdapter();
    }

    public boolean isDragAndDropEnabled(int i) {
        return true;
    }

    public void onEdgeXReached(boolean z) {
        this.keepScrolling = true;
        autoSmoothScroll(z);
    }

    public void onEdgeXLeft() {
        this.keepScrolling = false;
    }

    public void onResume() {
        super.onResume();
        recreateAdapter();
    }

    public void onRemoveItem(int i) {
        if (i > -1) {
            new ImageStorageImpl(getActivity().getIntent().getStringExtra("STORAGE_DIR")).deleteImage(((Image) getSelectedImagesFromActivity(false).remove(i)).getModifiedFilePath());
            recreateAdapter();
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (getSelectedImagesFromActivity(true).isEmpty()) {
            this.mCoolDragAndDropGridView.destroyDragImageView();
            return;
        }
        getActivity().startActivityForResult(ImageGalleryActivity.createIntent(getActivity(), i, getSelectedImagesFromActivity(true), getActivity().getIntent().getStringExtra("STORAGE_DIR"), getActivity().getIntent().getBooleanExtra("GALLERY_ENABLED", true), getActivity().getIntent().getIntExtra("LONGEST_DIMENSION", EbkImagePicker.DEFAULT_LONGEST_IMAGE_DIMENSION)), EbkImagePickerActivity.REQUEST_CODE_IMAGE_GALLERY);
    }

    public void onPause() {
        super.onPause();
        this.mCoolDragAndDropGridView.destroyDragImageView();
    }

    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.mCoolDragAndDropGridView.startDragAndDrop();
        return false;
    }

    public void autoSmoothScroll(boolean z) {
        this.scrollView.postDelayed(new 2(this, z), 20);
    }

    public void smoothScrollRight() {
        this.scrollView.postDelayed(new 3(this), 1000);
    }
}
