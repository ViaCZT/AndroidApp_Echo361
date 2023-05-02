package com.example.echo361;

public abstract class CTokenizer {
    /**
     * check whether there is a next token in the remaining text
     * @return
     */
    public abstract boolean hasNext();

    /**
     * return the current token
     * @return the current token
     */
    public abstract Object current();

    /**
     *  extract next token from the current text and save it
     */
    public abstract void next();
}
