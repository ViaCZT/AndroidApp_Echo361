package com.example.echo361;

public class NAddExp extends NExp{

    private NExp factor;
    private NExp exp;

    /**
     * Constructor for an addition expression.
     *
     * @param factor term first term in addition expression
     * @param exp exp second term in addition expression
     */
    public NAddExp(NExp factor, NExp exp) {
        this.factor = factor;
        this.exp = exp;
    }

    @Override
    public String show() {
        return "" + factor.show() +" "+ exp.show();
    }
}
