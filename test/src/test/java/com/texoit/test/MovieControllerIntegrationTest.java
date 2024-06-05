package com.texoit.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.texoit.test.models.Movie;
import com.texoit.test.repositories.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @BeforeEach
    public void setUp() {
        movieRepository.deleteAll();

        movieRepository.saveAll(Arrays.asList(
                new Movie(null, "Movie 1", "Producer 1", "Studio 1", "yes", 2008),
                new Movie(null, "Movie 2", "Producer 1", "Studio 2", "yes", 2009),
                new Movie(null, "Movie 3", "Producer 2", "Studio 3", "yes", 2018),
                new Movie(null, "Movie 4", "Producer 2", "Studio 4", "yes", 2019),
                new Movie(null, "Movie 5", "Producer 1", "Studio 1", "yes", 1900),
                new Movie(null, "Movie 6", "Producer 1", "Studio 2", "yes", 1999),
                new Movie(null, "Movie 7", "Producer 2", "Studio 3", "yes", 2000),
                new Movie(null, "Movie 8", "Producer 2", "Studio 4", "yes", 2099)
        ));
    }


    @Test
    public void testGetWinnerIntervals() throws Exception {
        mockMvc.perform(get("/movies/winners-min-max"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.min").isArray())
                .andExpect(jsonPath("$.max").isArray())
                .andExpect(jsonPath("$.min[?(@.producer == 'Producer 1' && @.interval == 1 && @.previousWin == 2008 && @.followingWin == 2009)]").exists())
                .andExpect(jsonPath("$.min[?(@.producer == 'Producer 2' && @.interval == 1 && @.previousWin == 2018 && @.followingWin == 2019)]").exists())
                .andExpect(jsonPath("$.max[?(@.producer == 'Producer 1' && @.interval == 99 && @.previousWin == 1900 && @.followingWin == 1999)]").exists())
                ;
    }
}
