package com.adjust.sdk;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class TimerCycle {
    private Runnable command;
    private long cycleDelay;
    private String cycleDelaySeconds;
    private long initialDelay;
    private boolean isPaused;
    private ILogger logger;
    private String name;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture waitingTask;

    public TimerCycle(Runnable runnable, long j, long j2, String str) {
        this.name = str;
        this.command = runnable;
        this.initialDelay = j;
        this.cycleDelay = j2;
        this.isPaused = true;
        this.logger = AdjustFactory.getLogger();
        this.cycleDelaySeconds = Util.SecondsDisplayFormat.format(((double) j2) / 1000.0d);
    }

    public void start() {
        if (this.isPaused) {
            String format = Util.SecondsDisplayFormat.format(((double) this.initialDelay) / 1000.0d);
            this.logger.verbose("%s starting in %s seconds and cycle every %s seconds", this.name, format, this.cycleDelaySeconds);
            this.waitingTask = this.scheduler.scheduleWithFixedDelay(new Runnable() {
                public void run() {
                    TimerCycle.this.logger.verbose("%s fired", TimerCycle.this.name);
                    TimerCycle.this.command.run();
                }
            }, this.initialDelay, this.cycleDelay, TimeUnit.MILLISECONDS);
            this.isPaused = false;
            return;
        }
        this.logger.verbose("%s is already started", this.name);
    }

    public void suspend() {
        if (this.isPaused) {
            this.logger.verbose("%s is already suspended", this.name);
            return;
        }
        this.initialDelay = this.waitingTask.getDelay(TimeUnit.MILLISECONDS);
        this.waitingTask.cancel(false);
        this.waitingTask = null;
        String format = Util.SecondsDisplayFormat.format(((double) this.initialDelay) / 1000.0d);
        this.logger.verbose("%s suspended with %s seconds left", this.name, format);
        this.isPaused = true;
    }
}
