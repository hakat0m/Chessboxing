package com.ebay.kleinanzeigen.imagepicker.image_library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.ebay.kleinanzeigen.imagepicker.R$id;
import com.ebay.kleinanzeigen.imagepicker.R$layout;
import com.ebay.kleinanzeigen.imagepicker.storage.ImageStorageImpl;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import java.util.List;
import uk.co.senab.photoview.IPhotoView;

public class ImageLibraryAdapter extends BaseAdapter {
    private Context context;
    private List<Image> imageList;
    private ImageLoader imageLoader;
    private int maxSelection;
    private ArrayList<Image> selectedImages;
    private String storageDirectory;

    public ImageLibraryAdapter(Context context, List<Image> list, ImageLoader imageLoader, ArrayList<Image> arrayList, int i, String str) {
        this.context = context;
        this.selectedImages = arrayList;
        this.maxSelection = i;
        this.context = context.getApplicationContext();
        this.imageList = list;
        this.imageLoader = imageLoader;
        this.storageDirectory = str;
    }

    public int getCount() {
        return this.imageList.size();
    }

    public Image getItem(int i) {
        return (Image) this.imageList.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(R$layout.griditem_image, null);
            ViewHolder viewHolder2 = new ViewHolder(this);
            viewHolder2.image = (ImageView) view.findViewById(R$id.grid_item_image);
            viewHolder2.selection = (ImageView) view.findViewById(R$id.grid_item_check);
            view.setTag(viewHolder2);
            viewHolder = viewHolder2;
        } else {
            viewHolder = (ViewHolder) view.getTag();
            this.imageLoader.cancelDisplayTask(viewHolder.image);
        }
        this.imageLoader.displayImage("file://" + getItem(i).getOriginalFilePath(), viewHolder.image);
        viewHolder.selection.setVisibility(getItem(i).isSelected() ? 0 : 8);
        viewHolder.image.setAlpha(getItem(i).isSelected() ? 0.5f : IPhotoView.DEFAULT_MIN_SCALE);
        viewHolder.image.setOnClickListener(new LibraryImageTouchListener(this, getItem(i), viewHolder.selection, viewHolder.image));
        return view;
    }

    public void recreate(String str) {
        this.selectedImages.removeAll(this.selectedImages);
        notifyDataSetChanged();
        new ImageStorageImpl(str).deleteAllModifiedFiles();
    }
}
