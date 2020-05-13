package Query;

import Database.*;

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
    public String run(Environment env) {


        Database db;

        try{
            db = env.getDatabase();
        }
        catch (DatabaseNotFoundException e){
            return "ERROR: Database Not Found";
        }

        Table table = db.addTable(tableName);

        if(columnNameList.isPresent()){
            for (String colName: columnNameList.get()) {
                table.addColumn(colName);
            }
        }
        return "OK";
    }
}
