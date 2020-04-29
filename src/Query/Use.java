package Query;

public class Use implements Command{
    public Use(String databaseName) {
        this.databaseName = databaseName;
    }

    String databaseName;
}

// <Use>            ::=  USE <DatabaseName>