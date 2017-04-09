/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.nio.ByteBuffer
 *  java.nio.ByteOrder
 */
package com.cygery.repetitouch;

import com.cygery.repetitouch.Event;
import com.cygery.utilities.a;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class o
extends Event {
    public static final short ABS_MT_BLOB_ID = 56;
    public static final short ABS_MT_DISTANCE = 59;
    public static final short ABS_MT_ORIENTATION = 52;
    public static final short ABS_MT_POSITION_X = 53;
    public static final short ABS_MT_POSITION_Y = 54;
    public static final short ABS_MT_PRESSURE = 58;
    public static final short ABS_MT_SLOT = 47;
    public static final short ABS_MT_TOOL_TYPE = 55;
    public static final short ABS_MT_TOUCH_MAJOR = 48;
    public static final short ABS_MT_TOUCH_MINOR = 49;
    public static final short ABS_MT_TRACKING_ID = 57;
    public static final short ABS_MT_WIDTH_MAJOR = 50;
    public static final short ABS_MT_WIDTH_MINOR = 51;
    public static final short BTN_TOUCH = 330;
    public static final int EVENT_SIZE = 18;
    public static final short EV_ABS = 3;
    public static final short EV_KEY = 1;
    public static final short EV_REL = 2;
    public static final short EV_SYN = 0;
    public static final short KEY_BACK = 158;
    public static final short KEY_HOME = 102;
    public static final short KEY_MENU = 139;
    public static final short KEY_POWER = 116;
    public static final short KEY_SEARCH = 217;
    public static final short KEY_SEND = 231;
    public static final short KEY_VOLUMEDOWN = 114;
    public static final short KEY_VOLUMEUP = 115;
    public static final short STOP_TYPE = 255;
    public static final short SYN_CONFIG = 1;
    public static final short SYN_MT_REPORT = 2;
    public static final short SYN_REPORT = 0;
    public static final Event a;
    public static final Event b;
    private static final String k;
    static final long serialVersionUID = 7987181418103629018L;
    protected int c;
    protected int d;
    protected short e;
    protected short f;
    protected int g;
    protected short h;
    protected byte[] i;
    protected long j;

    static {
        k = Event.class.getName();
        a = new Event();
        b = new Event(0, 0, 0);
    }

    public o() {
        this(255, 0, 0);
    }

    public o(int n2, int n3, short s2, short s3, int n4) {
        this(0, n2, n3, s2, s3, n4);
    }

    public o(Event event) {
        this.h = event.deviceIndex;
        this.c = event.time_sec;
        this.d = event.time_usec;
        this.e = event.type;
        this.f = event.code;
        this.g = event.value;
        this.j = event.timeInMicroSeconds;
        this.i = (byte[])event.rawBytes.clone();
    }

    public o(short s2, int n2, int n3, short s3, short s4, int n4) {
        this.h = s2;
        this.c = n2;
        this.d = n3;
        this.e = s3;
        this.f = s4;
        this.g = n4;
    }

    public o(short s2, short s3, int n2) {
        super(0, 0, s2, s3, n2);
    }

    public o(byte[] arrby) {
        this.i = (byte[])arrby.clone();
        super.b(arrby);
    }

    private void b(byte[] arrby) {
        if (arrby.length != 18) {
            a.c(k, "parseString: parameter length != EVENT_SIZE (18)");
            return;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap((byte[])arrby);
        byteBuffer.order(ByteOrder.nativeOrder());
        this.h = byteBuffer.getShort();
        this.c = byteBuffer.getInt();
        this.d = byteBuffer.getInt();
        this.e = byteBuffer.getShort();
        this.f = byteBuffer.getShort();
        this.g = byteBuffer.getInt();
        this.j = 1000 * (1000 * (long)this.c) + (long)this.d;
    }

    private void f() {
        this.c = (int)(this.j / 1000 / 1000);
        this.d = (int)(this.j % 1000000);
    }

    @Override
    public void a(long l2) {
        this.j = l2 + this.j;
        super.f();
    }

    @Override
    public void a(short s2) {
        this.h = s2;
    }

    @Override
    public void a(byte[] arrby) {
        this.i = arrby;
    }

    @Override
    public boolean a(short s2, short s3) {
        if (this.h == s2 && this.e == s3) {
            return true;
        }
        return false;
    }

    @Override
    public boolean a(short s2, short s3, int n2) {
        if (this.e == s2 && this.f == s3 && this.g == n2) {
            return true;
        }
        return false;
    }

    @Override
    public boolean a(short s2, short s3, short s4, int n2) {
        if (this.h == s2 && this.e == s3 && this.f == s4 && this.g == n2) {
            return true;
        }
        return false;
    }

    @Override
    public byte[] a() {
        byte[] arrby = new byte[18];
        ByteBuffer byteBuffer = ByteBuffer.wrap((byte[])arrby);
        byteBuffer.order(ByteOrder.nativeOrder());
        byteBuffer.putShort(this.h);
        byteBuffer.putInt(this.c);
        byteBuffer.putInt(this.d);
        byteBuffer.putShort(this.e);
        byteBuffer.putShort(this.f);
        byteBuffer.putInt(this.g);
        return arrby;
    }

    @Override
    public long b() {
        return this.j;
    }

    @Override
    public short c() {
        return this.e;
    }

    @Override
    public short d() {
        return this.h;
    }

    @Override
    public byte[] e() {
        return this.i;
    }

    @Override
    public short getCode() {
        return this.f;
    }

    @Override
    public int getTime_sec() {
        return this.c;
    }

    @Override
    public int getTime_usec() {
        return this.d;
    }

    @Override
    public int getValue() {
        return this.g;
    }

    @Override
    public boolean matchesIndexTypeCode(short s2, short s3, short s4) {
        if (this.h == s2 && this.e == s3 && this.f == s4) {
            return true;
        }
        return false;
    }

    @Override
    public boolean matchesTypeCode(short s2, short s3) {
        if (this.e == s2 && this.f == s3) {
            return true;
        }
        return false;
    }

    @Override
    public void setCode(short s2) {
        this.f = s2;
    }

    @Override
    public void setTime_sec(int n2) {
        this.c = n2;
    }

    @Override
    public void setTime_usec(int n2) {
        this.d = n2;
    }

    @Override
    public void setType(short s2) {
        this.e = s2;
    }

    @Override
    public void setValue(int n2) {
        this.g = n2;
    }

    @Override
    public String toString() {
        return "" + "DeviceIndex=" + this.h + " Time=" + this.c + ":" + this.d + " Type=" + this.e + " Code=" + this.f + " Value=" + this.g;
    }
}

