package Query;

public abstract class Condition{

    static public class Single extends Condition{

        public final String attributeName;
        public final Operator operator;
        public final Value value;

        public Single(String attributeName, Operator operator, Value value) {
            this.attributeName = attributeName;
            this.operator = operator;
            this.value = value;
        }

    }

    static public class Composite extends Condition{

        public final Condition left;
        public final Condition right;
        public final Junction junction;

        public Composite(Condition left,  Junction junction, Condition right) {
            this.left = left;
            this.right = right;
            this.junction = junction;
        }

    }

    public enum Operator{ Equals, GT, LT ,GTE, LTE, NEquals, Like}
    public enum Junction{ AND, OR}



}
