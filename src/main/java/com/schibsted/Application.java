package com.schibsted;

import com.schibsted.engine.SearchEngine;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        final String EXIT_COMMAND = ":quit";
        final int MAX_RESULTS_DISPLAYED = 10;

        String directory = args[0];

        if(checkIfDirectoryExists(directory)) {

            Scanner scanner = new Scanner(System.in);
            System.out.println(SearchEngine.getNumberOfTextFilesIn(Paths.get(directory)) +
                    " files read in directory " + directory);
            System.out.print("search> ");
            String phrases = scanner.nextLine();

            while (!phrases.equals(EXIT_COMMAND)) {
                if(phrases.isEmpty()){
                    System.out.println("Please provide non empty value");
                    System.out.print("search> ");
                    phrases = scanner.nextLine();
                }else {
                    SearchEngine searchEngine = new SearchEngine(Paths.get(directory), phrases.split(" "));
                    searchEngine.scanFiles().stream().sorted(Comparator.reverseOrder()).limit(MAX_RESULTS_DISPLAYED).forEach(System.out::println);
                    System.out.print("search> ");
                    phrases = scanner.nextLine();
                }
            }
        }else{
            System.out.println("Could not find directory with given path");
            System.exit(0);
        }
    }

    private static boolean checkIfDirectoryExists(String path){
        return Files.exists(Paths.get(path));
    }
}
