/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  java.io.Serializable
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.nio.ByteBuffer
 *  java.nio.ByteOrder
 *  java.util.ArrayList
 *  java.util.Iterator
 */
package com.cygery.repetitouch;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;

public class UInputDeviceInfo
implements Serializable {
    static final long serialVersionUID = 1;
    public ArrayList<EventInfo> events = new ArrayList();
    public IdInfo id;
    public ArrayList<Integer> inputProperties = new ArrayList();
    public byte[] name;

    /*
     * Enabled aggressive block sorting
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append("").append("name:");
        String string = this.name != null ? new String(this.name) : null;
        String string2 = stringBuilder.append(string).toString();
        String string3 = string2 + " id:[ " + this.id + " ]";
        Iterator iterator = this.events.iterator();
        String string4 = string3;
        while (iterator.hasNext()) {
            EventInfo eventInfo = (EventInfo)iterator.next();
            string4 = string4 + " info:[ " + eventInfo + " ]";
        }
        Iterator iterator2 = this.inputProperties.iterator();
        while (iterator2.hasNext()) {
            int n2 = (Integer)iterator2.next();
            string4 = string4 + " inputprop:[ " + n2 + " ]";
        }
        return string4;
    }

    public static class ABSEventInfo
    extends EventInfo {
        static final long serialVersionUID = 1;
        public int flat;
        public int fuzz;
        public int max;
        public int min;
        public int res;

        public ABSEventInfo(int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
            super(n2, n3);
            this.min = n4;
            this.max = n5;
            this.fuzz = n6;
            this.flat = n7;
            this.res = n8;
        }

        @Override
        public byte[] getBytes() {
            byte[] arrby = new byte[24];
            ByteBuffer byteBuffer = ByteBuffer.wrap((byte[])arrby);
            byteBuffer.order(ByteOrder.nativeOrder());
            byteBuffer.putInt(this.type);
            byteBuffer.putInt(this.code);
            byteBuffer.putInt(this.max);
            byteBuffer.putInt(this.min);
            byteBuffer.putInt(this.fuzz);
            byteBuffer.putInt(this.flat);
            return arrby;
        }

        @Override
        public String toString() {
            return super.toString() + " min:" + this.min + " max:" + this.max + " fuzz:" + this.fuzz + " flat:" + this.flat + " res:" + this.res;
        }
    }

    public static class EventInfo
    implements Serializable {
        static final long serialVersionUID = 1;
        public int code;
        public int type;

        public EventInfo(int n2, int n3) {
            this.type = n2;
            this.code = n3;
        }

        public byte[] getBytes() {
            byte[] arrby = new byte[8];
            ByteBuffer byteBuffer = ByteBuffer.wrap((byte[])arrby);
            byteBuffer.order(ByteOrder.nativeOrder());
            byteBuffer.putInt(this.type);
            byteBuffer.putInt(this.code);
            return arrby;
        }

        public String toString() {
            return "type:" + this.type + " code:" + this.code;
        }
    }

    public static class IdInfo
    implements Serializable {
        static final long serialVersionUID = 1;
        public int bustype;
        public int product;
        public int vendor;
        public int version;

        public IdInfo(int n2, int n3, int n4, int n5) {
            this.bustype = n2;
            this.vendor = n3;
            this.product = n4;
            this.version = n5;
        }

        public byte[] getBytes() {
            byte[] arrby = new byte[16];
            ByteBuffer byteBuffer = ByteBuffer.wrap((byte[])arrby);
            byteBuffer.order(ByteOrder.nativeOrder());
            byteBuffer.putInt(this.bustype);
            byteBuffer.putInt(this.vendor);
            byteBuffer.putInt(this.product);
            byteBuffer.putInt(this.version);
            return arrby;
        }

        public String toString() {
            return "bustype:" + this.bustype + " vendor:" + this.vendor + " product:" + this.product + " version:" + this.version;
        }
    }

}

