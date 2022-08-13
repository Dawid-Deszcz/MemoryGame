package com.company;

import java.util.List;
import java.util.Scanner;

public class GameMechanics {

    private static String level = "";
    private static final Scanner scanner = new Scanner(System.in);
    private static int chances = 3;  // I need to amend it when additional parts of code are added
    private static int numberOfWords = 4; // I need to amend it when additional parts of code are added

    public static void PlayMemoryGame () {



        List<String> words = FilesManagment.readWordsFromFile();

        GameMechanics.selectDifficultyLevel();

        //preparation of the game board
        Board board = new Board(numberOfWords, words);

        //number of selected words
        int selectedWords = 0;

        /*
        selectionProcess method performs all tasks connected with selection elements/words by the user.
        Method ends when either all elements are uncovered or all lives are lost.
        Method also measures how long it takes a user to complete this task.
         */
        long timeElapsed = selectionProcess(board);











    }















    public static long selectionProcess (Board board) {
        long start = System.currentTimeMillis();
        //game lasts until all lives are lost or all elements/words are uncovered
        String coordinates;
        int selectedWords = 0;
        while (chances > 0 && !board.allWordsUncovered()) {
            //print game board on the screen
            System.out.println("—-----------------------------------");
            printHeader(level, chances);
            board.printMatrix();
            System.out.println("—-----------------------------------");

            //coordinates indicating an element/word selected by user
            System.out.println("Enter your selection. Please use capital letters e.g. A1, B4, A2 etc.");
            coordinates = scanner.nextLine();

            //if no elements/words are correctly selected then selected items array is restarted
            // I need to rethink this part
            if (selectedWords == 0)
                board.selected = new boolean[2][numberOfWords];

            // checking if user selection was correct
            if (coordinates.length() == 2) {
                //checking if first part of the selection sequence (column)  is correct
                if (coordinates.charAt(0) == 'A' || coordinates.charAt(0) == 'B') {
                    // translate coordinates entered into array coordinates
                    int row = coordinates.charAt(0) - 'A';
                    int col = coordinates.charAt(1) - '1';

                    //checking if second part of the selection sequence (row) is correct
                    if (col < numberOfWords && col >= 0 ) {
                        // flagging the selected word
                        board.selected[row][col] = true;
                        selectedWords++;


                        //if second element/word is flagged, compare if both elements are identical
                        if(selectedWords == 2){

                            //if both elements are identical, they are flagged as permanently uncovered/guessed
                            if (board.checkSelectedWords())
                                board.markGuessedWords();

                            //resetting selectedWords to "0", and hiding elements/words which were not correctly selected
                            selectedWords = 0;
                            chances--;
                        }
                    }else{
                        System.out.println("Wrong input!");
                    }
                }else{
                    System.out.println("Wrong input!");
                }
            }else{
                System.out.println("Wrong input!");
            }
        }
        long finish = System.currentTimeMillis();
        return finish - start;
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
