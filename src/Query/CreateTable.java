package Query;

import java.util.List;
import java.util.Optional;

public class CreateTable implements Command{

    public CreateTable(String tableName, Optional<List<String>> attribList) {
        this.tableName = tableName;
        this.attribList = attribList;
    }

    public String tableName;
    public Optional<List<String>> attribList;

}


//<Create>         ::=  <CreateDatabase> | <CreateTable>
//
//<CreateTable>    ::=  CREATE TABLE <TableName>
//                   |  CREATE TABLE <TableName> ( <AttributeList> )