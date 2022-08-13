package com.company;

import java.util.Scanner;

public class GameMechanics {

    private static String level = "";
    private static final Scanner scanner = new Scanner(System.in);

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

}
