package com.facebook.stetho.inspector.protocol.module;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import com.facebook.stetho.common.Util;
import com.facebook.stetho.inspector.helper.ChromePeerManager;
import com.facebook.stetho.inspector.jsonrpc.JsonRpcException;
import com.facebook.stetho.inspector.jsonrpc.JsonRpcPeer;
import com.facebook.stetho.inspector.jsonrpc.JsonRpcResult;
import com.facebook.stetho.inspector.jsonrpc.protocol.JsonRpcError;
import com.facebook.stetho.inspector.jsonrpc.protocol.JsonRpcError.ErrorCode;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsDomain;
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsMethod;
import com.facebook.stetho.json.ObjectMapper;
import com.facebook.stetho.json.annotation.JsonProperty;
import com.gumtree.android.message_box.ConversationsIntentService;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

@TargetApi(11)
public class Database implements ChromeDevtoolsDomain {
    private static final int MAX_BLOB_LENGTH = 512;
    private static final int MAX_EXECUTE_RESULTS = 250;
    private static final String UNKNOWN_BLOB_LABEL = "{blob}";
    private final ChromePeerManager mChromePeerManager = new ChromePeerManager();
    private List<DatabaseDriver> mDatabaseDrivers = new ArrayList();
    private final ObjectMapper mObjectMapper;
    private final DatabasePeerRegistrationListener mPeerListener = new DatabasePeerRegistrationListener(this.mDatabaseDrivers, null);

    public class AddDatabaseEvent {
        @JsonProperty(required = true)
        public DatabaseObject database;
    }

    public class DatabaseObject {
        @JsonProperty(required = true)
        public String domain;
        @JsonProperty(required = true)
        public String id;
        @JsonProperty(required = true)
        public String name;
        @JsonProperty(required = true)
        public String version;
    }

    public class Error {
        @JsonProperty(required = true)
        public int code;
        @JsonProperty(required = true)
        public String message;
    }

    public class ExecuteSQLRequest {
        @JsonProperty(required = true)
        public String databaseId;
        @JsonProperty(required = true)
        public String query;
    }

    public class ExecuteSQLResponse implements JsonRpcResult {
        @JsonProperty
        public List<String> columnNames;
        @JsonProperty
        public Error sqlError;
        @JsonProperty
        public List<String> values;
    }

    class GetDatabaseTableNamesRequest {
        @JsonProperty(required = true)
        public String databaseId;

        private GetDatabaseTableNamesRequest() {
        }
    }

    class GetDatabaseTableNamesResponse implements JsonRpcResult {
        @JsonProperty(required = true)
        public List<String> tableNames;

        private GetDatabaseTableNamesResponse() {
        }
    }

    public Database() {
        this.mChromePeerManager.setListener(this.mPeerListener);
        this.mObjectMapper = new ObjectMapper();
    }

    public void add(DatabaseDriver databaseDriver) {
        this.mDatabaseDrivers.add(databaseDriver);
    }

    @ChromeDevtoolsMethod
    public void enable(JsonRpcPeer jsonRpcPeer, JSONObject jSONObject) {
        this.mChromePeerManager.addPeer(jsonRpcPeer);
    }

    @ChromeDevtoolsMethod
    public void disable(JsonRpcPeer jsonRpcPeer, JSONObject jSONObject) {
        this.mChromePeerManager.removePeer(jsonRpcPeer);
    }

    @ChromeDevtoolsMethod
    public JsonRpcResult getDatabaseTableNames(JsonRpcPeer jsonRpcPeer, JSONObject jSONObject) throws JsonRpcException {
        GetDatabaseTableNamesRequest getDatabaseTableNamesRequest = (GetDatabaseTableNamesRequest) this.mObjectMapper.convertValue(jSONObject, GetDatabaseTableNamesRequest.class);
        DatabaseDriver databasePeer = getDatabasePeer(getDatabaseTableNamesRequest.databaseId);
        try {
            GetDatabaseTableNamesResponse getDatabaseTableNamesResponse = new GetDatabaseTableNamesResponse();
            getDatabaseTableNamesResponse.tableNames = databasePeer.getTableNames(getDatabaseTableNamesRequest.databaseId);
            return getDatabaseTableNamesResponse;
        } catch (SQLiteException e) {
            throw new JsonRpcException(new JsonRpcError(ErrorCode.INVALID_REQUEST, e.toString(), null));
        }
    }

    @ChromeDevtoolsMethod
    public JsonRpcResult executeSQL(JsonRpcPeer jsonRpcPeer, JSONObject jSONObject) {
        ExecuteSQLRequest executeSQLRequest = (ExecuteSQLRequest) this.mObjectMapper.convertValue(jSONObject, ExecuteSQLRequest.class);
        String str = executeSQLRequest.databaseId;
        String str2 = executeSQLRequest.query;
        try {
            return getDatabasePeer(str).executeSQL(executeSQLRequest.databaseId, executeSQLRequest.query, new 1(this));
        } catch (SQLiteException e) {
            Error error = new Error();
            error.code = 0;
            error.message = e.getMessage();
            JsonRpcResult executeSQLResponse = new ExecuteSQLResponse();
            executeSQLResponse.sqlError = error;
            return executeSQLResponse;
        }
    }

    private DatabaseDriver getDatabasePeer(String str) {
        for (DatabaseDriver databaseDriver : this.mDatabaseDrivers) {
            List databaseNames = databaseDriver.getDatabaseNames();
            if (databaseNames != null && databaseNames.contains(str)) {
                return databaseDriver;
            }
        }
        return null;
    }

    private static ArrayList<String> flattenRows(Cursor cursor, int i) {
        boolean z;
        int i2 = 0;
        if (i >= 0) {
            z = true;
        } else {
            z = false;
        }
        Util.throwIfNot(z);
        ArrayList<String> arrayList = new ArrayList();
        int columnCount = cursor.getColumnCount();
        for (int i3 = 0; i3 < i && cursor.moveToNext(); i3++) {
            for (int i4 = 0; i4 < columnCount; i4++) {
                switch (cursor.getType(i4)) {
                    case ConversationsIntentService.DEFAULT_PAGE /*0*/:
                        arrayList.add(null);
                        break;
                    case HighlightView.GROW_NONE /*1*/:
                        arrayList.add(String.valueOf(cursor.getLong(i4)));
                        break;
                    case HighlightView.GROW_LEFT_EDGE /*2*/:
                        arrayList.add(String.valueOf(cursor.getDouble(i4)));
                        break;
                    case HighlightView.GROW_RIGHT_EDGE /*4*/:
                        arrayList.add(blobToString(cursor.getBlob(i4)));
                        break;
                    default:
                        arrayList.add(cursor.getString(i4));
                        break;
                }
            }
        }
        if (!cursor.isAfterLast()) {
            while (i2 < columnCount) {
                arrayList.add("{truncated}");
                i2++;
            }
        }
        return arrayList;
    }

    private static String blobToString(byte[] bArr) {
        if (bArr.length <= MAX_BLOB_LENGTH && fastIsAscii(bArr)) {
            try {
                return new String(bArr, "US-ASCII");
            } catch (UnsupportedEncodingException e) {
            }
        }
        return UNKNOWN_BLOB_LABEL;
    }

    private static boolean fastIsAscii(byte[] bArr) {
        for (byte b : bArr) {
            if ((b & -128) != 0) {
                return false;
            }
        }
        return true;
    }
}
