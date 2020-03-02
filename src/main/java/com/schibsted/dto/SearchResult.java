package com.schibsted.dto;

import java.util.Objects;

public class SearchResult implements Comparable<SearchResult>{
    final private String fileName;
    final private float score;

    public SearchResult(String fileName, float score) {
        this.fileName = fileName;
        this.score = score;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(fileName).append(" : ").append(score).append(" %").toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchResult that = (SearchResult) o;
        return Float.compare(that.score, score) == 0 &&
                fileName.equals(that.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, score);
    }

    @Override
    public int compareTo(SearchResult o) {
        return Float.compare(this.score, o.score);
    }
}
