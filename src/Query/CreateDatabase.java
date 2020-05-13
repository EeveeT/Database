package Query;

import Database.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class CreateDatabase implements Command {


    public String databaseName;

    public CreateDatabase(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public String run(Environment env) {

        Database db = new Database(databaseName);

        env.putDatabase(db);
        try{
            // Saves db to file system
            env.saveDatabase();
        } catch (IOException e) {
            e.printStackTrace();
            return "ERROR: See server logs";

        } catch (DatabaseNotFoundException e) {
            return "ERROR: DATABASE NOT FOUND";
        }
        // Removes db from environment; can then be USED from file system.
        env.removeDatabase();

        return "OK";

    }
}

