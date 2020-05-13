package Common;

//Text is the StringLiteral renamed to Text so to not confuse Value.string and java.lang.string
public class Text extends Value {

    private final String string;

    public Text(String string) {
        this.string = string;
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

        if (value instanceof Common.Text) {
            return ((Common.Text) value).toString().equals(string);
        }
        throw new IncorrectTypeException();
    }

    @Override
    public boolean like(Value value) throws IncorrectTypeException {

       if(value instanceof Common.Text){

           return string.contains(value.toString());
       }

       throw new IncorrectTypeException();
    }

    @Override
    public String toString() {

        return string;
    }

}
