package Query;

import java.util.List;
import java.util.Optional;

public class CreateTable implements Command{

    public String tableName;
    public Optional<List<String>> attribList;

    public CreateTable(String tableName, Optional<List<String>> attribList) {
        this.tableName = tableName;
        this.attribList = attribList;
    }

}
