package Query;

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

}
