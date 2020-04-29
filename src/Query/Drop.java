package Query;

public class Drop implements Command{

    public Drop(Structure structure, String strucName) {
        this.structure = structure;
        this.strucName = strucName;
    }

    public Structure structure;
    public String strucName;

    public enum Structure{ DATABASE, TABLE}
}


//
//<Drop>           ::=  DROP <Structure> <StructureName>
//<Structure>      ::=  DATABASE | TABLE

