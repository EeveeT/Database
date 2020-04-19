package Parsers;

public class Token
{
    public TokenType type;
    public String string;

    public Token(TokenType type, String string)
    {
        this.type = type;
        this.string = string;
    }
}