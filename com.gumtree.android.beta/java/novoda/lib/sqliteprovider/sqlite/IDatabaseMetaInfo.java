package novoda.lib.sqliteprovider.sqlite;

import java.util.List;
import java.util.Map;
import novoda.lib.sqliteprovider.util.Constraint;

public interface IDatabaseMetaInfo {
    Map<String, SQLiteType> getColumns(String str);

    List<String> getForeignTables(String str);

    Map<String, String> getProjectionMap(String str, String... strArr);

    List<String> getTables();

    @Deprecated
    List<String> getUniqueConstrains(String str);

    List<Constraint> getUniqueConstraints(String str);

    int getVersion();

    void setVersion(int i);
}
