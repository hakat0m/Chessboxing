package com.gumtree.android.post_ad.gallery.upload;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import com.gumtree.android.common.utils.Log;

public class PostUploadService extends Service {
    private static final String CANCEL = "CANCEL";
    private static final String IMAGE_FILE_PATH = "IMAGE_FILE_PATH";
    private static final String REQUEST_ID = "REQUEST_ID";
    private static final int SINGLE_IMAGE_UPLOAD_THREAD = 1;
    private static final String UPLOAD_AD = "UPLOAD_AD";
    private static final String UPLOAD_IMAGE = "UPLOAD_IMAGE";
    private final CancellableTaskExecutor<CancellableTask> cancellableTaskExecutor = new CancellableTaskExecutor(SINGLE_IMAGE_UPLOAD_THREAD);

    public static void upload(Context context, String str) {
        Intent intent = new Intent(context, PostUploadService.class);
        intent.putExtra(REQUEST_ID, UPLOAD_IMAGE);
        intent.putExtra(IMAGE_FILE_PATH, str);
        context.startService(intent);
    }

    public static void cancel(Context context, String str) {
        Intent intent = new Intent(context, PostUploadService.class);
        intent.putExtra(REQUEST_ID, CANCEL);
        intent.putExtra(IMAGE_FILE_PATH, str);
        context.startService(intent);
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent != null) {
            try {
                tryStartCommand(intent);
            } catch (Throwable th) {
                Log.e("Problem starting command", th);
            }
        }
        return 0;
    }

    private void tryStartCommand(Intent intent) {
        if (isUploadImageRequest(intent)) {
            scheduleUploadTask(intent);
        } else if (isCancelRequest(intent)) {
            cancelPendingTasks(intent.getStringExtra(IMAGE_FILE_PATH));
        }
    }

    private boolean isUploadImageRequest(Intent intent) {
        return UPLOAD_IMAGE.equals(intent.getStringExtra(REQUEST_ID));
    }

    private boolean isPostRequest(Intent intent) {
        return UPLOAD_AD.equals(intent.getStringExtra(REQUEST_ID));
    }

    private void scheduleUploadTask(Intent intent) {
        this.cancellableTaskExecutor.schedule(new CancellableUploadTask(intent.getStringExtra(IMAGE_FILE_PATH)));
    }

    private boolean isCancelRequest(Intent intent) {
        return CANCEL.equals(intent.getStringExtra(REQUEST_ID));
    }

    private void cancelPendingTasks(String str) {
        this.cancellableTaskExecutor.cancelBy(str);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }
}
