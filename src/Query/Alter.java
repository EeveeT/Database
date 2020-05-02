package Query;

import java.util.Optional;

public class Alter implements Command {
    public String tableName;
    public Action altType;
    public String attribName;


    public Alter(String tableName, Action altType, String attribName) {
        this.tableName = tableName;
        this.altType = altType;
        this.attribName = attribName;
    }


    public enum Action{ ADD, DROP}

}



