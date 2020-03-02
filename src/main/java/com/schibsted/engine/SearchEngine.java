package com.schibsted.engine;

import com.schibsted.dto.SearchResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchEngine {

    private final Path pathToDirectory;
    private final String [] phrases;


    public SearchEngine(Path pathToDirectory, String ...phrases){
        this.pathToDirectory = pathToDirectory;
        this.phrases = phrases;
    }

    public static long getNumberOfTextFilesIn(Path path){
        long numberOfFilesFound = 0;
        try {
            try(Stream<Path> files = Files.walk(path)){
                numberOfFilesFound = files.filter(Files::isRegularFile).map(Path::toString).filter(filePath -> filePath.endsWith("txt")).count();
            }
        } catch (IOException e) {
            System.out.println("Could not find the directory " + e);
            System.exit(0);
        }
        return numberOfFilesFound;
    }

    public List<SearchResult> scanFiles() {

        List<SearchResult> resultLines = Collections.emptyList();
        try(Stream<Path> files = Files.walk(pathToDirectory)){
            resultLines = files.filter(Files::isRegularFile).map(Path::toString).filter(filePath -> filePath.endsWith("txt"))
                    .map(filePath -> Paths.get(filePath)).map(this::searchFileIn).collect(Collectors.toList());

        }catch (IOException e){
            System.out.println("Could not find the directory " + e);
            System.exit(0);
        }
        return resultLines;
    }

    private SearchResult searchFileIn(Path path){

        int foundPhrases = 0;
        for(String phrase: phrases){
            try {
                BufferedReader bufferedReader = Files.newBufferedReader(path);
                String wordRegex = "\\b" + phrase.toLowerCase() + "\\b";
                boolean containsPhrase = bufferedReader.lines().map(word -> word.split(" ")).flatMap(Stream::of).anyMatch(line -> line.toLowerCase().matches(wordRegex));
                if(containsPhrase) foundPhrases++;
            } catch (IOException e) {
                System.out.println("Could not open the file " + e);
                System.exit(0);
            }
        }
        return new SearchResult(path.getFileName().toString(), calculatePercentage(phrases.length, foundPhrases));
    }

    private float calculatePercentage(int numberOfAllPhrases, int foundPhrases){
            float result = (float) foundPhrases / numberOfAllPhrases;
            return result * 100;
    }
}
