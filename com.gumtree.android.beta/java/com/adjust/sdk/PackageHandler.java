package com.adjust.sdk;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PackageHandler extends HandlerThread implements IPackageHandler {
    private static final String PACKAGE_QUEUE_FILENAME = "AdjustIoPackageQueue";
    private static final String PACKAGE_QUEUE_NAME = "Package queue";
    private IActivityHandler activityHandler;
    private BackoffStrategy backoffStrategy = AdjustFactory.getPackageHandlerBackoffStrategy();
    private Context context;
    private Handler internalHandler = new Handler(getLooper());
    private AtomicBoolean isSending;
    private ILogger logger = AdjustFactory.getLogger();
    private List<ActivityPackage> packageQueue;
    private boolean paused;
    private IRequestHandler requestHandler;

    public PackageHandler(IActivityHandler iActivityHandler, Context context, boolean z) {
        super(Constants.LOGTAG, 1);
        setDaemon(true);
        start();
        init(iActivityHandler, context, z);
        this.internalHandler.post(new Runnable() {
            public void run() {
                PackageHandler.this.initInternal();
            }
        });
    }

    public void init(IActivityHandler iActivityHandler, Context context, boolean z) {
        this.activityHandler = iActivityHandler;
        this.context = context;
        this.paused = !z;
    }

    public void addPackage(final ActivityPackage activityPackage) {
        this.internalHandler.post(new Runnable() {
            public void run() {
                PackageHandler.this.addInternal(activityPackage);
            }
        });
    }

    public void sendFirstPackage() {
        this.internalHandler.post(new Runnable() {
            public void run() {
                PackageHandler.this.sendFirstInternal();
            }
        });
    }

    public void sendNextPackage(ResponseData responseData) {
        this.internalHandler.post(new Runnable() {
            public void run() {
                PackageHandler.this.sendNextInternal();
            }
        });
        this.activityHandler.finishedTrackingActivity(responseData);
    }

    public void closeFirstPackage(ResponseData responseData, ActivityPackage activityPackage) {
        responseData.willRetry = true;
        this.activityHandler.finishedTrackingActivity(responseData);
        if (activityPackage != null) {
            long waitingTime = Util.getWaitingTime(activityPackage.increaseRetries(), this.backoffStrategy);
            String format = Util.SecondsDisplayFormat.format(((double) waitingTime) / 1000.0d);
            this.logger.verbose("Sleeping for %s seconds before retrying the %d time", format, Integer.valueOf(r0));
            SystemClock.sleep(waitingTime);
        }
        this.logger.verbose("Package handler can send", new Object[0]);
        this.isSending.set(false);
        sendFirstPackage();
    }

    public void pauseSending() {
        this.paused = true;
    }

    public void resumeSending() {
        this.paused = false;
    }

    private void initInternal() {
        this.requestHandler = AdjustFactory.getRequestHandler(this);
        this.isSending = new AtomicBoolean();
        readPackageQueue();
    }

    private void addInternal(ActivityPackage activityPackage) {
        this.packageQueue.add(activityPackage);
        this.logger.debug("Added package %d (%s)", Integer.valueOf(this.packageQueue.size()), activityPackage);
        this.logger.verbose("%s", activityPackage.getExtendedString());
        writePackageQueue();
    }

    private void sendFirstInternal() {
        if (!this.packageQueue.isEmpty()) {
            if (this.paused) {
                this.logger.debug("Package handler is paused", new Object[0]);
            } else if (this.isSending.getAndSet(true)) {
                this.logger.verbose("Package handler is already sending", new Object[0]);
            } else {
                this.requestHandler.sendPackage((ActivityPackage) this.packageQueue.get(0), this.packageQueue.size() - 1);
            }
        }
    }

    private void sendNextInternal() {
        this.packageQueue.remove(0);
        writePackageQueue();
        this.isSending.set(false);
        this.logger.verbose("Package handler can send", new Object[0]);
        sendFirstInternal();
    }

    private void readPackageQueue() {
        try {
            this.packageQueue = (List) Util.readObject(this.context, PACKAGE_QUEUE_FILENAME, PACKAGE_QUEUE_NAME, List.class);
        } catch (Exception e) {
            this.logger.error("Failed to read %s file (%s)", PACKAGE_QUEUE_NAME, e.getMessage());
            this.packageQueue = null;
        }
        if (this.packageQueue != null) {
            this.logger.debug("Package handler read %d packages", Integer.valueOf(this.packageQueue.size()));
            return;
        }
        this.packageQueue = new ArrayList();
    }

    private void writePackageQueue() {
        Util.writeObject(this.packageQueue, this.context, PACKAGE_QUEUE_FILENAME, PACKAGE_QUEUE_NAME);
        this.logger.debug("Package handler wrote %d packages", Integer.valueOf(this.packageQueue.size()));
    }

    public static Boolean deletePackageQueue(Context context) {
        return Boolean.valueOf(context.deleteFile(PACKAGE_QUEUE_FILENAME));
    }
}
