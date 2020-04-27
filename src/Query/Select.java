package Query;

import java.util.List;
import java.util.Optional;

public class Select
{
    public Select(Optional<List<String>> attribList, String tableName, Optional<Condition> condition) {
        this.attribList = attribList;
        this.tableName = tableName;
        this.condition = condition;
    }

    public Optional<List<String>> attribList;
    public String tableName;
    public Optional<Condition> condition;



}






//<Select>         ::=  -SELECT- (<AttributeList> | * ) FROM <TableName> (WHERE <Condition>)?
//<WildAttribList> ::=  <AttributeList> | *