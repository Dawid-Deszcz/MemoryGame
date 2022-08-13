package com.company;

import java.util.*;

public class Board {
    public int numberOfWords;
    public String[][] matrix;
    public boolean[][] selected;
    public boolean[][] guessed;

    public Board(int numberOfWords, List<String> words) {
        this.numberOfWords = numberOfWords;

        Random random = new Random();
        matrix = new String[2][numberOfWords];
        selected = new boolean[2][numberOfWords];
        guessed = new boolean[2][numberOfWords];

        // 1 - upper part/line of the board
        // drawing elements from the file to the upper level/line of the board
        for (int i = 0; i < numberOfWords; i++) {
            matrix[0][i] = words.get(random.nextInt(0, words.size()));
        }
        // 2a - lower part/line of the board
        // array preparation for non-repetitive drawing
        boolean[] used = new boolean[numberOfWords];
        for (int i = 0; i < numberOfWords; i++) {
            used[i] = false;
        }

        // 2b - upper part/line of the board
        // drawing elements to the lower level/line of the game
        int countWhile = 0;
        int countFor= 0;
        int index;
        for (int i = 0; i < numberOfWords; i++) {
            do {
                index = random.nextInt(0, numberOfWords);
                System.out.println(countWhile++);
            }while (used [index]);
            System.out.println(countFor++ + "Count For");

            matrix [1][i] = matrix[0][index];
            used[index]=true;

        }

        System.out.println(Arrays.deepToString(matrix));

    }

    public boolean allWordsUncovered() {
        /*
        Check if all elements/words on the board has been uncovered. If all elements have been uncovered true is returned.
        If a single element is still covered false is returned.
         */
        for (int i = 0; i < numberOfWords; i++) {
            if (!guessed[0][i] && !selected[0][i])
                return false;
            if (!guessed[1][i] && !selected[1][i])
                return false;
        }
        return true;
    }

    public void printMatrix() {
        // I could potentially merged it into larger for loop where number of lines will be repeated - that might be a better code


        // 1 - print upper part/line of the board
        System.out.print("A ");
        for (int i = 0; i < numberOfWords; i++) {
            // if the field is selected or permanently uncovered then the entire word/element is printed
            if (selected[0][i] || guessed[0][i])
                System.out.print(matrix[0][i] + " ");
            // otherwise, only "x" is displayed
            else
                System.out.print("X ");
        }

        // 2 - print lower part/line of the board
        System.out.print("\nB ");
        for (int i = 0; i < numberOfWords; i++) {
            // if the field is selected or permanently uncovered then the entire word/element is printed
            if (selected[1][i] || guessed[1][i])
                System.out.print(matrix[1][i] + " ");
                // otherwise, only "x" is displayed
            else
                System.out.print("X ");
        }

        System.out.println();

    }

    public boolean checkSelectedWords() {
        /*
        Checking if two words flagged by the player are identical. First word is searched in first line.
        Second word is searched in the second line.
         */

        String firstWord = "";
        String secondWord = "";

        for (int i = 0; i < numberOfWords; i++) {
            if (selected[0][i]) {
                firstWord = matrix[0][i];
            }
            if (selected[1][i]) {
                secondWord = matrix[1][i];
            }
        }
        return firstWord.equals(secondWord);
    }

    public void markGuessedWords() {
        //method flags uncovered/guessed elements which will be displayed on the board game
        //I could potentially collapse it into double for lopp ?????
        for (int i = 0; i < numberOfWords; i++) {
            if(selected[0][i])
                guessed[0][i] = true;
            if(selected[1][i])
                guessed[1][i] = true;
        }
    }

    public static void printHighScores () {
        List<Score> scores = new ArrayList<>();

        //reading the output from the file
        List<String> records = FilesManagment.readWordsFromFile("Scores.txt");


        //saving results in Score object
        for (String record : records) {
            String[] splitRecord = record.split(";");
            scores.add(new Score(splitRecord[0], splitRecord[1], Integer.parseInt(splitRecord[2]), Integer.parseInt(splitRecord[3])));

        }

        // Sorting results from the best to the worst (according to time and number of lives lost)
        Collections.sort(scores, new Comparator<Score>() {
            @Override
            public int compare(Score lhs, Score rhs) {
                if (lhs.time == rhs.time) {
                    if(lhs.tries < rhs.tries) {
                        return -1;
                    }else if (lhs.time == rhs.tries) {
                        return 0;
                    }
                    return 1;
                }
                if (lhs.time<rhs.time) {
                    return -1;
                }
                return 1;
            }
        });

        //printing out Scores Table
        System.out.println("============= HIGH SCORES =============");
        for (int i = 0; i < scores.size(); i++) {
            System.out.println((i+1) + ". " + scores.get(i).username + " | " + scores.get(i).date + " | " + scores.get(i).time + " s | | " + scores.get(i).tries + " tries");
        }
        System.out.println("=======================================");
    }


}
