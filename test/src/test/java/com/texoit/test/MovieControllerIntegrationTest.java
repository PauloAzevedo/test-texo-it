package com.texoit.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.texoit.test.models.Movie;
import com.texoit.test.models.dto.WinnerIntervalDTO;
import com.texoit.test.repositories.MovieRepository;
import com.texoit.test.services.MovieService;
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

    @Autowired
    private MovieService movieService;

    @BeforeEach
    public void setUp() {
        movieRepository.deleteAll();
        movieService.startLoadData();

    }


    @Test
    public void testGetWinnerIntervals() throws Exception {

        WinnerIntervalDTO producerIntervals = movieService.getProducerIntervals();
        final int minInterval = producerIntervals.getMin().stream().findFirst().get().getInterval();
        final int maxInterval = producerIntervals.getMax().stream().findFirst().get().getInterval();
        mockMvc.perform(get("/movies/winners-min-max"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.min").isArray())
                .andExpect(jsonPath("$.max").isArray())
                .andExpect(jsonPath("$.min[?(@.interval == " + minInterval + ")]").exists())
                .andExpect(jsonPath("$.max[?(@.interval == " + maxInterval + ")]").exists());
    }
}
