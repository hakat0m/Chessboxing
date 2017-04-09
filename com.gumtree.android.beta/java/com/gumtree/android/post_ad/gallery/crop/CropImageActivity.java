package com.gumtree.android.post_ad.gallery.crop;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import com.ebay.kleinanzeigen.imagepicker.R$id;
import com.gumtree.android.common.activities.BaseEditActivity;
import com.gumtree.android.common.utils.BitmapUtil;
import com.gumtree.android.common.utils.Log;
import uk.co.senab.photoview.IPhotoView;

public class CropImageActivity extends BaseEditActivity {
    private static final int CROP_QUALITY = 75;
    private static final String FILE = "file";
    private static final int NINE = 9;
    private static final int NINETY_DEGREES = 90;
    private static final int ONE_HUNDRED_EIGHTY_DEGREES = 180;
    private static final int OUTPUT_X_DEFAULT_VALUE = 1024;
    private static final int OUTPUT_Y_DEFAULT_VALUE = 1024;
    private static final int TEN = 10;
    private static final int TWO_HUNDRED_SEVENTY_DEGREES = 270;
    private static final int WIDTH_DEVICE_RESOLUTION = 1280;
    private Bitmap mBitmap;
    private HighlightView mCrop;
    private boolean mMaintainAspectRatio;
    private CompressFormat mOutputFormat = CompressFormat.JPEG;
    private int mOutputX;
    private int mOutputY;
    private Uri mSaveUri;
    boolean mSaving;
    private boolean mScale;

