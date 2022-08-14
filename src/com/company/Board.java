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
        // array preparation for non-repetitive drawing
        boolean[] usedFirstLine = new boolean[words.size()];
        for (int i = 0; i < words.size(); i++) {
            usedFirstLine[i] = false;
        }
        // drawing elements from the file to the upper level/line of the board
        int indexFirstLine;
        for (int i = 0; i < numberOfWords; i++) {
            do {
                indexFirstLine = random.nextInt(0, words.size());

            }while (usedFirstLine[indexFirstLine]);
            matrix[0][i] = words.get(indexFirstLine);
            usedFirstLine[indexFirstLine] = true;
        }
        // 2a - lower part/line of the board
        // array preparation for non-repetitive drawing
        boolean[] used = new boolean[numberOfWords];
        for (int i = 0; i < numberOfWords; i++) {
            used[i] = false;
        }

        // 2b - upper part/line of the board
        // drawing elements to the lower level/line of the game
        int index;
        for (int i = 0; i < numberOfWords; i++) {
            do {
                index = random.nextInt(0, numberOfWords);
            }while (used [index]);
            matrix [1][i] = matrix[0][index];
            used[index]=true;

        }
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
        // I could potentially merge it into larger for loop where number of lines will be repeated - that might be a better code


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
        for (int i = 0; i < numberOfWords; i++) {
            if(selected[0][i])
                guessed[0][i] = true;
            if(selected[1][i])
                guessed[1][i] = true;
        }
    }

    public static void printHighScores (String fileType,String level) {
        List<Score> scores = new ArrayList<>();

        //reading the output from the file
        List<String> records = FilesManagement.readWordsFromFile(fileType);


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
        System.out.println();
        System.out.println("============= HIGH SCORES =============");
        if (level.equals("easy"))
            System.out.println("============= LEVEL EASY =============");
        else if (level.equals("hard"))
            System.out.println("============= LEVEL HARD =============");


        for (int i = 0; i < scores.size(); i++) {
            System.out.println((i+1) + ". " + scores.get(i).username + " | " + scores.get(i).date + " | " + scores.get(i).time + " s | " + scores.get(i).tries + " tries");
        }
        System.out.println("=======================================");
        System.out.println();
    }

}
