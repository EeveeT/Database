package Query;

public class Join implements Command{
    public Join(String tableNameA, String tableNameB, String attribNameA, String attribNameB) {
        this.tableNameA = tableNameA;
        this.tableNameB = tableNameB;
        this.attribNameA = attribNameA;
        this.attribNameB = attribNameB;
    }

    public String tableNameA;
    public String tableNameB;
    public String attribNameA;
    public String attribNameB;

}


//JOIN  ::=  JOIN <TableName> AND <TableName> ON <AttributeName>
//           AND <AttributeName>