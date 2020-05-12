package Query;

import Database.Database;
import Database.Environment;


import java.io.*;

public class Use implements Command{
    String databaseName;

    public Use(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public String run(Environment env) {

        try {
            FileInputStream fis = new FileInputStream(String.format("%s.db", databaseName));
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj = ois.readObject();
            if(obj instanceof Database){

                env.putDatabase((Database)obj);
                return "OK";
            }
            return "ERROR";
        }
        catch (IOException | ClassNotFoundException e){
            return "ERROR";
        }
    }
}

