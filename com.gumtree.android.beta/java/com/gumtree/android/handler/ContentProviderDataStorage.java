package com.gumtree.android.handler;

import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import com.gumtree.android.common.providers.AppProvider;
import com.gumtree.android.handler.DataStorage.Batch;
import com.gumtree.android.handler.DataStorage.Delete;
import com.gumtree.android.handler.DataStorage.Insert;
import com.gumtree.android.handler.DataStorage.Update;
import java.util.ArrayList;
import java.util.List;

public class ContentProviderDataStorage implements DataStorage {
    private final Context context;

    class ContentProviderBatch implements Batch {
        private final List<PendingOperation> pendingOperations;

        private ContentProviderBatch() {
            this.pendingOperations = new ArrayList();
        }

        public Insert insert(Uri uri) {
            return (Insert) tracked(new PendingInsert(uri));
        }

        public Update update(Uri uri) {
            return (Update) tracked(new PendingUpdate(uri));
        }

        public Delete delete(Uri uri) {
            return (Delete) tracked(new PendingDelete(uri));
        }

        private <T extends PendingOperation> T tracked(T t) {
            this.pendingOperations.add(t);
            return t;
        }

        public int size() {
            return this.pendingOperations.size();
        }

        public void execute() throws RemoteException, OperationApplicationException {
            ContentProviderDataStorage.this.context.getContentResolver().applyBatch(AppProvider.AUTHORITY, createContentProviderOperations());
        }

        private ArrayList<ContentProviderOperation> createContentProviderOperations() {
            ArrayList<ContentProviderOperation> arrayList = new ArrayList(this.pendingOperations.size());
            for (PendingOperation build : this.pendingOperations) {
                arrayList.add(build.build());
            }
            return arrayList;
        }
    }

    public ContentProviderDataStorage(Context context) {
        this.context = context;
    }

    public Cursor query(Uri uri, String[] strArr, String str) {
        return this.context.getContentResolver().query(uri, strArr, str, null, null);
    }

    public Batch createBatchOperation() {
        return new ContentProviderBatch();
    }
}
