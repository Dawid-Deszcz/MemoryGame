package com.company;

import java.util.List;
import java.util.Scanner;

public class GameMechanics {

    private static String level = "";
    private static final Scanner scanner = new Scanner(System.in);
    private static int chances = 3;  // I need to amend it when additional parts of code are added
    private static int numberOfWords = 4; // I need to amend it when additional parts of code are added

    public static void PlayMemoryGame () {


        String coordinates;

        List<String> words = FilesManagment.readWordsFromFile();

        GameMechanics.selectDifficultyLevel();

        //preparation of the game board
        Board board = new Board(numberOfWords, words);

        //number of selected words
        int selectedWords = 0;

        //game lasts until all lives are lost or all elements/words are uncovered
        long start = System.currentTimeMillis();

        while (chances > 0 && !board.allWordsUncovered()) {
            //display
            System.out.println("—-----------------------------------");
            printHeader(level, chances);
            board.printMatrix();
            System.out.println("—-----------------------------------");


        }








    }



















    protected static void selectDifficultyLevel () {
        /*
        The method gives the user the ability to select a difficulty level.
        Currently, we have two levels of difficulties, but it can be easily expanded to include more levels.
         */
        while (!level.equals("1") && !level.equals("2")) {
            System.out.println("Please type \"1\" or \"2\" to indicate difficulty level:");
            System.out.println("\"1\" indicates that you selected level Easy");
            System.out.println("\"2\" indicates that you selected level Hard");

            level = scanner.nextLine();

            if (level.equals("1") || level.equals("2"))
                break;
            else
                System.out.println("Wrong input!");
        }





    }

    private static void printHeader (String level, int chances) {
        // displays the header part of the game board (level, number of lives/chances left and columns numbering)
        System.out.println("Level: " + level);
        System.out.println("Remaining lives: " + chances);
        System.out.println();
        if (level.equals("easy"))
            System.out.println("  1 2 3 4");
        else
            System.out.println("  1 2 3 4 5 6 7 8");
    }


}
