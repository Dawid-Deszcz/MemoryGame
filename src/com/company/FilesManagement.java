package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FilesManagement {

    private final static String filePathDirectory = "Files/";

    public static List<String> readWordsFromFile(String fileName) {
        /*
         Reads database of elements which will be matched by the Memory Game player. The code is optimised for Strings
         elements, but it could be adjusted to add images instead of words if needed.
        */
        try {
            Path path = Paths.get(filePathDirectory + fileName);
            return  Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Error! Cannot open file");
            e.printStackTrace();
            return null;
        }
    }

    public static void printASCIIartFromFile(String fileName){
        //reading ASCII art from file
        Path path = Paths.get(filePathDirectory + fileName);

        try {
            List<String> lines = Files.readAllLines(path);

            // shows every line in the file
            for (String line : lines)
                System.out.println(line);
        }catch (IOException e) {
            System.out.println("Error! Cannot open file");
            e.printStackTrace();
        }
    }

    public static void saveResults(String username, int time, int tries, String level) {
        BufferedWriter output;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        //entry preparation
        String record = username + ";" + dtf.format(now) + ";" + time  + ";" + tries + "\n";

        try {
            //saving information in the file
            if (level.equals("easy"))
                output = new BufferedWriter(new FileWriter(filePathDirectory+"ScoresEasy.txt",true));
            else
                output = new BufferedWriter(new FileWriter(filePathDirectory+"ScoresHard.txt",true));
            output.write(record);
            output.close();
        }catch (IOException e) {
            System.out.println("Error! Cannot open file");
            e.printStackTrace();
        }
    }

}
