package Query;

import Database.Database;

public class Use implements Command{
    String databaseName;

    public Use(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public String run(Database db) {
        return "Error";

    }
}

