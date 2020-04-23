package Parsers;

public class UnexpectedTokenException extends Throwable{

    public final Token token;

    public UnexpectedTokenException(Token token) {
        this.token = token;
    }
}