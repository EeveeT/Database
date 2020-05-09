package Database;

import java.util.HashMap;
import java.util.Map;

public class Database {

    public final String dbName;
    private final Map<String, Table> tables;

    public Database(String dbName) {
        this.dbName = dbName;
        this.tables = new HashMap<>();
    }

    public Table addTable(String tableName){

        return tables.put(tableName, new Table());

    }

    public boolean removeTable(String tableName){

        return tables.remove(tableName) != null;

    }

    public Table getTable(String tableName) throws TableNotFoundException {

        Table table = tables.get(tableName);
        if(table == null){
            throw new TableNotFoundException();
        }
        return table;
    }

}
