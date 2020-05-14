package Query;

import Common.IncorrectTypeException;
import Common.Value;
import Database.*;

import java.util.Map;

public class Delete implements Command {
    public String tableName;
    public Condition condition;

    public Delete(String tableName, Condition condition) {
        this.tableName = tableName;
        this.condition = condition;
    }

    @Override
    public String run(Environment env) {

        Table table;
        try {

           table = env.getDatabase().getTable(tableName);
        }
        catch (DatabaseNotFoundException e){
            return "ERROR: Database Not Loaded";
        }
        catch (TableNotFoundException e){
            return String.format("ERROR: Table '%s' Not Found", tableName);
        }

        int numRows = 0;

        try{
            numRows = table.getNumRows();
        }
        catch (NoColumnsException ignored ){}

        for(int rowIndex = 0; rowIndex < numRows; rowIndex++){

            Map<String, Value> row = table.getRow(rowIndex);
            try{
                if(condition.evaluateCondition(row)){
                    table.removeRow(rowIndex);
                }
            } catch (IncorrectTypeException e) {
                return "ERROR: Mismatched Types";
            }
        }

        return "OK";
    }

}
