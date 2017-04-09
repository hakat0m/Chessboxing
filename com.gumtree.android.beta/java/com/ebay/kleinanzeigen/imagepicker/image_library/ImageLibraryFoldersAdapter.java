package com.ebay.kleinanzeigen.imagepicker.image_library;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ebay.kleinanzeigen.imagepicker.R$id;
import com.ebay.kleinanzeigen.imagepicker.R$layout;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.List;

public class ImageLibraryFoldersAdapter extends BaseAdapter {
    private Context context;
    private List<Folder> folderList;
    private ImageLoader imageLoader;

    public ImageLibraryFoldersAdapter(Context context, List<Folder> list, ImageLoader imageLoader) {
        this.context = context;
        this.folderList = list;
        this.imageLoader = imageLoader;
    }

    public int getCount() {
        return this.folderList.size();
    }

    public Folder getItem(int i) {
        return (Folder) this.folderList.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(R$layout.griditem_folder, null);
            ViewHolder viewHolder2 = new ViewHolder(this);
            viewHolder2.folderImage = (ImageView) view.findViewById(R$id.grid_item_folder_image);
            viewHolder2.folderName = (TextView) view.findViewById(R$id.grid_item_folder_name);
            view.setTag(viewHolder2);
            viewHolder = viewHolder2;
        } else {
            viewHolder = (ViewHolder) view.getTag();
            this.imageLoader.cancelDisplayTask(viewHolder.folderImage);
        }
        this.imageLoader.displayImage("file://" + getItem(i).getFolderThumbnailPath(), viewHolder.folderImage);
        viewHolder.folderName.setText(getItem(i).getFolderName() + " (" + getItem(i).getImageCount() + ")");
        return view;
    }
}
