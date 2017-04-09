package com.adjust.sdk;

import android.os.Handler;
import android.os.HandlerThread;

public class RequestHandler extends HandlerThread implements IRequestHandler {
    private Handler internalHandler = new Handler(getLooper());
    private ILogger logger = AdjustFactory.getLogger();
    private IPackageHandler packageHandler;

    public RequestHandler(IPackageHandler iPackageHandler) {
        super(Constants.LOGTAG, 1);
        setDaemon(true);
        start();
        init(iPackageHandler);
    }

    public void init(IPackageHandler iPackageHandler) {
        this.packageHandler = iPackageHandler;
    }

    public void sendPackage(final ActivityPackage activityPackage, final int i) {
        this.internalHandler.post(new Runnable() {
            public void run() {
                RequestHandler.this.sendInternal(activityPackage, i);
            }
        });
    }

    private void sendInternal(ActivityPackage activityPackage, int i) {
        try {
            ResponseData readHttpResponse = Util.readHttpResponse(Util.createPOSTHttpsURLConnection(Constants.BASE_URL + activityPackage.getPath(), activityPackage.getClientSdk(), activityPackage.getParameters(), i), activityPackage);
            if (readHttpResponse.jsonResponse == null) {
                this.packageHandler.closeFirstPackage(readHttpResponse, activityPackage);
            } else {
                this.packageHandler.sendNextPackage(readHttpResponse);
            }
        } catch (Throwable e) {
            sendNextPackage(activityPackage, "Failed to encode parameters", e);
        } catch (Throwable e2) {
            closePackage(activityPackage, "Request timed out", e2);
        } catch (Throwable e22) {
            closePackage(activityPackage, "Request failed", e22);
        } catch (Throwable e222) {
            sendNextPackage(activityPackage, "Runtime exception", e222);
        }
    }

    private void closePackage(ActivityPackage activityPackage, String str, Throwable th) {
        String failureMessage = activityPackage.getFailureMessage();
        String reasonString = Util.getReasonString(str, th);
        failureMessage = String.format("%s. (%s) Will retry later", new Object[]{failureMessage, reasonString});
        this.logger.error(failureMessage, new Object[0]);
        ResponseData buildResponseData = ResponseData.buildResponseData(activityPackage);
        buildResponseData.message = failureMessage;
        this.packageHandler.closeFirstPackage(buildResponseData, activityPackage);
    }

    private void sendNextPackage(ActivityPackage activityPackage, String str, Throwable th) {
        String failureMessage = activityPackage.getFailureMessage();
        String reasonString = Util.getReasonString(str, th);
        failureMessage = String.format("%s. (%s)", new Object[]{failureMessage, reasonString});
        this.logger.error(failureMessage, new Object[0]);
        ResponseData buildResponseData = ResponseData.buildResponseData(activityPackage);
        buildResponseData.message = failureMessage;
        this.packageHandler.sendNextPackage(buildResponseData);
    }
}
