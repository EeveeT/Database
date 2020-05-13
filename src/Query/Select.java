package Query;

import Common.IncorrectTypeException;
import Common.Value;
import Database.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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
    public String run(Environment env) {

        Database db;
        Table table;

        try{
            db = env.getDatabase();
            table = db.getTable(tableName);
        }
        catch (DatabaseNotFoundException e){
            return "ERROR: Database Not Found";
        }
        catch (TableNotFoundException e) {
            return "Error: Table Not Found";
        }

        // Will only run table::getColumnNames when it needs to.
        List<String> columnNames = attribList.orElseGet(table::getColumnNames);

        int numRows = 0;

        try {
            numRows = table.getNumRows();
        }
        catch (NoColumnsException ignored) {}

        StringBuilder tableOutput = new StringBuilder();
//todo: try to find a way to sort this out
        tableOutput.append("id      ");
        for(String columnName : columnNames){
            tableOutput.append(String.format("%-8s", columnName));

        }
        tableOutput.append("\n");


        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {

            Map<String, Value> row = table.getRow(rowIndex);

            boolean condTrue;
            try {
                condTrue = condition.orElseThrow().evaluateCondition(row);
            }
            catch (IncorrectTypeException e) {
                return "Error: Incorrect type";

            }
            catch (NoSuchElementException e){

                condTrue = true;
            }
            if(condTrue){
                tableOutput.append(String.format("%-8s", rowIndex));

                for (String columnName: columnNames) {
                    tableOutput.append(String.format("%-8s", row.get(columnName)));
                }
                tableOutput.append("\n");

            }
        }
        return tableOutput.toString();
    }
}



