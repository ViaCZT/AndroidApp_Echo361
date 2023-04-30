package com.example.echo361;

public class NLitExp extends NExp {
    private String word;

    /**
     * Constructor for NLit
     * @param word String value of literal expression
     */
    public NLitExp(String word) {
        this.word = word;
    }


    @Override
    public String show() {
        return word;
    }
}