    private static int getExifOrientationInDegrees(int i) {
        if (i == 6) {
            return NINETY_DEGREES;
        }
        if (i == 3) {
            return ONE_HUNDRED_EIGHTY_DEGREES;
        }
        if (i == 8) {
            return TWO_HUNDRED_SEVENTY_DEGREES;
        }
        return 0;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            this.mOutputX = extras.getInt("outputX", OUTPUT_Y_DEFAULT_VALUE);
            this.mOutputY = extras.getInt("outputY", OUTPUT_Y_DEFAULT_VALUE);
            this.mScale = extras.getBoolean("scaleDownToOutputSize", true);
            extras.getBoolean("scaleUpToOutputSize", false);
            this.mMaintainAspectRatio = extras.getBoolean("maintainAspectRatio", true);
            this.mSaveUri = (Uri) extras.getParcelable("output");
        }
        this.mBitmap = loadBitmapUsingContentResolverStream(intent.getData());
        if (this.mBitmap == null) {
            Log.v("failed loading bitmap - finishing");
            finish();
            return;
        }
        setContentView(2130903076);
        findViewById(2131624126).setOnClickListener(CropImageActivity$$Lambda$1.lambdaFactory$(this));
        findViewById(2131624125).setOnClickListener(CropImageActivity$$Lambda$2.lambdaFactory$(this));
        setupImageView();
    }

    /* synthetic */ void lambda$onCreate$0(View view) {
        rotateRight();
    }

    /* synthetic */ void lambda$onCreate$1(View view) {
        rotateLeft();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void rotateLeft() {
        this.mBitmap = BitmapUtil.rotateLeft(this.mBitmap);
        setupImageView();
    }

    private void rotateRight() {
        this.mBitmap = BitmapUtil.rotateRight(this.mBitmap);
        setupImageView();
    }

    private Bitmap loadBitmapUsingContentResolverStream(Uri uri) {
        Bitmap decodeStream;
        Throwable e;
        try {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
            options.inPurgeable = true;
            options.inInputShareable = true;
            options.inJustDecodeBounds = false;
            options.inSampleSize = calculateSampleSizeForTargetOf1024(options.outWidth, options.outHeight);
            decodeStream = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, options);
            try {
                int rotation = getRotation(uri);
                if (rotation == 0) {
                    return decodeStream;
                }
                Matrix matrix = new Matrix();
                matrix.preRotate((float) rotation);
                Bitmap createBitmap = Bitmap.createBitmap(decodeStream, 0, 0, decodeStream.getWidth(), decodeStream.getHeight(), matrix, true);
                decodeStream.recycle();
                return createBitmap;
            } catch (Exception e2) {
                e = e2;
                Log.e("Problem loading image", e);
                return decodeStream;
            }
        } catch (Throwable e3) {
            Throwable th = e3;
            decodeStream = null;
            e = th;
            Log.e("Problem loading image", e);
            return decodeStream;
        }
    }

    public int getRotation(Uri uri) {
        if ("content".equals(uri.getScheme())) {
            Cursor query = getContentResolver().query(uri, new String[]{"orientation"}, null, null, null);
            if (query.moveToFirst()) {
                return query.getInt(0);
            }
        } else if (FILE.equals(uri.getScheme())) {
            try {
                return getExifOrientationInDegrees(new ExifInterface(uri.getPath()).getAttributeInt("Orientation", 1));
            } catch (Throwable e) {
                Log.e("Problem getting rotation", e);
            }
        }
        return 0;
    }

    private int calculateSampleSizeForTargetOf1024(int i, int i2) {
        return Math.max(calculateSampleSizeForTargetOf1024(i), calculateSampleSizeForTargetOf1024(i2));
    }

    private int calculateSampleSizeForTargetOf1024(int i) {
        int i2 = 1;
        while (i / i2 > WIDTH_DEVICE_RESOLUTION) {
            i2++;
        }
        return i2;
    }

    protected boolean onSave() {
        if (!this.mSaving) {
            Bitmap createBitmapInCropSize;
            this.mSaving = true;
            if (this.mOutputX == 0 || this.mOutputY == 0 || this.mScale) {
                createBitmapInCropSize = createBitmapInCropSize();
            } else {
                createBitmapInCropSize = fillBitmapInto();
            }
            ((CropImageView) findViewById(R$id.image)).clear();
            recycleBitmap();
            new AsyncTask<Bitmap, Void, Void>() {
                protected Void doInBackground(Bitmap... bitmapArr) {
                    Bitmap bitmap = bitmapArr[0];
                    CropImageActivity.this.saveOutput(bitmap);
                    bitmap.recycle();
                    return null;
                }

                protected void onPostExecute(Void voidR) {
                    CropImageActivity.this.finish();
                }
            }.execute(new Bitmap[]{createBitmapInCropSize});
        }
        return false;
    }

    protected void onCancel() {
        super.onCancel();
        finish();
    }

    private void recycleBitmap() {
        if (this.mBitmap != null && !this.mBitmap.isRecycled()) {
            this.mBitmap.recycle();
        }
    }

    private Bitmap createBitmapInCropSize() {
        Rect cropRect = this.mCrop.getCropRect();
        int width = cropRect.width();
        int height = cropRect.height();
        Bitmap createBitmap = Bitmap.createBitmap(width, height, Config.RGB_565);
        new Canvas(createBitmap).drawBitmap(this.mBitmap, cropRect, new Rect(0, 0, width, height), null);
        return createBitmap;
    }

    private Bitmap fillBitmapInto() {
        Bitmap createBitmap = Bitmap.createBitmap(this.mOutputX, this.mOutputY, Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        Rect cropRect = this.mCrop.getCropRect();
        Rect rect = new Rect(0, 0, this.mOutputX, this.mOutputY);
        int width = (cropRect.width() - rect.width()) / 2;
        int height = (cropRect.height() - rect.height()) / 2;
        cropRect.inset(Math.max(0, width), Math.max(0, height));
        rect.inset(Math.max(0, -width), Math.max(0, -height));
        canvas.drawBitmap(this.mBitmap, cropRect, rect, null);
        return createBitmap;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void saveOutput(android.graphics.Bitmap r5) {
        /*
        r4 = this;
        r0 = r4.mSaveUri;
        if (r0 == 0) goto L_0x006a;
    L_0x0004:
        r1 = 0;
        r0 = com.gumtree.android.common.utils.Log.verboseLoggingEnabled();	 Catch:{ IOException -> 0x0076 }
        if (r0 == 0) goto L_0x0023;
    L_0x000b:
        r0 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0076 }
        r0.<init>();	 Catch:{ IOException -> 0x0076 }
        r2 = "target save uri is: ";
        r0 = r0.append(r2);	 Catch:{ IOException -> 0x0076 }
        r2 = r4.mSaveUri;	 Catch:{ IOException -> 0x0076 }
        r0 = r0.append(r2);	 Catch:{ IOException -> 0x0076 }
        r0 = r0.toString();	 Catch:{ IOException -> 0x0076 }
        com.gumtree.android.common.utils.Log.v(r0);	 Catch:{ IOException -> 0x0076 }
    L_0x0023:
        r0 = "file";
        r2 = r4.mSaveUri;	 Catch:{ IOException -> 0x0076 }
        r2 = r2.getScheme();	 Catch:{ IOException -> 0x0076 }
        r0 = r0.equals(r2);	 Catch:{ IOException -> 0x0076 }
        if (r0 == 0) goto L_0x006b;
    L_0x0031:
        r2 = new java.io.File;	 Catch:{ IOException -> 0x0076 }
        r0 = r4.mSaveUri;	 Catch:{ IOException -> 0x0076 }
        r0 = r0.toString();	 Catch:{ IOException -> 0x0076 }
        r0 = java.net.URI.create(r0);	 Catch:{ IOException -> 0x0076 }
        r2.<init>(r0);	 Catch:{ IOException -> 0x0076 }
        r0 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x0076 }
        r0.<init>(r2);	 Catch:{ IOException -> 0x0076 }
        r1 = r0;
    L_0x0046:
        if (r1 == 0) goto L_0x004f;
    L_0x0048:
        r0 = r4.mOutputFormat;	 Catch:{ IOException -> 0x0076 }
        r2 = 75;
        r5.compress(r0, r2, r1);	 Catch:{ IOException -> 0x0076 }
    L_0x004f:
        com.gumtree.android.common.utils.io.Closeables.closeQuietly(r1);
    L_0x0052:
        r0 = new android.os.Bundle;
        r0.<init>();
        r1 = -1;
        r2 = new android.content.Intent;
        r3 = r4.mSaveUri;
        r3 = r3.toString();
        r2.<init>(r3);
        r0 = r2.putExtras(r0);
        r4.setResult(r1, r0);
    L_0x006a:
        return;
    L_0x006b:
        r0 = r4.getContentResolver();	 Catch:{ IOException -> 0x0076 }
        r2 = r4.mSaveUri;	 Catch:{ IOException -> 0x0076 }
        r1 = r0.openOutputStream(r2);	 Catch:{ IOException -> 0x0076 }
        goto L_0x0046;
    L_0x0076:
        r0 = move-exception;
        r2 = "Ptoblem saving cropping image";
        com.gumtree.android.common.utils.Log.e(r2, r0);	 Catch:{ all -> 0x0080 }
        com.gumtree.android.common.utils.io.Closeables.closeQuietly(r1);
        goto L_0x0052;
    L_0x0080:
        r0 = move-exception;
        com.gumtree.android.common.utils.io.Closeables.closeQuietly(r1);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.gumtree.android.post_ad.gallery.crop.CropImageActivity.saveOutput(android.graphics.Bitmap):void");
    }

    private void setupImageView() {
        CropImageView cropImageView = (CropImageView) findViewById(R$id.image);
        cropImageView.setImageBitmapResetBase(this.mBitmap, true);
        if (cropImageView.getScale() == IPhotoView.DEFAULT_MIN_SCALE) {
            cropImageView.center(true, true);
        }
        HighlightView highlightView = new HighlightView(cropImageView);
        RectF calculateCropRect = calculateCropRect();
        highlightView.setup(cropImageView.getImageMatrix(), new Rect(0, 0, this.mBitmap.getWidth(), this.mBitmap.getHeight()), calculateCropRect, this.mMaintainAspectRatio);
        cropImageView.set(highlightView);
        cropImageView.invalidate();
        this.mCrop = (HighlightView) cropImageView.mHighlightViews.get(0);
        this.mCrop.setFocus(true);
    }

    private RectF calculateCropRect() {
        int width = this.mBitmap.getWidth();
        int height = this.mBitmap.getHeight();
        if (height <= width) {
            int i = height;
            height = width;
            width = i;
        }
        int i2 = this.mMaintainAspectRatio ? this.mOutputX : height;
        int i3 = this.mMaintainAspectRatio ? this.mOutputY : width;
        while (true) {
            if (i2 >= this.mBitmap.getWidth() || i3 >= this.mBitmap.getHeight()) {
                i2 = (i2 * NINE) / TEN;
                i3 = (i3 * NINE) / TEN;
            } else {
                height = (height - i2) / 2;
                width = (width - i3) / 2;
                return new RectF((float) height, (float) width, (float) (height + i2), (float) (width + i3));
            }
        }
    }
}
