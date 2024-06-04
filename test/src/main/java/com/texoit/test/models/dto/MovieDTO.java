package com.texoit.test.models.dto;

import com.texoit.test.models.Movie;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class MovieDTO {
    private String title;

    private Integer year;

    private String studios;

    private String producers;

    private String winner;

    public MovieDTO(Movie movie){
        this.title= movie.getTitle();
        this.year = movie.getYear();
        this.producers = movie.getProducers();
        this.studios = movie.getStudios();
        this.winner = movie.getWinner();
    }

    public static List<MovieDTO> convert(List<Movie> movies) {
        return movies.stream().map(MovieDTO::new).collect(Collectors.toList());
    }


}
