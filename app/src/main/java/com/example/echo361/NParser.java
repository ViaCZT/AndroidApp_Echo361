package com.example.echo361;

/**
 * Parser for grammar:
 *
 * <exp> ::= <factor> | <factor> + <exp>
 * <factor> ::= <String>
 *
 *
 *
 */
public class NParser {

    /**
     * Parser for <exp>.
     * <exp> has two production rules: <factor> | <factor> <exp>
     * If there's one token after parsing factor, the parser return an extend expression
     * otherwise it returns parsed factor.
     *
     * @param tok NTokenizer
     * @return parsed expression for <exp>
     */
    public static NExp parseExp(NTokenizer tok) {
        NExp term = parseFactor(tok);


        if(tok.hasNext()) {
            NExp exp = parseExp(tok);
            tok.next();

            return new NAddExp(term, exp);
        }else {
            return term;
        }
    }

    /**
     * Parser for <factor>
     * Note that <factor> has a single production rule.
     * It always return integer literal as a parsed result.
     *
     * @param tok Tokenizer
     * @return parsed expression for <factor>
     */
    private static NExp parseFactor(NTokenizer tok) {
        NExp lit = new NLitExp((String) tok.current());
        tok.next();
        return lit;
    }
}
