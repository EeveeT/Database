package Query;

import Database.Database;
import Database.Table;

import java.util.List;
import java.util.Optional;

public class CreateTable implements Command{

    public String tableName;
    public Optional<List<String>> columnNameList;

    public CreateTable(String tableName, Optional<List<String>> attribList) {
        this.tableName = tableName;
        this.columnNameList = attribList;
    }

    @Override
    public String run(Database db) {

        Table table = db.addTable(tableName);
        if(columnNameList.isPresent()){
            for (String colNam: columnNameList.get()) {
                table.addColumn(colNam);
            }
        }
        return "OK";
    }
}
