package Query;

import Database.Database;

public class Drop implements Command{

    public Structure structure;
    public String structName;

    public Drop(Structure structure, String structName) {
        this.structure = structure;
        this.structName = structName;
    }

    @Override
    public String run(Database db) {
        return "Error";

    }

    public enum Structure{ DATABASE, TABLE}
}

