package Database;

import Common.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Table {
    // Invariants: MUST ensure size of each column remain the same. A.K.A.,
    // if you add an element to one column, you MUST
    // also add elements in the rest of the columns (to fill the entire 'row').

    private final ArrayList<String> columnNames;
    private final ArrayList<Column>  columns;

    public Table() {
        this.columns = new ArrayList<>();
        this.columnNames = new ArrayList<>();
    }


    public void addColumn(String columnName){
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
        columns.add(column);
        columnNames.add(columnName);
    }

    private int getNumRows() throws NoColumnsException {

        for (Column column: columns){
            return column.getNumElements();
        }
        throw new NoColumnsException();
    }

    private int getNumCol(){

        return columns.size();
    }

    public void addRow(List<Value> row) throws MismatchedRowLengthException {

       int numColumns = getNumCol();

       if(numColumns == row.size()){
           for(int index = 0; index < numColumns; index++){
               Value element = row.get(index);
               Column column = columns.get(index);
               column.addElement(element);
           }
       }
       else{
           throw new MismatchedRowLengthException();
       }
    }

    public Map<String, Value> getRow(int index){
    // a row is an index into the columns

        HashMap<String, Value> row = new HashMap<>();

        for(int column = 0; column < getNumCol(); column++){

            Value element = columns.get(column).getValue(index);
            String colName = columnNames.get(column);

            row.put(colName, element);
        }
        return row;
    }


}
