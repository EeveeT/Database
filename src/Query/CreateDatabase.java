package Query;

import Database.Database;

public class CreateDatabase implements Command {


    public String databaseName;

    public CreateDatabase(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public String run(Database db) {
        return "Error";
    }
}

