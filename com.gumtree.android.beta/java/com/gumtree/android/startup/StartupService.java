package com.gumtree.android.startup;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.handler.DataStorage.Batch;
import uk.co.senab.photoview.IPhotoView;

public class StartupService extends IntentService {
    public static final String ONLY_CACHED_DATA = "only_cached_data";
    private CategoryUpdate categoryUpdate = new CategoryUpdate();

    public StartupService() {
        super("STARTUP_SERVICE");
    }

    public static Intent getService(boolean z, Context context) {
        Intent intent = new Intent(context, StartupService.class);
        intent.putExtra(ONLY_CACHED_DATA, z);
        return intent;
    }

    protected void onHandleIntent(Intent intent) {
        if (intent == null || !intent.getBooleanExtra(ONLY_CACHED_DATA, true)) {
            loadLiveData();
            return;
        }
        loadCachedDataOnly();
        loadLiveData();
    }

    private void loadLiveData() {
        long currentTimeMillis = System.currentTimeMillis();
        try {
            if (this.categoryUpdate.executeLiveRequest(currentTimeMillis) == IPhotoView.DEFAULT_ZOOM_DURATION) {
                cleanStaleContent(currentTimeMillis);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCachedDataOnly() {
        try {
            long currentTimeMillis = System.currentTimeMillis();
            Batch dataStorageBatch = getDataStorageBatch();
            this.categoryUpdate.executeCachedRequest(currentTimeMillis, getApplicationContext(), dataStorageBatch);
            executeDataStorageBatch(dataStorageBatch);
            cleanStaleContent(currentTimeMillis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cleanStaleContent(long j) {
        this.categoryUpdate.cleanStaleContent(Long.valueOf(j), getApplicationContext());
    }

    private Batch getDataStorageBatch() {
        return GumtreeApplication.getDataStorage().createBatchOperation();
    }

    private void executeDataStorageBatch(Batch batch) throws RemoteException, OperationApplicationException {
        batch.execute();
    }
}
