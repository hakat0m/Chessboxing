package com.gumtree.android.events;

import com.ebay.classifieds.capi.executor.ResponseException;
import java.util.List;

public class OnAdDeletedEvent {
    private List<ResponseException> exception;
    private boolean result;

    public OnAdDeletedEvent(boolean z, List<ResponseException> list) {
        this.result = z;
        this.exception = list;
    }

    public boolean getResult() {
        return this.result;
    }

    public List<ResponseException> getException() {
        return this.exception;
    }

    public boolean hasError() {
        return this.exception != null && this.exception.size() > 0;
    }

    public boolean hasErrorType(int i) {
        boolean z = false;
        for (ResponseException error : this.exception) {
            boolean z2;
            if (error.getError().getType() == i) {
                z2 = true;
            } else {
                z2 = z;
            }
            z = z2;
        }
        return z;
    }
}
