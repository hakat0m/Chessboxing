package com.gumtree.android.common.http;

import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import java.io.IOException;

public interface RestExecutor {
    int execute() throws IOException, RemoteException, OperationApplicationException;

    HttpIntentRequest prepareRequest(Intent intent);
}
