package com.ebay.kleinanzeigen.imagepicker.image_library;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import com.ebay.kleinanzeigen.imagepicker.EbkImagePicker;
import com.ebay.kleinanzeigen.imagepicker.R$id;
import com.ebay.kleinanzeigen.imagepicker.R$layout;
import com.ebay.kleinanzeigen.imagepicker.R$string;
import com.ebay.kleinanzeigen.imagepicker.platform.ImageLoaderFactory;
import com.gumtree.android.dfp.DFPProcessor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageLibraryFoldersActivity extends ActionBarActivity {
    public static final String CAMERA_FOLDER_PATH = (Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/Camera");
    private List<Folder> folderList;
    private ImageLibraryFoldersAdapter foldersAdapter;
    private GridView imageGrid;
    private ArrayList<Image> selectedImages;

    class FolderClickListener implements OnItemClickListener {
        private FolderClickListener() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            ImageLibraryFoldersActivity.this.startActivityForResult(ImageLibraryActivity.createIntent(ImageLibraryFoldersActivity.this, ImageLibraryFoldersActivity.this.getIntent().getIntExtra("MAX_SELECTION", 1), ImageLibraryFoldersActivity.this.selectedImages, ImageLibraryFoldersActivity.this.getIntent().getStringExtra("STORAGE_DIR"), ((Folder) ImageLibraryFoldersActivity.this.folderList.get(i)).getFolderStoragePath(), ((Folder) ImageLibraryFoldersActivity.this.folderList.get(i)).isRoot(), ImageLibraryFoldersActivity.this.getIntent().getIntExtra("LONGEST_DIMENSION", EbkImagePicker.DEFAULT_LONGEST_IMAGE_DIMENSION)), 100);
        }
    }

    public static Intent createIntent(Context context, int i, List<Image> list, String str, int i2) {
        Intent intent = new Intent(context, ImageLibraryFoldersActivity.class);
        intent.putExtra("EXTRA_IMAGE_LIST", new ArrayList(list));
        intent.putExtra("MAX_SELECTION", i);
        intent.putExtra("STORAGE_DIR", str);
        intent.putExtra("LONGEST_DIMENSION", i2);
        return intent;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R$layout.activity_image_library);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.selectedImages = (ArrayList) getIntent().getSerializableExtra("EXTRA_IMAGE_LIST");
        this.imageGrid = (GridView) findViewById(R$id.image_library_grid);
        this.imageGrid.setOnItemClickListener(new FolderClickListener());
        loadFolders();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                setResultAndFinish();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void onBackPressed() {
        setResultAndFinish();
    }

    private void setResultAndFinish() {
        Intent intent = new Intent();
        intent.putExtra("EXTRA_IMAGE_LIST", this.selectedImages);
        setResult(-1, intent);
        finish();
    }

    private void loadFolders() {
        Cursor query = getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "_id", "_display_name", "bucket_display_name"}, null, null, "datetaken DESC");
        if (query != null && query.moveToFirst()) {
            int columnIndex = query.getColumnIndex("_data");
            int columnIndex2 = query.getColumnIndex("bucket_display_name");
            Folder folder = new Folder(getString(R$string.ebk_image_picker_all_photos_folder_name), DFPProcessor.SEPARATOR, query.getString(columnIndex), 0);
            Map hashMap = new HashMap();
            do {
                folder.increaseImageCount();
                String string = query.getString(columnIndex);
                String substring = string.substring(0, string.lastIndexOf(47));
                if (hashMap.containsKey(substring)) {
                    ((Folder) hashMap.get(substring)).increaseImageCount();
                } else {
                    hashMap.put(substring, new Folder(query.getString(columnIndex2), substring, string, 1));
                }
            } while (query.moveToNext());
            this.folderList = new ArrayList();
            if (hashMap.containsKey(CAMERA_FOLDER_PATH)) {
                Folder folder2 = (Folder) hashMap.get(CAMERA_FOLDER_PATH);
                folder2.setFolderName(getString(R$string.ebk_image_picker_camera_folder_name));
                hashMap.remove(CAMERA_FOLDER_PATH);
                this.folderList.add(folder2);
            }
            this.folderList.add(folder);
            this.folderList.addAll(getSortedFolderList(hashMap));
            displayImages();
        }
    }

    private ArrayList<Folder> getSortedFolderList(Map<String, Folder> map) {
        Object arrayList = new ArrayList(map.values());
        Collections.sort(arrayList);
        return arrayList;
    }

    private void displayImages() {
        if (this.foldersAdapter == null) {
            this.foldersAdapter = new ImageLibraryFoldersAdapter(this, this.folderList, ImageLoaderFactory.getImageLoader(this));
            this.imageGrid.setAdapter(this.foldersAdapter);
            return;
        }
        this.foldersAdapter.notifyDataSetChanged();
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i == 100) {
            this.selectedImages = (ArrayList) intent.getSerializableExtra("EXTRA_IMAGE_LIST");
            if (i2 == -1) {
                setResultAndFinish();
            }
        }
    }
}
