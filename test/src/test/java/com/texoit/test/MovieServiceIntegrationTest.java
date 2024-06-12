package com.texoit.test;

import com.texoit.test.models.dto.WinnerIntervalDTO;
import com.texoit.test.repositories.MovieRepository;
import com.texoit.test.services.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(MovieService.class)
public class MovieServiceIntegrationTest {

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
    public void testGetWinnerIntervals() {
        WinnerIntervalDTO intervals = movieService.getProducerIntervals();
        assertNotNull(intervals);
        assertEquals(1, intervals.getMin().size());
        assertEquals(1, intervals.getMax().size());
        assertEquals(1, intervals.getMin().get(0).getInterval());
        assertEquals(13, intervals.getMax().get(0).getInterval());
    }

}
