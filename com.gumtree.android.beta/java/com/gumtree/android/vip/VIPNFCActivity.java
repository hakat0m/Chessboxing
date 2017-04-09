package com.gumtree.android.vip;

import android.app.Activity;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.analytics.Track;
import java.nio.charset.Charset;

public class VIPNFCActivity extends VIPActivity implements CreateNdefMessageCallback, OnNdefPushCompleteCallback {
    private static final Handler HANDLER = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case VIPNFCActivity.MESSAGE_SENT /*1*/:
                    Toast.makeText(GumtreeApplication.getContext(), 2131165640, VIPNFCActivity.MESSAGE_SENT).show();
                    return;
                default:
                    Log.e(getClass().getSimpleName(), "case not supported " + message.what);
                    return;
            }
        }
    };
    private static final int MESSAGE_SENT = 1;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        NfcAdapter defaultAdapter = NfcAdapter.getDefaultAdapter(this);
        if (defaultAdapter != null) {
            defaultAdapter.setNdefPushMessageCallback(this, this, new Activity[0]);
            defaultAdapter.setOnNdefPushCompleteCallback(this, this, new Activity[0]);
        }
    }

    protected void initVipId(Bundle bundle) {
        if ("android.nfc.action.NDEF_DISCOVERED".equals(getIntent().getAction())) {
            Tag tag = (Tag) getIntent().getParcelableExtra("android.nfc.extra.TAG");
            if (tag == null) {
                this.vipId = -1;
                return;
            }
            this.vipId = getVIPIdFromTag(tag);
            Track.eventSuccessNFCVip(this.vipId);
            return;
        }
        super.initVipId(bundle);
    }

    private long getVIPIdFromTag(Tag tag) {
        Ndef ndef = Ndef.get(tag);
        if (ndef == null) {
            return -1;
        }
        NdefRecord[] records = ndef.getCachedNdefMessage().getRecords();
        int i = 0;
        while (i < records.length) {
            try {
                return Long.parseLong(new String(records[i].getPayload()));
            } catch (NumberFormatException e) {
                i += MESSAGE_SENT;
            }
        }
        return -1;
    }

    public NdefMessage createNdefMessage(NfcEvent nfcEvent) {
        String str = BuildConfig.FLAVOR + getVipId();
        Track.eventAttemptNFCVip(getVipId());
        if (VERSION.SDK_INT < 16) {
            return null;
        }
        NdefRecord[] ndefRecordArr = new NdefRecord[MESSAGE_SENT];
        ndefRecordArr[0] = NdefRecord.createMime("vnd.android.cursor.item/vnd.com.gumtree.ads.beam", str.getBytes(Charset.forName("US-ASCII")));
        return new NdefMessage(ndefRecordArr);
    }

    public void onNdefPushComplete(NfcEvent nfcEvent) {
        HANDLER.obtainMessage(MESSAGE_SENT).sendToTarget();
    }
}
