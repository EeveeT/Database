package Query;

import Database.*;

import java.io.IOException;

public class Drop implements Command{

    public enum Structure{ DATABASE, TABLE}

    public Structure structure;
    public String structName;

    public Drop(Structure structure, String structName) {
        this.structure = structure;
        this.structName = structName;
    }

    @Override
    public String run(Environment env) {

        try{
            if(structure == Structure.DATABASE){
                dropDatabase(env);
            }
            else{
                dropTable(env);
            }
        }
        catch (IOException e){
            return "ERROR: File Not Found";
        }
        catch (DatabaseNotFoundException e){
            return "ERROR: Database Not Found";
        }

        return "OK";
    }

    private void dropDatabase(Environment env) throws DatabaseNotFoundException, IOException {

        Database db;

        db = env.getDatabase();
        String currentDbName = db.getName();
        if(currentDbName.equals(structName)){
            env.deleteDatabase();
            env.removeDatabase();
        }
        else {
            throw new DatabaseNotFoundException();
        }

    }

    private void dropTable(Environment env) throws DatabaseNotFoundException {

        Database db = env.getDatabase();
        db.removeTable(structName);

    }


}

