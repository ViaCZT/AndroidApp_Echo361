package com.example.echo361.Search;

/**
 * @Author Yitao Zhang, u7504766
 * Expression for tokens(letter/letters from tokenizer)
 */
public class NLitExp extends NExp {
    private String word;

    /**
     * @Author Yitao Zhang, u7504766
     * Constructor for NLit
     * @param word String value of literal expression
     */
    public NLitExp(String word) {
        this.word = word;
    }


    /**
     * @Author Yitao Zhang, u7504766
     * returns the string value of the NLitExp
     * @return
     */
    @Override
    public String show() {
        return word;
    }
}
