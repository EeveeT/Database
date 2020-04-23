package Parsers;

import Query.Condition;
import Query.Value;

import javax.management.ValueExp;
import java.util.List;
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
        String variable = parseVariable();
        Condition.Operator operator = parseOperator();
        Value value = parseValue();

        return new Condition(variable, operator, value);
        //todo: AND & OR conditions

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




    private Token nextToken(){
        Token token = tokens.get(current);
        current += 1;
        return token;
    }

//todo: <Condition>      ::=  ( <Condition> ) AND ( <Condition> )  |
//                      ( <Condition> ) OR ( <Condition> )   |
//                      <AttributeName> <Operator> <Value>
}
