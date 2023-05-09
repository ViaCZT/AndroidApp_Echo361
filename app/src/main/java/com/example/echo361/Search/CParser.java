package com.example.echo361.Search;

public class CParser {

    /**
     * Parser for <exp>.
     * <exp> has foyr production rules: <factor><exp> | <factor> <exp> | <factor> | <exp>
     *
     * @param tok NTokenizer
     * @return parsed expression for <exp>
     */
    public static CExp parseExp(CTokenizer tok) {
        CExp term = parseFactor(tok);

        if(tok.hasNext() && !isNumeric(tok.current().toString())) {
            CExp exp = parseExp(tok);
            tok.next();

            return new CAddExp(exp, term);
        }else if (tok.hasNext()){
            CExp exp = parseExp(tok);
            tok.next();
            return new CAddExp(term, exp);
        }else {
            return term;
        }
    }

    private static CExp parseFactor(CTokenizer tok) {
        CExp lit = new CLitExp((String) tok.current());
        tok.next();
        return lit;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
