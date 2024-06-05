package com.texoit.test.controllers;

import com.texoit.test.models.Movie;
import com.texoit.test.models.dto.MovieDTO;
import com.texoit.test.models.dto.WinnerIntervalDTO;
import com.texoit.test.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<MovieDTO> getAll(){
        List<Movie> movies = movieService.getAll();
        return MovieDTO.convert(movies);
    }

    @GetMapping("/winners")
    public List<MovieDTO> getAllWinners(){
        List<Movie> movies = movieService.getAllWinners();
        return MovieDTO.convert(movies);
    }

    @GetMapping("/winners-min-max")
    public WinnerIntervalDTO getWinnersMinMax(){
        return movieService.getProducerIntervals();
    }

}
