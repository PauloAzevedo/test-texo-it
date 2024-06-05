package com.texoit.test;

import com.texoit.test.models.Movie;
import com.texoit.test.models.dto.WinnerIntervalDTO;
import com.texoit.test.repositories.MovieRepository;
import com.texoit.test.services.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Arrays;

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
        public void testGetWinnerIntervals() {
            WinnerIntervalDTO intervals = movieService.getProducerIntervals();

            assertNotNull(intervals);
            assertEquals(2, intervals.getMin().size());
            assertEquals(1, intervals.getMax().size());


            assertTrue(intervals.getMin().stream().anyMatch(detail ->
                    detail.getProducer().equals("Producer 1") && detail.getInterval() == 1 && detail.getPreviousWin() == 2008 && detail.getFollowingWin() == 2009));

            assertTrue(intervals.getMin().stream().anyMatch(detail ->
                    detail.getProducer().equals("Producer 2") && detail.getInterval() == 1 && detail.getPreviousWin() == 2018 && detail.getFollowingWin() == 2019));


            assertTrue(intervals.getMax().stream().anyMatch(detail ->
                    detail.getProducer().equals("Producer 1") && detail.getInterval() == 99 && detail.getPreviousWin() == 1900 && detail.getFollowingWin() == 1999));

            assertFalse(intervals.getMax().stream().anyMatch(detail ->
                    detail.getProducer().equals("Producer 2") && detail.getInterval() == 99 && detail.getPreviousWin() == 2000 && detail.getFollowingWin() == 2099));
        }



}
