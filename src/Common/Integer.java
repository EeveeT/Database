package Common;

public class Integer extends Value {

    private final int anInt;

    public Integer(int anInt) {
        this.anInt = anInt;
    }

    private int toInt(){
        return anInt;
    }

    @Override
    public boolean equalTo(Value value) throws IncorrectTypeException {
        if(value instanceof Common.Integer){
            return ((Common.Integer) value).toInt() == anInt ;
        }
        throw new IncorrectTypeException();
    }

    @Override
    public boolean greaterThan(Value value) throws IncorrectTypeException {
        if(value instanceof Common.Integer){
            return ((Common.Integer) value).toInt() > anInt ;
        }
        throw new IncorrectTypeException();
    }

    @Override
    public boolean lessThan(Value value) throws IncorrectTypeException {
        if(value instanceof Common.Integer){
            return ((Common.Integer) value).toInt() < anInt ;
        }
        throw new IncorrectTypeException();
    }
    @Override
    public String toString() {

        return String.format("%d", anInt);
    }

}
