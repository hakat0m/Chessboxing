package com.ebay.kleinanzeigen.imagepicker.platform;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import uk.co.senab.photoview.IPhotoView;

public class ImageUtils {
    private ImageUtils() {
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int i) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) i);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap mirrorBitmap(Bitmap bitmap) {
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), getMirrorMatrix(), true);
    }

    private static Matrix getMirrorMatrix() {
        float[] fArr = new float[]{-1.0f, 0.0f, 0.0f, 0.0f, IPhotoView.DEFAULT_MIN_SCALE, 0.0f, 0.0f, 0.0f, IPhotoView.DEFAULT_MIN_SCALE};
        Matrix matrix = new Matrix();
        Matrix matrix2 = new Matrix();
        matrix2.setValues(fArr);
        matrix.postConcat(matrix2);
        return matrix;
    }
}
