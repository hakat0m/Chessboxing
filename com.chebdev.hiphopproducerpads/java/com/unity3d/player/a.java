package com.unity3d.player;

import android.os.Build.VERSION;
import com.google.unity.BuildConfig;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public final class a extends SSLSocketFactory {
    private static volatile SSLSocketFactory c;
    private static final Object[] d = new Object[0];
    private static final boolean e;
    private final SSLSocketFactory a;
    private final a b = new a(this);

    class a implements HandshakeCompletedListener {
        final /* synthetic */ a a;

        a(a aVar) {
            this.a = aVar;
        }

        public final void handshakeCompleted(HandshakeCompletedEvent handshakeCompletedEvent) {
            SSLSession session = handshakeCompletedEvent.getSession();
            String cipherSuite = session.getCipherSuite();
            String protocol = session.getProtocol();
            String str = BuildConfig.FLAVOR;
            try {
                str = session.getPeerPrincipal().getName();
            } catch (SSLPeerUnverifiedException e) {
            }
            c.Log(2, "Connected to " + str + " using " + protocol + " protocol and " + cipherSuite + " cipher.");
        }
    }

    static {
        boolean z = false;
        if (VERSION.SDK_INT >= 16 && VERSION.SDK_INT < 20) {
            z = true;
        }
        e = z;
    }

    private a() {
        SSLContext instance = SSLContext.getInstance("TLS");
        instance.init(null, null, null);
        this.a = instance.getSocketFactory();
    }

    private static Socket a(Socket socket) {
        if (socket != null && (socket instanceof SSLSocket) && e) {
            ((SSLSocket) socket).setEnabledProtocols(((SSLSocket) socket).getSupportedProtocols());
        }
        return socket;
    }

    public static SSLSocketFactory a() {
        synchronized (d) {
            if (c != null) {
                SSLSocketFactory sSLSocketFactory = c;
                return sSLSocketFactory;
            }
            try {
                sSLSocketFactory = new a();
                c = sSLSocketFactory;
                return sSLSocketFactory;
            } catch (Exception e) {
                c.Log(5, "CustomSSLSocketFactory: Failed to create SSLSocketFactory (" + e.getMessage() + ")");
                return null;
            }
        }
    }

    public final Socket createSocket() {
        return a(this.a.createSocket());
    }

    public final Socket createSocket(String str, int i) {
        return a(this.a.createSocket(str, i));
    }

    public final Socket createSocket(String str, int i, InetAddress inetAddress, int i2) {
        return a(this.a.createSocket(str, i, inetAddress, i2));
    }

    public final Socket createSocket(InetAddress inetAddress, int i) {
        return a(this.a.createSocket(inetAddress, i));
    }

    public final Socket createSocket(InetAddress inetAddress, int i, InetAddress inetAddress2, int i2) {
        return a(this.a.createSocket(inetAddress, i, inetAddress2, i2));
    }

    public final Socket createSocket(Socket socket, String str, int i, boolean z) {
        return a(this.a.createSocket(socket, str, i, z));
    }

    public final String[] getDefaultCipherSuites() {
        return this.a.getDefaultCipherSuites();
    }

    public final String[] getSupportedCipherSuites() {
        return this.a.getSupportedCipherSuites();
    }
}
