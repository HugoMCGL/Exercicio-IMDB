package br.com.letscode.java.omdbclient.client;

import static java.lang.Boolean.valueOf;
import static java.lang.Integer.parseInt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public record ResultSearch(List<MovieMinimal> resultList, Integer total, Boolean response) {
    @JsonCreator
    public ResultSearch(
        @JsonProperty("Search") List<MovieMinimal> resultList,
        @JsonProperty("totalResults") String total,
        @JsonProperty("Response") String response) {
        this(resultList, parseInt(total), valueOf(response));

        try {
            BufferedWriter searchWriter = new BufferedWriter(new FileWriter("cache.csv", true));
            for(int i = 0; i < resultList.size(); i++) {
                searchWriter.write(resultList.get(i).imdbId());
                searchWriter.write(",");
                searchWriter.write(resultList.get(i).title());
                searchWriter.write(",");
                searchWriter.write(String.valueOf(resultList.get(i).year()));
                searchWriter.write(",");
            }

            searchWriter.write(total);
            searchWriter.write(",");
            searchWriter.write(response);
            searchWriter.newLine();
            searchWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}