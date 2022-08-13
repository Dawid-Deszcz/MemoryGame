package com.company;

import java.util.List;
import java.util.Random;

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

    }
}
