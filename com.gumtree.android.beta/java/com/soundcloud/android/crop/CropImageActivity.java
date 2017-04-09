package com.soundcloud.android.crop;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.opengl.GLES10;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import com.soundcloud.android.crop.ImageViewTouchBase.Recycler;
import com.soundcloud.android.crop.MonitoredActivity.LifeCycleListener;
import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class CropImageActivity extends MonitoredActivity {
    private static final int SIZE_DEFAULT = 2048;
    private static final int SIZE_LIMIT = 4096;
    private int aspectX;
    private int aspectY;
    private HighlightView cropView;
    private int exifRotation;
    private final Handler handler = new Handler();
    private CropImageView imageView;
    private boolean isSaving;
    private int maxX;
    private int maxY;
    private RotateBitmap rotateBitmap;
    private int sampleSize;
    private Uri saveUri;
    private Uri sourceUri;

    public /* bridge */ /* synthetic */ void addLifeCycleListener(LifeCycleListener lifeCycleListener) {
        super.addLifeCycleListener(lifeCycleListener);
    }

    public /* bridge */ /* synthetic */ void removeLifeCycleListener(LifeCycleListener lifeCycleListener) {
        super.removeLifeCycleListener(lifeCycleListener);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setupWindowFlags();
        setupViews();
        loadInput();
        if (this.rotateBitmap == null) {
            finish();
        } else {
            startCrop();
        }
    }

    @TargetApi(19)
    private void setupWindowFlags() {
        requestWindowFeature(1);
        if (VERSION.SDK_INT >= 19) {
            getWindow().clearFlags(67108864);
        }
    }

    private void setupViews() {
        setContentView(R$layout.crop__activity_crop);
        this.imageView = (CropImageView) findViewById(R$id.crop_image);
        this.imageView.context = this;
        this.imageView.setRecycler(new Recycler() {
            public void recycle(Bitmap bitmap) {
                bitmap.recycle();
                System.gc();
            }
        });
        findViewById(R$id.btn_cancel).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CropImageActivity.this.setResult(0);
                CropImageActivity.this.finish();
            }
        });
        findViewById(R$id.btn_done).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                CropImageActivity.this.onSaveClicked();
            }
        });
    }

    private void loadInput() {
        Closeable closeable = null;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            this.aspectX = extras.getInt("aspect_x");
            this.aspectY = extras.getInt("aspect_y");
            this.maxX = extras.getInt("max_x");
            this.maxY = extras.getInt("max_y");
            this.saveUri = (Uri) extras.getParcelable("output");
        }
        this.sourceUri = intent.getData();
        if (this.sourceUri != null) {
            this.exifRotation = CropUtil.getExifRotation(CropUtil.getFromMediaUri(this, getContentResolver(), this.sourceUri));
            try {
                this.sampleSize = calculateBitmapSampleSize(this.sourceUri);
                closeable = getContentResolver().openInputStream(this.sourceUri);
                Options options = new Options();
                options.inSampleSize = this.sampleSize;
                this.rotateBitmap = new RotateBitmap(BitmapFactory.decodeStream(closeable, null, options), this.exifRotation);
            } catch (Throwable e) {
                Log.e("Error reading image: " + e.getMessage(), e);
                setResultException(e);
            } catch (Throwable e2) {
                Log.e("OOM reading image: " + e2.getMessage(), e2);
                setResultException(e2);
            } finally {
                CropUtil.closeSilently(closeable);
            }
        }
    }

    private int calculateBitmapSampleSize(Uri uri) throws IOException {
        Closeable closeable = null;
        int i = 1;
        Options options = new Options();
        options.inJustDecodeBounds = true;
        try {
            closeable = getContentResolver().openInputStream(uri);
            BitmapFactory.decodeStream(closeable, null, options);
            int maxImageSize = getMaxImageSize();
            while (true) {
                if (options.outHeight / i <= maxImageSize && options.outWidth / i <= maxImageSize) {
                    return i;
                }
                i <<= 1;
            }
        } finally {
            CropUtil.closeSilently(closeable);
        }
    }

    private int getMaxImageSize() {
        int maxTextureSize = getMaxTextureSize();
        if (maxTextureSize == 0) {
            return SIZE_DEFAULT;
        }
        return Math.min(maxTextureSize, SIZE_LIMIT);
    }

    private int getMaxTextureSize() {
        int[] iArr = new int[1];
        GLES10.glGetIntegerv(3379, iArr, 0);
        return iArr[0];
    }

    private void startCrop() {
        if (!isFinishing()) {
            this.imageView.setImageRotateBitmapResetBase(this.rotateBitmap, true);
            CropUtil.startBackgroundJob(this, null, getResources().getString(R$string.crop__wait), new Runnable() {
                public void run() {
                    CountDownLatch countDownLatch = new CountDownLatch(1);
                    CropImageActivity.this.handler.post(new 1(this, countDownLatch));
                    try {
                        countDownLatch.await();
                        new Cropper(CropImageActivity.this, null).crop();
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                }
            }, this.handler);
        }
    }

    private void onSaveClicked() {
        if (this.cropView != null && !this.isSaving) {
            this.isSaving = true;
            Rect scaledCropRect = this.cropView.getScaledCropRect((float) this.sampleSize);
            int width = scaledCropRect.width();
            int height = scaledCropRect.height();
            if (this.maxX > 0 && this.maxY > 0 && (width > this.maxX || height > this.maxY)) {
                float f = ((float) width) / ((float) height);
                if (((float) this.maxX) / ((float) this.maxY) > f) {
                    height = this.maxY;
                    width = (int) ((((float) this.maxY) * f) + 0.5f);
                } else {
                    width = this.maxX;
                    height = (int) ((((float) this.maxX) / f) + 0.5f);
                }
            }
            try {
                Bitmap decodeRegionCrop = decodeRegionCrop(scaledCropRect, width, height);
                if (decodeRegionCrop != null) {
                    this.imageView.setImageRotateBitmapResetBase(new RotateBitmap(decodeRegionCrop, this.exifRotation), true);
                    this.imageView.center();
                    this.imageView.highlightViews.clear();
                }
                saveImage(decodeRegionCrop);
            } catch (Throwable e) {
                setResultException(e);
                finish();
            }
        }
    }

    private void saveImage(final Bitmap bitmap) {
        if (bitmap != null) {
            CropUtil.startBackgroundJob(this, null, getResources().getString(R$string.crop__saving), new Runnable() {
                public void run() {
                    CropImageActivity.this.saveOutput(bitmap);
                }
            }, this.handler);
        } else {
            finish();
        }
    }

    private Bitmap decodeRegionCrop(Rect rect, int i, int i2) {
        Closeable openInputStream;
        int width;
        int height;
        Bitmap decodeRegion;
        Throwable e;
        Closeable closeable;
        Throwable th;
        Throwable th2;
        Bitmap bitmap = null;
        float f = 0.0f;
        clearImageView();
        try {
            openInputStream = getContentResolver().openInputStream(this.sourceUri);
            try {
                BitmapRegionDecoder newInstance = BitmapRegionDecoder.newInstance(openInputStream, false);
                width = newInstance.getWidth();
                height = newInstance.getHeight();
                if (this.exifRotation != 0) {
                    Matrix matrix = new Matrix();
                    matrix.setRotate((float) (-this.exifRotation));
                    RectF rectF = new RectF();
                    matrix.mapRect(rectF, new RectF(rect));
                    float f2 = rectF.left < 0.0f ? (float) width : 0.0f;
                    if (rectF.top < 0.0f) {
                        f = (float) height;
                    }
                    rectF.offset(f2, f);
                    rect = new Rect((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
                }
                try {
                    decodeRegion = newInstance.decodeRegion(rect, new Options());
                } catch (IllegalArgumentException e2) {
                    e = e2;
                    try {
                        throw new IllegalArgumentException("Rectangle " + rect + " is outside of the image (" + width + "," + height + "," + this.exifRotation + ")", e);
                    } catch (Throwable e3) {
                        closeable = openInputStream;
                        Bitmap bitmap2 = bitmap;
                        th = e3;
                        decodeRegion = bitmap2;
                        try {
                            Log.e("Error cropping image: " + th.getMessage(), th);
                            setResultException(th);
                            CropUtil.closeSilently(closeable);
                            return decodeRegion;
                        } catch (Throwable th3) {
                            e3 = th3;
                            openInputStream = closeable;
                            CropUtil.closeSilently(openInputStream);
                            throw e3;
                        }
                    } catch (Throwable e32) {
                        th2 = e32;
                        decodeRegion = bitmap;
                        th = th2;
                        try {
                            Log.e("OOM cropping image: " + th.getMessage(), th);
                            setResultException(th);
                            CropUtil.closeSilently(openInputStream);
                            return decodeRegion;
                        } catch (Throwable th4) {
                            e32 = th4;
                            CropUtil.closeSilently(openInputStream);
                            throw e32;
                        }
                    }
                }
            } catch (Throwable e322) {
                closeable = openInputStream;
                th = e322;
                decodeRegion = null;
                Log.e("Error cropping image: " + th.getMessage(), th);
                setResultException(th);
                CropUtil.closeSilently(closeable);
                return decodeRegion;
            } catch (Throwable e3222) {
                th2 = e3222;
                decodeRegion = null;
                th = th2;
                Log.e("OOM cropping image: " + th.getMessage(), th);
                setResultException(th);
                CropUtil.closeSilently(openInputStream);
                return decodeRegion;
            }
            try {
                if (rect.width() > i || rect.height() > i2) {
                    Matrix matrix2 = new Matrix();
                    matrix2.postScale(((float) i) / ((float) rect.width()), ((float) i2) / ((float) rect.height()));
                    decodeRegion = Bitmap.createBitmap(decodeRegion, 0, 0, decodeRegion.getWidth(), decodeRegion.getHeight(), matrix2, true);
                }
                CropUtil.closeSilently(openInputStream);
            } catch (Throwable th5) {
                th2 = th5;
                bitmap = decodeRegion;
                e3222 = th2;
                throw new IllegalArgumentException("Rectangle " + rect + " is outside of the image (" + width + "," + height + "," + this.exifRotation + ")", e3222);
            } catch (IOException e4) {
                th5 = e4;
                closeable = openInputStream;
                Log.e("Error cropping image: " + th5.getMessage(), th5);
                setResultException(th5);
                CropUtil.closeSilently(closeable);
                return decodeRegion;
            } catch (OutOfMemoryError e5) {
                th5 = e5;
                Log.e("OOM cropping image: " + th5.getMessage(), th5);
                setResultException(th5);
                CropUtil.closeSilently(openInputStream);
                return decodeRegion;
            }
        } catch (Throwable e32222) {
            closeable = null;
            th5 = e32222;
            decodeRegion = null;
            Log.e("Error cropping image: " + th5.getMessage(), th5);
            setResultException(th5);
            CropUtil.closeSilently(closeable);
            return decodeRegion;
        } catch (Throwable e322222) {
            openInputStream = null;
            th5 = e322222;
            decodeRegion = null;
            Log.e("OOM cropping image: " + th5.getMessage(), th5);
            setResultException(th5);
            CropUtil.closeSilently(openInputStream);
            return decodeRegion;
        } catch (Throwable th6) {
            e322222 = th6;
            openInputStream = null;
            CropUtil.closeSilently(openInputStream);
            throw e322222;
        }
        return decodeRegion;
    }

    private void clearImageView() {
        this.imageView.clear();
        if (this.rotateBitmap != null) {
            this.rotateBitmap.recycle();
        }
        System.gc();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void saveOutput(final android.graphics.Bitmap r5) {
        /*
        r4 = this;
        r0 = r4.saveUri;
        if (r0 == 0) goto L_0x0037;
    L_0x0004:
        r1 = 0;
        r0 = r4.getContentResolver();	 Catch:{ IOException -> 0x0045 }
        r2 = r4.saveUri;	 Catch:{ IOException -> 0x0045 }
        r1 = r0.openOutputStream(r2);	 Catch:{ IOException -> 0x0045 }
        if (r1 == 0) goto L_0x0018;
    L_0x0011:
        r0 = android.graphics.Bitmap.CompressFormat.JPEG;	 Catch:{ IOException -> 0x0045 }
        r2 = 90;
        r5.compress(r0, r2, r1);	 Catch:{ IOException -> 0x0045 }
    L_0x0018:
        com.soundcloud.android.crop.CropUtil.closeSilently(r1);
    L_0x001b:
        r0 = r4.getContentResolver();
        r1 = r4.sourceUri;
        r0 = com.soundcloud.android.crop.CropUtil.getFromMediaUri(r4, r0, r1);
        r1 = r4.getContentResolver();
        r2 = r4.saveUri;
        r1 = com.soundcloud.android.crop.CropUtil.getFromMediaUri(r4, r1, r2);
        com.soundcloud.android.crop.CropUtil.copyExifRotation(r0, r1);
        r0 = r4.saveUri;
        r4.setResultUri(r0);
    L_0x0037:
        r0 = r4.handler;
        r1 = new com.soundcloud.android.crop.CropImageActivity$6;
        r1.<init>(r5);
        r0.post(r1);
        r4.finish();
        return;
    L_0x0045:
        r0 = move-exception;
        r4.setResultException(r0);	 Catch:{ all -> 0x0065 }
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0065 }
        r2.<init>();	 Catch:{ all -> 0x0065 }
        r3 = "Cannot open file: ";
        r2 = r2.append(r3);	 Catch:{ all -> 0x0065 }
        r3 = r4.saveUri;	 Catch:{ all -> 0x0065 }
        r2 = r2.append(r3);	 Catch:{ all -> 0x0065 }
        r2 = r2.toString();	 Catch:{ all -> 0x0065 }
        com.soundcloud.android.crop.Log.e(r2, r0);	 Catch:{ all -> 0x0065 }
        com.soundcloud.android.crop.CropUtil.closeSilently(r1);
        goto L_0x001b;
    L_0x0065:
        r0 = move-exception;
        com.soundcloud.android.crop.CropUtil.closeSilently(r1);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.soundcloud.android.crop.CropImageActivity.saveOutput(android.graphics.Bitmap):void");
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.rotateBitmap != null) {
            this.rotateBitmap.recycle();
        }
    }

    public boolean onSearchRequested() {
        return false;
    }

    public boolean isSaving() {
        return this.isSaving;
    }

    private void setResultUri(Uri uri) {
        setResult(-1, new Intent().putExtra("output", uri));
    }

    private void setResultException(Throwable th) {
        setResult(Crop.RESULT_ERROR, new Intent().putExtra("error", th));
    }
}
