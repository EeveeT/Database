package Database;

import java.util.HashMap;
import java.util.Map;

public class Database {

    public final String dbName;
    public final Map<String, Table> tables;

    public Database(String dbName) {
        this.dbName = dbName;
        this.tables = new HashMap<>();
    }

    public void addTable(String tableName){

        tables.put(tableName, new Table());

    }

    public boolean removeTable(String tableName){

        return tables.remove(tableName) != null;

    }

    public Table getTable(String tableName){

        return tables.get(tableName);
    }

}
