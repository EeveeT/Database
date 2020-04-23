package Query;

public class Condition{
    public Condition(String attributeName, Operator operator, Value value) {
        this.attributeName = attributeName;
        this.operator = operator;
        this.value = value;
    }

    public enum Operator{ Equals, GT, LT ,GTE, LTE, NEquals, Like}

    public String attributeName;
    public Operator operator;
    public Value value;


}
