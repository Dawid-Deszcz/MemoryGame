package com.company;

import java.util.List;

public class Main {

    private static int numberOfWords;

    public static void main(String[] args) {

        String coordinates;

        List<String> words = FilesManagment.readWordsFromFile();

        GameMechanics.selectDifficultyLevel();

        //preparation of the game board
        Board board = new Board(numberOfWords, words);




    }
}
