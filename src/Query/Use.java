package Query;

public class Use implements Command{
    String databaseName;

    public Use(String databaseName) {
        this.databaseName = databaseName;
    }
}

