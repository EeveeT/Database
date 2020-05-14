package Query;

import Database.*;

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
        return "Error";

        // JOIN tableA AND tableB ON colX AND colY;

        // SELECT * FROM tableA
        // JOIN tableB ON tableA.colX = tableB.colY;

    }
}
