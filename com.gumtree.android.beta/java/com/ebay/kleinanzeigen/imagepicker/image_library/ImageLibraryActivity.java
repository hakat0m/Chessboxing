package com.ebay.kleinanzeigen.imagepicker.image_library;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.widget.GridView;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.kleinanzeigen.imagepicker.EbkImagePicker;
import com.ebay.kleinanzeigen.imagepicker.EbkImagePickerActivity;
import com.ebay.kleinanzeigen.imagepicker.R$id;
import com.ebay.kleinanzeigen.imagepicker.R$layout;
import com.ebay.kleinanzeigen.imagepicker.R$string;
import com.ebay.kleinanzeigen.imagepicker.platform.DoneActivity;
import com.ebay.kleinanzeigen.imagepicker.platform.ImageLoaderFactory;
import com.ebay.kleinanzeigen.imagepicker.platform.LOG;
import com.ebay.kleinanzeigen.imagepicker.storage.ImageStorage;
import com.ebay.kleinanzeigen.imagepicker.storage.ImageStorageImpl;
import com.gumtree.android.dfp.DFPProcessor;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImageLibraryActivity extends DoneActivity {
    private static final String DIALOG_PRESENT = "DIALOG_PRESENT";
    private static final String EXTRA_FOLDER_PATH = "FOLDER_PATH";
    private static final String EXTRA_INCLUDE_SUBFOLDERS = "INCLUDE_SUBFOLDERS";
    private static final int PHOTO_LIBRARY_LOADER = 1;
    private ProgressDialog creatingPrivateCopiesDialog;
    private boolean dialogPresent;
    private ImageLibraryAdapter imageAdapter;
    private GridView imageGrid;
    private List<Image> imageList;
    private int imageLongestDimension;
    private int maxSelection;
    private ArrayList<Image> selectedImages;

    class CreatePrivateCopiesTask extends AsyncTask<Void, Void, Void> {
        private int result;

        public CreatePrivateCopiesTask(int i) {
            this.result = i;
        }

        protected void onPreExecute() {
            ImageLibraryActivity.this.creatingPrivateCopiesDialog = ProgressDialog.show(ImageLibraryActivity.this, null, ImageLibraryActivity.this.getString(R$string.ebk_image_picker_optimizing_images), true, false);
            ImageLibraryActivity.this.creatingPrivateCopiesDialog.setCancelable(false);
            ImageLibraryActivity.this.creatingPrivateCopiesDialog.show();
            ImageLibraryActivity.this.dialogPresent = true;
        }

        protected Void doInBackground(Void... voidArr) {
            List<Image> arrayList = new ArrayList();
            Iterator it = ImageLibraryActivity.this.selectedImages.iterator();
            while (it.hasNext()) {
                Image image = (Image) it.next();
                if (TextUtils.isEmpty(image.getModifiedFilePath()) && image.getOriginalFilePath() != null) {
                    ImageStorage imageStorageImpl = new ImageStorageImpl(ImageLibraryActivity.this.getIntent().getStringExtra("STORAGE_DIR"));
                    Object makeCopyForModification = imageStorageImpl.makeCopyForModification(image.getOriginalFilePath());
                    if (TextUtils.isEmpty(makeCopyForModification)) {
                        arrayList.add(image);
                    } else {
                        image.setModifiedFilePath(makeCopyForModification);
                        Bitmap loadImageSync = ImageLoaderFactory.getImageLoader(ImageLibraryActivity.this).loadImageSync("file://" + image.getFinalPath(), new ImageSize(ImageLibraryActivity.this.imageLongestDimension, ImageLibraryActivity.this.imageLongestDimension));
                        int access$600 = ImageLibraryActivity.this.imageLongestDimension;
                        int access$6002 = ImageLibraryActivity.this.imageLongestDimension;
                        if (loadImageSync.getWidth() > access$600 || loadImageSync.getHeight() > access$6002) {
                            if (loadImageSync.getWidth() > loadImageSync.getHeight()) {
                                access$6002 = (loadImageSync.getHeight() * access$600) / loadImageSync.getWidth();
                            } else {
                                access$600 = (loadImageSync.getWidth() * access$6002) / loadImageSync.getHeight();
                            }
                            imageStorageImpl.storeImage(Bitmap.createScaledBitmap(loadImageSync, access$600, access$6002, true), new File(image.getFinalPath()).getName(), true, true);
                        } else {
                            imageStorageImpl.storeImage(loadImageSync, new File(image.getFinalPath()).getName(), true, true);
                        }
                        loadImageSync.recycle();
                    }
                }
            }
            for (Image image2 : arrayList) {
                ImageLibraryActivity.this.selectedImages.remove(image2);
            }
            arrayList.clear();
            return null;
        }

        protected void onPostExecute(Void voidR) {
            Intent intent = new Intent();
            intent.putExtra("EXTRA_IMAGE_LIST", ImageLibraryActivity.this.selectedImages);
            ImageLibraryActivity.this.setResult(this.result, intent);
            if (ImageLibraryActivity.this.creatingPrivateCopiesDialog != null) {
                ImageLibraryActivity.this.creatingPrivateCopiesDialog.dismiss();
            }
            ImageLibraryActivity.this.dialogPresent = false;
            ImageLoaderFactory.clearCaches();
            ImageLibraryActivity.this.finish();
        }
    }

    class PhotoLibraryLoaderCallback implements LoaderCallbacks<Cursor> {
        private PhotoLibraryLoaderCallback() {
        }

        public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
            String[] strArr = new String[]{"_id", "_data"};
            String stringExtra = ImageLibraryActivity.this.getIntent().getStringExtra(ImageLibraryActivity.EXTRA_FOLDER_PATH);
            int length = stringExtra.length() - stringExtra.replace(DFPProcessor.SEPARATOR, BuildConfig.FLAVOR).length();
            String str = "_data LIKE '" + stringExtra + "%'";
            if (!ImageLibraryActivity.this.getIntent().getBooleanExtra(ImageLibraryActivity.EXTRA_INCLUDE_SUBFOLDERS, false)) {
                str = str + " AND (SELECT LENGTH(_data) - LENGTH(REPLACE(_data, '/', ''))) = " + (length + ImageLibraryActivity.PHOTO_LIBRARY_LOADER);
            }
            return new CursorLoader(ImageLibraryActivity.this, Media.EXTERNAL_CONTENT_URI, strArr, str, null, "datetaken DESC");
        }

        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            ImageLibraryActivity.this.imageList = new ArrayList();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Image image = new Image();
                    image.setOriginalFilePath(cursor.getString(cursor.getColumnIndex("_data")));
                    ImageLibraryActivity.this.imageList.add(image);
                } while (cursor.moveToNext());
                LOG.info(ImageLibraryActivity.this.imageList.size() + " Images Loaded");
                ImageLibraryActivity.this.displayImages();
            }
        }

        public void onLoaderReset(Loader<Cursor> loader) {
            ImageLibraryActivity.this.imageList = new ArrayList();
        }
    }

    public static Intent createIntent(Context context, int i, List<Image> list, String str, String str2, boolean z, int i2) {
        Intent intent = new Intent(context, ImageLibraryActivity.class);
        intent.putExtra("EXTRA_IMAGE_LIST", new ArrayList(list));
        intent.putExtra("MAX_SELECTION", i);
        intent.putExtra("STORAGE_DIR", str);
        intent.putExtra(EXTRA_FOLDER_PATH, str2);
        intent.putExtra(EXTRA_INCLUDE_SUBFOLDERS, z);
        intent.putExtra("LONGEST_DIMENSION", i2);
        return intent;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R$layout.activity_image_library);
        this.imageGrid = (GridView) findViewById(R$id.image_library_grid);
        getSupportLoaderManager().initLoader(PHOTO_LIBRARY_LOADER, null, new PhotoLibraryLoaderCallback());
        if (getIntent().getSerializableExtra("EXTRA_IMAGE_LIST") != null) {
            this.selectedImages = (ArrayList) getIntent().getSerializableExtra("EXTRA_IMAGE_LIST");
        } else {
            this.selectedImages = new ArrayList();
        }
        this.imageLongestDimension = getIntent().getIntExtra("LONGEST_DIMENSION", EbkImagePicker.DEFAULT_LONGEST_IMAGE_DIMENSION);
        this.selectedImages = EbkImagePickerActivity.cleanImages(this.selectedImages);
        this.maxSelection = getIntent().getIntExtra("MAX_SELECTION", PHOTO_LIBRARY_LOADER);
        showDialogIfWasPresent(bundle);
    }

    private void showDialogIfWasPresent(Bundle bundle) {
        if (bundle != null && bundle.getBoolean(DIALOG_PRESENT)) {
            this.creatingPrivateCopiesDialog = ProgressDialog.show(this, null, getString(R$string.ebk_image_picker_optimizing_images), true, false);
            this.creatingPrivateCopiesDialog.setCancelable(false);
            this.dialogPresent = true;
            this.creatingPrivateCopiesDialog.show();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onPause() {
        super.onPause();
        if (this.creatingPrivateCopiesDialog != null) {
            this.creatingPrivateCopiesDialog.dismiss();
        }
    }

    protected void onDone() {
        setResultAndFinish(-1);
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putBoolean(DIALOG_PRESENT, this.dialogPresent);
    }

    public void onBackPressed() {
        setResultAndFinish(0);
    }

    private void setResultAndFinish(int i) {
        new CreatePrivateCopiesTask(i).execute(new Void[0]);
    }

    private void displayImages() {
        mergeSelection();
        if (this.imageAdapter == null) {
            this.imageAdapter = new ImageLibraryAdapter(this, this.imageList, ImageLoaderFactory.getImageLoader(this), this.selectedImages, this.maxSelection, getIntent().getStringExtra("STORAGE_DIR"));
            this.imageGrid.setAdapter(this.imageAdapter);
            return;
        }
        this.imageAdapter.notifyDataSetChanged();
    }

    private void mergeSelection() {
        for (Image image : this.imageList) {
            if (this.selectedImages.contains(image)) {
                image.setSelected(true);
            } else {
                image.setSelected(false);
            }
        }
    }
}
