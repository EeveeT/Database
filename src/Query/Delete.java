package Query;

public class Delete implements Command {
    public String tableName;
    public Condition condition;

    public Delete(String tableName, Condition condition) {
        this.tableName = tableName;
        this.condition = condition;
    }
}
