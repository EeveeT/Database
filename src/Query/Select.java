package Query;

import Common.IncorrectTypeException;
import Common.TableFormatter;
import Common.Value;
import Database.*;

import java.util.*;

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

        try {
            db = env.getDatabase();
            table = db.getTable(tableName);
        } catch (DatabaseNotFoundException e) {
            return "ERROR: Database Not Found";
        } catch (TableNotFoundException e) {
            return "Error: Table Not Found";
        }

        // Will only run table::getColumnNames when it needs to.
        List<String> columnNames = attribList.orElseGet(table::getColumnNames);

        int numRows = 0;

        try {
            numRows = table.getNumRows();
        } catch (NoColumnsException ignored) {}
        // '+ 1' to account for id.
        TableFormatter tableFormatter = new TableFormatter(columnNames.size() + 1);

        try {
            List<String> header = new ArrayList<>(List.copyOf(columnNames));
            header.add(0, "id");
            tableFormatter.addRow(header);
        } catch (MismatchedRowLengthException e) {
            System.err.println("Mismatched Row Length Error");
            return "ERROR: Server Crashed";
        }

        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {

            Map<String, Value> row = table.getRow(rowIndex);

            boolean condTrue;
            try {
                condTrue = condition.orElseThrow().evaluateCondition(row);
            } catch (IncorrectTypeException e) {
                return "ERROR: Incorrect type";

            } catch (NoSuchElementException e) {

                condTrue = true;
            }
            if (condTrue) {
                List<String> printedRow = new ArrayList<>();

                printedRow.add(Integer.toString(rowIndex));

                for (String columnName : columnNames) {
                    printedRow.add(row.get(columnName).toString());
                }
                try {
                    tableFormatter.addRow(printedRow);
                }
                catch (MismatchedRowLengthException e){
                    return "ERROR: Server Crashed";
                }
            }
        }
        return tableFormatter.toString();
    }
}



