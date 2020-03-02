package com.schibsted.engine;

import com.schibsted.dto.SearchResult;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;

public class SearchEngineTest {

    @Test
    public void testIfCorrectNumberOfTextFilesIsFound(){
        //Given
        String pathTotTestDirectory = "src/test/java/resources/testDirectory";
        long expectedNumberOfFiles = 3;
        long actualNumberOFFilesFound = SearchEngine.getNumberOfTextFilesIn(Paths.get(pathTotTestDirectory));
        //Then
        Assert.assertEquals("Incorrect number of detected test files", expectedNumberOfFiles, actualNumberOFFilesFound);
    }


    @Test
    public void testPercentageCalc() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //Given
        String pathTotTestDirectory = "src/test/java/resources/testDirectory";
        SearchEngine searchEngine = new SearchEngine(Paths.get(pathTotTestDirectory));
        Method calculatePercentage = searchEngine.getClass().getDeclaredMethod("calculatePercentage", int.class, int.class);
        calculatePercentage.setAccessible(true);
        float actualResult = (float) calculatePercentage.invoke(searchEngine, 100, 90);
        //Then
        Assert.assertEquals(90, actualResult, 0.01);
    }

    @Test
    public void testSearchScoreForTestFile(){
        //Given
        SearchResult expectedSearchResult = new SearchResult("testTextFile.txt", 75.0f);
        String pathTotTestDirectory = "src/test/java/resources/testDirectory2";
        SearchEngine searchEngine = new SearchEngine(Paths.get(pathTotTestDirectory), "This", "abc" ,"abcd" ,"abcde");
        SearchResult actualSearchResult = searchEngine.scanFiles().get(0);
        //Then
        Assert.assertEquals(expectedSearchResult, actualSearchResult);

    }

}
