package com.example.echo361.Search;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParserTest {

    private static final String test1 = "Yitao 123";
    private static final String test2 = "Yitao 123Zhang";
    private static final String test3 = "yitao";
    private static final String test4 = "Yi tao";
    private static final String test5 = "Yi123tao";
    private static final String test6 = "Yi123ta*&o";
    private static final String test7 = "123Yi123tao";
    private static final String test8 = "  123Yi tao123 123 Zhang  ";


    // test an example mixed with letters-space-numbers "Yitao 123"
    @Test(timeout=1000)
    public void testTest1Perser() {
        NTokenizer tok = new NameTokenizer(test1);
        NExp parsedExp = NParser.parseExp(tok);
        String inputPersed = parsedExp.show();
        assertEquals("Yitao", inputPersed);
    }

    // test an example mixed with letters-space-numbersLetters "Yitao 123Zhang"
    @Test(timeout=1000)
    public void testTest2Perser() {
        NTokenizer tok = new NameTokenizer(test2);
        NExp parsedExp = NParser.parseExp(tok);
        String inputPersed = parsedExp.show();
        assertEquals("Yitao Zhang", inputPersed);
    }

    // test the lowercase "yitao"
    @Test(timeout=1000)
    public void testTest3Perser() {
        NTokenizer tok = new NameTokenizer(test3);
        NExp parsedExp = NParser.parseExp(tok);
        String inputPersed = parsedExp.show();
        assertEquals("yitao", inputPersed);
    }

    // test an example mixed with letters-letters "Yi tao"
    @Test(timeout=1000)
    public void testTest4Perser() {
        NTokenizer tok = new NameTokenizer(test4);
        NExp parsedExp = NParser.parseExp(tok);
        String inputPersed = parsedExp.show();
        assertEquals("Yi tao", inputPersed);
    }

    // test an example mixed with lettersNumbersLetters "Yi123tao"
    @Test(timeout=1000)
    public void testTest5Perser() {
        NTokenizer tok = new NameTokenizer(test5);
        NExp parsedExp = NParser.parseExp(tok);
        String inputPersed = parsedExp.show();
        assertEquals("Yi tao", inputPersed);
    }

    // test an example mixed with lettersNumbersLetters-symbols-letter "Yi123ta*&o"
    @Test(timeout=1000)
    public void testTest6Perser() {
        NTokenizer tok = new NameTokenizer(test6);
        NExp parsedExp = NParser.parseExp(tok);
        String inputPersed = parsedExp.show();
        assertEquals("Yi ta o", inputPersed);
    }

    // test an example mixed with numbersLettersNumbersLetters "123Yi123tao"
    @Test(timeout=1000)
    public void testTest7Perser() {
        NTokenizer tok = new NameTokenizer(test7);
        NExp parsedExp = NParser.parseExp(tok);
        String inputPersed = parsedExp.show();
        assertEquals("Yi tao", inputPersed);
    }

    // test an example mixed with spaces-numbersLetters-space-lettersNumbers-numbers-letters-spaces "  123Yi tao123 123 Zhang  "
    @Test(timeout=1000)
    public void testTest8Perser() {
        NTokenizer tok = new NameTokenizer(test8);
        NExp parsedExp = NParser.parseExp(tok);
        String inputPersed = parsedExp.show();
        assertEquals("Yi tao Zhang", inputPersed);
    }







}
