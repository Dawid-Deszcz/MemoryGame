package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FilesManagment {

    private final static String filePathWords = "Files/Words.txt";

    public static List<String> readWordsFromFile() {
        /*
         Reads database of elements which will be matched by the Memory Game player. The code is optimised for Strings
         elements, but it could be adjusted to images if needed.
        */
        try {

            Path path = Paths.get(filePathWords);
            return  Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Error! Cannot open file" + filePathWords);
            e.printStackTrace();
            return null;
        }
    }

}
