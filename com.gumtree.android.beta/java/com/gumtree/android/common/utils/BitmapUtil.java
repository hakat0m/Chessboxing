package com.gumtree.android.common.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import java.io.File;

public class BitmapUtil {
    private static final int NINETY_DEGREES = 90;

    public static Bitmap rotateLeft(Bitmap bitmap) {
        return rotate(bitmap, -90);
    }

    public static Bitmap rotateRight(Bitmap bitmap) {
        return rotate(bitmap, NINETY_DEGREES);
    }

    private static Bitmap rotate(Bitmap bitmap, int i) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        int i2 = width / 2;
        int i3 = height / 2;
        matrix.preTranslate((float) (-i2), (float) (-i3));
        matrix.postRotate((float) i);
        matrix.postTranslate((float) i3, (float) i2);
        Bitmap createBitmap = Bitmap.createBitmap(height, width, Config.RGB_565);
        new Canvas(createBitmap).drawBitmap(bitmap, matrix, null);
        bitmap.recycle();
        return createBitmap;
    }

    public Bitmap getBitmapFromFile(String str, int i, int i2) {
        File file = new File(str);
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), options);
        if (options.outWidth == -1 || options.outHeight == -1) {
            return null;
        }
        int i3 = options.outHeight > options.outWidth ? options.outHeight : options.outWidth;
        Options options2 = new Options();
        options2.inSampleSize = i3 / i;
        Bitmap decodeFile = BitmapFactory.decodeFile(file.getPath(), options2);
        if (i2 == 0) {
            return decodeFile;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate((float) i2);
        Bitmap createBitmap = Bitmap.createBitmap(decodeFile, 0, 0, decodeFile.getWidth(), decodeFile.getHeight(), matrix, true);
        decodeFile.recycle();
        return createBitmap;
    }
}
