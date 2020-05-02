package Query;

public class CreateDatabase implements Command {


    public CreateDatabase(String databaseName) {
        this.databaseName = databaseName;
    }

    public String databaseName;

}

//todo: do we need to implement an error message? -
// does an error message matter

//<Create>         ::=  <CreateDatabase> | <CreateTable>
//
//<CreateDatabase> ::=  CREATE DATABASE <DatabaseName>
