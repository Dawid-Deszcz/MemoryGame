package com.company;

import java.util.List;
import java.util.Scanner;

public class GameMechanics {

    private static String level = "";
    private static final Scanner scanner = new Scanner(System.in);
    private static int chances;
    private static int numberOfWords;

    public static void PlayMemoryGame () {

        //1. Transfer elements/words from file to List<String>
        List<String> words = FilesManagement.readWordsFromFile("Words.txt");

        //2. Create the game board. Board size depends on difficulty level
        GameMechanics.selectDifficultyLevel();
        Board board = new Board(numberOfWords, words);

        /*
        3. SelectionProcess method performs all tasks connected with selection elements/words by the user.
        Method ends when either all elements are uncovered or all lives are lost.
        Method also measures how long it takes a user to complete this task.
         */
        int timeElapsed = selectionProcess(board);

        //4. After game is completed game board with correctly guessed words is printed for the last time
        printGameBoard(board);


        /*
        5. The method check the results and if player won or lost and prints appropriate ASCII art
        to make program more appealing to the player
         */
        System.out.println();
        areYouWinnerOrLoser(board, timeElapsed);

        //6. List of 10 best winners is printed out.
        if (level.equals("easy"))
            Board.printHighScores("ScoresEasy.txt", level);
        else if (level.equals("hard"))
            Board.printHighScores("ScoresHard.txt", level);

        //7. Player is asked whether he/she wants to repeat the game
        playAgain ();

    }

    public static void areYouWinnerOrLoser(Board board, int timeElapsed) {
        int guessingTries;

        //display information about player victory
        if (chances >=0 && board.allWordsUncovered()) {
            if (level.equals("easy"))
                guessingTries = 10 - chances;
            else
                guessingTries = 15 - chances;
            FilesManagement.printASCIIartFromFile("Winner.txt");
            System.out.println();
            System.out.println("You solved the memory game after " + guessingTries + " tries. It took you " + timeElapsed + " seconds." );
            System.out.println("Enter your name:");
            String username = scanner.nextLine();

            FilesManagement.saveResults(username, timeElapsed, guessingTries, level);
        }
        //displays information about player defeat
        else {
            FilesManagement.printASCIIartFromFile("Loser.txt");
            System.out.println();
            System.out.println("You are out of chances. Good luck next time!");

        }

    }

    public static int selectionProcess (Board board) {
        long start = System.currentTimeMillis();
        //game lasts until all lives are lost or all elements/words are uncovered
        String coordinates;
        int selectedWords = 0;
        while (chances > 0 && !board.allWordsUncovered()) {
            //print game board on the screen
            printGameBoard(board);

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
        return (int) ((finish - start)/1000); // returns time elapsed in seconds
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

        //depending on the difficulty level chosen different values are assigned to numberOfWords and chances
        if (level.equals("1")) {
            numberOfWords = 4;
            chances = 10;
            level = "easy";
        }else  {
            numberOfWords = 8;
            chances = 15;
            level = "hard";
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

    public static void playAgain () {
        String playAgain;
        while (true) {
            System.out.println("Would you like to play again? Please type \"1\" or \"2\" to select Yes or No");
            System.out.println("\"1\" indicates that you selected Yes");
            System.out.println("\"2\" indicates that you selected No");

            playAgain = scanner.nextLine();

            if (playAgain.equals("1")) {
                PlayMemoryGame();
            } else if (playAgain.equals("2"))
                break;
        }
    }

    private static void printGameBoard (Board board) {

        System.out.println("----------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------");
        printHeader(level, chances);
        board.printMatrix();
        System.out.println("----------------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------------");
    }

}
