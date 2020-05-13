package Common;

public class Float extends Value {

    private final float aFloat;

    public Float(float aFloat) {
        this.aFloat = aFloat;
    }

    private float toFloat(){
        return aFloat;
    }

    @Override
    public boolean equalTo(Value value) throws IncorrectTypeException {
        if(value instanceof Common.Float){
            return ((Common.Float) value).toFloat() == aFloat ;
        }
        throw new IncorrectTypeException();
    }

    @Override
    public boolean greaterThan(Value value) throws IncorrectTypeException {
        if(value instanceof Common.Float){
            return ((Common.Float) value).toFloat() > aFloat ;
        }
        throw new IncorrectTypeException();
    }

    @Override
    public boolean lessThan(Value value) throws IncorrectTypeException {
        if(value instanceof Common.Float){
            return ((Common.Float) value).toFloat() < aFloat ;
        }
        throw new IncorrectTypeException();
    }

    @Override
    public String toString() {

        return String.format("%f", aFloat);
    }

}
