package Query;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import Common.IncorrectTypeException;
import Common.Value;
import Database.*;

public class Update implements Command {

    public String tableName;
    public List<NameValue> nameValue;
    public Condition condition;

    public Update(String tableName, List<NameValue> nameValue, Condition condition) {
        this.tableName = tableName;
        this.nameValue = nameValue;
        this.condition = condition;
    }

    @Override
    public String run(Environment env) {

        Table table;

        try {
            table = env.getDatabase().getTable(tableName);
        }
        catch (DatabaseNotFoundException e ){
            return "ERROR: Database Not Loaded";
        }
        catch (TableNotFoundException e){
            return "ERROR: Table Not Found";
        }

        int numRows = 0;
        try{
            numRows = table.getNumRows();
        } catch (NoColumnsException ignored) {}

        for(int rowIndex = 0; rowIndex < numRows; rowIndex++) {

            Map<String, Value> row = table.getRow(rowIndex);
            try{
                if(condition.evaluateCondition(row)){
                    table.updateRow(rowIndex, row);
                }
            }
            catch (IncorrectTypeException e){
                return "ERROR";
            }
        }
        return "OK";
    }

    static public class NameValue {

        public final String name;
        public final Value value;

        public NameValue(String name, Value value) {
            this.name = name;
            this.value = value;
        }
    }
}

