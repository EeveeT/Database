package Common;

public class Nothing extends Value {

    public Nothing() {}

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
        throw new IncorrectTypeException();
    }
    @Override
    public String toString() {

        return "<Empty>";
    }

}
