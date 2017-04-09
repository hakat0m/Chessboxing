package com.gumtree.android.handler;

import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;

public interface DataStorage {

    public interface Batch {
        Delete delete(Uri uri);

        void execute() throws RemoteException, OperationApplicationException;

        Insert insert(Uri uri);

        int size();

        Update update(Uri uri);
    }

    Batch createBatchOperation();

    Cursor query(Uri uri, String[] strArr, String str);
}
