package com.comscore.utils;

import com.comscore.analytics.Core;
import com.comscore.measurement.Measurement;
import java.util.concurrent.ConcurrentLinkedQueue;

@Deprecated
public class DispatchQueue extends ConcurrentLinkedQueue<Runnable> {
    public static final int LIVE_TRANSMISSION_MODE = 0;
    public static final int MEASUREMENT_LABEL_ORDER = 3;
    public static final int OFFLINE_TRANSMISSION_MODE = 1;
    public static final int SECURE_MODE = 2;
    private static final long a = 1;
    private Core b;

    public DispatchQueue(Core core) {
        this.b = core;
    }

    @Deprecated
    public void enqueue(Measurement measurement) {
        offer(measurement);
    }

    @Deprecated
    public void enqueueSettingChange(int i, Object obj) {
        switch (i) {
            case LIVE_TRANSMISSION_MODE /*0*/:
                this.b.allowLiveTransmission((TransmissionMode) obj, true);
                return;
            case OFFLINE_TRANSMISSION_MODE /*1*/:
                this.b.allowOfflineTransmission((TransmissionMode) obj, true);
                return;
            case SECURE_MODE /*2*/:
                this.b.setSecure(((Boolean) obj).booleanValue(), true);
                return;
            case MEASUREMENT_LABEL_ORDER /*3*/:
                this.b.setMeasurementLabelOrder((String[]) obj, true);
                return;
            default:
                return;
        }
    }

    @Deprecated
    public boolean offer(Measurement measurement) {
        CSLog.d(this, "offer(): " + measurement.retrieveLabelsAsString(this.b.getMeasurementLabelOrder()));
        return this.b.getMeasurementDispatcher().sendMeasurmement(measurement, true);
    }

    @Deprecated
    public boolean offer(Runnable runnable) {
        return this.b.getTaskExecutor().execute(runnable, true);
    }

    @Deprecated
    public void processAggregateData(Measurement measurement) {
        this.b.getMeasurementDispatcher().addAggregateData(measurement);
    }

    @Deprecated
    public void processEventCounter(Measurement measurement) {
        this.b.getMeasurementDispatcher().addEventCounter(measurement);
    }

    @Deprecated
    public void stop() {
        this.b.getTaskExecutor().removeAllEnqueuedTasks();
    }
}
