package Query;

import Database.*;

public class Drop implements Command{

    public Structure structure;
    public String structName;

    public Drop(Structure structure, String structName) {
        this.structure = structure;
        this.structName = structName;
    }

    @Override
    public String run(Environment env) {
        return "Error";

    }

    public enum Structure{ DATABASE, TABLE}
}

