package com.adjust.sdk;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TimerOnce {
    private Runnable command;
    private ILogger logger = AdjustFactory.getLogger();
    private String name;
    private ScheduledExecutorService scheduler;
    private ScheduledFuture waitingTask;

    public TimerOnce(ScheduledExecutorService scheduledExecutorService, Runnable runnable, String str) {
        this.name = str;
        this.scheduler = scheduledExecutorService;
        this.command = runnable;
    }

    public void startIn(long j) {
        cancel(false);
        String format = Util.SecondsDisplayFormat.format(((double) j) / 1000.0d);
        this.logger.verbose("%s starting. Launching in %s seconds", this.name, format);
        this.waitingTask = this.scheduler.schedule(new Runnable() {
            public void run() {
                TimerOnce.this.logger.verbose("%s fired", TimerOnce.this.name);
                TimerOnce.this.command.run();
                TimerOnce.this.waitingTask = null;
            }
        }, j, TimeUnit.MILLISECONDS);
    }

    public long getFireIn() {
        if (this.waitingTask == null) {
            return 0;
        }
        return this.waitingTask.getDelay(TimeUnit.MILLISECONDS);
    }

    private void cancel(boolean z) {
        if (this.waitingTask != null) {
            this.waitingTask.cancel(false);
        }
        this.waitingTask = null;
        if (z) {
            this.logger.verbose("%s canceled", this.name);
        }
    }

    public void cancel() {
        cancel(true);
    }
}
