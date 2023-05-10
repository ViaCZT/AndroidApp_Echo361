package com.example.echo361.Search;

public class CAddExp extends CExp{

    private CExp factor1;
    private CExp factor2;


    public CAddExp(CExp factor1, CExp factor2) {
        this.factor1 = factor1;
        this.factor2 = factor2;
    }


    @Override
    public String show() {
        return "" + factor1.show() + factor2.show();
    }
}
