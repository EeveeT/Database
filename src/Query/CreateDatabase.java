package Query;

public class CreateDatabase implements Command {


    public String databaseName;

    public CreateDatabase(String databaseName) {
        this.databaseName = databaseName;
    }

}

