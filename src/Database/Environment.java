package Database;

public class Environment {

    private Database db;

    public Environment() {
        this.db = null;
    }


    public void putDatabase(Database db){
        this.db = db;
    }
    public Database getDatabase() throws DatabaseNotFoundException {

        if(db == null){
            throw new DatabaseNotFoundException();
        }
        return db;
    }

}
