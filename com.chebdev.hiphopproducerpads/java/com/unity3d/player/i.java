package com.unity3d.player;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
import java.io.FileInputStream;
import java.io.IOException;

public final class i extends FrameLayout implements OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener, OnVideoSizeChangedListener, Callback, MediaPlayerControl {
    private static boolean a = false;
    private final Context b;
    private final SurfaceView c;
    private final SurfaceHolder d;
    private final String e;
    private final int f;
    private final int g;
    private final boolean h;
    private final long i;
    private final long j;
    private final FrameLayout k;
    private final Display l;
    private int m;
    private int n;
    private int o;
    private int p;
    private MediaPlayer q;
    private MediaController r;
    private boolean s = false;
    private boolean t = false;
    private int u = 0;
    private boolean v = false;
    private boolean w = false;
    private a x;
    private volatile int y = 0;

    public interface a {
        void a(int i);
    }

    protected i(Context context, String str, int i, int i2, int i3, boolean z, long j, long j2, a aVar) {
        super(context);
        this.x = aVar;
        this.b = context;
        this.k = this;
        this.c = new SurfaceView(context);
        this.d = this.c.getHolder();
        this.d.addCallback(this);
        this.d.setType(3);
        this.k.setBackgroundColor(i);
        this.k.addView(this.c);
        this.l = ((WindowManager) this.b.getSystemService("window")).getDefaultDisplay();
        this.e = str;
        this.f = i2;
        this.g = i3;
        this.h = z;
        this.i = j;
        this.j = j2;
        if (a) {
            a("fileName: " + this.e);
        }
        if (a) {
            a("backgroundColor: " + i);
        }
        if (a) {
            a("controlMode: " + this.f);
        }
        if (a) {
            a("scalingMode: " + this.g);
        }
        if (a) {
            a("isURL: " + this.h);
        }
        if (a) {
            a("videoOffset: " + this.i);
        }
        if (a) {
            a("videoLength: " + this.j);
        }
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    private void a(int i) {
        this.y = i;
        if (this.x != null) {
            this.x.a(this.y);
        }
    }

    private static void a(String str) {
        Log.i("Video", "VideoPlayer: " + str);
    }

    private void b() {
        if (this.q != null) {
            this.q.setDisplay(this.d);
            if (!this.v) {
                if (a) {
                    a("Resuming playback");
                }
                this.q.start();
                return;
            }
            return;
        }
        a(0);
        doCleanUp();
        try {
            this.q = new MediaPlayer();
            if (this.h) {
                this.q.setDataSource(this.b, Uri.parse(this.e));
            } else if (this.j != 0) {
                FileInputStream fileInputStream = new FileInputStream(this.e);
                this.q.setDataSource(fileInputStream.getFD(), this.i, this.j);
                fileInputStream.close();
            } else {
                try {
                    AssetFileDescriptor openFd = getResources().getAssets().openFd(this.e);
                    this.q.setDataSource(openFd.getFileDescriptor(), openFd.getStartOffset(), openFd.getLength());
                    openFd.close();
                } catch (IOException e) {
                    FileInputStream fileInputStream2 = new FileInputStream(this.e);
                    this.q.setDataSource(fileInputStream2.getFD());
                    fileInputStream2.close();
                }
            }
            this.q.setDisplay(this.d);
            this.q.setScreenOnWhilePlaying(true);
            this.q.setOnBufferingUpdateListener(this);
            this.q.setOnCompletionListener(this);
            this.q.setOnPreparedListener(this);
            this.q.setOnVideoSizeChangedListener(this);
            this.q.setAudioStreamType(3);
            this.q.prepare();
            if (this.f == 0 || this.f == 1) {
                this.r = new MediaController(this.b);
                this.r.setMediaPlayer(this);
                this.r.setAnchorView(this);
                this.r.setEnabled(true);
                this.r.show();
            }
        } catch (Exception e2) {
            if (a) {
                a("error: " + e2.getMessage() + e2);
            }
            a(2);
        }
    }

    private void c() {
        if (!isPlaying()) {
            a(1);
            if (a) {
                a("startVideoPlayback");
            }
            updateVideoLayout();
            if (!this.v) {
                start();
            }
        }
    }

    final boolean a() {
        return this.v;
    }

    public final boolean canPause() {
        return true;
    }

    public final boolean canSeekBackward() {
        return true;
    }

    public final boolean canSeekForward() {
        return true;
    }

    protected final void destroyPlayer() {
        if (a) {
            a("destroyPlayer");
        }
        if (!this.v) {
            pause();
        }
        doCleanUp();
    }

    protected final void doCleanUp() {
        if (this.q != null) {
            this.q.release();
            this.q = null;
        }
        this.o = 0;
        this.p = 0;
        this.t = false;
        this.s = false;
    }

    public final int getBufferPercentage() {
        return this.h ? this.u : 100;
    }

    public final int getCurrentPosition() {
        return this.q == null ? 0 : this.q.getCurrentPosition();
    }

    public final int getDuration() {
        return this.q == null ? 0 : this.q.getDuration();
    }

    public final boolean isPlaying() {
        boolean z = this.t && this.s;
        return this.q == null ? !z : this.q.isPlaying() || !z;
    }

    public final void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        if (a) {
            a("onBufferingUpdate percent:" + i);
        }
        this.u = i;
    }

