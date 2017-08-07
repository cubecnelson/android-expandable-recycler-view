package com.example.expandablerecyclerviewexample.items;

/**
 * Created by Nelson.Cheung on 8/7/2017.
 */

public class Word {
    String word;

    public Word(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                '}';
    }
}
