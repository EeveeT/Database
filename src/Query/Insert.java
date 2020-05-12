package Query;

import java.util.List;
import Common.Value;
import Database.*;

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

            return "OK";
        }
        catch(MismatchedRowLengthException
             | TableNotFoundException
             | DatabaseNotFoundException e){
            return "Error";
        }
    }
}