    public final void onCompletion(MediaPlayer mediaPlayer) {
        if (a) {
            a("onCompletion called");
        }
        destroyPlayer();
        a(3);
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4 && (this.f != 2 || i == 0 || keyEvent.isSystem())) {
            return this.r != null ? this.r.onKeyDown(i, keyEvent) : super.onKeyDown(i, keyEvent);
        } else {
            destroyPlayer();
            a(3);
            return true;
        }
    }

    public final void onPrepared(MediaPlayer mediaPlayer) {
        if (a) {
            a("onPrepared called");
        }
        this.t = true;
        if (this.t && this.s) {
            c();
        }
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (this.f != 2 || action != 0) {
            return this.r != null ? this.r.onTouchEvent(motionEvent) : super.onTouchEvent(motionEvent);
        } else {
            destroyPlayer();
            a(3);
            return true;
        }
    }

    public final void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
        if (a) {
            a("onVideoSizeChanged called " + i + "x" + i2);
        }
        if (i != 0 && i2 != 0) {
            this.s = true;
            this.o = i;
            this.p = i2;
            if (this.t && this.s) {
                c();
            }
        } else if (a) {
            a("invalid video width(" + i + ") or height(" + i2 + ")");
        }
    }

    public final void pause() {
        if (this.q != null) {
            if (this.w) {
                this.q.pause();
            }
            this.v = true;
        }
    }

    public final void seekTo(int i) {
        if (this.q != null) {
            this.q.seekTo(i);
        }
    }

    public final void start() {
        if (a) {
            a("Start");
        }
        if (this.q != null) {
            if (this.w) {
                this.q.start();
            }
            this.v = false;
        }
    }

    public final void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        if (a) {
            a("surfaceChanged called " + i + " " + i2 + "x" + i3);
        }
        if (this.m != i2 || this.n != i3) {
            this.m = i2;
            this.n = i3;
            if (this.w) {
                updateVideoLayout();
            }
        }
    }

    public final void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (a) {
            a("surfaceCreated called");
        }
        this.w = true;
        b();
    }

    public final void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (a) {
            a("surfaceDestroyed called");
        }
        this.w = false;
    }

    protected final void updateVideoLayout() {
        if (a) {
            a("updateVideoLayout");
        }
        if (this.q != null) {
            if (this.m == 0 || this.n == 0) {
                WindowManager windowManager = (WindowManager) this.b.getSystemService("window");
                this.m = windowManager.getDefaultDisplay().getWidth();
                this.n = windowManager.getDefaultDisplay().getHeight();
            }
            int i = this.m;
            int i2 = this.n;
            float f = ((float) this.o) / ((float) this.p);
            float f2 = ((float) this.m) / ((float) this.n);
            if (this.g == 1) {
                if (f2 <= f) {
                    i2 = (int) (((float) this.m) / f);
                } else {
                    i = (int) (((float) this.n) * f);
                }
            } else if (this.g == 2) {
                if (f2 >= f) {
                    i2 = (int) (((float) this.m) / f);
                } else {
                    i = (int) (((float) this.n) * f);
                }
            } else if (this.g == 0) {
                i = this.o;
                i2 = this.p;
            }
            if (a) {
                a("frameWidth = " + i + "; frameHeight = " + i2);
            }
            this.k.updateViewLayout(this.c, new LayoutParams(i, i2, 17));
        }
    }
}
