package com.example.echo361.Search;

/**
 * @Author Yitao Zhang, u7504766
 * The NTokenizer class is an abstract class for marking a given input.
 * It provides a set of abstract methods that any concrete tokeniser needs to implement.
 * These methods provide the ability to check if the next token exists, retrieve the current
 * token and go to the next token.
 */
public abstract class NTokenizer {

    /**
     * @Author Yitao Zhang, u7504766
     * check whether there is a next token in the remaining text
     */
    public abstract boolean hasNext();

    /**
     * @Author Yitao Zhang, u7504766
     * return the current token
     * @return the current token
     */
    public abstract Object current();

    /**
     * @Author Yitao Zhang, u7504766
     *  extract next token from the current text and save it
     */
    public abstract void next();
}
