package Query;

import Common.IncorrectTypeException;
import Common.TableFormatter;
import Common.Value;
import Database.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Join implements Command {
    public String tableNameA;
    public String tableNameB;
    public String attribNameA;
    public String attribNameB;

    public Join(String tableNameA, String tableNameB, String attribNameA, String attribNameB) {
        this.tableNameA = tableNameA;
        this.tableNameB = tableNameB;
        this.attribNameA = attribNameA;
        this.attribNameB = attribNameB;
    }

    @Override
    public String run(Environment env) {

        Database db;
        Table tableA;
        Table tableB;
        int rowNumA;
        int rowNumB;

        try {
             db = env.getDatabase();
             tableA = db.getTable(tableNameA);
             tableB = db.getTable(tableNameB);
             rowNumA = tableA.getNumRows();
             rowNumB = tableB.getNumRows();
        }
        catch (DatabaseNotFoundException | TableNotFoundException | NoColumnsException e){
            return "ERROR";
        }

        int numCols = tableA.getNumCol() + tableB.getNumCol() + 1;
        TableFormatter tableFormatter = new TableFormatter(numCols);

        List<String> header = new ArrayList<>();
        header.add("id");

        for(String columnName: tableA.getColumnNames()){
            header.add(String.format("%s.%s", tableNameA, columnName));
        }
        for(String columnName: tableB.getColumnNames()) {
            header.add(String.format("%s.%s", tableNameB, columnName));
        }
        try {
            tableFormatter.addRow(header);
        }
        catch (MismatchedRowLengthException e){
            return "ERROR";
        }

        int id = 1;

        for(int rowIndexA = 0; rowIndexA < rowNumA; rowIndexA++){
            Map<String, Value> rowA = tableA.getRow(rowIndexA);
            for(int rowIndexB = 0; rowIndexB < rowNumB; rowIndexB++){
                Map<String, Value> rowB = tableB.getRow(rowIndexB);
                try {
                    if (rowA.get(attribNameA).equalTo(rowB.get(attribNameB))) {

                        List<String> printedRow = new ArrayList<>();
                        printedRow.add((Integer.toString(id)));
                        id++;

                        for(String columnName: tableA.getColumnNames()){
                            printedRow.add(rowA.get(columnName).toString());
                        }
                        for(String columnName: tableB.getColumnNames()){
                            printedRow.add(rowB.get(columnName).toString());
                        }
                        try {
                            tableFormatter.addRow(printedRow);
                        }
                        catch (MismatchedRowLengthException e){
                            return "ERROR";
                        }
                    }
                }
                catch (IncorrectTypeException e){
                    return "ERROR: Incorrect Type";
                }
            }
        }
        return tableFormatter.toString();
    }
}
