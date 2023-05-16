package com.example.echo361.Search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NtokenizerTest {

    private static NTokenizer tokenizer;
    private static final String test1 = "Yitao 123";
    private static final String test2 = "123 Yi 123 tao";

    private static final String test3 = "  123yi tao123   Zhang  ";


    // combine with letters-space-numbers "Yitao 123"
    @Test(timeout=1000)
    public void testTest1Tokenizer() {
        tokenizer = new NameTokenizer(test1);

        //test hasNext
        assertTrue(tokenizer.hasNext());

        // test first token
        assertEquals("Yitao", tokenizer.current());

        //test hasNext
        tokenizer.next();
        assertFalse(tokenizer.hasNext());

    }

    // combine with numbers-letters-numbers-letters "123 Yi 123 tao"
    @Test(timeout=1000)
    public void testTest2Tokenizer(){
        tokenizer = new NameTokenizer(test2);

        //test hasNext
        assertTrue(tokenizer.hasNext());

        // test first token
        assertEquals("Yi", tokenizer.current());

        //test hasNext
        assertTrue(tokenizer.hasNext());

        // test second token
        tokenizer.next();
        assertEquals("tao", tokenizer.current());

        //test hasNext
        tokenizer.next();
        assertFalse(tokenizer.hasNext());

    }

    // combine with spaces-numbersLetters-lettersNumbers-spaces-letters-spaces "  123yi tao123   Zhang  "
    @Test(timeout=1000)
    public void testTest3Tokenizer(){
        tokenizer = new NameTokenizer(test3);

        //test hasNext
        assertTrue(tokenizer.hasNext());

        // test first token
        assertEquals("yi", tokenizer.current());

        //test hasNext
        assertTrue(tokenizer.hasNext());

        // test second token
        tokenizer.next();
        assertEquals("tao", tokenizer.current());

        //test hasNext
        assertTrue(tokenizer.hasNext());

        // test second token
        tokenizer.next();
        assertEquals("Zhang", tokenizer.current());

        //test hasNext
        tokenizer.next();
        assertFalse(tokenizer.hasNext());

    }
}
