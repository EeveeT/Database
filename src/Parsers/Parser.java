package Parsers;

import Common.Boolean;
import Common.Float;
import Common.Text;
import Query.*;
import Common.Value;

import java.util.ArrayList;
import java.util.Arrays;
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

    //private static final List<ParserInterface<Command>> commands;


    public Command parseCommand() throws UnexpectedTokenException {


        List<ParserInterface<Command>> commandParsers = Arrays.asList(
                this::parseUse,
                this::parseCreateDatabase,
                this::parseCreateTable,
                this::parseDrop,
                this::parseAlter,
                this::parseInsert,
                this::parseSelect,
                this::parseUpdate,
                this::parseDelete,
                this::parseJoin
        );
        for(ParserInterface<Command> commandParser: commandParsers) {

            try {
                Command command = commandParser.parse();
                expect(SEMICOLON);
                return command;
            }
            catch (UnexpectedTokenException e){
                reset();
            }

        }
        throw new UnexpectedTokenException(nextToken());

    }

    private CreateTable parseCreateTable() throws UnexpectedTokenException {

        expect(CREATE);
        expect(TABLE);
        String tableName = parseVariable();

        Optional <List<String>> attribList = Optional.empty();
        if(matches(BRACKET_LEFT)){

            attribList = Optional.of(parseListOf(this::parseVariable));
            expect(BRACKET_RIGHT);

        }

        return new CreateTable(tableName, attribList);

    }

    private CreateDatabase parseCreateDatabase() throws UnexpectedTokenException {

        expect(CREATE);
        expect(DATABASE);
        String databaseName = parseVariable();

        return new CreateDatabase(databaseName);
    }

    private Use parseUse() throws UnexpectedTokenException {

        expect(USE);
        String databaseName = parseVariable();

        return new Use(databaseName);

    }

    private Update.NameValue parseNameValue() throws UnexpectedTokenException {

        String attribName = parseVariable();
        expect(EQUALS);
        Value value = parseValue();

        return new Update.NameValue(attribName, value);


    }

    private Update parseUpdate() throws UnexpectedTokenException {

        expect(UPDATE);
        String tableName = parseVariable();
        expect(SET);

        List<Update.NameValue> nameValue = parseListOf(this::parseNameValue);

        expect(WHERE);

        Condition condition = parseCondition();

        return new Update(tableName, nameValue, condition);
    }

    private Drop parseDrop() throws UnexpectedTokenException {

        expect(DROP);

        Drop.Structure structure;
        if(matches(DATABASE)){
            structure = Drop.Structure.DATABASE;
        }
        else{
            expect(TABLE);
            structure = Drop.Structure.TABLE;
        }

        String structName = parseVariable();

        return new Drop(structure, structName);
    }

    private Alter parseAlter() throws UnexpectedTokenException {

        expect(ALTER);
        expect(TABLE);
        String tableName = parseVariable();

        Alter.Action altType;
        if(matches(ADD)){
            altType = Alter.Action.ADD;
        }
        else{
            expect(DROP);
            altType = Alter.Action.DROP;
        }

        String attribName = parseVariable();

        return new Alter(tableName, altType, attribName);
    }

    private Join parseJoin() throws UnexpectedTokenException {

        expect(JOIN);
        String tableNameA = parseVariable();
        expect(AND);
        String tableNameB = parseVariable();
        expect(ON);
        String attribNameA = parseVariable();
        expect(AND);
        String attribNameB = parseVariable();

        return new Join(tableNameA, tableNameB, attribNameA, attribNameB);
    }


    private Delete parseDelete() throws UnexpectedTokenException {

        expect(DELETE);
        expect(FROM);
        String tableName = parseVariable();
        expect(WHERE);
        Condition condition = parseCondition();

        return new Delete(tableName, condition);
    }

    private Insert parseInsert() throws UnexpectedTokenException {

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
            System.out.println("Asterisk found!");
            attribList = Optional.empty();
        }
        else{
            System.out.println("Looking for column name list..");
            attribList = Optional.of(parseListOf(this::parseVariable));
            System.out.println("Found them!");
        }

        expect(FROM);
        String tableName = parseVariable();
        Optional<Condition> condition = Optional.empty();
        if(matches(WHERE)){
            System.out.println("Ooh! A where clause!");
            condition = Optional.of(parseCondition());
            System.out.println("And that's done too!");
        }

        return new Select(attribList, tableName, condition );


    }

    private Condition.Operator parseOperator() throws UnexpectedTokenException {
        Token token = nextToken();

        switch(token.type){
            case DOUBLE_EQUALS:
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
            else{
                expect(OR);
                junction = Condition.Junction.OR;
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
                String contents = token.string.substring(1, token.string.length()-1);
                return new Text(contents);

            case TRUE:
                return new Boolean(true);

            case FALSE:
                return new Boolean(false);

            case INT:
                return new Common.Integer(parseInt(token.string));

            case FLOAT:
                return new Float(parseFloat(token.string));

            default:
                throw new UnexpectedTokenException(token);
        }
    }
    //todo: comment
    private <T> List<T> parseListOf(ParserInterface<T> parseItem) throws UnexpectedTokenException {

        List<T> list = new ArrayList<>();

        list.add(parseItem.parse());

        while(matches(COMMA)){
            list.add(parseItem.parse());
        }

        return list;
    }


    private Token nextToken() throws UnexpectedTokenException {

        Token token = lookAhead();

        if(token == null){
            throw new UnexpectedTokenException(null);
        }

        current += 1;
        return token;
    }

    private void expect(TokenType type) throws UnexpectedTokenException {

        if(!matches(type)) {

            throw new UnexpectedTokenException(nextToken());
        }
    }

    private boolean matches(TokenType nextType) throws UnexpectedTokenException {

        Token lookAhead = lookAhead();

        if(lookAhead != null && lookAhead.type == nextType ){
            nextToken();
            return true;
        }
        return false;
    }

    private Token lookAhead() {
        if(isAtEnd()){
            return null;
        }
        return tokens.get(current);

    }

    private boolean isAtEnd() {
        return current >= tokens.size();
    }

    private void reset() {

        current = 0;
    }

}



