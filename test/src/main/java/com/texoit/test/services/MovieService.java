package com.texoit.test.services;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.texoit.test.models.Movie;
import com.texoit.test.models.dto.WinnerDTO;
import com.texoit.test.models.dto.WinnerIntervalDTO;
import com.texoit.test.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public void startLoadData(){
        try {
            ClassPathResource resource = new ClassPathResource("movielist.csv");
            Reader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            CsvToBean<Movie> csvToBean = new CsvToBeanBuilder<Movie>(reader)
                    .withType(Movie.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSeparator(';')
                    .build();

            List<Movie> movies = csvToBean.parse();
            movieRepository.deleteAll();
            movieRepository.saveAll(movies);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Movie> getAll(){
        return movieRepository.findAll();
    }

    public List<Movie> getAllWinners(){
        return movieRepository.getAllWinners("yes");
    }

    public Map<String, List<Integer>> getProducerAwards() {
        List<Movie> winners = getAllWinners();
        Map<String, List<Integer>> producerAwards = new HashMap<>();

        for (Movie movie : winners) {
            String[] producers = movie.getProducers().split(",| and ");
            for (String producer : producers) {
                producer = producer.trim();
                if (!producerAwards.containsKey(producer)) {
                    producerAwards.put(producer, new ArrayList<>());
                }
                producerAwards.get(producer).add(movie.getYear());
            }
        }

        for (List<Integer> years : producerAwards.values()) {
            Collections.sort(years);
        }

        return producerAwards;
    }

    public WinnerIntervalDTO getProducerIntervals() {
        Map<String, List<Integer>> producerAwards = getProducerAwards();
        WinnerIntervalDTO result = new WinnerIntervalDTO();

        for (Map.Entry<String, List<Integer>> entry : producerAwards.entrySet()) {
            String producer = entry.getKey();
            List<Integer> years = entry.getValue();

            for (int i = 1; i < years.size(); i++) {
                int interval = years.get(i) - years.get(i - 1);
                WinnerDTO detail = new WinnerDTO(producer, interval, years.get(i - 1), years.get(i));

                if (interval == result.getMax().stream().mapToInt(WinnerDTO::getInterval).max().orElse(Integer.MIN_VALUE)) {
                    result.getMax().add(detail);
                } else if (interval > result.getMax().stream().mapToInt(WinnerDTO::getInterval).max().orElse(Integer.MIN_VALUE)) {
                    result.getMax().clear();
                    result.getMax().add(detail);
                }

                if (interval == result.getMin().stream().mapToInt(WinnerDTO::getInterval).min().orElse(Integer.MAX_VALUE)) {
                    result.getMin().add(detail);
                } else if (interval < result.getMin().stream().mapToInt(WinnerDTO::getInterval).min().orElse(Integer.MAX_VALUE)) {
                    result.getMin().clear();
                    result.getMin().add(detail);
                }
            }
        }

        return result;
    }
}
