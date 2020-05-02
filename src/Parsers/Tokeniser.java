package Parsers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Parsers.TokenType.*;
import static java.lang.Character.*;

// This class converts a String into a List of Tokens.
//
// #Example Usage
//
// > List<Token> tokens = new Tokeniser("SELECT * FROM table")
// >                      .tokenise();
//
public class Tokeniser {

    // List of the keywords with their associated TokenTypes.
    private static final Map<String, TokenType> keywords;

    static {
        keywords = new HashMap<>();
        keywords.put("SELECT", SELECT);
        keywords.put("USE", USE);
        keywords.put("CREATE", CREATE);
        keywords.put("DROP", DROP);
        keywords.put("ADD", ADD);
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
        keywords.put("LIKE", LIKE);

    }


    private final String source;
    private final List<Token> tokens;
    // Indices in the source string
    private int start;
    private int current;


    // The constructor accepts the string to be tokenised.
    public Tokeniser(String source) {
        this.source = source;
        this.tokens = new ArrayList<>();
        this.start = 0;
        this.current = 0;
    }

    // Tokenises the string given in the constructor.
    // An exception is thrown if a character does not from a valid token.
    // Method keeps going until the string is completely consumed.
    public List<Token> tokenise() throws UnexpectedCharacterException {
        while (!isAtEnd()) {
            start = current;
            scanToken();
        }
        return tokens;
    }

    // Advances the input and pushes the encountered token onto `tokens`.
    private void scanToken() throws UnexpectedCharacterException {
        char ch = nextChar();

        // The type of most tokens can be defined by the first character.
        switch (ch) {
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
                if (matches('=')) {
                    addToken(DOUBLE_EQUALS);
                } else {
                    addToken(EQUALS);
                }
                break;

            case '<':
                if (matches('=')) {
                    addToken(LESS_THAN_EQ);
                } else {
                    addToken(LESS_THAN);
                }
                break;

            case '>':
                if (matches('=')) {
                    addToken(GREATER_THAN_EQ);
                } else {
                    addToken(GREATER_THAN);
                }
                break;

            case '!':
                if (matches('=')) {
                    addToken(NOT_EQUAL_TO);
                }
                break;

            // This case recognises the opening quote of a string literal and scans the rest of the string.
            case '\'':
                scanString();
                break;

            case '*':
                addToken(ASTERISK);
                break;

            case ';':
                addToken(SEMICOLON);
                break;


            // The following ignores whitespaces.
            case ' ':
            case '\r':
            case '\t':
            case '\n':
                break;


            default:
                if (isDigit(ch)) {
                    scanNumber();
                } else if (isLetter(ch)) {
                    scanWord();
                } else {
                    throw new UnexpectedCharacterException();
                }

        }
    }

    // Returns the current char and then advances the current position by one.
    private char nextChar() {
        current += 1;
        return source.charAt(current - 1);
    }

    // Adds a new `Token` composed of the supplied `TokenType` and the current substring to `tokens`.
    private void addToken(TokenType type) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text));
    }

    // Returns true if the source text has been completely consumed.
    private boolean isAtEnd() {
        return current >= source.length();
    }

    // This method advances the input if the next char matches `nextChar`.
    // Returns true if the chars matched.
    private boolean matches(char nextChar) {
        if (lookAhead() == nextChar) {
            nextChar();
            return true;
        }

        return false;
    }

    // Equivalent to `nextChar` but does NOT advance the input.
    private char lookAhead() {
        if (isAtEnd()) {
            return '\0';
        }
        return source.charAt(current);
    }

    // Consumes the input whilst there are ASCII digits with an optional decimal point.
    // If there is a decimal point, a `FLOAT` token is added, otherwise an `INT` token is added.
    private void scanNumber() {

        while (isDigit(lookAhead())) {
            nextChar();
        }
        if (matches('.')) {
            while (isDigit(lookAhead())) {
                nextChar();

            }
            addToken(FLOAT);
        } else {
            addToken(INT);
        }
    }

    // Consumes ASCII letters and digits followed by a single closing quote mark(').
    // Adds a `STRING` token.
    private void scanString() throws UnexpectedCharacterException {

        while (isLetterOrDigit(lookAhead())) {
            nextChar();
        }
        if (matches('\'')) {
            addToken(STRING);
        } else {
            throw new UnexpectedCharacterException();
        }
    }

    // Consumes letters; if the word that it consumes is a keyword, that keyword is added to `tokens`.
    // If the word is not a keyword, a variable token is added.
    private void scanWord() {

        while (isLetter(lookAhead())) {
            nextChar();
        }
        String text = source.substring(start, current);
        addToken(keywords.getOrDefault(text.toUpperCase(), VARIABLE));

    }

}


