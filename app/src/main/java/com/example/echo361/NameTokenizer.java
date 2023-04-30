package com.example.echo361;

public class NameTokenizer extends NTokenizer{

    private String text;
    private int pos;
    private Object current;

    public NameTokenizer(String text) {
        this.text = text;
        this.pos = 0;
        next();
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public Object current() {
        return current;
    }

    @Override
    public void next() {
        consumeInvalid();
        if (pos == text.length()) {
            current = null;

        } else {
            int start = pos;
            while (pos < text.length() && isWord(text.charAt(pos)))
                pos++;
            current = text.substring(start, pos);
        }
    }


    private boolean isWord(char c){
        if ((c >=  'a' && c <= 'z') || (c >=  'A' && c <= 'Z')){
            return true;
        }else{
            return false;
        }
    }

    private void consumeInvalid() {
        while (pos < text.length() && !isWord(text.charAt(pos)))
            pos++;
    }
}
