package Parsers;

import Query.Condition;
import Query.Insert;
import Query.Select;
import Query.Value;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static Parsers.TokenType.*;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class Parser
{
    //Function that takes in input and maybe returns a value and rest of output

    private final List<Token> tokens;
    private int current;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.current = 0;

    }

    private Insert parseInsert() throws UnexpectedTokenException{

        expect(INSERT);
        expect(INTO);
        String tableName = parseVariable();
        expect(VALUES);
        expect(BRACKET_LEFT);
        List<Value> valueList = parseListOf(this::parseValue);
        expect(BRACKET_RIGHT);

        return new Insert(tableName, valueList);
    }


    private Select parseSelect() throws UnexpectedTokenException {

        expect(SELECT);
        Optional<List<String>> attribList;
        if(matches(ASTERISK)){
            attribList = Optional.empty();
        }
        else{
            attribList = Optional.of(parseListOf(this::parseVariable));

        }
        expect(FROM);
        String tableName = parseVariable();
        Optional<Condition> condition = Optional.empty();
        if(matches(WHERE)){
            condition = Optional.of(parseCondition());
        }

        return new Select(attribList, tableName, condition );


    }

    private Condition.Operator parseOperator() throws UnexpectedTokenException {
        Token token = nextToken();

        switch(token.type){
            case EQUALS:
                return Condition.Operator.Equals;

            case GREATER_THAN:
                return Condition.Operator.GT;

            case LESS_THAN:
                return Condition.Operator.LT;

            case GREATER_THAN_EQ:
                return Condition.Operator.GTE;

            case LESS_THAN_EQ:
                return Condition.Operator.LTE;

            case NOT_EQUAL_TO:
                return Condition.Operator.NEquals;

            case LIKE:
                return Condition.Operator.Like;

            default:
                throw new UnexpectedTokenException(token);

        }

    }

    private Condition parseCondition() throws UnexpectedTokenException {

        if(matches(TokenType.BRACKET_LEFT)){
            Condition leftCondition = parseCondition();

            expect(TokenType.BRACKET_RIGHT);

            Condition.Junction junction;

            if(matches(AND)){
                junction = Condition.Junction.AND;
            }
            else if(matches(OR)){
                junction = Condition.Junction.OR;
            }
            else{
                throw new UnexpectedTokenException(nextToken());
            }

            expect(TokenType.BRACKET_LEFT);
            Condition rightCondition = parseCondition();
            expect(TokenType.BRACKET_RIGHT);

            return new Condition.Composite(leftCondition, junction, rightCondition);

        }

        else{

            String variable = parseVariable();
            Condition.Operator operator = parseOperator();
            Value value = parseValue();
            return new Condition.Single(variable, operator, value);
        }

    }

    private String parseVariable() throws UnexpectedTokenException {

        Token token = nextToken();

        if(token.type == TokenType.VARIABLE){
            return token.string;
        }
        else{
            throw new UnexpectedTokenException(token);
        }
    }

    private Value parseValue() throws UnexpectedTokenException {
        Token token = nextToken();

        switch(token.type){
            case STRING:
                return new Value.Text(token.string);

            case TRUE:
                return new Value.Boolean(true);

            case FALSE:
                return new Value.Boolean(false);

            case INT:
                return new Value.Integer(parseInt(token.string));

            case FLOAT:
                return new Value.Float(parseFloat(token.string));

            default:
                throw new UnexpectedTokenException(token);
        }
    }
    // This method
    private <T> List<T> parseListOf(ParserInterface<T> parseItem) throws UnexpectedTokenException {

        List<T> list = new ArrayList<>();

        list.add(parseItem.parse());

        while(matches(COMMA)){
            list.add(parseItem.parse());
        }

        return list;
    }

    private Token nextToken(){
        Token token = tokens.get(current);
        current += 1;
        return token;
    }

    private void expect(TokenType type) throws UnexpectedTokenException {

        if(!matches(type)) {

            throw new UnexpectedTokenException(nextToken());
        }
    }

    private boolean matches(TokenType nextType){

        if(lookAhead().type == nextType ){
            nextToken();
            return true;
        }
        return false;
    }

    private Token lookAhead(){
        if(isAtEnd()){
            return null;
        }
        return tokens.get(current);

    }

    private boolean isAtEnd()
    {
        return current >= tokens.size();
    }


}
