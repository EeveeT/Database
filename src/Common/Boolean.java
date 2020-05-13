package Common;

public class Boolean extends Value {
    private final boolean bool;

    public Boolean(boolean bool) {
        this.bool = bool;
    }

    @Override
    public boolean greaterThan(Value value) throws IncorrectTypeException {
        throw new IncorrectTypeException();
    }

    @Override
    public boolean lessThan(Value value) throws IncorrectTypeException {
        throw new IncorrectTypeException();
    }

    @Override
    public boolean equalTo(Value value) throws IncorrectTypeException {

        if(value instanceof Common.Boolean){
            return ((Common.Boolean) value).bool == bool;
        }
        throw new IncorrectTypeException();
    }
    @Override
    public String toString() {

        // Ternary operator
        return bool ? "true":"false";
    }

}
