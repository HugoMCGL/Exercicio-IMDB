package br.com.letscode.java.omdbclient.rest;

import br.com.letscode.java.omdbclient.client.MovieMinimalRestRepository;
import br.com.letscode.java.omdbclient.client.ResultSearch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@RequestMapping("/search")
@RestController
public record SearchRestController(MovieMinimalRestRepository restRepository) {

    @GetMapping
    public ResultSearch search(@RequestParam String title) {

        try {
            BufferedWriter searchWriter = new BufferedWriter(new FileWriter("cache.csv", true));
            searchWriter.write(title);
            searchWriter.write(",");
            searchWriter.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return restRepository.search(title);
    }
}