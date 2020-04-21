package Parsers;

import java.util.ArrayList;
import java.util.List;
import static Parsers.TokenType.*;
import static java.lang.Character.isDigit;



public class Tokeniser
{
    // A specific type or parser

    private final String source;
    private final List<Token> tokens;
    private int start;
    private int current;

    public Tokeniser(String source) {
        this.source = source;
        this.tokens = new ArrayList<>();
        this.start = 0;
        this.current = 0;
    }

    List<Token> tokenise() throws UnexpectedCharacterException {
        while(!isAtEnd()){
            start = current;
            scanToken();
        }
        return tokens;
    }

    private void scanToken() throws UnexpectedCharacterException {
        char ch = nextChar();

        switch(ch){
            case '(':
                addToken(BRACKET_LEFT);
                break;

            case ')':
                addToken(BRACKET_RIGHT);
                break;

            case ',':
                addToken(COMMA);
                break;

            case '=':
                if (matches('=')) { addToken(EQUALS); }
                break;

            case '<':
                // ask in forum about brackets for if statement below
                if(matches('=')) { addToken(LESS_THAN_EQ); }
                else { addToken(LESS_THAN); }
                break;

            case '>':
                if(matches('=')) { addToken(GREATER_THAN_EQ); }
                else { addToken(GREATER_THAN);}
                break;

            case '!':
                if(matches('=')) { addToken(NOT_EQUAL_TO); }
                break;

            // Ignores whitespaces
            case ' ':
            case '\r':
            case '\t':
            case '\n':
                break;


            default:
                if(isDigit(ch)){
                    scanNumber();
                }
                else {
                    throw new UnexpectedCharacterException();
                }
        }
    }


    private char nextChar() {
        current += 1;
        return source.charAt(current - 1);
    }

    private void addToken(TokenType type){
       String text = source.substring(start, current);
       tokens.add(new Token(type, text));
    }


    private boolean isAtEnd()
    {
        return current >= source.length();
    }

    // This method advances the input if the next char matches `nextChar`.
    // Returns true if the chars matched.
    private boolean matches(char nextChar)
    {
        if(lookAhead() == nextChar){
            nextChar();
            return true;
        }

        return false;
    }

    private char lookAhead(){
        if (isAtEnd()){
            return '\0';
        }
        return source.charAt(current);
    }

    private void scanNumber(){

        while(isDigit(lookAhead())){
            nextChar();
        }
        if(matches('.')){
            while (isDigit(lookAhead())){
                nextChar();
            }
        }
        addToken(NUMBER);
    }

}


