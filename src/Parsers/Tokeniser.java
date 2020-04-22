package Parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Parsers.TokenType.*;
import static java.lang.Character.isDigit;
import static java.lang.Character.isLetterOrDigit;
import static java.lang.Character.isLetter;



public class Tokeniser
{
    // A specific type or parser

    private static final Map<String, TokenType> keywords;

    static{
        keywords = new HashMap<>();
        keywords.put("SELECT", SELECT);
        keywords.put("USE", USE);
        keywords.put("CREATE", CREATE);
        keywords.put("DROP", DROP);
        keywords.put("ALTER", ALTER);
        keywords.put("INSERT", INSERT);
        keywords.put("UPDATE", UPDATE);
        keywords.put("DELETE", DELETE);
        keywords.put("JOIN", JOIN);
        keywords.put("DATABASE", DATABASE);
        keywords.put("TABLE", TABLE);
        keywords.put("INTO", INTO);
        keywords.put("VALUES", VALUES);
        keywords.put("FROM", FROM);
        keywords.put("SET", SET);
        keywords.put("WHERE", WHERE);
        keywords.put("ON", ON);
        keywords.put("TRUE", TRUE);
        keywords.put("FALSE", FALSE);
        keywords.put("AND ", AND);
        keywords.put("OR", OR);
    }

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

            case '\'':
                scanString();
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
                else if(isLetter(ch)){
                    scanWord();
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

    private void scanString() throws UnexpectedCharacterException {

        while(isLetterOrDigit(lookAhead())){
            nextChar();
        }
        if(matches('\'')){
            addToken(STRING);
        }
        else{
            throw new UnexpectedCharacterException();
        }
    }

    private void scanWord(){

        while(isLetter(lookAhead())){
            nextChar();
        }
        String text = source.substring(start, current);
        addToken(keywords.getOrDefault(text, VARIABLE));

    }


}


