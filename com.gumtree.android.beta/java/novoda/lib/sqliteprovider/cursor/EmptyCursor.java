package novoda.lib.sqliteprovider.cursor;

import android.database.AbstractCursor;

public class EmptyCursor extends AbstractCursor {
    public String[] getColumnNames() {
        return new String[]{"_id"};
    }

    public int getCount() {
        return 0;
    }

    public double getDouble(int i) {
        return 0.0d;
    }

    public float getFloat(int i) {
        return 0.0f;
    }

    public int getInt(int i) {
        return 0;
    }

    public long getLong(int i) {
        return 0;
    }

    public short getShort(int i) {
        return (short) 0;
    }

    public String getString(int i) {
        return null;
    }

    public boolean isNull(int i) {
        return true;
    }
}
