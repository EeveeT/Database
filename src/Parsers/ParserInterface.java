package Parsers;
/*
    Any function that returns a <T> and throws an Exception implements
    this method.
    This interface is necessary because parseListOf must be able to throw an Exception
    and Supplies is unable to do that. Therefore, this interface was implemented.
 */

@FunctionalInterface
public interface ParserInterface<T>{
    T parse() throws UnexpectedTokenException;
}

