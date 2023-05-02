package com.example.echo361;

public class CLitExp extends CExp {
    private String character;

    public CLitExp(String character) {
        this.character = character;
    }


    @Override
    public String show() {
        return character;
    }
}
