package Common;

public abstract class Value{

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


    static public class Integer extends Value {

        private final int anInt;

        public Integer(int anInt) {
            this.anInt = anInt;
        }

        private int toInt(){
            return anInt;
        }

        @Override
        public boolean equalTo(Value value) throws IncorrectTypeException {
            if(value instanceof Integer){
                return ((Integer) value).toInt() == anInt ;
            }
            throw new IncorrectTypeException();
        }

        @Override
        public boolean greaterThan(Value value) throws IncorrectTypeException {
            if(value instanceof Integer){
                return ((Integer) value).toInt() > anInt ;
            }
            throw new IncorrectTypeException();
        }

        @Override
        public boolean lessThan(Value value) throws IncorrectTypeException {
            if(value instanceof Integer){
                return ((Integer) value).toInt() < anInt ;
            }
            throw new IncorrectTypeException();
        }

    }
    static public class Float extends Value {

        private final float aFloat;

        public Float(float aFloat) {
            this.aFloat = aFloat;
        }

        private float toFloat(){
            return aFloat;
        }

        @Override
        public boolean equalTo(Value value) throws IncorrectTypeException {
            if(value instanceof Float){
                return ((Float) value).toFloat() == aFloat ;
            }
            throw new IncorrectTypeException();
        }

        @Override
        public boolean greaterThan(Value value) throws IncorrectTypeException {
            if(value instanceof Float){
                return ((Float) value).toFloat() > aFloat ;
            }
            throw new IncorrectTypeException();
        }

        @Override
        public boolean lessThan(Value value) throws IncorrectTypeException {
            if(value instanceof Float){
                return ((Float) value).toFloat() < aFloat ;
            }
            throw new IncorrectTypeException();
        }

    }
    //Text is the StringLiteral renamed to Text so to not confuse Value.string and java.lang.string
    static public class Text extends Value {

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

            if (value instanceof Text) {
                return ((Text) value).toString().equals(string);
            }
            throw new IncorrectTypeException();
        }

        @Override
        public boolean like(Value value) throws IncorrectTypeException {

           if(value instanceof Text){

               return string.contains(value.toString());
           }

           throw new IncorrectTypeException();
        }

        @Override
        public String toString() {

            return string;
        }

    }
    static public class Boolean extends Value {
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

            if(value instanceof Boolean){
                return ((Boolean) value).bool == bool;
            }
            throw new IncorrectTypeException();
        }
    }
    static public class Nothing extends Value {

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
    }


}
