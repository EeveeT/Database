package Parsers;

public class Token
{
    public final TokenType type;
    public final String string;

    public Token(TokenType type, String string) {
        this.type = type;
        this.string = string;
    }
}