package Common;

import Database.MismatchedRowLengthException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TableFormatter {

    private final List<String[]> listOfRows;
    private final int numColumns;

    public TableFormatter(int numColumns) {

        listOfRows = new ArrayList<>();
        this.numColumns = numColumns;
    }

    public void addRow(Collection<String> row) throws MismatchedRowLengthException {

        // Guard against invalid input
        if(row.size() != numColumns){
            throw new MismatchedRowLengthException();
        }
        // Selects correct override that doesnt cast elements to Object.
        listOfRows.add(row.toArray(new String[numColumns]));

    }

    @Override
    public String toString() {

        StringBuilder tableOutput = new StringBuilder();

        int[] maxWidths = findMaxWidthCol();

        for (String[] row: listOfRows) {
            for(int colIndex = 0; colIndex < numColumns; colIndex++){
                tableOutput.append(
                        String.format(
                                "%-" + maxWidths[colIndex] + "s  ",
                                row[colIndex]
                        )
                );
            }
            tableOutput.append("\n");
        }

        return tableOutput.toString();
    }

    private int[] findMaxWidthCol(){

        int[] maxColumnWidths = new int[numColumns];

        for (String[] row: listOfRows ) {
            for(int colIndex = 0; colIndex < numColumns; colIndex++){
                int currentWidth = row[colIndex].length();
                if(currentWidth > maxColumnWidths[colIndex]){
                    // Finds the max width of column
                    maxColumnWidths[colIndex] = currentWidth;
                }
            }
        }
        return maxColumnWidths;
    }
}
