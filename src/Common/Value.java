package Common;

import java.io.Serializable;

public abstract class Value implements Serializable {

    public abstract boolean greaterThan(Value value) throws IncorrectTypeException;

    public abstract boolean lessThan(Value value) throws IncorrectTypeException;

    public boolean greaterThanEquals(Value value) throws IncorrectTypeException{

        return !lessThan(value);
    }

    public boolean lessThanEquals(Value value) throws IncorrectTypeException{

        return !greaterThan(value);
    }

    public abstract boolean equalTo(Value value) throws IncorrectTypeException;

    public boolean like(Value value) throws IncorrectTypeException {
        throw new IncorrectTypeException();
    }


}
