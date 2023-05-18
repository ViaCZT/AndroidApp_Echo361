package com.example.echo361.Search;

/**
 * @Author Yitao Zhang, u7504766
 * Parser for grammar:
 * <exp> ::= <factor> | <factor> <exp>
 * <factor> ::= <token>
 *
 *
 */
public class NParser {

    /**
     * @Author Yitao Zhang, u7504766
     * Parser for <exp>.
     * <exp> has two production rules: <factor> | <factor> <exp>
     *
     * parseExp is responsible for parsing an expression, <exp>. It first parses a factor, <factor>,
     * from the expression. If there are any tokens left in the input, it recursively parses the
     * remainder of the expression and creates a new NAddExp (an connection expression) with the
     * parsed factor and the remainder of the expression. If there are no remaining tokens,
     * it simply returns the parsed factor.
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
     * @Author Yitao Zhang, u7504766
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
