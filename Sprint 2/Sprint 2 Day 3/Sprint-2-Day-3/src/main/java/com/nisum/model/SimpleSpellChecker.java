package com.nisum.model;

public class SimpleSpellChecker implements SpellChecker {

    @Override
    public void checkSpelling(String text) {
        System.out.println("Checking spelling for: " + text);
        System.out.println("Spelling check completed!");
    }
}
