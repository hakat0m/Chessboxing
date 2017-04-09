/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  java.io.Serializable
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.nio.ByteBuffer
 *  java.nio.ByteOrder
 */
package com.cygery.repetitouch;

import android.os.Build;
import com.cygery.utilities.a;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Event
implements Serializable {
    public static final short ABS_HAT0X = 16;
    public static final short ABS_HAT0Y = 17;
    public static final short ABS_HAT1X = 18;
    public static final short ABS_HAT1Y = 19;
    public static final short ABS_HAT2X = 20;
    public static final short ABS_HAT2Y = 21;
    public static final short ABS_HAT3X = 22;
    public static final short ABS_HAT3Y = 23;
    public static final short ABS_MT_AMPLITUDE = 43;
    public static final short ABS_MT_BLOB_ID = 56;
    public static final short ABS_MT_DISTANCE = 59;
    public static final short ABS_MT_ORIENTATION = 52;
    public static final short ABS_MT_POSITION = 42;
    public static final short ABS_MT_POSITION_X = 53;
    public static final short ABS_MT_POSITION_Y = 54;
    public static final short ABS_MT_PRESSURE = 58;
    public static final short ABS_MT_SLOT = 47;
    public static final short ABS_MT_TOOL_TYPE = 55;
    public static final short ABS_MT_TOOL_X = 60;
    public static final short ABS_MT_TOOL_Y = 61;
    public static final short ABS_MT_TOUCH_MAJOR = 48;
    public static final short ABS_MT_TOUCH_MINOR = 49;
    public static final short ABS_MT_TRACKING_ID = 57;
    public static final short ABS_MT_WIDTH_MAJOR = 50;
    public static final short ABS_MT_WIDTH_MINOR = 51;
    public static final short ABS_PRESSURE = 24;
    public static final short BTN_TOOL_FINGER = 325;
    public static final short BTN_TOUCH = 330;
    public static final int EVENT_SIZE = 0;
    public static final int EVENT_SIZE_32_BIT = 18;
    public static final int EVENT_SIZE_64_BIT = 26;
    public static final short EV_ABS = 3;
    public static final short EV_KEY = 1;
    public static final short EV_MSC = 4;
    public static final short EV_REL = 2;
    public static final short EV_SYN = 0;
    public static final boolean IS_64_BIT = false;
    public static final short KEY_BACK = 158;
    public static final short KEY_DUMMY_BACK = 253;
    public static final short KEY_HOME = 102;
    public static final short KEY_MENU = 139;
    public static final short KEY_POWER = 116;
    public static final short KEY_SEARCH = 217;
    public static final short KEY_SEND = 231;
    public static final short KEY_VOLUMEDOWN = 114;
    public static final short KEY_VOLUMEUP = 115;
    public static final short MSC_ACTIVITY = 8;
    public static final short MSC_GESTURE = 2;
    public static final short MSC_PULSELED = 1;
    public static final short MSC_RAW = 3;
    public static final short MSC_SCAN = 4;
    public static final short MSC_SERIAL = 0;
    public static final short MSC_TIMESEC = 6;
    public static final short MSC_TIMESTAMP = 5;
    public static final short MSC_TIMEUSEC = 7;
    public static final Event PAUSE_EVENT;
    public static final short PAUSE_TYPE = 253;
    public static final Event PLAY_EVENT;
    public static final short PLAY_TYPE = 254;
    public static final Event STOP_EVENT;
    public static final short STOP_TYPE = 255;
    public static final short SYN_CONFIG = 1;
    public static final Event SYN_EVENT;
    public static final short SYN_MT_REPORT = 2;
    public static final short SYN_REPORT = 0;
    private static final String a;
    static final long serialVersionUID = 2;
    protected short code;
    protected short deviceIndex;
    protected byte[] rawBytes;
    protected long timeInMicroSeconds;
    protected int time_sec;
    protected int time_usec;
    protected short type;
    protected int value;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    static {
        Event.a = Event.class.getName();
        var0 = Build.VERSION.SDK_INT < 21 ? Build.CPU_ABI : Build.SUPPORTED_ABIS[0];
        switch (var0.hashCode()) {
            case 145444210: {
                if (!var0.equals((Object)"armeabi-v7a")) ** GOTO lbl28
                var1_1 = 0;
                ** GOTO lbl35
            }
            case -738963905: {
                if (!var0.equals((Object)"armeabi")) ** GOTO lbl28
                var1_1 = 1;
                ** GOTO lbl35
            }
            case 3351711: {
                if (!var0.equals((Object)"mips")) ** GOTO lbl28
                var1_1 = 2;
                ** GOTO lbl35
            }
            case 117110: {
                if (!var0.equals((Object)"x86")) ** GOTO lbl28
                var1_1 = 3;
                ** GOTO lbl35
            }
            case 1431565292: {
                if (!var0.equals((Object)"arm64-v8a")) ** GOTO lbl28
                var1_1 = 4;
                ** GOTO lbl35
            }
            case -806050265: {
                if (!var0.equals((Object)"x86_64")) ** GOTO lbl28
                var1_1 = 5;
                ** GOTO lbl35
            }
lbl28: // 7 sources:
            default: {
                ** GOTO lbl-1000
            }
            case -1073971299: 
        }
        if (var0.equals((Object)"mips64")) {
            var1_1 = 6;
        } else lbl-1000: // 2 sources:
        {
            var1_1 = -1;
        }
lbl35: // 8 sources:
        switch (var1_1) {
            default: {
                Event.EVENT_SIZE = 18;
                Event.IS_64_BIT = false;
                break;
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: {
                Event.EVENT_SIZE = 18;
                Event.IS_64_BIT = false;
                break;
            }
            case 4: 
            case 5: 
            case 6: {
                Event.EVENT_SIZE = 26;
                Event.IS_64_BIT = true;
            }
        }
        Event.PAUSE_EVENT = new Event(253, 0, 0);
        Event.PLAY_EVENT = new Event(254, 0, 0);
        Event.STOP_EVENT = new Event();
        Event.SYN_EVENT = new Event(0, 0, 0);
    }

    public Event() {
        this(255, 0, 0);
    }

    public Event(int n2, int n3, short s2, short s3, int n4) {
        this(0, n2, n3, s2, s3, n4);
    }

    public Event(Event event) {
        this.deviceIndex = event.deviceIndex;
        this.time_sec = event.time_sec;
        this.time_usec = event.time_usec;
        this.type = event.type;
        this.code = event.code;
        this.value = event.value;
        this.timeInMicroSeconds = event.timeInMicroSeconds;
        this.rawBytes = (byte[])event.rawBytes.clone();
    }

    public Event(short s2, int n2, int n3, short s3, short s4, int n4) {
        this.deviceIndex = s2;
        this.time_sec = n2;
        this.time_usec = n3;
        this.type = s3;
        this.code = s4;
        this.value = n4;
        this.timeInMicroSeconds = 1000 * (1000 * (long)n2) + (long)n3;
    }

    public Event(short s2, short s3, int n2) {
        super(0, 0, s2, s3, n2);
    }

    public Event(byte[] arrby) {
        this.rawBytes = (byte[])arrby.clone();
        super.b(arrby);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String b(short s2) {
        String string;
        if (s2 == 0) {
            string = "" + "EV_SYN";
            do {
                return string + " (" + s2 + ")";
                break;
            } while (true);
        }
        if (s2 == 1) {
            string = "" + "EV_KEY";
            return string + " (" + s2 + ")";
        }
        if (s2 == 2) {
            string = "" + "EV_REL";
            return string + " (" + s2 + ")";
        }
        if (s2 == 3) {
            string = "" + "EV_ABS";
            return string + " (" + s2 + ")";
        }
        if (s2 == 4) {
            string = "" + "EV_MSC";
            return string + " (" + s2 + ")";
        }
        if (s2 == 255) {
            string = "" + "STOP_TYPE";
            return string + " (" + s2 + ")";
        }
        string = "" + "UNKNOWN";
        return string + " (" + s2 + ")";
    }

    /*
     * Enabled aggressive block sorting
     */
    private String b(short s2, short s3) {
        String string = s2 == 0 ? (s3 == 0 ? "" + "SYN_REPORT        " : (s3 == 1 ? "" + "SYN_CONFIG        " : (s3 == 2 ? "" + "SYN_MT_REPORT     " : "" + "UNKNOWN"))) : (s2 == 1 ? (s3 == 102 ? "" + "KEY_HOME          " : (s3 == 114 ? "" + "KEY_VOLUMEDOWN    " : (s3 == 115 ? "" + "KEY_VOLUMEUP      " : (s3 == 116 ? "" + "KEY_POWER         " : (s3 == 139 ? "" + "KEY_MENU          " : (s3 == 158 ? "" + "KEY_BACK          " : (s3 == 217 ? "" + "KEY_SEARCH        " : (s3 == 231 ? "" + "KEY_SEND          " : (s3 == 253 ? "" + "KEY_DUMMY_BACK    " : (s3 == 325 ? "" + "BTN_TOOL_FINGER   " : (s3 == 330 ? "" + "BTN_TOUCH         " : "" + "UNKNOWN"))))))))))) : (s2 == 2 ? "" + "UNKNOWN" : (s2 == 3 ? (s3 == 16 ? "" + "ABS_HAT0X         " : (s3 == 17 ? "" + "ABS_HAT0Y         " : (s3 == 18 ? "" + "ABS_HAT1X         " : (s3 == 19 ? "" + "ABS_HAT1Y         " : (s3 == 20 ? "" + "ABS_HAT2X         " : (s3 == 21 ? "" + "ABS_HAT2Y         " : (s3 == 22 ? "" + "ABS_HAT3X         " : (s3 == 23 ? "" + "ABS_HAT3Y         " : (s3 == 24 ? "" + "ABS_PRESSURE      " : (s3 == 42 ? "" + "ABS_MT_POSITION   " : (s3 == 43 ? "" + "ABS_MT_AMPLITUDE  " : (s3 == 47 ? "" + "ABS_MT_SLOT       " : (s3 == 48 ? "" + "ABS_MT_TOUCH_MAJOR" : (s3 == 49 ? "" + "ABS_MT_TOUCH_MINOR" : (s3 == 50 ? "" + "ABS_MT_WIDTH_MAJOR" : (s3 == 51 ? "" + "ABS_MT_WIDTH_MINOR" : (s3 == 52 ? "" + "ABS_MT_ORIENTATION" : (s3 == 53 ? "" + "ABS_MT_POSITION_X " : (s3 == 54 ? "" + "ABS_MT_POSITION_Y " : (s3 == 55 ? "" + "ABS_MT_TOOL_TYPE  " : (s3 == 56 ? "" + "ABS_MT_BLOB_ID    " : (s3 == 57 ? "" + "ABS_MT_TRACKING_ID" : (s3 == 58 ? "" + "ABS_MT_PRESSURE   " : (s3 == 59 ? "" + "ABS_MT_DISTANCE   " : (s3 == 60 ? "" + "ABS_MT_TOOL_X     " : (s3 == 61 ? "" + "ABS_MT_TOOL_Y     " : "" + "UNKNOWN           ")))))))))))))))))))))))))) : (s2 == 4 ? (s3 == 0 ? "" + "MSC_SERIAL        " : (s3 == 1 ? "" + "MSC_PULSELED      " : (s3 == 2 ? "" + "MSC_GESTURE       " : (s3 == 3 ? "" + "MSC_RAW           " : (s3 == 4 ? "" + "MSC_SCAN          " : (s3 == 5 ? "" + "MSC_TIMESTAMP     " : (s3 == 6 ? "" + "MSC_TIMESEC       " : (s3 == 7 ? "" + "MSC_TIMEUSEC      " : (s3 == 8 ? "" + "MSC_ACTIVITY      " : "" + "UNKNOWN           "))))))))) : (s2 == 255 ? "" + "UNKNOWN" : "" + "UNKNOWN")))));
        StringBuilder stringBuilder = new StringBuilder().append(string).append(" (");
        Object[] arrobject = new Object[]{s3};
        return stringBuilder.append(String.format((String)"%3d", (Object[])arrobject)).append(")").toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void b(byte[] arrby) {
        if (arrby.length != EVENT_SIZE) {
            a.c(a, "parseBytes: parameter length != EVENT_SIZE (" + EVENT_SIZE + ")");
            return;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap((byte[])arrby);
        byteBuffer.order(ByteOrder.nativeOrder());
        this.deviceIndex = byteBuffer.getShort();
        if (IS_64_BIT) {
            this.time_sec = (int)byteBuffer.getLong();
            this.time_usec = (int)byteBuffer.getLong();
        } else {
            this.time_sec = byteBuffer.getInt();
            this.time_usec = byteBuffer.getInt();
        }
        this.type = byteBuffer.getShort();
        this.code = byteBuffer.getShort();
        this.value = byteBuffer.getInt();
        this.timeInMicroSeconds = 1000 * (1000 * (long)this.time_sec) + (long)this.time_usec;
    }

    private void f() {
        this.time_sec = (int)(this.timeInMicroSeconds / 1000 / 1000);
        this.time_usec = (int)(this.timeInMicroSeconds % 1000000);
    }

    public void a(long l2) {
        this.timeInMicroSeconds = l2 + this.timeInMicroSeconds;
        super.f();
    }

    public void a(short s2) {
        this.deviceIndex = s2;
    }

    public void a(byte[] arrby) {
        this.rawBytes = arrby;
    }

    public boolean a(short s2, short s3) {
        if (this.deviceIndex == s2 && this.type == s3) {
            return true;
        }
        return false;
    }

    public boolean a(short s2, short s3, int n2) {
        if (this.type == s2 && this.code == s3 && this.value == n2) {
            return true;
        }
        return false;
    }

    public boolean a(short s2, short s3, short s4, int n2) {
        if (this.deviceIndex == s2 && this.type == s3 && this.code == s4 && this.value == n2) {
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public byte[] a() {
        byte[] arrby = new byte[EVENT_SIZE];
        ByteBuffer byteBuffer = ByteBuffer.wrap((byte[])arrby);
        byteBuffer.order(ByteOrder.nativeOrder());
        byteBuffer.putShort(this.deviceIndex);
        if (IS_64_BIT) {
            byteBuffer.putLong((long)this.time_sec);
            byteBuffer.putLong((long)this.time_usec);
        } else {
            byteBuffer.putInt(this.time_sec);
            byteBuffer.putInt(this.time_usec);
        }
        byteBuffer.putShort(this.type);
        byteBuffer.putShort(this.code);
        byteBuffer.putInt(this.value);
        return arrby;
    }

    public long b() {
        return this.timeInMicroSeconds;
    }

    public short c() {
        return this.type;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void convertRawBytes(ByteOrder byteOrder, boolean bl) {
        ByteOrder byteOrder2 = ByteOrder.nativeOrder();
        boolean bl2 = IS_64_BIT;
        int n2 = bl ? 26 : 18;
        int n3 = EVENT_SIZE;
        int n4 = this.rawBytes.length / n2;
        byte[] arrby = new byte[n3 * n4];
        if (n2 != n3 || !byteOrder.equals((Object)byteOrder2)) {
            ByteBuffer byteBuffer = ByteBuffer.wrap((byte[])this.rawBytes);
            ByteBuffer byteBuffer2 = ByteBuffer.wrap((byte[])arrby);
            byteBuffer.order(byteOrder);
            byteBuffer2.order(byteOrder2);
            for (int i2 = 0; i2 < n4; ++i2) {
                int n5;
                int n6;
                short s2 = byteBuffer.getShort();
                if (bl) {
                    n5 = (int)byteBuffer.getLong();
                    n6 = (int)byteBuffer.getLong();
                } else {
                    n5 = byteBuffer.getInt();
                    n6 = byteBuffer.getInt();
                }
                short s3 = byteBuffer.getShort();
                short s4 = byteBuffer.getShort();
                int n7 = byteBuffer.getInt();
                byteBuffer2.putShort(s2);
                if (bl2) {
                    byteBuffer2.putLong((long)n5);
                    byteBuffer2.putLong((long)n6);
                } else {
                    byteBuffer2.putInt(n5);
                    byteBuffer2.putInt(n6);
                }
                byteBuffer2.putShort(s3);
                byteBuffer2.putShort(s4);
                byteBuffer2.putInt(n7);
            }
            this.a(arrby);
        }
    }

    public short d() {
        return this.deviceIndex;
    }

    public byte[] e() {
        return this.rawBytes;
    }

    public short getCode() {
        return this.code;
    }

    public int getTime_sec() {
        return this.time_sec;
    }

    public int getTime_usec() {
        return this.time_usec;
    }

    public int getValue() {
        return this.value;
    }

    public boolean matchesIndexTypeCode(short s2, short s3, short s4) {
        if (this.deviceIndex == s2 && this.type == s3 && this.code == s4) {
            return true;
        }
        return false;
    }

    public boolean matchesTypeCode(short s2, short s3) {
        if (this.type == s2 && this.code == s3) {
            return true;
        }
        return false;
    }

    public void setCode(short s2) {
        this.code = s2;
    }

    public void setDeviceIndicesInRawBytes(short s2) {
        int n2 = this.rawBytes.length / EVENT_SIZE;
        byte[] arrby = new byte[2];
        ByteBuffer byteBuffer = ByteBuffer.wrap((byte[])arrby);
        byteBuffer.order(ByteOrder.nativeOrder());
        byteBuffer.putShort(s2);
        for (int i2 = 0; i2 < n2; ++i2) {
            for (int i3 = 0; i3 < arrby.length; ++i3) {
                this.rawBytes[i3 + (0 + i2 * Event.EVENT_SIZE)] = arrby[i3];
            }
        }
    }

    public void setTimeInMicroSeconds(long l2) {
        this.timeInMicroSeconds = l2;
        super.f();
    }

    public void setTime_sec(int n2) {
        this.time_sec = n2;
    }

    public void setTime_usec(int n2) {
        this.time_usec = n2;
    }

    public void setType(short s2) {
        this.type = s2;
    }

    public void setValue(int n2) {
        this.value = n2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append("").append("DeviceIndex=").append((int)this.deviceIndex).append(" Time=").append(this.time_sec).append(":");
        Object[] arrobject = new Object[]{this.time_usec};
        String string = stringBuilder.append(String.format((String)"%06d", (Object[])arrobject)).toString();
        String string2 = string + " Type=";
        String string3 = string2 + this.b(this.type);
        String string4 = string3 + " Code=";
        String string5 = string4 + this.b(this.type, this.code);
        StringBuilder stringBuilder2 = new StringBuilder().append(string5).append(" Value=");
        Object[] arrobject2 = new Object[]{this.value};
        return stringBuilder2.append(String.format((String)"%-5d", (Object[])arrobject2)).toString();
    }
}

