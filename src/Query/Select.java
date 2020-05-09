package Query;

import Database.Database;
import Database.TableNotFoundException;
import Database.Table;

import java.util.List;
import java.util.Optional;

public class Select implements Command {
    public Optional<List<String>> attribList;
    public String tableName;
    public Optional<Condition> condition;

    public Select(Optional<List<String>> attribList, String tableName, Optional<Condition> condition) {
        this.attribList = attribList;
        this.tableName = tableName;
        this.condition = condition;
    }

    @Override
    public String run(Database db) {

        Table table;

        try{
            table = db.getTable(tableName);
        } catch (TableNotFoundException e) {
            return "Error: Table Not Found";
        }



    }
}



