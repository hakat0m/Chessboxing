package okhttp3;

import java.io.IOException;

public interface Interceptor {
    Response intercept(Chain chain) throws IOException;
}
