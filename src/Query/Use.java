package Query;

import Database.*;

public class Use implements Command{
    String databaseName;

    public Use(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public String run(Environment env) {

        try {
            env.loadDatabase(databaseName);
            return "OK";
        }
        catch (DatabaseNotFoundException e){
            return "ERROR: Database Not Found";
        }
    }
}

