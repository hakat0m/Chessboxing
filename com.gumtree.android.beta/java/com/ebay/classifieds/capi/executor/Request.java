package com.ebay.classifieds.capi.executor;

import java.io.Serializable;

public interface Request<T> extends Serializable {
    Result<T> execute();
}
