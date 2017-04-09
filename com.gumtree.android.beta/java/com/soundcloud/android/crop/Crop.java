package com.soundcloud.android.crop;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class Crop {
    public static final int REQUEST_CROP = 6709;
    public static final int REQUEST_PICK = 9162;
    public static final int RESULT_ERROR = 404;
    private Intent cropIntent = new Intent();

    public static Crop of(Uri uri, Uri uri2) {
        return new Crop(uri, uri2);
    }

    private Crop(Uri uri, Uri uri2) {
        this.cropIntent.setData(uri);
        this.cropIntent.putExtra("output", uri2);
    }

    public Crop withAspect(int i, int i2) {
        this.cropIntent.putExtra("aspect_x", i);
        this.cropIntent.putExtra("aspect_y", i2);
        return this;
    }

    public Crop asSquare() {
        this.cropIntent.putExtra("aspect_x", 1);
        this.cropIntent.putExtra("aspect_y", 1);
        return this;
    }

    public Crop withMaxSize(int i, int i2) {
        this.cropIntent.putExtra("max_x", i);
        this.cropIntent.putExtra("max_y", i2);
        return this;
    }

    public void start(Activity activity) {
        start(activity, (int) REQUEST_CROP);
    }

    public void start(Activity activity, int i) {
        activity.startActivityForResult(getIntent(activity), i);
    }

    public void start(Context context, Fragment fragment) {
        start(context, fragment, (int) REQUEST_CROP);
    }

    public void start(Context context, android.support.v4.app.Fragment fragment) {
        start(context, fragment, (int) REQUEST_CROP);
    }

    @TargetApi(11)
    public void start(Context context, Fragment fragment, int i) {
        fragment.startActivityForResult(getIntent(context), i);
    }

    public void start(Context context, android.support.v4.app.Fragment fragment, int i) {
        fragment.startActivityForResult(getIntent(context), i);
    }

    public Intent getIntent(Context context) {
        this.cropIntent.setClass(context, CropImageActivity.class);
        return this.cropIntent;
    }

    public static Uri getOutput(Intent intent) {
        return (Uri) intent.getParcelableExtra("output");
    }

    public static Throwable getError(Intent intent) {
        return (Throwable) intent.getSerializableExtra("error");
    }

    public static void pickImage(Activity activity) {
        pickImage(activity, (int) REQUEST_PICK);
    }

    public static void pickImage(Context context, Fragment fragment) {
        pickImage(context, fragment, (int) REQUEST_PICK);
    }

    public static void pickImage(Context context, android.support.v4.app.Fragment fragment) {
        pickImage(context, fragment, (int) REQUEST_PICK);
    }

    public static void pickImage(Activity activity, int i) {
        try {
            activity.startActivityForResult(getImagePicker(), i);
        } catch (ActivityNotFoundException e) {
            showImagePickerError(activity);
        }
    }

    @TargetApi(11)
    public static void pickImage(Context context, Fragment fragment, int i) {
        try {
            fragment.startActivityForResult(getImagePicker(), i);
        } catch (ActivityNotFoundException e) {
            showImagePickerError(context);
        }
    }

    public static void pickImage(Context context, android.support.v4.app.Fragment fragment, int i) {
        try {
            fragment.startActivityForResult(getImagePicker(), i);
        } catch (ActivityNotFoundException e) {
            showImagePickerError(context);
        }
    }

    private static Intent getImagePicker() {
        return new Intent("android.intent.action.GET_CONTENT").setType("image/*");
    }

    private static void showImagePickerError(Context context) {
        Toast.makeText(context, R$string.crop__pick_error, 0).show();
    }
}
