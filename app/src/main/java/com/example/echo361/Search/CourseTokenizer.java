package com.example.echo361.Search;

public class CourseTokenizer extends CTokenizer{
    private String text;
    private int pos;
    private Object current;

    public CourseTokenizer(String text) {
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

        } else if (isWord(text.charAt(pos))){
            int start = pos;
            while (pos < text.length() && isWord(text.charAt(pos)))
                pos++;
            if (pos-start <=4){
                current = text.substring(start, pos);
            }else {
                current = text.substring(start, start+4);
            }
        }else if (Character.isDigit(text.charAt(pos))){
            int start = pos;
            while (pos < text.length() && Character.isDigit(text.charAt(pos)) )
                pos++;
            if (pos-start <=4){
                current = text.substring(start, pos);
            }else {
                current = text.substring(start, start+4);
            }
        }
    }


    public boolean isWord(char c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private void consumeInvalid() {
        while (pos < text.length() && (!(isWord(text.charAt(pos)) || Character.isDigit(text.charAt(pos)))))
            pos++;
    }
}
