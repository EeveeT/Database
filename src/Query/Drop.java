package Query;

public class Drop implements Command{

    public Structure structure;
    public String structName;

    public Drop(Structure structure, String structName) {
        this.structure = structure;
        this.structName = structName;
    }

    public enum Structure{ DATABASE, TABLE}
}

