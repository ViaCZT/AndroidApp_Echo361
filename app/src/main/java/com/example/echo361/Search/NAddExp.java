package com.example.echo361.Search;

/**
 * @Author Yitao Zhang, u7504766
 * Expression for connecting/addition factor and exp
 * Note that the first argument of this expression is <factor>
 * and the section expression in connection is <exp>
 */
public class NAddExp extends NExp{

    private NExp factor;
    private NExp exp;


    /**
     * @Author Yitao Zhang, u7504766
     * Constructor for an connection/addition expression
     * @param factor the first term in connection
     * @param exp the second term in connection
     */
    public NAddExp(NExp factor, NExp exp) {
        this.factor = factor;
        this.exp = exp;
    }

    /**
     * @Author Yitao Zhang, u7504766
     * returns connected expression of factor and exp
     * @return
     */
    @Override
    public String show() {
        return "" + factor.show() +" "+ exp.show();
    }
}
