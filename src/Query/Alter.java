package Query;

import Database.*;

import java.io.IOException;

public class Alter implements Command {

    public String columnName;

    public String tableName;
    public Action altType;

    public Alter(String tableName, Action altType, String columnName) {
        this.tableName = tableName;
        this.altType = altType;
        this.columnName = columnName;
    }

    @Override
    public String run(Environment env) {

        Table table;

        try {
            Database db = env.getDatabase();
            table = db.getTable(tableName);
        } catch (DatabaseNotFoundException | TableNotFoundException e) {
            return "ERROR";
        }

        if (altType == Action.ADD) {
            table.addColumn(columnName);
        } else {
            table.removeColumn(columnName);
        }

        try {
            env.saveDatabase();
        } catch (IOException | DatabaseNotFoundException ignored) {
        }

        return "OK";

    }

    public enum Action{ ADD, DROP}

}



