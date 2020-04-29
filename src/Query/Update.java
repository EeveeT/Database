package Query;

import java.util.List;

public class Update implements Command{

    public String tableName;
    public List<NameValue> nameValue;
    public Condition condition;

    public Update(String tableName, List<NameValue> nameValue, Condition condition) {
        this.tableName = tableName;
        this.nameValue = nameValue;
        this.condition = condition;
    }

    static public class NameValue{

        public String name;
        public Value value;

        public NameValue(String name, Value value) {
            this.name = name;
            this.value = value;
        }
    }


    // data Condition = Single String Operator Value
    //                | Composite Condition Condition Junction
}




//<Update>         ::=  UPDATE <TableName> SET <NameValueList> WHERE <Condition>
//<NameValueList>  ::=  <NameValuePair> |  <NameValuePair> , <NameValueList>
//<NameValuePair>  ::=  <AttributeName> = <Value>