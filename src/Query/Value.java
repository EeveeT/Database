package Query;

public abstract class Value{

    static public class Integer extends Value{
        private final int anInt;

        public Integer(int anInt) {
            this.anInt = anInt;
        }
    }

    static public class Float extends Value{
        private final float aFloat;

        public Float(float aFloat) {
            this.aFloat = aFloat;
        }
    }
    //Text is the StringLiteral renamed to Text so to not confuse Value.string and java.lang.string
    static public class Text extends Value{
        private final String string;

        public Text(String string) {
            this.string = string;
        }
    }
    static public class Boolean extends Value{
        private final boolean bool;

        public Boolean(boolean bool) {
            this.bool = bool;
        }
    }

}