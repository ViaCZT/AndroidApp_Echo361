package com.example.echo361.Search;

/**
 * @Author Yitao Zhang, u7504766
 * The NameTokenizer class is an implementation of the NTokenizer abstract class.
 * It tokens a given text as a single word, where the word is defined as a sequence of
 * consecutive letters of the alphabet.
 */
public class NameTokenizer extends NTokenizer{

    // The text to be tokenized
    private String text;
    // The current position in the text
    private int pos;
    // The current token extracted from the text
    private Object current;

    /**
     * @Author Yitao Zhang, u7504766
     * Constructor of NameTokenizer
     * @param text
     */
    public NameTokenizer(String text) {
        this.text = text;
        this.pos = 0;
        next();
    }

    /**
     * @Author Yitao Zhang, u7504766
     * Method to check if there's any token in the rest
     * @return return whether there's a current token
     */
    @Override
    public boolean hasNext() {
        return current != null;
    }

    /**
     * @Author Yitao Zhang, u7504766
     * Method to get the current token
     * @return return the current token
     */
    @Override
    public Object current() {
        return current;
    }


    /**
     * @Author Yitao Zhang, u7504766
     * Method to extract the next token from the text
     */
    @Override
    public void next() {
        // Skip any invalid characters in the text
        consumeInvalid();
        // Check if the end of the text is reached
        if (pos == text.length()) {
            // If so, there's no current token
            current = null;

        } else {
            // If not, start extracting the next token
            int start = pos;
            // Continue moving in the text while it's a valid word character
            while (pos < text.length() && isWord(text.charAt(pos)))
                pos++;
            // Extract the token from the text
            current = text.substring(start, pos);
        }
    }

    /**
     * @Author Yitao Zhang, u7504766
     * Helper method to check if a character is a valid word character
     * @param c
     * @return
     */
    private boolean isWord(char c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    /**
     * @Author Yitao Zhang, u7504766
     * Helper method to skip any invalid characters in the text
     */
    private void consumeInvalid() {
        while (pos < text.length() && !isWord(text.charAt(pos)))
            pos++;
    }
}
