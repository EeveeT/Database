package Database;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Database implements Serializable {

    private final String dbName;
    private final Map<String, Table> tables;

    public Database(String dbName) {
        this.dbName = dbName;
        this.tables = new HashMap<>();
    }

    public Table addTable(String tableName){
        Table table = new Table();
        tables.put(tableName, table);
        return table;

    }

    public boolean removeTable(String tableName){

        return tables.remove(tableName) != null;

    }

    public Table getTable(String tableName) throws TableNotFoundException {

        Table table = tables.get(tableName);
        if(table == null){
            throw new TableNotFoundException(tableName, tables.keySet());
        }
        return table;
    }

    public String getName(){

        return dbName;
    }

}
