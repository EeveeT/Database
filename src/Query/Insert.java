package Query;

import java.util.List;
import Common.Value;

public class Insert implements Command {
    public String tableName;
    public List<Value> valueList;

    public Insert(String tableName, List<Value> valueList) {
        this.tableName = tableName;
        this.valueList = valueList;
    }
}
