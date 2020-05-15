package Query;

import Common.Value;
import Database.*;

import java.io.IOException;
import java.util.List;

public class Insert implements Command {
    public String tableName;
    public List<Value> valueList;

    public Insert(String tableName, List<Value> valueList) {
        this.tableName = tableName;
        this.valueList = valueList;
    }

    public String run(Environment env) {

        Database db;

        try {
            db = env.getDatabase();
            Table table = db.getTable(tableName);
            table.addRow(valueList);
        } catch (MismatchedRowLengthException
                | TableNotFoundException
                | DatabaseNotFoundException e) {
            return "Error";
        }

        try {
            env.saveDatabase();
        } catch (IOException | DatabaseNotFoundException ignored) {
        }

        return "OK";
    }
}
