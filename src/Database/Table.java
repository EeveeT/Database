package Database;

import Common.Value;

import java.util.LinkedHashMap;

public class Table {
    // Invariants: MUST ensure size of each column remain the same. A.K.A.,
    // if you add an element to one column, you MUST
    // also add elements in the rest of the columns (to fill the entire 'row').

    private final LinkedHashMap<String, Column> columns;

    public Table() {
        this.columns = new LinkedHashMap<>();
    }


    private void addColumn(String columnName){
        // To add to a column, must first know how many elements (rows) exist in each column - they should
        // all be the same. Once we know how many elements there are in existing columns, need to add that
        // many elements to the new column to maintain invariance.
        Column column = new Column();

        try {
            int numRows = getNumRows();
            column.extendColumnBy(numRows);

        }
        catch (NoColumnsException e){
            // Don't want to do anything if there are no columns
        }




        columns.put(columnName,column);

    }

    private int getNumRows() throws NoColumnsException {

        for (Column column: columns.values()){
            return column.getNumElements();
        }
        throw new NoColumnsException();
    }


}
