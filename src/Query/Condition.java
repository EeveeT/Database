package Query;

import Common.IncorrectTypeException;
import Common.Value;

import java.util.Map;

public abstract class Condition {

    public abstract boolean evaluateCondition(Map<String, Value> row) throws IncorrectTypeException;

    public enum Operator {Equals, GT, LT, GTE, LTE, NEquals, Like}

    public enum Junction {AND, OR}

    static public class Single extends Condition {

        public final String columnName;
        public final Operator operator;
        public final Value value;

        public Single(String columnName, Operator operator, Value value) {
            this.columnName = columnName;
            this.operator = operator;
            this.value = value;
        }

        @Override
        public boolean evaluateCondition(Map<String, Value> row) throws IncorrectTypeException {

            Value element = row.get(columnName);

            switch(operator){

                case Equals:
                    return element.equalTo(value);

                case GT:
                    return element.greaterThan(value);

                case LT:
                    return element.lessThan(value);

                case GTE:
                    return element.greaterThanEquals(value);

                case LTE:
                    return element.lessThanEquals(value);

                case NEquals:
                    return !element.equalTo(value);

                // Compiler did not recognise this switch is exhaustive so had to
                // set a default.
                default: //Like
                    return element.like(value);

            }
        }
    }

    static public class Composite extends Condition {

        public final Condition left;
        public final Condition right;
        public final Junction junction;

        public Composite(Condition left, Junction junction, Condition right) {
            this.left = left;
            this.right = right;
            this.junction = junction;
        }

        @Override
        public boolean evaluateCondition(Map<String, Value> row) throws IncorrectTypeException {

            if (junction == Junction.AND) {
                return left.evaluateCondition(row) && right.evaluateCondition(row);
            } else {
                return left.evaluateCondition(row) || right.evaluateCondition(row);
            }
        }
    }


}
