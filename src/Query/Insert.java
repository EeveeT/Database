package Query;

import java.util.List;

public class Insert implements Command{
    public Insert(String tableName, List<Value> valueList) {

        this.tableName = tableName;
        this.valueList = valueList;
    }

    public String tableName;
    public List<Value> valueList;

}


