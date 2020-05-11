package Query;

import java.util.List;
import Common.Value;
import Database.Database;

public class Update implements Command {

    public String tableName;
    public List<NameValue> nameValue;
    public Condition condition;

    public Update(String tableName, List<NameValue> nameValue, Condition condition) {
        this.tableName = tableName;
        this.nameValue = nameValue;
        this.condition = condition;
    }

    @Override
    public String run(Database db) {
        return "Error";

    }

    static public class NameValue {

        public String name;
        public Value value;

        public NameValue(String name, Value value) {
            this.name = name;
            this.value = value;
        }
    }
}

