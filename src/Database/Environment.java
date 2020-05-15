package Database;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

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

    public void loadDatabase(String databaseName) throws DatabaseNotFoundException{

        try {
            FileInputStream fis = new FileInputStream(String.format("%s.db", databaseName));
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj = ois.readObject();
            if(obj instanceof Database){

                putDatabase((Database)obj);

            }
            else{
                throw new DatabaseNotFoundException();
            }
        }
        catch (IOException | ClassNotFoundException e){
            throw new DatabaseNotFoundException();
        }
    }


    public void saveDatabase() throws IOException, DatabaseNotFoundException {

        Database db = getDatabase();

        String fileName = String.format("%s.db", db.getName());
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(db);
        oos.close();
        fos.close();

    }
    // Removes from environment but doesn't delete file.
    // To get the database user must USE the database.
    public void removeDatabase(){

        db = null;

    }

    public void deleteDatabase() throws IOException {

        Path fileName = Path.of(db.getName(), ".db");

        Files.delete(fileName);
    }

}
